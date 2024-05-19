package com.example.appthoitrang.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.example.appthoitrang.Domain.AccountDomain;
import com.example.appthoitrang.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends BaseActivity {
    private EditText eUsername, eEmail, eAddress, ePassword, ePasswordConfirm;
    private AppCompatButton registerAccountBtn, cancelRegisterBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        registerAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference accountRef = database.getReference("Account");
                // Lấy dữ liệu từ editText
                AccountDomain accountDomain=new AccountDomain(eUsername.getText().toString(),
                        ePassword.getText().toString(), eAddress.getText().toString(),
                        eEmail.getText().toString(),0);
                // Kiểm tra username đã tồn tại trong firebase hay chưa
                accountRef.orderByChild("username").equalTo(accountDomain.getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Nếu username đã tồn tại, hiển thị thông báo lỗi
                            Toast.makeText(RegisterActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            // Nếu username chưa tồn tại, thêm tài khoản mới vào cơ sở dữ liệu
                            String key = accountRef.push().getKey();
                            accountRef.child(key).setValue(accountDomain);
                            Toast.makeText(RegisterActivity.this, "Account added successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(RegisterActivity.this, "Account added failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        cancelRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        eUsername=findViewById(R.id.eUsernameRegister);
        eEmail=findViewById(R.id.eEmail);
        eAddress=findViewById(R.id.eAddress);
        ePassword=findViewById(R.id.ePasswordRegister);
        ePasswordConfirm=findViewById(R.id.ePasswordConfirm);
        registerAccountBtn=findViewById(R.id.registerAccountBtn);
        cancelRegisterBtn=findViewById(R.id.cancelRegisterBtn);
    }
}