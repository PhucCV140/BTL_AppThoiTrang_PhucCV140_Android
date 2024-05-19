package com.example.appthoitrang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.appthoitrang.Domain.AccountDomain;
import com.example.appthoitrang.R;

public class ProfileActivity extends AppCompatActivity {
    private TextView usernameTxt, addressTxt, emailTxt;
    private AppCompatButton resetProfileBtn, logoutBtn;
    private ImageView backBtn;
    private AccountDomain accountDomain;
    private final int REQUEST_CODE_RESET_PASSWORD=123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
        Intent i=getIntent();
        accountDomain= (AccountDomain) i.getSerializableExtra("client");
        usernameTxt.setText(accountDomain.getUsername());
        addressTxt.setText(accountDomain.getAddress());
        emailTxt.setText(accountDomain.getEmail());
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        resetProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this,ResetPasswordActivity.class);
                startActivityForResult(intent,REQUEST_CODE_RESET_PASSWORD);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        usernameTxt=findViewById(R.id.usernameTxt);
        addressTxt=findViewById(R.id.addressTxt);
        emailTxt=findViewById(R.id.emailTxt);
        resetProfileBtn =findViewById(R.id.resetProfileBtn);
        logoutBtn=findViewById(R.id.logoutBtn);
        backBtn=findViewById(R.id.backResetPasswordBtn);
    }
}