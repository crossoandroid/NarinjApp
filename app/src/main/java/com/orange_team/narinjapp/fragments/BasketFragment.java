package com.orange_team.narinjapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.orange_team.narinjapp.R;
import com.orange_team.narinjapp.adapters.ChoiceAdapter;
import com.orange_team.narinjapp.adapters.DividerItemDecor;
import com.orange_team.narinjapp.model.Food;


import java.util.ArrayList;
import java.util.List;

public class BasketFragment extends Fragment {

    TextView mTotal;
    Button mContinueBtn;
    RecyclerView mBasketRecyclerView;
    ChoiceAdapter mAdapter;
    List<Food> foodList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.basket_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        init(view);
    }

    private void init(View view) {

        foodList = new ArrayList<>();

        mAdapter = new ChoiceAdapter(getActivity(), foodList);

        mTotal = (TextView) view.findViewById(R.id.totalText);
        mContinueBtn = (Button) view.findViewById(R.id.continue_btn);
        mBasketRecyclerView = (RecyclerView) view.findViewById(R.id.basket_recycler);
        mBasketRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBasketRecyclerView.addItemDecoration(new DividerItemDecor(getContext(), LinearLayoutManager.VERTICAL));
        mBasketRecyclerView.setAdapter(mAdapter);

        addFood();

        mContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOrderDetailsFragment();
            }
        });
    }

    private void openOrderDetailsFragment() {

        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        OrderDetailsFragment orderDetailsFragment = new OrderDetailsFragment();
        fragmentTransaction.replace(R.id.container, orderDetailsFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void addFood() {
        Food food = new Food("Pizza Peperoni",1500);
        foodList.add(food);

        food = new Food("Apur",800);
        foodList.add(food);

        food = new Food("Txvacq",500 );
        foodList.add(food);

        food = new Food("Axcan",1000);
        foodList.add(food);

        food = new Food("Xavart",500);
        foodList.add(food);

        food = new Food("Xorovac",2300);
        foodList.add(food);

    }
}

