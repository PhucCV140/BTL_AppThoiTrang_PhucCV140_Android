package com.example.appthoitrang.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appthoitrang.Activity.UpdateDeleteActivity;
import com.example.appthoitrang.Adapter.ProductAdapter;
import com.example.appthoitrang.Domain.ItemsDomain;
import com.example.appthoitrang.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentProduct extends Fragment implements ProductAdapter.ItemListener {
    private RecyclerView recyclerViewProduct;
    private ProductAdapter productAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewProduct=view.findViewById(R.id.recyclerViewProduct);
        productAdapter=new ProductAdapter();
        productAdapter.setItemListener(this);
        initItem();
    }

    private void initItem() {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference metaData=database.getReference("Items");
        ArrayList<ItemsDomain> itemsDomains=new ArrayList<>();
        metaData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        itemsDomains.add(issue.getValue(ItemsDomain.class));
                    }
                    if (!itemsDomains.isEmpty()) {
                        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                        productAdapter.setList(itemsDomains);
                        recyclerViewProduct.setAdapter(productAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        ItemsDomain itemsDomain=productAdapter.getItem(position);
        Intent intent=new Intent(getContext(), UpdateDeleteActivity.class);
        intent.putExtra("item",itemsDomain);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        initItem();
    }
}
