package com.example.appthoitrang.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.appthoitrang.Domain.PurchasedDomain;
import com.example.appthoitrang.R;

import java.util.ArrayList;
import java.util.List;

public class SoldAdapter extends RecyclerView.Adapter<SoldAdapter.SoldViewHolder> {
    private List<PurchasedDomain> domainList;

    public SoldAdapter() {
        this.domainList = new ArrayList<>();
    }
    public void setList(List<PurchasedDomain> domainList) {
        this.domainList = domainList;
        notifyDataSetChanged();
    }
    public PurchasedDomain getItem(int position){
        return domainList.get(position);
    }
    @NonNull
    @Override
    public SoldAdapter.SoldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sold,parent,false);
        return new SoldViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoldAdapter.SoldViewHolder holder, int position) {
        PurchasedDomain purchasedDomain=domainList.get(position);
        holder.titleSold.setText(purchasedDomain.getTitle());
        holder.priceSold.setText("$"+purchasedDomain.getPrice());
        holder.totalSold.setText("$"+purchasedDomain.getPrice()*purchasedDomain.getQuantity());
        holder.numberSold.setText(""+purchasedDomain.getQuantity());
        holder.dateSold.setText(purchasedDomain.getDate());
        holder.usernameSold.setText(purchasedDomain.getUsername());

        RequestOptions requestOptions=new RequestOptions();
        requestOptions=requestOptions.transform(new CenterCrop());

        Glide.with(holder.itemView.getContext())
                .load(purchasedDomain.getPicUrl().get(0))
                .apply(requestOptions)
                .into(holder.picSold);
    }

    @Override
    public int getItemCount() {
        return domainList.size();
    }

    public class SoldViewHolder extends RecyclerView.ViewHolder {
        private TextView titleSold, priceSold, totalSold, numberSold, dateSold, usernameSold;
        private ImageView picSold;
        public SoldViewHolder(@NonNull View itemView) {
            super(itemView);
            titleSold=itemView.findViewById(R.id.titleSold);
            priceSold=itemView.findViewById(R.id.priceSold);
            totalSold=itemView.findViewById(R.id.totalSold);
            numberSold=itemView.findViewById(R.id.numberSold);
            dateSold=itemView.findViewById(R.id.dateSold);
            usernameSold=itemView.findViewById(R.id.usernameSold);
            picSold=itemView.findViewById(R.id.picSold);
        }
    }
}
