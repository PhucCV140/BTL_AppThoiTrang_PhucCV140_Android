package com.example.appthoitrang.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appthoitrang.Domain.AccountDomain;
import com.example.appthoitrang.R;

import java.util.ArrayList;
import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {
    private List<AccountDomain> domainList;
    private ClientListener clientListener;

    public ClientAdapter() {
        this.domainList=new ArrayList<>();
    }

    public void setClientListener(ClientListener clientListener) {
        this.clientListener = clientListener;
    }
    public void setList(List<AccountDomain> domainList) {
        this.domainList = domainList;
        notifyDataSetChanged();
    }
    public AccountDomain getItem(int position){
        return domainList.get(position);
    }
    @NonNull
    @Override
    public ClientAdapter.ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client,parent,false);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientAdapter.ClientViewHolder holder, int position) {
        AccountDomain accountDomain=domainList.get(position);
        holder.nameClient.setText(accountDomain.getUsername());
        holder.addressClient.setText(accountDomain.getAddress());
        holder.emailClient.setText(accountDomain.getEmail());
    }

    @Override
    public int getItemCount() {
        return domainList.size();
    }

    public class ClientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nameClient, addressClient, emailClient;
        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            nameClient=itemView.findViewById(R.id.nameClient);
            addressClient=itemView.findViewById(R.id.addressClient);
            emailClient=itemView.findViewById(R.id.emailClient);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clientListener!=null){
                clientListener.onClientClick(v,getAdapterPosition());
            }
        }
    }
    public interface ClientListener{
        void onClientClick(View view, int position);
    }
}
