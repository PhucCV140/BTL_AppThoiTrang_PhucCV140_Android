package com.example.appthoitrang.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appthoitrang.Domain.ItemsDomain;
import com.example.appthoitrang.Domain.SliderItems;
import com.example.appthoitrang.Fragment.DescriptionFragment;
import com.example.appthoitrang.Fragment.ReviewFragment;
import com.example.appthoitrang.Helper.ManagmentCart;
import com.example.appthoitrang.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private ItemsDomain object;
    private int numberOrder=1;
    private ViewPager2 viewPagerSlider;
    private TextView titleTxt, priceTxt, ratingTxt;
    private RatingBar ratingBar;
    private AppCompatButton addTocartBtn;
    private ImageView backBtn;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ManagmentCart managmentCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        managmentCart=new ManagmentCart(this);
        getBundles();
        initbanners();
        setupViewPager();
    }
    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList=new ArrayList<>();
        private final List<String> mFragmentTitleList=new ArrayList<>();
        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        private void addFrag(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    private void setupViewPager() {
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        DescriptionFragment tab1=new DescriptionFragment();
        ReviewFragment tab2=new ReviewFragment();
        Bundle bundle1=new Bundle();
        Bundle bundle2=new Bundle();

        bundle1.putString("description",object.getDescription());

        tab1.setArguments(bundle1);
        tab2.setArguments(bundle2);

        adapter.addFrag(tab1,"Description");
        adapter.addFrag(tab2,"Reviews");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void initbanners() {
        ArrayList<SliderItems> sliderItems=new ArrayList<>();
        for (int i=0;i<object.getPicUrl().size();i++){
            sliderItems.add(new SliderItems(object.getPicUrl().get(i)));
        }
        viewPagerSlider.setAdapter(new com.example.appthoitrang.Adapter.SliderAdapter(sliderItems,viewPagerSlider));
        viewPagerSlider.setClipToPadding(false);
        viewPagerSlider.setClipChildren(false);
        viewPagerSlider.setOffscreenPageLimit(3);
        viewPagerSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
    }

    private void getBundles() {
        object= (ItemsDomain) getIntent().getSerializableExtra("object");
        titleTxt.setText(object.getTitle());
        priceTxt.setText("$"+ object.getPrice());
        ratingBar.setRating((float) object.getRating());
        ratingTxt.setText(object.getRating()+" Rating");
        addTocartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setNumberinCart(numberOrder);
                managmentCart.insertItem(object);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initView() {
        viewPagerSlider=findViewById(R.id.viewPagerSlider);
        titleTxt=findViewById(R.id.titleTxt);
        priceTxt=findViewById(R.id.priceTxt);
        ratingTxt=findViewById(R.id.ratingTxt);
        ratingBar=findViewById(R.id.ratingBar);
        addTocartBtn=findViewById(R.id.addTocartBtn);
        backBtn=findViewById(R.id.backResetPasswordBtn);
        viewPager=findViewById(R.id.viewPager);
        tabLayout=findViewById(R.id.tabLayout);
    }
}