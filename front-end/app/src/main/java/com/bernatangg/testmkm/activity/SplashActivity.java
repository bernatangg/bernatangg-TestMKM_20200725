package com.bernatangg.testmkm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.bernatangg.testmkm.R;
import com.bernatangg.testmkm.helper.DBHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLoginData();
            }
        },3000);
    }

    private void checkLoginData() {
        DBHelper db = new DBHelper(this);
        if (db.getLoginCount() != 0) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else {
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

    }
}
