package com.example.appthoitrang.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.example.appthoitrang.Domain.ItemsDomain;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;


public class TinyDB {

    private SharedPreferences preferences;

    public TinyDB(Context appContext) {
        preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
    }
    // Lấy danh sách các chuỗi String từ SharedPreferences và chuyển sang ArrayList
    public ArrayList<String> getListString(String key) {
        return new ArrayList<String>(Arrays.asList(TextUtils.split(preferences.getString(key, ""), "‚‗‚")));
    }
    // Xóa mục có khóa key khỏi SharedPreferences
    public void clearList(String key) {
        preferences.edit().remove(key).apply();
    }
    // Lấy danh sách các đổi tượng ItemsDomain trong SharedPreferences
    public ArrayList<ItemsDomain> getListObject(String key) {
        Gson gson = new Gson();
        // Lấy ra danh sách ArrayList sau kki chuyển từ String sang để đổi sang kiểu JSON
        ArrayList<String> objStrings = getListString(key);
        ArrayList<ItemsDomain> playerList = new ArrayList<ItemsDomain>();
        // Chuyển từng bộ JSON trong SharedPreferences và lưu vào ArrayList<ItemsDomain>
        for (String jObjString : objStrings) {
            ItemsDomain player = gson.fromJson(jObjString, ItemsDomain.class);
            playerList.add(player);
        }
        return playerList;
    }
    // Lưu danh sách chuỗi vào SharedPreferences
    public void putListString(String key, ArrayList<String> stringList) {
        checkForNullKey(key);
        String[] myStringList = stringList.toArray(new String[stringList.size()]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myStringList)).apply();
    }
    // Lưu danh sách các đối tượng IntemsDomain và SharedPreferences
    public void putListObject(String key, ArrayList<ItemsDomain> playerList) {
        checkForNullKey(key);
        Gson gson = new Gson();
        ArrayList<String> objStrings = new ArrayList<String>();
        for (ItemsDomain player : playerList) {
            objStrings.add(gson.toJson(player));
        }
        putListString(key, objStrings);
    }
    // Kiểm tra xem khóa có null hay không
    private void checkForNullKey(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
    }

}