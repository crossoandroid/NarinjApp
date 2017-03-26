package com.orange_team.narinjapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orange_team.narinjapp.R;
import com.orange_team.narinjapp.adapters.OrdersDetailsAdapter;
import com.orange_team.narinjapp.model.OrdersDetails;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class OrdersDetailsFrag extends Fragment {

    RecyclerView mOrdersDetailsRecycler;
    OrdersDetailsAdapter mAdapter;
    List<OrdersDetails> ordersDetailses;
    OrdersDetails ordersDetails;
    int on = 2;
    String mDate;
    Calendar calendar = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.orders_details_frag, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        init(view);
    }

    public void init(View view) {
        ordersDetailses = new ArrayList<>();
        ordersDetails = new OrdersDetails();

        ordersDetails.setId(on);
        ordersDetails.setDate(calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "."
                + calendar.get(Calendar.YEAR) + "  " + calendar.get(Calendar.HOUR) + ":"
                + calendar.get(Calendar.MINUTE));
        ordersDetailses.add(ordersDetails);

        ordersDetails = new OrdersDetails();
        ordersDetails.setId(on + 1);
        ordersDetails.setDate(calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "."
                + calendar.get(Calendar.YEAR) + "  " + calendar.get(Calendar.HOUR) + ":"
                + calendar.get(Calendar.MINUTE));
        ordersDetailses.add(ordersDetails);
        mAdapter = new OrdersDetailsAdapter(getActivity(), ordersDetailses);
        mAdapter.notifyDataSetChanged();

        mOrdersDetailsRecycler = (RecyclerView) view.findViewById(R.id.orders_details_recycler);
        mOrdersDetailsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mOrdersDetailsRecycler.setAdapter(mAdapter);

    }
}
