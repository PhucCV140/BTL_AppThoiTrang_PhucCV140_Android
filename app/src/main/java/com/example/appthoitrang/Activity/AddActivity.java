package com.example.appthoitrang.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.appthoitrang.Domain.ItemsDomain;
import com.example.appthoitrang.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {
    private EditText addTitle, addDescription, addOldPrice, addPrice, addPicture;
    private Spinner addSpiner;
    private AppCompatButton addBtn, cancelBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference category = database.getReference("Category");
        ArrayList<String> list=new ArrayList<>();
        category.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String title = dataSnapshot.child("title").getValue(String.class);
                        list.add(title);
                    }
                    addSpiner.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.item_spinner,list));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference itemsRef = database.getReference("Items");
                itemsRef.orderByChild("title").equalTo(addTitle.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Title đã tồn tại trong danh sách, không thêm mục mới
                            Toast.makeText(AddActivity.this, "Title already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            ItemsDomain itemsDomain=new ItemsDomain();
                            itemsDomain.setTitle(addTitle.getText().toString());
                            itemsDomain.setDescription(addDescription.getText().toString());
                            itemsDomain.setOldPrice(Double.parseDouble(addOldPrice.getText().toString()));
                            itemsDomain.setPrice(Double.parseDouble(addPrice.getText().toString()));
                            ArrayList<String> picUrl=new ArrayList<>();
                            picUrl.add(addPicture.getText().toString());
                            itemsDomain.setPicUrl(picUrl);
                            itemsDomain.setCategory(addSpiner.getSelectedItem().toString());
                            itemsDomain.setRating(5);
                            itemsDomain.setReview(0);
                            itemsRef.push().setValue(itemsDomain);
                            Toast.makeText(AddActivity.this, "Add Product Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView(){
        addTitle=findViewById(R.id.addTitle);
        addDescription=findViewById(R.id.addDescription);
        addOldPrice=findViewById(R.id.addOldPrice);
        addPrice=findViewById(R.id.addPrice);
        addPicture=findViewById(R.id.addPicture);
        addSpiner=findViewById(R.id.addSpiner);
        addBtn=findViewById(R.id.addBtn);
        cancelBtn=findViewById(R.id.cancelBtn);
    }
}