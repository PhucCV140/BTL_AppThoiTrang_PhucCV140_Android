package com.example.appthoitrang.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
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
import com.example.appthoitrang.Helper.ChangeNumberItemsListener;
import com.example.appthoitrang.Helper.ManagmentCart;
import com.example.appthoitrang.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CarViewHolder> {
    private ChangeNumberItemsListener changeNumberItemsListener;
    private List<ItemsDomain> itemsDomainList;
    private ManagmentCart managmentCart;
    private SharedPreferences preferences;

    public CartAdapter(ArrayList<ItemsDomain> itemsDomainList, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.itemsDomainList = itemsDomainList;
        this.changeNumberItemsListener = changeNumberItemsListener;
        managmentCart=new ManagmentCart(context);
    }
    private CartListener cartListener;

    public void setCartListener(CartListener cartListener) {
        this.cartListener = cartListener;
    }
    public void setList(List<ItemsDomain> itemsDomainList){
        this.itemsDomainList=itemsDomainList;
        notifyDataSetChanged();
    }
    public ItemsDomain getItem(int position){
        return itemsDomainList.get(position);
    }

    @NonNull
    @Override
    public CartAdapter.CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        preferences = PreferenceManager.getDefaultSharedPreferences(parent.getContext());
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,parent,false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CarViewHolder holder, int position) {
        ItemsDomain itemsDomain=itemsDomainList.get(position);
        holder.titleTxt.setText(itemsDomain.getTitle());
        holder.feeEachItem.setText("$"+itemsDomain.getPrice());
        holder.totalEachItem.setText("$"+Math.round(itemsDomain.getNumberinCart()*itemsDomain.getPrice()));
        holder.numberPurchasedTxt.setText(String.valueOf(itemsDomain.getNumberinCart()));

        RequestOptions requestOptions=new RequestOptions();
        requestOptions=requestOptions.transform(new CenterCrop());

        Glide.with(holder.itemView.getContext())
                .load(itemsDomain.getPicUrl().get(0))
                .apply(requestOptions)
                .into(holder.pic);

        holder.plusCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusItem((ArrayList<ItemsDomain>) itemsDomainList, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });
        holder.minusCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minusItem((ArrayList<ItemsDomain>) itemsDomainList, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });
    }
    // Tăng số lượng của sản phẩm trong giỏ hàng
    public void plusItem(ArrayList<ItemsDomain> listitem, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        // Tăng số lượng lên 1
        listitem.get(position).setNumberinCart(listitem.get(position).getNumberinCart() + 1);
        // Lưu lại sản phẩm trong giỏ hàng và cập nhật thay đổi trên giao diện
        putListObject("CartList", listitem);
        changeNumberItemsListener.changed();
    }
    // Lưu danh sách các ItemsDomain vào khóa CartList trong SharedPreferences
    public void putListObject(String key, ArrayList<ItemsDomain> itemsList) {
        // Kiểm tra xem CartList có tồn tại hay không
        checkForNullKey(key);
        // Sử dụng JSON để chuyển các đối tượng sang dạng JSON
        Gson gson = new Gson();
        ArrayList<String> objStrings = new ArrayList<String>();
        for (ItemsDomain player : itemsList) {
            objStrings.add(gson.toJson(player));
        }
        // Chuyển từ JSON sang chuỗi String để lưu vào SharedPreferences
        putListString(key, objStrings);
    }
    // Kiểm tra xem có tồn tại sản phẩm nào trong giỏ hàng hay không, ko có sẽ in ra ngoại lệ
    private void checkForNullKey(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
    }
    // Lưu danh sách các chuỗi vào SharedPreferences dưới dạng 1 chuỗi duy nhất
    public void putListString(String key, ArrayList<String> stringList) {
        checkForNullKey(key);
        // Chuyển đổi ArrayList thành mảng chuỗi String
        String[] myStringList = stringList.toArray(new String[stringList.size()]);
        // Nối các chuỗi trong mảng thành một chuỗi duy nhất
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myStringList)).apply();
    }
    // Giảm số lượng sản phẩm trong giỏ hàng
    public void minusItem(ArrayList<ItemsDomain> listfood, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        // Nếu như số lượng bằng 1 thì tiến hành xóa khỏi danh sách trong SharedPreferences
        if (listfood.get(position).getNumberinCart() == 1) {
            listfood.remove(position);
        }
        // Nếu lơn hơn 1 thì tiến hành trừ đi 1
        else {
            listfood.get(position).setNumberinCart(listfood.get(position).getNumberinCart() - 1);
        }
        // Lưu lại sản phẩm trong giỏ hàng và cập nhật thay đổi trên giao diện
        putListObject("CartList", listfood);
        changeNumberItemsListener.changed();
    }
    @Override
    public int getItemCount() {
        return itemsDomainList.size();
    }

    public class CarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleTxt, feeEachItem, totalEachItem, numberPurchasedTxt, plusCartBtn, minusCartBtn;
        private ImageView pic;
        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt=itemView.findViewById(R.id.titleTxt);
            feeEachItem=itemView.findViewById(R.id.feeEachItem);
            totalEachItem=itemView.findViewById(R.id.totalEachItem);
            numberPurchasedTxt=itemView.findViewById(R.id.numberPurchasedTxt);
            plusCartBtn=itemView.findViewById(R.id.plusCartBtn);
            minusCartBtn=itemView.findViewById(R.id.minusCartBtn);
            pic=itemView.findViewById(R.id.pic);
        }

        @Override
        public void onClick(View v) {
            if (cartListener!=null){
                cartListener.onCartClick(v,getAdapterPosition());
            }
        }
    }
    public interface CartListener{
        void onCartClick(View view,int position);
    }
}
