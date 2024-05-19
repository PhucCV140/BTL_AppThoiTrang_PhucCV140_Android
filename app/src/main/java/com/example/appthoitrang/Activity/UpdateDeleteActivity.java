package com.example.appthoitrang.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpdateDeleteActivity extends AppCompatActivity {
    private EditText updateDeleteTitle, updateDeleteDescription, updateDeleteOldPrice, updateDeletePrice, updateDeletePicture;
    private Spinner updateDeleteSpiner;
    private AppCompatButton updateBtn, deleteBtn, cancelUpdateDeleteBtn;
    private ItemsDomain itemsDomain;
    private int p=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        initView();
        Intent intent=getIntent();
        itemsDomain= (ItemsDomain) intent.getSerializableExtra("item");
        updateDeleteTitle.setText(itemsDomain.getTitle());
        updateDeleteDescription.setText(itemsDomain.getDescription());
        updateDeleteOldPrice.setText(""+itemsDomain.getOldPrice());
        updateDeletePrice.setText(""+itemsDomain.getPrice());
        updateDeletePicture.setText(itemsDomain.getPicUrl().get(0));
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
                    updateDeleteSpiner.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.item_spinner,list));
                    for (int i=0;i<list.size();i++){
                        if (list.get(i).equals(itemsDomain.getCategory())){
                            p=i;
                            break;
                        }
                    }
                    updateDeleteSpiner.setSelection(p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference itemsRef = database.getReference("Items");
                Query query = itemsRef.orderByChild("title").equalTo(updateDeleteTitle.getText().toString());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot issue : snapshot.getChildren()) {
                                issue.getRef().child("title").setValue(updateDeleteTitle.getText().toString());
                                issue.getRef().child("description").setValue(updateDeleteDescription.getText().toString());
                                issue.getRef().child("oldPrice").setValue(Double.parseDouble(updateDeleteOldPrice.getText().toString()));
                                issue.getRef().child("price").setValue(Double.parseDouble(updateDeletePrice.getText().toString()));
                                ArrayList<String> picUrl=new ArrayList<>();
                                picUrl.add(updateDeletePicture.getText().toString());
                                issue.getRef().child("picUrl").setValue(picUrl);
                                issue.getRef().child("category").setValue(updateDeleteSpiner.getSelectedItem().toString());
                            }
                            Toast.makeText(UpdateDeleteActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(UpdateDeleteActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setTitle("Product deletion notice");
                builder.setMessage("Are you sure you want to delete this product "+itemsDomain.getTitle()+"?");
                builder.setIcon(R.drawable.icon_remove);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference itemsRef = database.getReference("Items");
                        Query query = itemsRef.orderByChild("title").equalTo(itemsDomain.getTitle());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        dataSnapshot.getRef().removeValue();
                                        finish();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        cancelUpdateDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        updateDeleteTitle=findViewById(R.id.updateDeleteTitle);
        updateDeleteDescription=findViewById(R.id.updateDeleteDescription);
        updateDeleteOldPrice=findViewById(R.id.updateDeleteOldPrice);
        updateDeletePrice=findViewById(R.id.updateDeletePrice);
        updateDeletePicture=findViewById(R.id.updateDeletePicture);
        updateDeleteSpiner=findViewById(R.id.updateDeleteSpiner);
        updateBtn=findViewById(R.id.updateBtn);
        deleteBtn=findViewById(R.id.deleteBtn);
        cancelUpdateDeleteBtn=findViewById(R.id.cancelUpdateDeleteBtn);
    }
}