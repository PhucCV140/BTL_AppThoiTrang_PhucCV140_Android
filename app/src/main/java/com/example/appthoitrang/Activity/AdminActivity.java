package com.example.appthoitrang.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.appthoitrang.Adapter.ViewPagerAdapter;
import com.example.appthoitrang.Domain.AccountDomain;
import com.example.appthoitrang.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AdminActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private ViewPager viewPager;
    private FloatingActionButton fab;
    public static AccountDomain accountDomainAdmin;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        navigationView=findViewById(R.id.bottom_nav);
        viewPager=findViewById(R.id.viewPagerAdmin);
        fab=findViewById(R.id.fab);
        Intent intent=getIntent();
        accountDomainAdmin= (AccountDomain) intent.getSerializableExtra("admin");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        navigationView.getMenu().findItem(R.id.mProduct).setChecked(true);
                        break;
                    case 1:
                        navigationView.getMenu().findItem(R.id.mSold).setChecked(true);
                        break;
                    case 2:
                        navigationView.getMenu().findItem(R.id.mClient).setChecked(true);
                        break;
                    case 3:
                        navigationView.getMenu().findItem(R.id.mProfile).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.mProduct){
                    viewPager.setCurrentItem(0);
                } else if (item.getItemId() == R.id.mSold) {
                    viewPager.setCurrentItem(1);
                } else if (item.getItemId() == R.id.mClient) {
                    viewPager.setCurrentItem(2);
                } else if (item.getItemId() == R.id.mProfile) {
                    viewPager.setCurrentItem(3);
                }
                return true;
            }
        });
    }
}