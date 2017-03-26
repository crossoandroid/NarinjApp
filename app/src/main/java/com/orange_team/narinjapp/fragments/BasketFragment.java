package com.orange_team.narinjapp.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.orange_team.narinjapp.R;
import com.orange_team.narinjapp.adapters.ChoiceAdapter;
import com.orange_team.narinjapp.db.DBDescription;
import com.orange_team.narinjapp.db.DataBaseHelper;
import com.orange_team.narinjapp.model.Dish;
import com.orange_team.narinjapp.model.DishOrders;
import com.orange_team.narinjapp.utils.AlertDialogManager;
import com.orange_team.narinjapp.model.Food;
import com.orange_team.narinjapp.utils.InternetConnectionDetector;


import java.util.ArrayList;
import java.util.List;

public class BasketFragment extends Fragment {

    public static TextView mTotal;
    Button mContinueBtn;
    String price;
    RecyclerView mBasketRecyclerView;
    ChoiceAdapter mAdapter;
    List<Food> foodList;
    static List<DishOrders> dishOrders;
    SQLiteDatabase db;
    String name, qty, imgPath;
    String chiefId,dishId;
    Cursor cursor = null;
    public static int inttotal;
    Boolean isInternetPresent = false;
    InternetConnectionDetector internetConnectionDetector;

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
        mBasketRecyclerView = (RecyclerView) view.findViewById(R.id.basket_recycler);
        ItemTouchHelper itemTouch=new ItemTouchHelper(simpleCallback);
        itemTouch.attachToRecyclerView(mBasketRecyclerView);
        mTotal = (TextView) view.findViewById(R.id.totalText);
        mContinueBtn = (Button) view.findViewById(R.id.continue_btn);


        getList();

        mContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                internetConnectionDetector=new InternetConnectionDetector(getContext());
                isInternetPresent=internetConnectionDetector.isConnectingToInternet();
                if(isInternetPresent) {
                    openOrderDetailsFragment();
                }
                else
                {
                    AlertDialogManager alertDialogManager=new AlertDialogManager();
                    alertDialogManager.showAlertDialog(getContext(),getString(R.string.enable_internet),getString(R.string.internet_access),true);
                }
            }
        });

    }

    private void openOrderDetailsFragment() {

        initFields();
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        OrderDetailsFragment orderDetailsFragment = new OrderDetailsFragment();
        fragmentTransaction.replace(R.id.basket_fragment, orderDetailsFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }

    private void initFields()
    {
        dishOrders=new ArrayList<>();

        for (int i = 0; i < foodList.size(); i++) {
            Dish dish=new Dish();
            DishOrders dishOrder =new DishOrders();
            dish.setName(foodList.get(i).getName());
            dish.setDishId(foodList.get(i).getId());
            dishOrder.setDish(dish);
            dishOrder.setCount(Integer.parseInt(foodList.get(i).getCount()));
            dishOrders.add(dishOrder);
        }
    }
    public void getList() {
        new SplashScreenDelay().execute();
    }

    private class SplashScreenDelay extends AsyncTask<String, Integer, Integer> {
        private static final String TAG = "SplashScreenTask";

        @Override
        protected Integer doInBackground(String... params) {
            foodList.clear();
            DataBaseHelper myDbHelper =  new DataBaseHelper(getContext());

            db = myDbHelper.getReadableDatabase();

            cursor = db.rawQuery("select * from "+ DBDescription.Cart.TABLE_NAME, null);
            if (cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Food food=new Food();

                        name = cursor.getString(cursor.getColumnIndex(DBDescription.Cart.COLUMN_NAME));
                        qty = cursor.getString(cursor.getColumnIndex(DBDescription.Cart.COLUMN_QTY));
                        price = cursor.getString(cursor.getColumnIndex(DBDescription.Cart.COLUMN_TOTAL));
                        imgPath=cursor.getString(cursor.getColumnIndex(DBDescription.Cart.COLUMN_IMG_PATH));
                        dishId=cursor.getString(cursor.getColumnIndex(DBDescription.Cart.COLUMN_DISH_ID));
                        chiefId=cursor.getString(cursor.getColumnIndex(DBDescription.Cart.COLUMN_CHIEF_ID));

                        food.setName(name);
                        food.setPrice(Integer.parseInt(price));
                        food.setCount(qty);
                        food.setPicture(imgPath);
                        food.setChiefId(Long.parseLong(chiefId));
                        food.setId(Long.parseLong(dishId));
                        foodList.add(food);

                    } while (cursor.moveToNext());
                }
            }

            cursor.close();
            db.close();
            myDbHelper.close();
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {

            Log.d("Vishal", "" + foodList.size());
            inttotal = 0;
            mBasketRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mAdapter = new ChoiceAdapter(getActivity(), foodList);
            mBasketRecyclerView.setAdapter(mAdapter);
            for (int i = 0; i < foodList.size(); i++) {
                inttotal+=foodList.get(i).getPrice()+400;
            }
            mTotal.setText("Ընդամենը:"+inttotal);
        }
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            mAdapter.deleteItem(foodList.get(position).getName());
            inttotal-= foodList.get(position).getPrice()-400;
            foodList.remove(position);
            mTotal.setText("Ընդամենը:"+inttotal);
            mAdapter.notifyItemRemoved(position);
            mAdapter.notifyItemRangeChanged(position,foodList.size());
        }
    };

    public static List<DishOrders> listInstance()
    {
        return dishOrders;
    }
}

