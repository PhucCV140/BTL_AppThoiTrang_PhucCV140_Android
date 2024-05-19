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

import com.example.appthoitrang.Activity.ProductClientActivity;
import com.example.appthoitrang.Adapter.ClientAdapter;
import com.example.appthoitrang.Domain.AccountDomain;
import com.example.appthoitrang.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentClient extends Fragment implements ClientAdapter.ClientListener {
    private RecyclerView recyclerViewClient;
    private ClientAdapter clientAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_client,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewClient=view.findViewById(R.id.recyclerViewClient);
        clientAdapter=new ClientAdapter();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference metaData=database.getReference("Account");
        ArrayList<AccountDomain> list=new ArrayList<>();
        metaData.orderByChild("isAdmin").equalTo(0).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        list.add(issue.getValue(AccountDomain.class));
                    }
                    if (!list.isEmpty()){
                        recyclerViewClient.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
                        clientAdapter.setList(list);
                        recyclerViewClient.setAdapter(clientAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        clientAdapter.setClientListener(this);
    }

    @Override
    public void onClientClick(View view, int position) {
        AccountDomain accountDomain=clientAdapter.getItem(position);
        Intent intent=new Intent(getContext(), ProductClientActivity.class);
        intent.putExtra("username",accountDomain.getUsername());
        startActivity(intent);
    }
}
