package com.example.appthoitrang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.example.appthoitrang.Domain.AccountDomain;
import com.example.appthoitrang.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends BaseActivity {
    private EditText eUsername, ePassword;
    private AppCompatButton loginBtn;
    private TextView resetPassword, registerAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intView();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=eUsername.getText().toString();
                String passWord=ePassword.getText().toString();
                DatabaseReference metaData=database.getReference("Account");
                ArrayList<AccountDomain> accountLists=new ArrayList<>();
                metaData.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot issue : snapshot.getChildren()) {
                                accountLists.add(issue.getValue(AccountDomain.class));
                            }
                            if (userName.isEmpty() || passWord.isEmpty()){
                                Toast.makeText(LoginActivity.this, "All Fileds Required", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                StringBuilder text= new StringBuilder();
                                int dem=0;
                                for (AccountDomain i:accountLists){
                                    if (i.getUsername().equals(userName) && i.getPassword().equals(passWord) && i.getIsAdmin()==1){
                                        Toast.makeText(LoginActivity.this, "Login Admin Successful", Toast.LENGTH_SHORT).show();
                                        Intent intentAdmin=new Intent(getApplicationContext(),AdminActivity.class);
                                        intentAdmin.putExtra("admin",new AccountDomain(userName,passWord,i.getAddress(),i.getEmail(),i.getIsAdmin()));
                                        startActivity(intentAdmin);
                                        dem=1;
                                        break;
                                    } else if (i.getUsername().equals(userName) && i.getPassword().equals(passWord) && i.getIsAdmin()==0) {
                                        Toast.makeText(LoginActivity.this, "Login Client Successful", Toast.LENGTH_SHORT).show();
                                        Intent intentClient=new Intent(getApplicationContext(),MainActivity.class);
                                        intentClient.putExtra("client",new AccountDomain(userName,passWord,i.getAddress(),i.getEmail(),i.getIsAdmin()));
                                        startActivity(intentClient);
                                        dem=1;
                                        break;
                                    }
                                }
                                if (dem==0){
                                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
        registerAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void intView() {
        eUsername=findViewById(R.id.eUsername);
        ePassword=findViewById(R.id.ePassword);
        loginBtn=findViewById(R.id.loginBtn);
        resetPassword =findViewById(R.id.resetPassword);
        registerAccount=findViewById(R.id.registerAccount);
    }
}