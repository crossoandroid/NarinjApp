package com.orange_team.narinjapp.fragments; //H

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.orange_team.narinjapp.R;
import com.orange_team.narinjapp.adapters.FoodListAdapter;
import com.orange_team.narinjapp.application.NApplication;
import com.orange_team.narinjapp.constants.Constants;
import com.orange_team.narinjapp.enums.OrderCategories;
import com.orange_team.narinjapp.interfaces.RetrofitInterface;
import com.orange_team.narinjapp.model.Food;
import com.orange_team.narinjapp.model.OrderedItem;
import com.orange_team.narinjapp.model.Result;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FoodListFragment extends Fragment {

    FoodListAdapter mFoodListAdapter;
    List<Food> mFoodList;
    int mOrderQuantity;
    OrderedItem mOrderedItem;
    List<OrderedItem> mOrderedItemsList;
    FrameLayout mRedCircle;
    TextView mItemsCountTV;
    RecyclerView mRecyclerView;
    View mDialogView;
    Food mFood;
    Boolean mChecker = false;
    RetrofitInterface mRetrofitInterface;
    Call<List<Result.NFood>> mFoodListCall;

    public static final int SINGLE_ORDER_QUANTITY = 1;
    public static final String CHEF_ID = "Chef partnerId";
    public static final String CATEGORY_KEY = "Category";
    public static final String MAKE_ORDER = "Order";
    public static final String DO_NOT_ORDER = "Cancel";
    public static final String SOUP = "soup";
    public static final String SALAD = "salad";
    public static final String LUNCH = "lunch";
    public static final String CAKE = "cake";
    public static final String HOT_DISHES = "hotDishes";
    public static final String GARNISH = "garnish";
    public static final String ALL = "all";
    public static final String COUNT_VALUE = "10";
    public static final String PAGE_VALUE_0 = "0";
    public static final String PAGE_VALUE_1 = "1";






    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_items_list, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        init();
    }

    public void init(){

        createObjects();
        defineAdapterContent();
        defineComponents();

    }

    private void createObjects() {


        mFoodList = new ArrayList<>();
        mOrderedItemsList = new ArrayList<>();
        mFoodListAdapter = new FoodListAdapter(getActivity());
        mRetrofitInterface = ((NApplication) getActivity().getApplication()).getRetrofitInterface();

    }

    private void defineComponents() {

        mRedCircle = (FrameLayout)getActivity().findViewById(R.id.menuIconRedCircleFrame);
        mItemsCountTV = (TextView)getActivity().findViewById(R.id.menuIconRedCircleText);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.itemRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mFoodListAdapter);
        mFoodListAdapter.setOnItemSelectedListener(mOnItemSelectedListener);

    }

    private void defineAdapterContent() {
        Log.d(Constants.LOG_TAG, "Defining adapter content 1");

        if(getArguments()!=null) {
            Log.d(Constants.LOG_TAG, "Defining adapter content 2");

            OrderCategories request = (OrderCategories) getArguments().getSerializable(CATEGORY_KEY);
            switch (request) {
                case SOUP: {
                    mFoodListCall = mRetrofitInterface.getFoodByCategory1("soup", 0, 10);
                    getJSONObjects(mFoodListCall);
                    Log.d(Constants.LOG_TAG, "Defining adapter content 3");
                }
                break;

                case SALAD: {
/*                    mParamsMap.put(CATEGORY_KEY, SALAD);
                    mFoodListCall = mRetrofitInterface.getFoodByCategory(mParamsMap);
                    getJSONObjects(mFoodListCall);
                    mParamsMap.put(PAGE_KEY, PAGE_VALUE_1);
                    mFoodListCall = mRetrofitInterface.getFoodByCategory(mParamsMap);
                    getJSONObjects(mFoodListCall);
                    mFoodListAdapter.setFoodList(mFoodList);*/
                }
                break;

                case LUNCH: {
                    mFoodListCall = mRetrofitInterface.getFoodByCategory1(LUNCH, 0, 10);
                    getJSONObjects(mFoodListCall);
                    mFoodListCall = mRetrofitInterface.getFoodByCategory1(LUNCH, 1, 10);
                    getJSONObjects(mFoodListCall);
                    Log.d(Constants.LOG_TAG, "Defining adapter content 3");

                    Log.d(Constants.LOG_TAG, ">>>>>>>>>>><<<<<<<<<<<");

                    for(Food food: mFoodList){
                        Log.d(Constants.LOG_TAG, food.getName());
                    }
                }
                break;

                case CAKE: {
                    mFoodListCall = mRetrofitInterface.getFoodByCategory1(CAKE, 0, 10);
                    getJSONObjects(mFoodListCall);

                }
                break;

                case HOT_DISHES: {
                    mFoodListCall = mRetrofitInterface.getFoodByCategory1(HOT_DISHES, 0, 10);
                    getJSONObjects(mFoodListCall);
                }
                break;

                case GARNISH: {
                    mFoodListCall = mRetrofitInterface.getFoodByCategory1(GARNISH, 0, 10);
                    getJSONObjects(mFoodListCall);
                }
                break;

                case ALL: {
                    for(int i=0; i<6; i++) {
                        mFoodListCall = mRetrofitInterface.getFoodByCategory1(ALL, i, 10);
                        getJSONObjects(mFoodListCall);

                     }
                }
                break;

                case CHEF: {
                    Log.d(Constants.LOG_TAG, "Defining adapter content 3");
                    for(int i=0; i<4; i++) {
                        mFoodListCall = mRetrofitInterface.getChefFoodList(getArguments().getLong(CHEF_ID), i, 10);
                        getJSONObjects(mFoodListCall);
                    }
                }
                break;

            }




        }
    }

    private void getJSONObjects(Call<List<Result.NFood>> FoodListCall){

        FoodListCall.enqueue(new Callback<List<Result.NFood>>() {
            @Override
            public void onResponse(Call<List<Result.NFood>> call, Response<List<Result.NFood>> response) {
                Log.d(Constants.LOG_TAG, "Success");
                Log.d(Constants.LOG_TAG, ""+response.code());
                List<Result.NFood> NFoodList = response.body();
                Log.d(Constants.LOG_TAG, ""+response.code());

                for(Result.NFood food : NFoodList){
                    mFood = new Food();
                    mFood.setId(food.dishId);
                    mFood.setName(food.name);
                    mFood.setDesc(food.description);
                    mFood.setPrice(food.price);
                    //mFood.setUrl(food.);        Food image
                    //mFood.setChefName(food.chefName);
                    Log.d(Constants.LOG_TAG, mFood.getName());
                    mFoodList.add(mFood);
                }
                mFoodListAdapter.setFoodList(mFoodList);
            }

            @Override
            public void onFailure(Call<List<Result.NFood>> call, Throwable t) {
                Toast.makeText(getContext(), "Food list was not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    FoodListAdapter.IOnItemSelectedListener mOnItemSelectedListener = new FoodListAdapter.IOnItemSelectedListener() {
        @Override
        public void onItemSelected(Food food) {

            Log.d(Constants.LOG_TAG, food.getName() + " View");
            createDialog(food);

        }

        @Override
        public void onAddButtonClicked(Food food) {

            Log.d(Constants.LOG_TAG, food.getName()+" Button");
            addOneItem(food);

        }
    };

    public void createDialog(Food food){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(food.getName());
        mDialogView = getActivity().getLayoutInflater().inflate(R.layout.selected_food, null);
        alertDialog.setView(mDialogView);
        ((TextView)mDialogView.findViewById(R.id.descDialog)).setText(food.getDesc());
        ((TextView)mDialogView.findViewById(R.id.itemPriceDialog)).setText(""+food.getPrice());
        ((TextView)mDialogView.findViewById(R.id.chefName)).setText(food.getChefName());
        final long dishId = food.getId();


        alertDialog.setPositiveButton(MAKE_ORDER, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            mOrderQuantity = Integer.parseInt(((TextView)mDialogView.findViewById(R.id.countDialog)).getText().toString());

            for(OrderedItem item : mOrderedItemsList){
                if(item.getDishId() == dishId) {
                    item.setCount(item.getCount()+ mOrderQuantity);
                    mChecker = true;
                    break;
                }
                Log.d(Constants.LOG_TAG, "end of iteration");
            }
            if(!mChecker){
                mOrderedItem = new OrderedItem(dishId, mOrderQuantity);
                mOrderedItemsList.add(mOrderedItem);
                updateMenuCount(increaseCartCount());
                Log.d(Constants.LOG_TAG, "adding object");

            }
            mChecker = false;

            }
        });



        alertDialog.setNegativeButton(DO_NOT_ORDER, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        Dialog dialog = alertDialog.create();
        dialog.show();

    }

    private void addOneItem(Food food) {

        long dishId = food.getId();

        for(OrderedItem item : mOrderedItemsList){
            if(item.getDishId() == dishId) {
                item.setCount(item.getCount()+ SINGLE_ORDER_QUANTITY);
                mChecker = true;
                break;
            }
        }
        if(!mChecker){
            mOrderedItem = new OrderedItem(dishId, SINGLE_ORDER_QUANTITY);
            mOrderedItemsList.add(mOrderedItem);
            updateMenuCount(increaseCartCount());

        }
        mChecker = false;

    }

    public void updateMenuCount(int itemCount){
        if(itemCount == 0){
            mItemsCountTV.setText("");
            mRedCircle.setVisibility(View.GONE);
        }else{
            mRedCircle.setVisibility(View.VISIBLE);
            mItemsCountTV.setText(""+Constants.CART_COUNT);
        }
    }

    public static int increaseCartCount() {
        return Constants.CART_COUNT = Constants.CART_COUNT + SINGLE_ORDER_QUANTITY;
    }
}
