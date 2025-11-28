package com.example.midtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

    View startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        startBtn = findViewById(R.id.startBtn);
        checkSession();

        // Nếu session hết -> ở lại Intro -> chờ nhấn nút "Bắt đầu"
        startBtn.setOnClickListener(v -> goNext());
    }

    private void checkSession() {
        SharedPreferences prefs = getSharedPreferences("USER_FILE", MODE_PRIVATE);

        boolean logged = prefs.getBoolean("IS_LOGGED_IN", false);
        long loginTime = prefs.getLong("LOGIN_TIME", 0);

        long now = System.currentTimeMillis();
        long EXPIRE = 60 * 1000; // 1 phút

        // Nếu session còn hạn → chuyển sang Main luôn
        if (logged && (now - loginTime) < EXPIRE) {
            Intent i = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void goNext() {
        SharedPreferences prefs = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        boolean logged = prefs.getBoolean("IS_LOGGED_IN", false);
        long loginTime = prefs.getLong("LOGIN_TIME", 0);
        long now = System.currentTimeMillis();
        long EXPIRE = 60 * 1000;

        // Nếu bấm nút Bắt đầu khi session còn hạn → Main
        if (logged && (now - loginTime) < EXPIRE) {
            startActivity(new Intent(this, MainActivity.class));
        }
        // Nếu không thì chuyển login
        else {
            startActivity(new Intent(this, LoginActivity.class));
        }

        finish();
    }
}
