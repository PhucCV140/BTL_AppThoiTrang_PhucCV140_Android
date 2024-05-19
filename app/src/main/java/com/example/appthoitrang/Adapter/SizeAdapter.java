package com.example.appthoitrang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appthoitrang.R;

import java.util.ArrayList;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.SizeViewHolder> {
    private ArrayList<String> items;
    private Context context;
    private int selectedPositon=-1;
    private int lastSelectedPosition=-1;
    public SizeAdapter(ArrayList<String> items) {
        this.items = items;
    }
    @NonNull
    @Override
    public SizeAdapter.SizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_size,parent,false);
        return new SizeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SizeAdapter.SizeViewHolder holder, int position) {
        holder.sizeTxt.setText(items.get(position));
        holder.sizeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectedPosition=selectedPositon;
                selectedPositon=holder.getAdapterPosition();
                notifyItemChanged(lastSelectedPosition);
                notifyItemChanged(selectedPositon);
            }
        });
        if (selectedPositon==holder.getAdapterPosition()){
            holder.sizeLayout.setBackgroundResource(R.drawable.size_selected);
            holder.sizeTxt.setTextColor(context.getResources().getColor(R.color.green));
        }else {
            holder.sizeLayout.setBackgroundResource(R.drawable.size_unselected);
            holder.sizeTxt.setTextColor(context.getResources().getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class SizeViewHolder extends RecyclerView.ViewHolder {
        private TextView sizeTxt;
        private ConstraintLayout sizeLayout;
        public SizeViewHolder(@NonNull View itemView) {
            super(itemView);
            sizeTxt=itemView.findViewById(R.id.sizeTxt);
            sizeLayout=itemView.findViewById(R.id.sizeLayout);
        }
    }
}
