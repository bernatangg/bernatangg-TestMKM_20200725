package com.bernatangg.testmkm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bernatangg.testmkm.AppView;
import com.bernatangg.testmkm.R;
import com.bernatangg.testmkm.api.ApiClient;
import com.bernatangg.testmkm.api.ApiService;
import com.bernatangg.testmkm.helper.DBHelper;
import com.bernatangg.testmkm.model.Login;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements AppView {

    EditText etUsername, etPassword;
    Button btnRegister, btnLogin;
    String username, password;
    RelativeLayout layoutForm, layoutPb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username_login);
        etPassword = findViewById(R.id.et_password_login);

        btnRegister = findViewById(R.id.btn_login_to_regis);
        btnLogin = findViewById(R.id.btn_login);

        layoutForm = findViewById(R.id.layout_form_login);
        layoutPb = findViewById(R.id.layout_pb_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                validate();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

    }

    private void validate() {

        username = etUsername.getText().toString();
        password = etPassword.getText().toString();

        if (username.isEmpty()) {
            hideLoading();
            etUsername.setError("Isikan username dengan benar");
        } else {
            etUsername.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            hideLoading();
            etPassword.setError("Isikan kata sandi dengan benar");
        } else {
            etPassword.setError(null);
        }

        if (!username.isEmpty() && !password.isEmpty()) {
            login(username, password);
        }
    }

    private void login(String username, String password) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<Login> sendData = service.postLogin(username, password);
        sendData.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()) {
                    hideLoading();
                    Login loginResponse = response.body();
                    DBHelper dbHelper = new DBHelper(getApplicationContext());
                    dbHelper.checkLoginTable();
                    dbHelper.addLoginResponse(loginResponse);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    hideLoading();
                    showToast("GAGAL LOGIN");
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                hideLoading();
                t.printStackTrace();
                showToast("CEK KONEKSI INTERNET");
            }
        });

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        layoutPb.setVisibility(View.VISIBLE);
        layoutForm.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        layoutPb.setVisibility(View.GONE);
        layoutForm.setVisibility(View.VISIBLE);

    }
}
