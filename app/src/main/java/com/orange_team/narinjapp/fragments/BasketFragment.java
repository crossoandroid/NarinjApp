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
import com.orange_team.narinjapp.activities.MainActivity;
import com.orange_team.narinjapp.adapters.ChoiceAdapter;
import com.orange_team.narinjapp.db.DBDescription;
import com.orange_team.narinjapp.db.DataBaseHelper;
import com.orange_team.narinjapp.model.Dish;
import com.orange_team.narinjapp.model.DishOrders;
import com.orange_team.narinjapp.utils.NetworkDialogManager;
import com.orange_team.narinjapp.model.Food;
import com.orange_team.narinjapp.utils.CartPreferences;
import com.orange_team.narinjapp.utils.InternetConnectionDetector;


import java.util.ArrayList;
import java.util.List;

public class BasketFragment extends Fragment {

    public static TextView foodTotal;
    TextView mSupp, mTotal;
    Button mContinueBtn;
    String mPrice;
    RecyclerView mBasketRecyclerView;
    ChoiceAdapter mAdapter;
    List<Food> mFoodList;
    static List<DishOrders> mDishOrders;
    SQLiteDatabase db;
    String mName, mQty, mImgPath;
    String mChiefId, mDishId;
    Cursor mCursor = null;
    public int inttotal;
    Boolean isInternetPresent = false;
    InternetConnectionDetector internetConnectionDetector;
    public static int araqum = 400;
    public static int sum;
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

        mFoodList = new ArrayList<>();
        mBasketRecyclerView = (RecyclerView) view.findViewById(R.id.basket_recycler);
        ItemTouchHelper itemTouch=new ItemTouchHelper(simpleCallback);
        itemTouch.attachToRecyclerView(mBasketRecyclerView);
        foodTotal = (TextView) view.findViewById(R.id.foodTotalText);
        mSupp=(TextView)view.findViewById(R.id.araqumText);
        mTotal=(TextView)view.findViewById(R.id.totalText);
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
                    NetworkDialogManager networkDialogManager =new NetworkDialogManager();
                    networkDialogManager.showAlertDialog(getContext(),getString(R.string.enable_internet),getString(R.string.internet_access),true);
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
        mDishOrders =new ArrayList<>();
        for (int i = 0; i < mFoodList.size(); i++) {
            Dish dish = new Dish();
            DishOrders dishOrder = new DishOrders();
            dish.setName(mFoodList.get(i).getName());
            dish.setDishId(mFoodList.get(i).getId());
            dishOrder.setDish(dish);
            dishOrder.setCount(Integer.parseInt(mFoodList.get(i).getCount()));
            mDishOrders.add(dishOrder);
        }
    }
    public void getList() {
        new DBRead().execute();
    }

    private class DBRead extends AsyncTask<String, Integer, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            mFoodList.clear();
            DataBaseHelper myDbHelper =  new DataBaseHelper(getContext());

            db = myDbHelper.getReadableDatabase();

            mCursor = db.rawQuery("select * from "+ DBDescription.Cart.TABLE_NAME, null);
            if (mCursor.getCount() != 0) {
                if (mCursor.moveToFirst()) {
                    do {
                        Food food=new Food();

                        mName = mCursor.getString(mCursor.getColumnIndex(DBDescription.Cart.COLUMN_NAME));
                        mQty = mCursor.getString(mCursor.getColumnIndex(DBDescription.Cart.COLUMN_QTY));
                        mPrice = mCursor.getString(mCursor.getColumnIndex(DBDescription.Cart.COLUMN_TOTAL));
                        mImgPath = mCursor.getString(mCursor.getColumnIndex(DBDescription.Cart.COLUMN_IMG_PATH));
                        mDishId = mCursor.getString(mCursor.getColumnIndex(DBDescription.Cart.COLUMN_DISH_ID));
                        mChiefId = mCursor.getString(mCursor.getColumnIndex(DBDescription.Cart.COLUMN_CHIEF_ID));


                        food.setName(mName);
                        food.setPrice(Integer.parseInt(mPrice));
                        food.setCount(mQty);
                        food.setPicture(mImgPath);
                        food.setChiefId(Long.parseLong(mChiefId));
                        food.setId(Long.parseLong(mDishId));
                        mFoodList.add(food);

                    } while (mCursor.moveToNext());
                }
            }

            mCursor.close();
            db.close();
            myDbHelper.close();
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {

            Log.d("Vishal", "" + mFoodList.size());
            inttotal = 0;
            mBasketRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mAdapter = new ChoiceAdapter(getActivity(), mFoodList);
            mBasketRecyclerView.setAdapter(mAdapter);
            for (int i = 0; i < mFoodList.size(); i++) {
                inttotal+= mFoodList.get(i).getPrice();
            }
            foodTotal.setText("Ընդհանուր:"+inttotal);
            mSupp.setText("Առաքում:"+araqum);
            sum=inttotal+araqum;
            mTotal.setText("Ընդամենը"+sum);
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
            mAdapter.deleteItem(mFoodList.get(position).getName());
            inttotal-= mFoodList.get(position).getPrice();
            FoodListFragment.cart-=Integer.parseInt(mFoodList.get(position).getCount());
            MainActivity.updateMenuCount(FoodListFragment.cart);
            CartPreferences.saveInt(getContext(),"count",FoodListFragment.cart);
            mFoodList.remove(position);
            foodTotal.setText("Ընդհանուր:"+inttotal);
            mAdapter.notifyItemRemoved(position);
            mAdapter.notifyItemRangeChanged(position, mFoodList.size());
        }
    };

    public static List<DishOrders> listInstance()
    {
        return mDishOrders;
    }
}

