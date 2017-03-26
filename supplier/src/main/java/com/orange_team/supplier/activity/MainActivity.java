package com.orange_team.supplier.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.orange_team.supplier.R;
import com.orange_team.supplier.adapters.CustomAdapter;
import com.orange_team.supplier.models.Dish;
import com.orange_team.supplier.models.OrderDetails;
import com.orange_team.supplier.models.OrderedDishAndCount;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class MainActivity extends AppCompatActivity {

    TabLayout mTabLayout;
    ViewPager mViewPager;
    List<OrderDetails> mOrderDetailsList;
    Realm mRealm;

    public static final String LOG_TAG = "MyLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOG_TAG, "Main Activity On create");
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setUserName("Petros");
        orderDetails.setPhone("091222222");
        orderDetails.setAddress("komitas 1 ");
        Dish dish = new Dish();
        dish.setName("dish1");
        dish.setChefName("chef");
        dish.setPictureUrl("default_food");
        dish.setDishId(4);
        dish.setPrice(1500);
        OrderedDishAndCount orderedDishAndCount = new OrderedDishAndCount(9, dish);
        RealmList<OrderedDishAndCount> orderedDishAndCountList = new RealmList<>();
        orderedDishAndCountList.add(orderedDishAndCount);
        orderDetails.setOrderedDishAndCountList(orderedDishAndCountList);
        mOrderDetailsList = new ArrayList<>();
        mOrderDetailsList.add(orderDetails);
        orderDetails.setRealmIdentifier(OrderDetails.CURRENT_OBJECT);
        mRealm.init(getApplicationContext());
        mRealm= Realm.getDefaultInstance();
        mRealm.beginTransaction();
        mRealm.where(OrderDetails.class).findAll().deleteAllFromRealm();
        mRealm.copyToRealm(orderDetails);
        mRealm.commitTransaction();



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
        mRealm.close();
        Log.d(LOG_TAG, "onDestroy");
    }


}
