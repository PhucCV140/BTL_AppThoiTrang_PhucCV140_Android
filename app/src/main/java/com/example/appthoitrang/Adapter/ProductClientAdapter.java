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

public class ProductClientAdapter extends RecyclerView.Adapter<ProductClientAdapter.ProductClientViewHolder> {
    private List<PurchasedDomain> domainList;

    public ProductClientAdapter() {
        this.domainList=new ArrayList<>();
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
    public ProductClientAdapter.ProductClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_product_client,parent,false);
        return new ProductClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductClientAdapter.ProductClientViewHolder holder, int position) {
        PurchasedDomain purchasedDomain=domainList.get(position);
        holder.titleProductClient.setText(purchasedDomain.getTitle());
        holder.priceProductClient.setText("$"+purchasedDomain.getPrice());
        holder.totalProductClient.setText("$"+purchasedDomain.getPrice()*purchasedDomain.getQuantity());
        holder.dateProductClient.setText(purchasedDomain.getDate());
        holder.numberProductClient.setText(""+purchasedDomain.getQuantity());

        RequestOptions requestOptions=new RequestOptions();
        requestOptions=requestOptions.transform(new CenterCrop());

        Glide.with(holder.itemView.getContext())
                .load(purchasedDomain.getPicUrl().get(0))
                .apply(requestOptions)
                .into(holder.picProductClient);
    }

    @Override
    public int getItemCount() {
        return domainList.size();
    }

    public class ProductClientViewHolder extends RecyclerView.ViewHolder {
        private TextView titleProductClient, priceProductClient, totalProductClient, dateProductClient, numberProductClient;
        private ImageView picProductClient;
        public ProductClientViewHolder(@NonNull View itemView) {
            super(itemView);
            titleProductClient=itemView.findViewById(R.id.titleProductClient);
            priceProductClient=itemView.findViewById(R.id.priceProductClient);
            totalProductClient=itemView.findViewById(R.id.totalProductClient);
            dateProductClient=itemView.findViewById(R.id.dateProductClient);
            numberProductClient=itemView.findViewById(R.id.numberProductClient);
            picProductClient=itemView.findViewById(R.id.picProductClient);
        }
    }
}
