package com.orange_team.supplier.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.orange_team.supplier.R;
import com.orange_team.supplier.adapters.DetailedFoodItemsAdapter;
import com.orange_team.supplier.models.OrderDetails;
import com.orange_team.supplier.models.OrderedDishAndCount;

import java.util.List;

import io.realm.Realm;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    DetailedFoodItemsAdapter mDetailedFoodItemsAdapter;
    Realm mRealm;
    public static final String KEY_DATE = "date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerHistoryActivity);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setHasFixedSize(true);
        mRealm.init(getApplicationContext());
        mRealm = Realm.getDefaultInstance();
        OrderDetails orderDetails = mRealm.where(OrderDetails.class).equalTo(OrderDetails.DATE_AND_TIME, getIntent().getStringExtra(KEY_DATE)).findFirst();
        mDetailedFoodItemsAdapter = new DetailedFoodItemsAdapter(this, orderDetails.getOrderedDishAndCountList());
        mRecyclerView.setAdapter(mDetailedFoodItemsAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );

    }
}
