package com.bernatangg.testmkm.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.bernatangg.testmkm.model.Login;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    //logcat tag
    private static final String LOG = "DBHelper";

    //database version
    private static final int DATABASE_VERSION = 1;

    //database name
    private static final String DATABASE_NAME = "testDB";

    //table name
    private static final String TABLE_LOGIN = "loginTbl";

    //common column names
    private static final String KEY_ID = "id";

    //login table field
    private static final String KEY_USERNAME = "username";
    private static final String KEY_SESSION = "session";

    //table login create statement
    private static final String CREATE_TABLE_LOGIN = "CREATE TABLE "
            + TABLE_LOGIN + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_USERNAME + " TEXT,"
            + KEY_SESSION + " TEXT"
            + ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_LOGIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS loginTbl");
        onCreate(sqLiteDatabase);
    }

    public void addLoginResponse(Login login) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, login.getSession());
        values.put(KEY_SESSION, login.getSession());
        db.insert(TABLE_LOGIN, null, values);
        db.close();
    }

    public int getLoginCount() {
        String countQuery = "SELECT * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public void deleteLogin() {
        int id = 1;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOGIN, KEY_ID + " = ? ",
                new String[] {String.valueOf(id)});
        db.close();
    }

    public void checkLoginTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS loginTbl");
        onCreate(db);
    }

    public Login getLoginResponse() {
        int id = 1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LOGIN, new String[] {
                        KEY_ID, KEY_USERNAME, KEY_SESSION},
                KEY_ID + "=?", new String[] {String.valueOf(id)}, null, null,
                null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            Login response = new Login(
                    cursor.getString(1),
                    cursor.getString(2));

            return response;
        }

        return null;
    }
}
