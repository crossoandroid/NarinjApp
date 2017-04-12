package com.orange_team.supplier.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.orange_team.supplier.R;
import com.orange_team.supplier.adapters.ListViewAdapter;
import com.orange_team.supplier.models.Body;

public class HistoryActivity extends AppCompatActivity {

    private TextView mHistoryName,mHistoryNumber,mHistoryAddress,mHistoryPrice;
    private Body mBody;
    ListView mHistoryListView;
    ListViewAdapter mHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        init();
        Intent intent = getIntent();
        mBody = (Body) intent.getSerializableExtra("OrderFromHistory");
        defineObject();
    }

    private void init(){
        mHistoryName = (TextView) findViewById(R.id.historyActivityOrderName);
        mHistoryNumber = (TextView) findViewById(R.id.historyActivityOrderNumber);
        mHistoryAddress = (TextView) findViewById(R.id.historyActivityOrderAddress);
        mHistoryPrice = (TextView) findViewById(R.id.historyActivityOrderPrice);
        mHistoryListView = (ListView) findViewById(R.id.historyActivityListView);
    }

    private void defineObject(){
        mHistoryName.setText(mBody.getName());
        mHistoryNumber.setText(mBody.getPhone());
        mHistoryAddress.setText(mBody.getLocation());
        mHistoryPrice.setText(mBody.getPrice() + " Դրամ");
        mHistoryAdapter = new ListViewAdapter(HistoryActivity.this,0, mBody.getDishOrders());
        mHistoryListView.setAdapter(mHistoryAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
    }
}
