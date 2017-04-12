package com.orange_team.supplier.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.orange_team.supplier.R;
import com.orange_team.supplier.adapters.CustomAdapter;

public class MainActivity extends AppCompatActivity {

    TabLayout mTabLayout;
    public static ViewPager mViewPager;
    public static final String LOG_TAG = "MyLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
     }

    private void init() {

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(new CustomAdapter(getSupportFragmentManager(), getApplicationContext()));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
        });
        mViewPager.setOffscreenPageLimit(2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }
}
