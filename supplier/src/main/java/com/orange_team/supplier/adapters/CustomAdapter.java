package com.orange_team.supplier.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.orange_team.supplier.activity.MainActivity;
import com.orange_team.supplier.fragments.CurrentOrderFragment;
import com.orange_team.supplier.fragments.NewOrderFragment;
import com.orange_team.supplier.fragments.OrderHistoryFragment;

public class CustomAdapter extends FragmentPagerAdapter {

    private String fragments [] = {"New Orders", "Current Order", "Order History"};
    public static final int NEW_ORDER_FRAGMENT_POSITION = 0;
    public static final int CURRENT_ORDER_FRAGMENT_POSITION = 1;
    public static final int ORDER_HISTORY_FRAGMENT_POSITION = 2;

    public CustomAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case NEW_ORDER_FRAGMENT_POSITION: {
                Log.d(MainActivity.LOG_TAG, "returning New");
                return new NewOrderFragment();
            }
            case CURRENT_ORDER_FRAGMENT_POSITION: {
                Log.d(MainActivity.LOG_TAG, "returning Current");
                return new CurrentOrderFragment();
            }
            case ORDER_HISTORY_FRAGMENT_POSITION: {
                Log.d(MainActivity.LOG_TAG, "returning History");
                return new OrderHistoryFragment();
            }
            default:return null;
        }
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments[position];
    }
}
