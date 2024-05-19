package com.example.appthoitrang.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appthoitrang.Adapter.CategoryAdapter;
import com.example.appthoitrang.Adapter.PopularAdapter;
import com.example.appthoitrang.Domain.AccountDomain;
import com.example.appthoitrang.Domain.CategoryDomain;
import com.example.appthoitrang.Domain.ItemsDomain;
import com.example.appthoitrang.Domain.SliderItems;
import com.example.appthoitrang.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements PopularAdapter.ItemListener, CategoryAdapter.CategoryListener {
    private LinearLayout cartBtn, profileBtn, purchasedBtn;
    private ProgressBar progressBarPopular, progressBarOfficial, progressBarBanner;
    private RecyclerView recyclerViewPopular, recyclerViewOfficial;
    private ViewPager2 viewPagerSlider;
    private PopularAdapter popularAdapter;
    private CategoryAdapter categoryAdapter;
    private Context context;
    private TextView allPopular;
    private SearchView searchPopular;
    private ArrayList<ItemsDomain> items;
    private AccountDomain accountDomain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Intent t=getIntent();
        accountDomain= (AccountDomain) t.getSerializableExtra("client");
        initBanner();
        categoryAdapter=new CategoryAdapter();
        initCategory();
        popularAdapter=new PopularAdapter();
        initPopular();
        popularAdapter.setItemListener(this::onItemClick);
        bottomNavigation();
        categoryAdapter.setCategoryListener(this::onCategoryClick);
        allPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopular();
            }
        });
        searchPopular.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<ItemsDomain> list = new ArrayList<>();
                for (ItemsDomain i : items) {
                    if (i.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                        list.add(i);
                    }
                }
                recyclerViewPopular.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                popularAdapter.setList(list);
                recyclerViewPopular.setAdapter(popularAdapter);
                return true;
            }
        });
    }
    private void bottomNavigation() {
        purchasedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, PurchasedActivity.class);
                intent.putExtra("client",new AccountDomain(accountDomain.getUsername(),accountDomain.getPassword(),
                        accountDomain.getAddress(),accountDomain.getEmail(),accountDomain.getIsAdmin()));
                startActivity(intent);
            }
        });
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, CartActivity.class);
                intent.putExtra("client",new AccountDomain(accountDomain.getUsername(),accountDomain.getPassword(),
                        accountDomain.getAddress(),accountDomain.getEmail(),accountDomain.getIsAdmin()));
                startActivity(intent);
            }
        });
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                intent.putExtra("client",new AccountDomain(accountDomain.getUsername(),accountDomain.getPassword(),
                        accountDomain.getAddress(),accountDomain.getEmail(),accountDomain.getIsAdmin()));
                startActivity(intent);
            }
        });
    }

    private void initPopular() {
        DatabaseReference metaData=database.getReference("Items");
        progressBarPopular.setVisibility(View.VISIBLE);
        items=new ArrayList<>();
        metaData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        items.add(issue.getValue(ItemsDomain.class));
                    }
                    if (!items.isEmpty()) {
                        recyclerViewPopular.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                        popularAdapter.setList(items);
                        recyclerViewPopular.setAdapter(popularAdapter);
                    }
                    progressBarPopular.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initCategory() {
        DatabaseReference metaData=database.getReference("Category");
        progressBarOfficial.setVisibility(View.VISIBLE);
        ArrayList<CategoryDomain> items=new ArrayList<>();
        metaData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue:snapshot.getChildren()){
                        items.add(issue.getValue(CategoryDomain.class));
                    }
                    if (!items.isEmpty()){
                        recyclerViewOfficial.setLayoutManager(new LinearLayoutManager(MainActivity.this,
                                LinearLayoutManager.HORIZONTAL,false));
                        categoryAdapter.setList(items);
                        recyclerViewOfficial.setAdapter(categoryAdapter);
                    }
                    progressBarOfficial.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initBanner() {
        DatabaseReference metaData=database.getReference("Banner");
        progressBarBanner.setVisibility(View.VISIBLE);
        ArrayList<SliderItems> items=new ArrayList<>();
        metaData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue:snapshot.getChildren()){
                        items.add(issue.getValue(SliderItems.class));
                    }
                    banners(items);
                    progressBarBanner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void banners(ArrayList<SliderItems> items) {
        viewPagerSlider.setAdapter(new com.example.appthoitrang.Adapter.SliderAdapter(items, viewPagerSlider));
        viewPagerSlider.setClipToPadding(false); //Giúp hiển thị các banner bên cạnh
        viewPagerSlider.setClipChildren(false);
        viewPagerSlider.setOffscreenPageLimit(3);
        viewPagerSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));

        viewPagerSlider.setPageTransformer(compositePageTransformer);
    }
    private void initView() {
        cartBtn=findViewById(R.id.cart_btn);
        profileBtn=findViewById(R.id.profileBtn);
        purchasedBtn=findViewById(R.id.purchasedBtn);
        progressBarPopular=findViewById(R.id.progressBarPopular);
        progressBarOfficial=findViewById(R.id.progressBarOfficial);
        progressBarBanner=findViewById(R.id.progressBarBanner);
        recyclerViewPopular=findViewById(R.id.recyclerViewPopular);
        recyclerViewOfficial=findViewById(R.id.recyclerViewOfficial);
        viewPagerSlider=findViewById(R.id.viewPagerSlider);
        allPopular=findViewById(R.id.allPopular);
        searchPopular=findViewById(R.id.searchPopular);
    }

    @Override
    public void onItemClick(View view, int position) {
        ItemsDomain itemsDomain=popularAdapter.getItem(position);
        Intent intent=new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("object",itemsDomain);
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(View view, int position) {
        DatabaseReference metaData=database.getReference("Items");
        progressBarPopular.setVisibility(View.VISIBLE);
        ArrayList<ItemsDomain> items=new ArrayList<>();
        metaData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        ItemsDomain item = issue.getValue(ItemsDomain.class);
                        // Kiểm tra xem item có thuộc category đã chọn không
                        if (item != null && item.getCategory().equals(categoryAdapter.getItem(position).getTitle())) {
                            items.add(item);
                        }
                    }
                    if (!items.isEmpty()) {
                        recyclerViewPopular.setLayoutManager(new GridLayoutManager(context, 2));
                        popularAdapter.setList(items);
                        recyclerViewPopular.setAdapter(popularAdapter);
                    }
                    progressBarPopular.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Not found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}