package com.orange_team.supplier.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orange_team.supplier.R;
import com.orange_team.supplier.activity.MainActivity;
import com.orange_team.supplier.adapters.DetailedFoodItemsAdapter;
import com.orange_team.supplier.models.OrderDetails;


public class DetailedFoodItemsFragment extends Fragment {


    OrderDetails mOrderDetails;
    RecyclerView mRecyclerView;
    DetailedFoodItemsAdapter mDetailedFoodItemsAdapter;
    public static final String ARGS_KEY = "key";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detailed_food_items, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(MainActivity.LOG_TAG, "onViewCreated Detailed Food Items Fragment");

        init();
    }

    private void init() {

        mRecyclerView = (RecyclerView)getActivity().findViewById(R.id.detailedFoodItemsRecycler);

        if(getArguments()!=null) {

            mOrderDetails = getArguments().getParcelable(ARGS_KEY);
            mDetailedFoodItemsAdapter = new DetailedFoodItemsAdapter(getContext(), mOrderDetails.getOrderedDishAndCountList());
            mRecyclerView.setAdapter(mDetailedFoodItemsAdapter);

        }
    }
}
