package com.example.appthoitrang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.appthoitrang.Activity.DetailActivity;
import com.example.appthoitrang.Domain.ItemsDomain;
import com.example.appthoitrang.R;

import java.util.ArrayList;
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {
    private List<ItemsDomain> itemsDomainList;
    private ItemListener itemListener;
    public PopularAdapter() {
        this.itemsDomainList = new ArrayList<>();
    }
    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setList(List<ItemsDomain> itemsDomainList) {
        this.itemsDomainList = itemsDomainList;
        notifyDataSetChanged();
    }
    public ItemsDomain getItem(int position){
        return itemsDomainList.get(position);
    }
    @NonNull
    @Override
    public PopularAdapter.PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_pop_list,parent,false);
        return new PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.PopularViewHolder holder, int position) {
        ItemsDomain itemsDomain=itemsDomainList.get(position);
        holder.title.setText(itemsDomain.getTitle());
        holder.reviewTxt.setText(""+itemsDomain.getReview());
        holder.priceTxt.setText("$"+itemsDomain.getPrice());
        holder.ratingTxt.setText("("+itemsDomain.getRating()+")");
        holder.oldPriceTxt.setText("$"+itemsDomain.getOldPrice());
        holder.oldPriceTxt.setPaintFlags(holder.oldPriceTxt.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        holder.ratingBar.setRating((float) itemsDomain.getRating());

        RequestOptions requestOptions=new RequestOptions();
        requestOptions=requestOptions.transform(new CenterCrop());

        Glide.with(holder.itemView.getContext())
                .load(itemsDomain.getPicUrl().get(0))
                .apply(requestOptions)
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return itemsDomainList.size();
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title, reviewTxt, priceTxt, ratingTxt, oldPriceTxt;
        private RatingBar ratingBar;
        private ImageView pic;
        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            reviewTxt=itemView.findViewById(R.id.reviewTxt);
            priceTxt=itemView.findViewById(R.id.priceTxt);
            ratingTxt=itemView.findViewById(R.id.ratingTxt);
            oldPriceTxt=itemView.findViewById(R.id.oldPriceTxt);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            pic=itemView.findViewById(R.id.pic);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemListener!=null){
                itemListener.onItemClick(v,getAdapterPosition());
            }
        }
    }
    public interface ItemListener{
        void onItemClick(View view, int position);
    }
}
