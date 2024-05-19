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


public class PurchasedAdapter extends RecyclerView.Adapter<PurchasedAdapter.PurchasedViewHolder> {
    private List<PurchasedDomain> purchasedDomainList;
    private PurchasedListener purchasedListener;

    public PurchasedAdapter() {
        this.purchasedDomainList = new ArrayList<>();
    }

    public void setPurchasedListener(PurchasedListener purchasedListener) {
        this.purchasedListener = purchasedListener;
    }

    public void setList(List<PurchasedDomain> purchasedDomainList) {
        this.purchasedDomainList = purchasedDomainList;
        notifyDataSetChanged();
    }
    public PurchasedDomain getItem(int position){
        return purchasedDomainList.get(position);
    }
    @NonNull
    @Override
    public PurchasedAdapter.PurchasedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_purchased,parent,false);
        return new PurchasedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchasedAdapter.PurchasedViewHolder holder, int position) {
        PurchasedDomain purchasedDomain=purchasedDomainList.get(position);
        holder.titleTxt.setText(purchasedDomain.getTitle());
        holder.feeEachItem.setText("$"+purchasedDomain.getPrice());
        holder.totalEachItem.setText("$"+Math.round(purchasedDomain.getQuantity()*purchasedDomain.getPrice()));
        holder.numberItemTxt.setText(String.valueOf(purchasedDomain.getQuantity()));
        holder.datePurchased.setText(purchasedDomain.getDate());
        RequestOptions requestOptions=new RequestOptions();
        requestOptions=requestOptions.transform(new CenterCrop());

        Glide.with(holder.itemView.getContext())
                .load(purchasedDomain.getPicUrl().get(0))
                .apply(requestOptions)
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return purchasedDomainList.size();
    }

    public class PurchasedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleTxt, feeEachItem, totalEachItem, numberItemTxt, datePurchased;
        private ImageView pic;
        public PurchasedViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt=itemView.findViewById(R.id.titleTxt);
            feeEachItem=itemView.findViewById(R.id.feeEachItem);
            totalEachItem=itemView.findViewById(R.id.totalEachItem);
            numberItemTxt=itemView.findViewById(R.id.numberPurchasedTxt);
            datePurchased=itemView.findViewById(R.id.datePurchased);
            pic=itemView.findViewById(R.id.pic);
        }

        @Override
        public void onClick(View v) {
            if (purchasedListener!=null){
                purchasedListener.onPurchasedClick(v,getAdapterPosition());
            }
        }
    }
    public interface PurchasedListener{
        void onPurchasedClick(View view, int position);
    }
}
