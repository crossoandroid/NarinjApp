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
    Map mParamsMap;
    RetrofitInterface mRetrofitInterface;
    Call<Result.NFoodList> mFoodListCall;

    public static final int SINGLE_ORDER_QUANTITY = 1;
    public static final String CHEF_ID = "Chef id";
    public static final String CATEGORY_KEY = "Category";
    public static final String MAKE_ORDER = "Order";
    public static final String DO_NOT_ORDER = "Cancel";




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

        mParamsMap = new HashMap();
        mParamsMap.put("page", "0");
        mParamsMap.put("count", "10");
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
                    mParamsMap.put("category", "soup");
                    String s = "soup&page=0&count=10";

                    mFoodListCall = mRetrofitInterface.getFoodByCategory1("soup");
                    Log.d(Constants.LOG_TAG, "Defining adapter content 3");
                }
                break;

                case SALAD: {
                    mParamsMap.put("category", "salad");
                    mFoodListCall = mRetrofitInterface.getFoodByCategory(mParamsMap);
                }
                break;

                case LUNCH: {
                    mParamsMap.put("category", "lunch");
                    mFoodListCall = mRetrofitInterface.getFoodByCategory(mParamsMap);
                }
                break;

                case CAKE: {
                    mParamsMap.put("category", "cake");
                    mFoodListCall = mRetrofitInterface.getFoodByCategory(mParamsMap);
                }
                break;

                case HOT_DISHES: {
                    mParamsMap.put("category", "hotDishes");
                    mFoodListCall = mRetrofitInterface.getFoodByCategory(mParamsMap);
                }
                break;

                case GARNISH: {
                    mParamsMap.put("category", "garnish");
                    mFoodListCall = mRetrofitInterface.getFoodByCategory(mParamsMap);
                }
                break;

                case ALL: {
                    mParamsMap.put("category", "all");
                    mFoodListCall = mRetrofitInterface.getFoodByCategory(mParamsMap);
                }
                break;

                case CHEF: {

                    mFoodListCall = mRetrofitInterface.getChefFoodList(getArguments().getLong(CHEF_ID));

                }
                break;

            }

            mFoodListCall.enqueue(new Callback<Result.NFoodList>() {
                @Override
                public void onResponse(Call<Result.NFoodList> call, Response<Result.NFoodList> response) {
                    Log.d(Constants.LOG_TAG, "Success");
                    Log.d(Constants.LOG_TAG, ""+response.code());
                    List<Result.NFood> NFoodList = response.body().items;
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
                public void onFailure(Call<Result.NFoodList> call, Throwable t) {
                    Toast.makeText(getContext(), "Files are not found", Toast.LENGTH_SHORT).show();
                }
            });


        }
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

    public void createDialog(final Food food){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(food.getName());
        mDialogView = getActivity().getLayoutInflater().inflate(R.layout.selected_food, null);
        alertDialog.setView(mDialogView);
        ((TextView)mDialogView.findViewById(R.id.descDialog)).setText(food.getDesc());
        ((TextView)mDialogView.findViewById(R.id.itemPriceDialog)).setText(food.getPrice());
        ((TextView)mDialogView.findViewById(R.id.chefName)).setText(food.getChefName());


        alertDialog.setPositiveButton(MAKE_ORDER, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateMenuCount(increaseCartCount());

            long dishId = food.getId();
            mOrderQuantity = Integer.parseInt(((TextView)mDialogView.findViewById(R.id.countDialog)).getText().toString());

            for(OrderedItem item : mOrderedItemsList){
                if(item.getDishId() == dishId) {
                    item.setCount(item.getCount()+ mOrderQuantity);
                    mChecker = true;
                    break;
                }
            }
            if(!mChecker){
                mOrderedItem = new OrderedItem(dishId, mOrderQuantity);
                mOrderedItemsList.add(mOrderedItem);
                updateMenuCount(increaseCartCount());

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
