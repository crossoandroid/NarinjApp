package com.orange_team.supplier.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orange_team.supplier.R;
import com.orange_team.supplier.activity.HistoryActivity;
import com.orange_team.supplier.activity.MainActivity;
import com.orange_team.supplier.adapters.OrderHistoryAdapter;
import com.orange_team.supplier.models.OrderDetails;

import io.realm.Realm;
import io.realm.RealmResults;



public class OrderHistoryFragment extends Fragment {

    OrderHistoryAdapter mOrderHistoryAdapter;
    RecyclerView mRecyclerView;
    Realm mRealm;
    RealmResults<OrderDetails> mOrderDetailsList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_history, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(MainActivity.LOG_TAG, "onViewCreated History Fragment");
        mRealm.init(getContext());
        mRealm = Realm.getDefaultInstance();
        mRecyclerView = (RecyclerView)getActivity().findViewById(R.id.orderHistoryRecycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            Log.d(MainActivity.LOG_TAG, "calling init History Fragment");
            init();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    private void init() {

        defineObject();
            if (mOrderDetailsList.size() > 0) {
                defineComponents();

            } else {
                Log.d(MainActivity.LOG_TAG, "mOrderDetails size is zero");

            }

    }




    private void defineObject() {

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mOrderDetailsList = mRealm.where(OrderDetails.class)
                        .equalTo(OrderDetails.REALM_IDENTIFIER, OrderDetails.HISTORY_OBJECT)
                        .findAll();
            }
        });

    }

    private void defineComponents() {
        mOrderHistoryAdapter = new OrderHistoryAdapter(getContext(), mOrderDetailsList);
        mOrderHistoryAdapter.setOnItemSelectedListener(mOnItemSelectedListener);
        mRecyclerView.setAdapter(mOrderHistoryAdapter);
    }


    OrderHistoryAdapter.IOnItemSelectedListener mOnItemSelectedListener = new OrderHistoryAdapter.IOnItemSelectedListener() {
            @Override
            public void onItemSelected(OrderDetails orderDetails) {

                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                intent.putExtra(HistoryActivity.KEY_DATE, orderDetails.getDateAndTime());
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);



            }
    };



}
