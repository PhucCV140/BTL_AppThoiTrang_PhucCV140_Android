package com.example.appthoitrang.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.example.appthoitrang.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ResetPasswordActivity extends BaseActivity {
    private EditText userReset, passReset, PassConfirmReset, passResetNew;
    private AppCompatButton resetBtn, cancelResetBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        intView();
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference accountRef = database.getReference("Account");
                // Tìm đối tượng người dùng có username như đã nhập
                Query query = accountRef.orderByChild("username").equalTo(userReset.getText().toString());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot issue : snapshot.getChildren()) {
                            String currentPassword = issue.child("password").getValue(String.class);
                            if (currentPassword.equals(passReset.getText().toString()) && passResetNew.getText().toString().equals(PassConfirmReset.getText().toString())) {
                                // Nếu mật khẩu hiện tại nhập đúng, tiến hành cập nhật mật khẩu mới
                                issue.getRef().child("password").setValue(passResetNew.getText().toString());
                                Toast.makeText(ResetPasswordActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                // Nếu mật khẩu hiện tại nhập không đúng, hiển thị thông báo lỗi
                                Toast.makeText(ResetPasswordActivity.this, "Current password is incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ResetPasswordActivity.this, "Failed to change password", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        cancelResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void intView() {
        userReset=findViewById(R.id.userReset);
        passReset=findViewById(R.id.passReset);
        passResetNew=findViewById(R.id.passResetNew);
        PassConfirmReset=findViewById(R.id.PassConfirmReset);
        resetBtn=findViewById(R.id.resetBtn);
        cancelResetBtn=findViewById(R.id.cancelResetBtn);
    }
}