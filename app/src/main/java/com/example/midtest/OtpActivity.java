package com.example.midtest;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OtpActivity extends AppCompatActivity {

    EditText et1, et2, et3, et4, et5, et6;
    Button btnVerify;
    ImageButton btnClose;
    TextView tvResend;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        // Ánh xạ
        et1 = findViewById(R.id.etCode1);
        et2 = findViewById(R.id.etCode2);
        et3 = findViewById(R.id.etCode3);
        et4 = findViewById(R.id.etCode4);
        et5 = findViewById(R.id.etCode5);
        et6 = findViewById(R.id.etCode6);

        btnVerify = findViewById(R.id.btnVerifyCode);
        btnClose = findViewById(R.id.btnClose);
        tvResend = findViewById(R.id.tvResend);

        // Auto move
        setupOtpMove();

        // Countdown 90s
        startTimer();

        // Nút Verify
        btnVerify.setOnClickListener(v -> {
            if (validateOTP()) {
                Toast.makeText(this, "Xác thực thành công!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(OtpActivity.this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Vui lòng nhập đủ 6 số", Toast.LENGTH_SHORT).show();
            }
        });

        // Close → quay lại Login
        btnClose.setOnClickListener(v -> {
            startActivity(new Intent(OtpActivity.this, LoginActivity.class));
            finish();
        });
    }

    private boolean validateOTP() {
        return et1.length() == 1 &&
                et2.length() == 1 &&
                et3.length() == 1 &&
                et4.length() == 1 &&
                et5.length() == 1 &&
                et6.length() == 1;
    }

    private void setupOtpMove() {
        TextWatcher move1 = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et1.length() == 1) et2.requestFocus();
            }
            @Override public void afterTextChanged(Editable s) {}
        };

        TextWatcher move2 = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et2.length() == 1) et3.requestFocus();
            }
            @Override public void afterTextChanged(Editable s) {}
        };

        TextWatcher move3 = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et3.length() == 1) et4.requestFocus();
            }
            @Override public void afterTextChanged(Editable s) {}
        };

        TextWatcher move4 = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et4.length() == 1) et5.requestFocus();
            }
            @Override public void afterTextChanged(Editable s) {}
        };

        TextWatcher move5 = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et5.length() == 1) et6.requestFocus();
            }
            @Override public void afterTextChanged(Editable s) {}
        };

        et1.addTextChangedListener(move1);
        et2.addTextChangedListener(move2);
        et3.addTextChangedListener(move3);
        et4.addTextChangedListener(move4);
        et5.addTextChangedListener(move5);
    }

    private void startTimer() {
        timer = new CountDownTimer(90_000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long sec = millisUntilFinished / 1000;
                tvResend.setText("Resend Code in " + sec + " s");
            }

            @Override
            public void onFinish() {
                tvResend.setText("Resend Code");
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) timer.cancel();
    }
}
