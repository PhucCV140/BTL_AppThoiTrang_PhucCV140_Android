package com.example.appthoitrang.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appthoitrang.Adapter.ProductClientAdapter;
import com.example.appthoitrang.Domain.PurchasedDomain;
import com.example.appthoitrang.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductClientActivity extends AppCompatActivity {
    private RecyclerView productClientView;
    private ProductClientAdapter adapter;
    private ImageView backClientBtn;
    private TextView emptyClient;
    private ScrollView scrollViewClient;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_client);
        Intent intent=getIntent();
        String username=intent.getStringExtra("username");
        productClientView=findViewById(R.id.productClientView);
        backClientBtn=findViewById(R.id.backClientBtn);
        emptyClient =findViewById(R.id.emptyClient);
        scrollViewClient=findViewById(R.id.scrollViewClient);
        adapter=new ProductClientAdapter();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
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
                    productClientView.setLayoutManager(new LinearLayoutManager(ProductClientActivity.this,LinearLayoutManager.VERTICAL,false));
                    for (PurchasedDomain i:purchasedDomains){
                        if (username.equals(i.getUsername())){
                            purchasedDomains2.add(i);
                        }
                    }
                    if (purchasedDomains2.isEmpty()){
                        emptyClient.setVisibility(View.VISIBLE);
                        scrollViewClient.setVisibility(View.GONE);
                    }else {
                        emptyClient.setVisibility(View.GONE);
                        scrollViewClient.setVisibility(View.VISIBLE);
                    }
                    adapter.setList(purchasedDomains2);
                    productClientView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        backClientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}