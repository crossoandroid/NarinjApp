package com.orange_team.narinjapp.fragments;

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
import com.orange_team.narinjapp.adapters.CategoriesAdapter;
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


public class ListItemFragment extends Fragment {

    CategoriesAdapter mCategoriesAdapter;
    List<Food> mFoodList;
    int mItemsCountNumber = 0;
    int mOrderQuantity;
    String mOrderLink;                      //test
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
    Call<Result.JSONFoodList> mFoodListCall;




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

        //createObjectsForTest();    //for test, will be deleted
        defineAdapterContent();
        defineComponents();

    }
    CategoriesAdapter.IOnItemSelectedListener mOnItemSelectedListener = new CategoriesAdapter.IOnItemSelectedListener() {
        @Override
        public void onItemSelected(Food food) {

                    Log.d(Constants.LOG_TAG, food.getName() + " View");
                    createDialog(food);

        }

        @Override
        public void onAddButtonClicked(Food food) {
            Log.d(Constants.LOG_TAG, food.getName()+" Button");
            addOneItem();
        }
    };




    private void defineComponents() {
        mRetrofitInterface = ((NApplication) getActivity().getApplication()).getRetrofitInterface();
        mRedCircle = (FrameLayout)getActivity().findViewById(R.id.menuIconRedCircleFrame);
        mItemsCountTV = (TextView)getActivity().findViewById(R.id.menuIconRedCircleText);

        mOrderedItemsList = new ArrayList<>();
        mCategoriesAdapter = new CategoriesAdapter(getActivity());
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mCategoriesAdapter.setFoodList(mFoodList);
        mRecyclerView.setAdapter(mCategoriesAdapter);
        mCategoriesAdapter.setOnItemSelectedListener(mOnItemSelectedListener);
    }

    private void defineAdapterContent() {
        mParamsMap = new HashMap();
        mParamsMap.put("page", "0");
        mParamsMap.put("count", "10");
        mFoodList = new ArrayList<>();

        if(getArguments()!=null){
            Bundle args = getArguments();
            OrderCategories request = (OrderCategories)args.getSerializable(Constants.CATEGORY_KEY);

            switch (request) {
                case SOUP: {
                    mParamsMap.put("category", "soup");
                }
                break;
                case SALAD: {
                    mParamsMap.put("category", "salad");
                }
                break;
        /*
                case RECEPTION: {

                }
                break;
        */
                case LUNCH: {
                    mParamsMap.put("category", "lunch");
                }
                break;
                case CAKE: {
                    mParamsMap.put("category", "cake");
                }
                break;
                case HOT_DISHES: {
                    mParamsMap.put("category", "hotDishes");
                }
                break;
                case GARNISH: {
                    mParamsMap.put("category", "garnish");
                }
                break;
                case ALL: {
                    mParamsMap.put("category", "all");
                }
                break;
            }

            mFoodListCall = mRetrofitInterface.getFoodByCategory(mParamsMap);
            mFoodListCall.enqueue(new Callback<Result.JSONFoodList>() {
                @Override
                public void onResponse(Call<Result.JSONFoodList> call, Response<Result.JSONFoodList> response) {
                    List<Result.JSONFood> JSONFoodList = response.body().items;
                    for(Result.JSONFood food : JSONFoodList){

                        mFood = new Food();
                        mFood.setName(food.name);
                        mFood.setDesc(food.description);
                        mFood.setPrice(food.price);
                        //mFood.setUrl(food.);
                        //mFood.setCookName(food.);
                        //mFood.setID(food.dishID);
                        mFoodList.add(mFood);
                    }
                }

                @Override
                public void onFailure(Call<Result.JSONFoodList> call, Throwable t) {
                    Toast.makeText(getContext(), "Files are not found", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    public void createDialog(Food food){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(food.getName());
        mDialogView = getActivity().getLayoutInflater().inflate(R.layout.selected_food, null);
        alertDialog.setView(mDialogView);
        ((TextView)mDialogView.findViewById(R.id.descDialog)).setText(food.getDesc());
        ((TextView)mDialogView.findViewById(R.id.itemPriceDialog)).setText(food.getPrice());
        ((TextView)mDialogView.findViewById(R.id.cookName)).setText(food.getCookName());


        alertDialog.setPositiveButton(Constants.MAKE_ORDER, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateMenuCount(Constants.increaseCartCount());
        //TODO
      /*      mOrderLink = "";                       //LINK OF FOOD OBJECT FROM RETROFIT
            mOrderQuantity = Integer.parseInt(((TextView)mDialogView.findViewById(R.id.countDialog)).getText().toString());

            for(OrderedItem item : mOrderedItemsList){
                if(item.getItemLink().equals(mOrderLink)) {                    //Checking with food link
                    item.setQuantity(item.getQuantity()+ mOrderQuantity);
                    mChecker = true;
                    break;
                }
            }
            if(!mChecker){
                mOrderedItem = new OrderedItem("", mOrderQuantity);
                mOrderedItemsList.add(mOrderedItem);
                mItemsCountNumber = mItemsCountNumber + SINGLE_ORDER_QUANTITY;
                updateMenuCount(mItemsCountNumber);

            }
            mChecker = false;*/

            }
        });
        alertDialog.setNegativeButton(Constants.DO_NOT_ORDER, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        Dialog dialog = alertDialog.create();
        dialog.show();
    }

    private void addOneItem() {
/*        mOrderLink = "";                       //LINK OF FOOD OBJECT FROM RETROFIT

        for(OrderedItem item : mOrderedItemsList){
            if(item.getItemLink().equals(mOrderLink)) {                    //Checking with food link
                item.setQuantity(item.getQuantity()+ SINGLE_ORDER_QUANTITY);
                mChecker = true;
                break;
            }
        }
        if(!mChecker){
            mOrderedItem = new OrderedItem("", SINGLE_ORDER_QUANTITY);
            mOrderedItemsList.add(mOrderedItem);
            mItemsCountNumber = mItemsCountNumber++;
            updateMenuCount(mItemsCountNumber);

        }*/
        updateMenuCount(Constants.increaseCartCount());
    }

    private void createObjectsForTest(){
        for (int i = 0; i <20; i++){           // Just for testing rec view
            mFood = new Food();
            mFood.setName("m"+i);
            mFoodList.add(mFood);
        }
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
}
