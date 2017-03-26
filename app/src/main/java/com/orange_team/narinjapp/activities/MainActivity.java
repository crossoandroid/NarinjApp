package com.orange_team.narinjapp.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.orange_team.narinjapp.R;
import com.orange_team.narinjapp.fragments.AboutUsFragment;
import com.orange_team.narinjapp.fragments.BasketFragment;
import com.orange_team.narinjapp.fragments.HelpPageFragment;
import com.orange_team.narinjapp.fragments.MainFragment;
import com.orange_team.narinjapp.fragments.MapFrag;
import com.orange_team.narinjapp.fragments.OrdersDetailsFrag;
import com.orange_team.narinjapp.fragments.WhyUsePageFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static int menuCount;
    public static String PREFS_NAME="Narinj";
    static FrameLayout mMenuRootFrame, mRedCircle;
    static TextView mItemsCountTV;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment mainFragment = new MainFragment();
            fragmentTransaction.add(R.id.fragment_main, mainFragment);

            fragmentTransaction.commit();
        }

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && toolbar != null) {
            actionBar.setDisplayShowTitleEnabled(false);

            toolbar.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    ActionBar actionBar = getSupportActionBar();
                    if (actionBar != null) {
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.toolbarlogo);
                        Bitmap bitmap1 = Bitmap.createBitmap(toolbar.getWidth(), toolbar.getHeight(), bitmap.getConfig());
                        Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * toolbar.getHeight() / bitmap.getHeight(), toolbar.getHeight(), true);
                        Canvas canvas = new Canvas(bitmap1);
                        canvas.drawBitmap(bitmap2, (toolbar.getWidth() / 2) - (bitmap2.getWidth() / 2), 0, null);
                        BitmapDrawable background = new BitmapDrawable(getResources(), bitmap1);
                        actionBar.setBackgroundDrawable(background);
                    }
                }
            });
        }
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setBackgroundColor(getResources().getColor(R.color.nav_color));


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.shop_cart) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment basketFragment = new BasketFragment();
            fragmentTransaction.replace(R.id.fragment_main, basketFragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final MenuItem cartItem = menu.findItem(R.id.shop_cart);
        mMenuRootFrame = (FrameLayout) cartItem.getActionView();
        mRedCircle = (FrameLayout) mMenuRootFrame.findViewById(R.id.menuIconRedCircleFrame);
        mItemsCountTV = (TextView) mMenuRootFrame.findViewById(R.id.menuIconRedCircleText);
            SharedPreferences prefs = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            if (prefs.getInt("count", 0) != 0) {
                menuCount = prefs.getInt("count", 0);

            } else {
                menuCount = 0;
            }
        mItemsCountTV.setText(""+menuCount);
        mMenuRootFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOptionsItemSelected(cartItem);
            }
        });
        return super.onPrepareOptionsMenu(menu);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (id == R.id.home_page) {
            MainFragment mainFragment = new MainFragment();
            fragmentTransaction.replace(R.id.fragment_main, mainFragment);
        } else if (id == R.id.why_use) {
            WhyUsePageFragment whyUsePageFragment = new WhyUsePageFragment();
            fragmentTransaction.replace(R.id.fragment_main, whyUsePageFragment);
        } else if (id == R.id.about_us) {
            AboutUsFragment aboutUsFragment = new AboutUsFragment();
            fragmentTransaction.replace(R.id.fragment_main, aboutUsFragment);
        } else if (id == R.id.help) {
            HelpPageFragment helpPageFragment = new HelpPageFragment();
            fragmentTransaction.replace(R.id.fragment_main, helpPageFragment);
        } else if (id == R.id.order_details) {
            OrdersDetailsFrag ordersDetailsFrag = new OrdersDetailsFrag();
            fragmentTransaction.replace(R.id.fragment_main,ordersDetailsFrag);

        } else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = getResources().getString(R.string.share_message);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_via)));
        } else if (id == R.id.nav_send) {
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            String messageBody = getResources().getString(R.string.share_message);
            sendIntent.putExtra("sms_body", messageBody);
            sendIntent.setType("vnd.android-dir/mms-sms");
            startActivity(sendIntent);
        }else if(id == R.id.map){
            MapFrag mapFrag = new MapFrag();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling

                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                        }, MapFrag.PERMISSION_REQUEST_CODE);

                fragmentTransaction.replace(R.id.fragment_main,mapFrag);
            }
            else {
                fragmentTransaction.replace(R.id.fragment_main,mapFrag);
            }


        }

        fragmentTransaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void updateMenuCount(int count) {
        if (count == 0) {
            mItemsCountTV.setText("");

        } else {
            mItemsCountTV.setText(""+count);
        }
    }

}
