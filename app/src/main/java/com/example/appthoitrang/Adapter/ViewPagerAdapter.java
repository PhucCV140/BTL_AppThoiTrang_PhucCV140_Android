package com.example.appthoitrang.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.appthoitrang.Fragment.FragmentClient;
import com.example.appthoitrang.Fragment.FragmentProduct;
import com.example.appthoitrang.Fragment.FragmentProfile;
import com.example.appthoitrang.Fragment.FragmentSold;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentProduct();
            case 1:
                return new FragmentSold();
            case 2:
                return new FragmentClient();
            case 3:
                return new FragmentProfile();
            default: return new FragmentProduct();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
