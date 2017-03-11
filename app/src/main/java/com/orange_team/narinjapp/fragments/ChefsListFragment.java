package com.orange_team.narinjapp.fragments; //H

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
import android.widget.Toast;
import com.orange_team.narinjapp.R;
import com.orange_team.narinjapp.adapters.ChefsListAdapter;
import com.orange_team.narinjapp.application.NApplication;
import com.orange_team.narinjapp.constants.Constants;
import com.orange_team.narinjapp.enums.OrderCategories;
import com.orange_team.narinjapp.interfaces.RetrofitInterface;
import com.orange_team.narinjapp.model.Chef;
import com.orange_team.narinjapp.model.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hayk on 07-Mar-17.
 */

public class ChefsListFragment extends Fragment {

    RecyclerView mRecyclerView;
    Call<List<Result.NChef>> mNChefListCall;
    List<Result.NChef> mNChefList;
    Chef mChef;
    List<Chef> mChefList;
    ChefsListAdapter mChefsListAdapter;
    RetrofitInterface mRetrofitInterface;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chef_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        init();
    }

    private void init() {

        createObjects();
        defineComponents();

    }

    private void createObjects() {

        mChefList = new ArrayList<>();
        mChefsListAdapter = new ChefsListAdapter(getContext());
        mRetrofitInterface = ((NApplication) getActivity().getApplication()).getRetrofitInterface();
        mNChefListCall = mRetrofitInterface.getAllChefs();
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.chefRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mChefsListAdapter);
        mChefsListAdapter.setOnItemSelectedListener(new ChefsListAdapter.IOnItemSelectedListener() {
            @Override
            public void onItemSelected(Chef chef) {
                Bundle args = new Bundle();
                args.putLong(FoodListFragment.CHEF_ID, chef.getId());
                args.putSerializable(FoodListFragment.CATEGORY_KEY, OrderCategories.CHEF);
                args.putLong(FoodListFragment.CHEF_ID, chef.getId());
                FoodListFragment foodListFragment = new FoodListFragment();
                foodListFragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_main, foodListFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }

    private void defineComponents() {

        mNChefListCall.enqueue(new Callback<List<Result.NChef>>() {
            @Override
            public void onResponse(Call<List<Result.NChef>> call, Response<List<Result.NChef>> response) {
                mNChefList = response.body();
                for(Result.NChef chef : mNChefList){
                    mChef = new Chef();
                    mChef.setId(chef.partnerId);
                    mChef.setName(chef.name);
                    mChef.setSurname(chef.surName);
                    mChef.setPhone(chef.phone);
                    //mChef.setAvatar(chef.avatar);
                    mChefList.add(mChef);
                    Log.d(Constants.LOG_TAG, mChef.getName());
                }
                mChefsListAdapter.setChefList(mChefList);

            }

            @Override
            public void onFailure(Call<List<Result.NChef>> call, Throwable t) {
                Toast.makeText(getContext(), "Chef list was not found", Toast.LENGTH_SHORT).show();
            }
        });
    }


//    ChefsListAdapter.IOnItemSelectedListener onItemSelectedListener = new ChefsListAdapter.IOnItemSelectedListener() {
//        @Override
//        public void onItemSelected(Chef chef) {
//
//
//
//        }
//    };

}
