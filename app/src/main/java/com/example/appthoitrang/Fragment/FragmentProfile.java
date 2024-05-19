package com.example.appthoitrang.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.appthoitrang.Activity.AdminActivity;
import com.example.appthoitrang.Activity.LoginActivity;
import com.example.appthoitrang.Activity.ResetPasswordActivity;
import com.example.appthoitrang.R;

public class FragmentProfile extends Fragment {
    private TextView usernameAdTxt, addressAdTxt, emailAdTxt;
    private AppCompatButton resetProfileAdBtn, logoutAdBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intView(view);
        resetProfileAdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
        logoutAdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void intView(View view) {
        usernameAdTxt=view.findViewById(R.id.usernameAdTxt);
        addressAdTxt=view.findViewById(R.id.addressAdTxt);
        emailAdTxt=view.findViewById(R.id.emailAdTxt);
        resetProfileAdBtn=view.findViewById(R.id.resetProfileAdBtn);
        logoutAdBtn=view.findViewById(R.id.logoutAdBtn);

        usernameAdTxt.setText(AdminActivity.accountDomainAdmin.getUsername());
        addressAdTxt.setText(AdminActivity.accountDomainAdmin.getAddress());
        emailAdTxt.setText(AdminActivity.accountDomainAdmin.getEmail());
    }
}
