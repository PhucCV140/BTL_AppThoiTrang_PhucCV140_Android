package com.example.appthoitrang.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.appthoitrang.Domain.ItemsDomain;

import java.util.ArrayList;

public class ManagmentCart {

    private Context context;
    private TinyDB tinyDB;

    public ManagmentCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }
    // Thêm một Items vào giỏ hàng
    public void insertItem(ItemsDomain item) {
        ArrayList<ItemsDomain> list = getListCart();
        boolean existAlready = false; // Biến này để xem sản phẩm đã tồn tại trong giỏ hàng hay chưa
        int n = 0;
        // Duyệt từng mục trong giỏ hàng nếu có thì flag được đánh dấu là true
        for (int y = 0; y < list.size(); y++) {
            if (list.get(y).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = y;
                break;
            }
        }
        // Nếu có thì cập nhật số lượng trong giỏ hàng
        if (existAlready) {
            list.get(n).setNumberinCart(item.getNumberinCart());
        }
        // Thêm vào giỏ hàng
        else {
            list.add(item);
        }
        // Lưu danh sách giỏ hàng vào SharedPreferences
        tinyDB.putListObject("CartList", list);
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
    }
    // Xóa key CartList khỏi SharedPreferences
    public void clearCartList() {
        tinyDB.clearList("CartList");
        Toast.makeText(context, "Cart list cleared", Toast.LENGTH_SHORT).show();
    }
    // Lấy ra danh sách các CartList trong SharedPreferences
    public ArrayList<ItemsDomain> getListCart() {
        return tinyDB.getListObject("CartList");
    }
    // Dựa vào danh sách CartList trong SharedPreferences để tính toán số tiền hiển thị trong CartActivity
    public Double getTotalFee() {
        ArrayList<ItemsDomain> list2 = getListCart();
        double fee = 0;
        for (int i = 0; i < list2.size(); i++) {
            fee = fee + (list2.get(i).getPrice() * list2.get(i).getNumberinCart());
        }
        return fee;
    }
}
