package com.orange_team.supplier.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orange_team.supplier.R;
import com.orange_team.supplier.adapters.DetailedFoodItemsAdapter;

public class NewOrderActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    DetailedFoodItemsAdapter mDetailedFoodItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerNewOrderActivity);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setHasFixedSize(true);



    }
}
