package com.example.appthoitrang.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.appthoitrang.Domain.ReviewDomain;
import com.example.appthoitrang.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private ArrayList<ReviewDomain> reviewDomainArrayList;
    private ReviewListener reviewListener;
    public ReviewAdapter(ArrayList<ReviewDomain> reviewDomainArrayList) {
        this.reviewDomainArrayList = reviewDomainArrayList;
    }
    public ReviewAdapter() {
        this.reviewDomainArrayList = new ArrayList<>();
    }

    public void setItemListener(ReviewListener reviewListener) {
        this.reviewListener = reviewListener;
    }

    public void setList(List<ReviewDomain> reviewDomainArrayList) {
        this.reviewDomainArrayList = (ArrayList<ReviewDomain>) reviewDomainArrayList;
        notifyDataSetChanged();
    }
    public ReviewDomain getItem(int position){
        return reviewDomainArrayList.get(position);
    }
    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_review,parent,false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder holder, int position) {
        ReviewDomain reviewDomain=reviewDomainArrayList.get(position);
        holder.nameTxt.setText(reviewDomain.getName());
        holder.descTxt.setText(reviewDomain.getDescription());
        holder.ratingTxt.setText(""+ reviewDomain.getRating());

        Glide.with(holder.itemView.getContext())
                .load(reviewDomain.getPicUrl())
                .transform(new GranularRoundedCorners(100, 100, 100, 100))
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return reviewDomainArrayList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameTxt, descTxt, ratingTxt;
        private ImageView pic;
        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt=itemView.findViewById(R.id.nameTxt);
            descTxt=itemView.findViewById(R.id.descTxt);
            ratingTxt=itemView.findViewById(R.id.ratingTxt);
            pic=itemView.findViewById(R.id.pic);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (reviewListener!=null){
                reviewListener.onReviewClick(v,getAdapterPosition());
            }
        }
    }
    public interface ReviewListener{
        void onReviewClick(View view, int position);
    }
}
