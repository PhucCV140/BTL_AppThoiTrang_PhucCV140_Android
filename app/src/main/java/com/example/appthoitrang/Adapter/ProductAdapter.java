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
import com.example.appthoitrang.Domain.ItemsDomain;
import com.example.appthoitrang.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<ItemsDomain> itemsDomainList;
    private ItemListener itemListener;

    public ProductAdapter() {
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
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        ItemsDomain itemsDomain=itemsDomainList.get(position);
        holder.priceProduct.setText("$"+itemsDomain.getPrice());
        holder.oldPriceProduct.setText("$"+itemsDomain.getOldPrice());
        holder.titleProductTxt.setText(itemsDomain.getTitle());
        holder.categoryProduct.setText(itemsDomain.getCategory());

        RequestOptions requestOptions=new RequestOptions();
        requestOptions=requestOptions.transform(new CenterCrop());

        Glide.with(holder.itemView.getContext())
                .load(itemsDomain.getPicUrl().get(0))
                .apply(requestOptions)
                .into(holder.picProduct);
    }

    @Override
    public int getItemCount() {
        return itemsDomainList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView picProduct;
        private TextView priceProduct, oldPriceProduct, titleProductTxt, categoryProduct;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            picProduct=itemView.findViewById(R.id.picProduct);
            priceProduct=itemView.findViewById(R.id.priceProduct);
            oldPriceProduct=itemView.findViewById(R.id.oldPriceProduct);
            titleProductTxt=itemView.findViewById(R.id.titleProductTxt);
            categoryProduct=itemView.findViewById(R.id.categoryProduct);
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
