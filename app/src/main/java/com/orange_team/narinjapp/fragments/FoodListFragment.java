package com.orange_team.narinjapp.fragments; //H

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
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
import com.orange_team.narinjapp.activities.MainActivity;
import com.orange_team.narinjapp.adapters.FoodListAdapter;
import com.orange_team.narinjapp.application.NApplication;
import com.orange_team.narinjapp.constants.Constants;
import com.orange_team.narinjapp.enums.OrderCategories;
import com.orange_team.narinjapp.interfaces.RetrofitInterface;
import com.orange_team.narinjapp.model.Food;
import com.orange_team.narinjapp.model.OrderedItem;
import com.orange_team.narinjapp.model.Result;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FoodListFragment extends Fragment {

    FoodListAdapter mFoodListAdapter;
    List<Food> mFoodList;
    int mOrderQuantity;
    OrderedItem mOrderedItem;
    List<OrderedItem> mOrderedItemsList;
    RecyclerView mRecyclerView;
    View mDialogView;
    Food mFood;
    Boolean mChecker = false;
    RetrofitInterface mRetrofitInterface;
    Call<List<Result.NFood>> mFoodListCall;
    android.os.Handler mHandler;

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
    public static final int COUNT_VALUE = 10;
    public static final int PAGE_VALUE_0 = 0;
    public static final int PAGE_VALUE_1 = 1;
    public static final String IMAGE_BASE_URL = "http://narinj.am/resources/site/assets/img/";
    public static final int HANDLER_MESSAGE = 0;






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
        mHandler = new android.os.Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case HANDLER_MESSAGE:{
                        mFoodListAdapter.setFoodList(mFoodList);
                    }
                }
            }
        };
        mRetrofitInterface = ((NApplication) getActivity().getApplication()).getRetrofitInterface();

    }

    private void defineComponents() {

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
                    mFoodListCall = mRetrofitInterface.getFoodByCategory1(SOUP, PAGE_VALUE_0, COUNT_VALUE);
                    getJSONObjects(mFoodListCall);
                }
                break;

                case SALAD: {
                    //Salad page is currently unavailable.
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
                    mFoodListCall = mRetrofitInterface.getFoodByCategory1(LUNCH, PAGE_VALUE_0, COUNT_VALUE);    // 2 pages
                    getJSONObjects(mFoodListCall);
                    mFoodListCall = mRetrofitInterface.getFoodByCategory1(LUNCH, PAGE_VALUE_1, COUNT_VALUE);
                    getJSONObjects(mFoodListCall);

                }
                break;

                case CAKE: {
                    mFoodListCall = mRetrofitInterface.getFoodByCategory1(CAKE, PAGE_VALUE_0, COUNT_VALUE);
                    getJSONObjects(mFoodListCall);

                }
                break;

                case HOT_DISHES: {
                    mFoodListCall = mRetrofitInterface.getFoodByCategory1(HOT_DISHES, PAGE_VALUE_0, COUNT_VALUE);
                    getJSONObjects(mFoodListCall);
                }
                break;

                case GARNISH: {
                    mFoodListCall = mRetrofitInterface.getFoodByCategory1(GARNISH, PAGE_VALUE_0, COUNT_VALUE);
                    getJSONObjects(mFoodListCall);
                }
                break;

                case ALL: {
                    for(int i=0; i<6; i++) {        //all the dishes are included in 5 pages
                        mFoodListCall = mRetrofitInterface.getFoodByCategory1(ALL, i, COUNT_VALUE);
                        getJSONObjects(mFoodListCall);

                     }
                }
                break;

                case CHEF: {
                    for(int i=0; i<4; i++) {     //the max number of dishes made by 1 chef are included in 4 pages
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

                List<Result.NFood> NFoodList = response.body();
                for(Result.NFood food : NFoodList){
                    mFood = new Food();
                    mFood.setId(food.dishId);
                    mFood.setName(food.name);
                    mFood.setDesc(food.description);
                    mFood.setPrice(food.price);
                    if(food.files.get(0).path!=null) {
                        mFood.setPicture(IMAGE_BASE_URL + food.files.get(0).path);
                    }
                    Log.d(Constants.LOG_TAG, mFood.getName());
                    addBulkToList(mFood);
                }
                mHandler.sendEmptyMessage(HANDLER_MESSAGE);
            }

            @Override
            public void onFailure(Call<List<Result.NFood>> call, Throwable t) {
                Toast.makeText(getContext(), "Food list was not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private synchronized void addBulkToList(Food food) {
        mFoodList.add(food);
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

    private void createDialog(Food food){

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

            for(OrderedItem item : MainActivity.orderedItems){
                if(item.getDishId() == dishId) {
                    item.setCount(item.getCount()+ mOrderQuantity);
                    mChecker = true;
                    break;
                }
                Log.d(Constants.LOG_TAG, "end of iteration");
            }
            if(!mChecker){
                mOrderedItem = new OrderedItem(dishId, mOrderQuantity);
                MainActivity.orderedItems.add(mOrderedItem);
                MainActivity.updateMenuCount();
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

        for(OrderedItem item : MainActivity.orderedItems){
            if(item.getDishId() == dishId) {
                item.setCount(item.getCount()+ SINGLE_ORDER_QUANTITY);
                mChecker = true;
                break;
            }
        }
        if(!mChecker){
            mOrderedItem = new OrderedItem(dishId, SINGLE_ORDER_QUANTITY);
            MainActivity.orderedItems.add(mOrderedItem);
            MainActivity.updateMenuCount();

        }
        mChecker = false;

    }



}
