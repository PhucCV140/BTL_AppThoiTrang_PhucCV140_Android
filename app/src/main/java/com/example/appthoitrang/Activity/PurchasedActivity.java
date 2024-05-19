package com.example.appthoitrang.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appthoitrang.Adapter.PurchasedAdapter;
import com.example.appthoitrang.Domain.AccountDomain;
import com.example.appthoitrang.Domain.PurchasedDomain;
import com.example.appthoitrang.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PurchasedActivity extends BaseActivity implements PurchasedAdapter.PurchasedListener{
    private ScrollView scrollViewPurchased;
    private RecyclerView purchasedView;
    private PurchasedAdapter purchasedAdapter;
    private ImageView backBtn;
    private TextView emptyTxt;
    private AccountDomain accountDomain;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchased);
        scrollViewPurchased=findViewById(R.id.scrollViewPurchased);
        purchasedView=findViewById(R.id.purchasedView);
        backBtn=findViewById(R.id.backResetPasswordBtn);
        emptyTxt=findViewById(R.id.emptyTxt);
        Intent i=getIntent();
        accountDomain= (AccountDomain) i.getSerializableExtra("client");
        purchasedAdapter=new PurchasedAdapter();
        initPurchassed();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //purchasedAdapter.setPurchasedListener(this::onPurchasedClick);
    }

    private void initPurchassed() {
        DatabaseReference metaData=database.getReference("Purchased");
        List<PurchasedDomain> purchasedDomains=new ArrayList<>();
        ArrayList<PurchasedDomain> purchasedDomains2=new ArrayList<>();
        metaData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue:snapshot.getChildren()){
                        PurchasedDomain purchasedDomain=issue.getValue(PurchasedDomain.class);
                        purchasedDomains.add(purchasedDomain);
                    }
                    purchasedView.setLayoutManager(new LinearLayoutManager(PurchasedActivity.this,LinearLayoutManager.VERTICAL,false));
                    for (PurchasedDomain i:purchasedDomains){
                        if (accountDomain.getUsername().equals(i.getUsername())){
                            purchasedDomains2.add(i);
                        }
                    }
                    if (purchasedDomains2.isEmpty()){
                        emptyTxt.setVisibility(View.VISIBLE);
                        scrollViewPurchased.setVisibility(View.GONE);
                    }else {
                        emptyTxt.setVisibility(View.GONE);
                        scrollViewPurchased.setVisibility(View.VISIBLE);
                    }
                    purchasedAdapter.setList(purchasedDomains2);
                    purchasedView.setAdapter(purchasedAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onPurchasedClick(View view, int position) {

    }
}