package com.example.appthoitrang.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appthoitrang.Domain.CategoryDomain;
import com.example.appthoitrang.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<CategoryDomain> categoryDomainList;
    private CategoryListener categoryListener;

    public CategoryAdapter() {
        this.categoryDomainList = new ArrayList<>();
    }

    public void setCategoryListener(CategoryListener categoryListener) {
        this.categoryListener = categoryListener;
    }
    public void setList(List<CategoryDomain> categoryDomainList) {
        this.categoryDomainList = categoryDomainList;
        notifyDataSetChanged();
    }
    public CategoryDomain getItem(int position){
        return categoryDomainList.get(position);
    }
    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        CategoryDomain categoryDomain=categoryDomainList.get(position);
        holder.title.setText(categoryDomain.getTitle());

        Glide.with(holder.itemView.getContext())
                .load(categoryDomain.getPicUrl())
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return categoryDomainList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private ImageView pic;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            pic=itemView.findViewById(R.id.pic);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (categoryListener!=null){
                categoryListener.onCategoryClick(v,getAdapterPosition());
            }
        }
    }
    public interface CategoryListener{
        void onCategoryClick(View view, int position);
    }
}
