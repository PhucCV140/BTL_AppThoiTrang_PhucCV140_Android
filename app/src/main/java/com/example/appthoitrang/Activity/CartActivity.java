package com.example.appthoitrang.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appthoitrang.Adapter.CartAdapter;
import com.example.appthoitrang.Domain.AccountDomain;
import com.example.appthoitrang.Domain.ItemsDomain;
import com.example.appthoitrang.Helper.ChangeNumberItemsListener;
import com.example.appthoitrang.Helper.ManagmentCart;
import com.example.appthoitrang.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends BaseActivity {
    private double tax;
    private TextView emptyTxt, totalFeeTxt, taxTxt, deliveryTxt, totalTxt;
    private ScrollView scrollViewCart;
    private RecyclerView cartView;
    private ImageView backBtn;
    private ManagmentCart managmentCart;
    private AppCompatButton checkOutBtn;
    private AccountDomain accountDomain;
    private static final String TAG = "tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();
        Intent i=getIntent();
        accountDomain= (AccountDomain) i.getSerializableExtra("client");
        managmentCart=new ManagmentCart(this);
        caculatorCart();
        setVarialbe();
        initCartList();
        checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                DatabaseReference metaData= database.getReference("Purchased");
                List<Map<String, Object>> purchasedItems = new ArrayList<>();
                for (ItemsDomain i:managmentCart.getListCart()){
                    Map<String, Object> purchasedItem=new HashMap<>();
                    // Lấy ngày hiện tại
                    LocalDate currentDate = LocalDate.now();
                    // Định dạng ngày thành chuỗi
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String dateString = currentDate.format(formatter);
                    purchasedItem.put("username", accountDomain.getUsername());
                    purchasedItem.put("title", i.getTitle());
                    purchasedItem.put("price", i.getPrice());
                    purchasedItem.put("quantity", i.getNumberinCart());
                    ArrayList<String> img=new ArrayList<>();
                    img.add(i.getPicUrl().get(0));
                    purchasedItem.put("picUrl", img);
                    purchasedItem.put("date",dateString);
                    purchasedItems.add(purchasedItem);
                }
                for (Map<String, Object> purchasedItem : purchasedItems) {
                    metaData.push().setValue(purchasedItem)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "Purchase was successful");
                            Toast.makeText(CartActivity.this, "Purchase was successful", Toast.LENGTH_SHORT).show();
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CartActivity.this, "Purchase failed", Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "Purchase failed", e);
                        }
                    });
                    managmentCart.clearCartList();
                    emptyTxt.setVisibility(View.VISIBLE);
                    scrollViewCart.setVisibility(View.GONE);
                }
            }
        });
    }
    private void initCartList() {
        if (managmentCart.getListCart().isEmpty()){
            emptyTxt.setVisibility(View.VISIBLE);
            scrollViewCart.setVisibility(View.GONE);
        }else {
            emptyTxt.setVisibility(View.GONE);
            scrollViewCart.setVisibility(View.VISIBLE);
        }
        cartView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        cartView.setAdapter(new CartAdapter(managmentCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                caculatorCart();
            }
        }));
    }
    private void setVarialbe() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void caculatorCart() {
        double percentTax=0.02;
        double delivery=10;
        tax=Math.round(managmentCart.getTotalFee()*percentTax*100.0)/100.0;
        double total=Math.round((managmentCart.getTotalFee()+tax+delivery)*100)/100;
        double itemTotal=Math.round(managmentCart.getTotalFee()*100)/100;
        totalFeeTxt.setText("$"+itemTotal);
        taxTxt.setText("$"+tax);
        deliveryTxt.setText("$"+delivery);
        totalTxt.setText("$"+total);
    }
    private void initView() {
        emptyTxt=findViewById(R.id.emptyPurchasedTxt);
        totalFeeTxt=findViewById(R.id.totalFeeTxt);
        taxTxt=findViewById(R.id.taxTxt);
        deliveryTxt=findViewById(R.id.deliveryTxt);
        totalTxt=findViewById(R.id.totalTxt);
        scrollViewCart=findViewById(R.id.scrollViewCart);
        cartView=findViewById(R.id.cartView);
        backBtn=findViewById(R.id.backResetPasswordBtn);
        checkOutBtn=findViewById(R.id.checkOutBtn);
    }
}