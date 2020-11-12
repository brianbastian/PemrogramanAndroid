package com.example.helloworld;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static android.text.Html.fromHtml;

public class LoginPage extends AppCompatActivity {
    private static final String TAG = LoginPage.class.getSimpleName();
    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;
    private Database database;
    private Preferences sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        database = new Database(this);
        sharedPrefManager = new Preferences(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickBtnLogin();
            }
        });
        TextView textViewCreateAccount = (TextView) findViewById(R.id.textViewCreateAccount);
        textViewCreateAccount.setText(fromHtml("<font color='#ffffff'>Belum punya akun? </font><font color='#0c0099'>daftar sekarang!</font>"));
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        if (sharedPrefManager.getSPSudahLogin()){
            startActivity(new Intent(LoginPage.this, HomePage.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }

    private void onClickBtnLogin() {
        String username = txtUsername.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        Boolean res = database.checkUser(username,password);
        if(res == true){
            Toast.makeText(LoginPage.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginPage.this, HomePage.class));
            sharedPrefManager.saveSPBoolean(Preferences.SP_SUDAH_LOGIN, true);
            finish();
        }else {
            Toast.makeText(LoginPage.this, "Login Gagal", Toast.LENGTH_SHORT).show();
        }
    }
    public static Spanned fromHtml(String html){
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        }else {
            result = Html.fromHtml(html);
        }
        return result;
    }
}