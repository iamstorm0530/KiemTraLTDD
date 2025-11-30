//DangNgocThaoQuyen-23162082


package com.example.midtest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.regex.Pattern;

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
    public void goOtp(android.view.View v) {
        Toast.makeText(SignupActivity.this, "CLICK!", Toast.LENGTH_SHORT).show();
        registerUser();
    }


    private void registerUser() {

        String username = edtUser.getText().toString().trim();
        String fullname = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
        String confirm = edtConfirm.getText().toString().trim();

        // Validate rỗng
        if (username.isEmpty() || fullname.isEmpty() || email.isEmpty() ||
                pass.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate email
        if (!Pattern.matches(".+@.+\\..+", email)) {
            Toast.makeText(this, "Email không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check pass confirm
        if (!pass.equals(confirm)) {
            Toast.makeText(this, "Mật khẩu nhập lại không khớp!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy danh sách user để tạo ID mới
        apiMain.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(SignupActivity.this, "Lỗi server!", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<User> list = response.body();
                int newId = (list == null ? 1 : list.size() + 1);

                User newUser = new User(
                        String.valueOf(newId),
                        fullname,
                        username,
                        pass,
                        email,
                        "https://i.ibb.co/8N6B8QP/avatar-clone.png"
                );

                // Đăng ký user
                apiMain.registerUser(newUser).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        if (!response.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Intent i = new Intent(SignupActivity.this, OtpActivity.class);
                        startActivity(i);
                        Log.d("TEST_INTENT", "OTP Intent started");


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
