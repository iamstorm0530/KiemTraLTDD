package com.example.midtest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    EditText edtUser, edtName, edtEmail, edtPass, edtConfirm;
    ImageButton btnSend;

    APIMain apiMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtUser = findViewById(R.id.editTextUsername);
        edtName = findViewById(R.id.editTextName);
        edtEmail = findViewById(R.id.editTextEmail);
        edtPass = findViewById(R.id.editTextPassword);
        edtConfirm = findViewById(R.id.editConfirmPassword);
        btnSend = findViewById(R.id.imageBlueButton);

        apiMain = RetroClientMain.getClient().create(APIMain.class);

        btnSend.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String username = edtUser.getText().toString().trim();
        String name = edtName.getText().toString().trim();
        String fullname = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
        String confirm = edtConfirm.getText().toString().trim();

        if (username.isEmpty() || fullname.isEmpty() || email.isEmpty() ||
                pass.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pass.equals(confirm)) {
            Toast.makeText(this, "Mật khẩu nhập lại không khớp!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy danh sách user để xác định ID mới
        apiMain.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(SignupActivity.this, "Lỗi server!", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<User> list = response.body();

                // ID mới = số lượng user + 1
                int newId = list.size() + 1;

                User newUser = new User(
                        String.valueOf(newId),
                        name,
                        username,
                        pass,
                        email,
                        "https://i.ibb.co/8N6B8QP/avatar-clone.png"
                );

                // CALL API POST
                apiMain.registerUser(newUser).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        if (!response.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Thành công → CHUYỂN SANG TRANG OTP
                        Intent i = new Intent(SignupActivity.this, OtpActivity.class);
                        i.putExtra("email", email);
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(SignupActivity.this, "Không thể kết nối server!", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "Không thể kết nối API!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
