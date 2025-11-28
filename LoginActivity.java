//DangNgocThaoQuyen-23162082


package com.example.midtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtPass;
    ImageButton btnLogin;
    APIMain apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtUsername);
        edtPass = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        apiService = RetroClientMain.getClient().create(APIMain.class);

        btnLogin.setOnClickListener(view -> login());
        TextView textRegister = findViewById(R.id.textRegister);

        textRegister.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(i);
        });

    }

    private void login() {
        String email_username = edtEmail.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();

        if (email_username.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Lỗi API!", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<User> list = response.body();
                boolean ok = false;

                for (User u : list) {
                    if ((u.getEmail().equals(email_username) && u.getPassword().equals(pass))
                            || (u.getUsername().equals(email_username) && u.getPassword().equals(pass))) {

                        ok = true;

                        // 👉 Lưu thông tin user để MainActivity đọc lại
                        SharedPreferences prefs = getSharedPreferences("USER_FILE", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();

                        editor.putBoolean("IS_LOGGED_IN", true);
                        editor.putLong("LOGIN_TIME", System.currentTimeMillis());
                        editor.putString("USER_ID", u.getId());
                        editor.putString("USERNAME", u.getUsername());  // 👉 TÊN SẼ HIỆN Ở MAIN
                        editor.putString("EMAIL", u.getEmail());
                        editor.putString("AVATAR", u.getAvatar());
                        editor.apply();

                        // Mở MainActivity
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                        return;
                    }
                }

                if (!ok) {
                    Toast.makeText(LoginActivity.this, "Sai email hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Không thể kết nối server!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

