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
import android.widget.TextView;

import com.orange_team.user_application.narinj.tester.R;
import com.orange_team.user_application.narinj.tester.enums.OrderCategories;
import com.orange_team.user_application.narinj.tester.model.Food;
import com.orange_team.user_application.narinj.tester.model.OrderedItem;
import com.orange_team.user_application.narinj.tester.recyclers.CategoriesAdapter;

import java.util.ArrayList;
import java.util.List;


public class ListItemFragment extends Fragment {

    CategoriesAdapter mCategoriesAdapter;
    List<Food> mFoodList;               //for testing
    int mOrderQuantity;
    String mOrderLink;
    OrderedItem mOrderedItem;
    List<OrderedItem> mOrderedItemsList;
    RecyclerView mRecyclerView;
    View mDialogView;
    Food food;
    Boolean mChecker = false;

  //  RetrofitInterface mRetrofitInterface;
   // Call<Results.FoodList> mFoodListCall;

    public static final int SINGLE_ORDER_QUANTITY = 1;
    public static final String LOG_TAG = "MyLogs";
    public static final String MAKE_ORDER = "Order";
    public static final String DO_NOT_ORDER = "Cancel";
    public static final String CATEGORY_KEY = "Category";


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

        createObjectsForTest();    //for test, will be deleted
        defineRecyclerAndAdapter();
        defineAdapterContent();

    }
    CategoriesAdapter.IOnItemSelectedListener mOnItemSelectedListener = new CategoriesAdapter.IOnItemSelectedListener() {
        @Override
        public void onItemSelected(Food food) {

                    Log.d(LOG_TAG, food.getName() + " View");
                    createDialog(food);

        }

        @Override
        public void onAddButtonClicked(Food food) {
            Log.d(LOG_TAG, food.getName()+" Button");
        }
    };

//    CategoriesAdapter.IOnItemButtonSelectedListener mIOnItemButtonSelectedListener = new CategoriesAdapter.IOnItemButtonSelectedListener() {
//        @Override
//        public void onItemButtonSelected(Food food) {
//            //TODO
//            Log.d(LOG_TAG, food.getName());
//            mOrderLink = food.toString();         //MUST GET ITEM LINK FROM RETROFIT
//            for(OrderedItem item : mOrderedItemsList){
//                if(item.getItemLink().equals(mOrderLink)) {                    //Checking with food link
//                    item.setQuantity(item.getQuantity()+ SINGLE_ORDER_QUANTITY);
//                    mChecker = true;
//                    break;
//                }
//            }
//            if(!mChecker){
//                mOrderedItem = new OrderedItem("", SINGLE_ORDER_QUANTITY);
//                mOrderedItemsList.add(mOrderedItem);
//            }
//            mChecker = false;
//        }
//    };


    private void defineRecyclerAndAdapter() {
     //   mRetrofitInterface = ((NarinjApplication) getActivity().getApplication()).getRetrofitInterface();
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
        if(getArguments()!=null){
        Bundle args = getArguments();
        OrderCategories request = (OrderCategories)args.getSerializable(CATEGORY_KEY);

        switch (request) {
            case SOUP: {

            }
            break;
            case SALAD: {

            }
            break;
            case RECEPTION: {

            }
            break;
            case LUNCH: {

            }
            break;
            case CAKE: {

            }
            break;
            case MEAL: {

            }
            break;
            case GARNISH: {

            }
            break;
            case COOKS: {

            }
            break;
            case ALL: {

            }
            break;
        }
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


        alertDialog.setPositiveButton(MAKE_ORDER, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

        //TODO
            mOrderLink = "";                       //LINK OF FOOD OBJECT FROM RETROFIT
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

    private void createObjectsForTest(){
        mFoodList = new ArrayList<>();
        for (int i = 0; i <20; i++){           // Just for testing rec view
            food = new Food();
            food.setName("m"+i);
            mFoodList.add(food);
        }
    }


}
