package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {
    private static final String TAG = LoginPage.class.getSimpleName();
    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickBtnLogin();
            }
        });
    }

    private void onClickBtnLogin() {
        if (txtPassword.getText().toString().equals("admin") && txtUsername.getText().toString().equals("admin")) {
            Intent i = new Intent(LoginPage.this, HomePage.class);
            Toast.makeText(getApplicationContext(), "Selamat Datang " + txtUsername.getText(), Toast.LENGTH_LONG).show();
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(), "Maaf tidak ada " + txtUsername.getText() + " dalam sistem", Toast.LENGTH_LONG).show();
        }
    }
}