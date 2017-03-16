package com.orange_team.narinjapp.fragments;

import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.orange_team.narinjapp.R;
import com.orange_team.narinjapp.activities.CheckNetworkConnection;
import com.orange_team.narinjapp.adapters.ChoiceAdapter;
import com.orange_team.narinjapp.db.DBDescription;
import com.orange_team.narinjapp.db.DataBaseHelper;
import com.orange_team.narinjapp.utils.AlertDialogManager;
import com.orange_team.narinjapp.utils.DividerItemDecor;
import com.orange_team.narinjapp.model.Food;
import com.squareup.picasso.Picasso;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BasketFragment extends Fragment {

    TextView mTotal;
    Button mContinueBtn;
    String price;
    RecyclerView mBasketRecyclerView;
    ChoiceAdapter mAdapter;
    List<Food> foodList;
    SQLiteDatabase db;
    String name, qty, imgPath;
    Cursor cursor = null;
    int inttotal;

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
                openOrderDetailsFragment();
            }
        });

    }

    private void openOrderDetailsFragment() {

        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        OrderDetailsFragment orderDetailsFragment = new OrderDetailsFragment();
        fragmentTransaction.replace(R.id.basket_fragment, orderDetailsFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

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
                        Food food = new Food();

                        name = cursor.getString(cursor
                                .getColumnIndex(DBDescription.Cart.COLUMN_NAME));
                        qty = cursor
                                .getString(cursor.getColumnIndex(DBDescription.Cart.COLUMN_QTY));

                        price = cursor.getString(cursor.getColumnIndex(DBDescription.Cart.COLUMN_TOTAL));

                        imgPath=cursor.getString(cursor.getColumnIndex(DBDescription.Cart.COLUMN_IMG_PATH));


                        food.setName(name);
                        food.setPrice(Integer.parseInt(price));
                        food.setCount(qty);
                        food.setPicture(imgPath);

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
            mBasketRecyclerView.addItemDecoration(new DividerItemDecor(getContext(), LinearLayoutManager.VERTICAL));
            mAdapter = new ChoiceAdapter(getActivity(), foodList);
            mBasketRecyclerView.setAdapter(mAdapter);
            for (int i = 0; i < foodList.size(); i++) {
                inttotal+=foodList.get(i).getPrice();
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
            inttotal-= foodList.get(position).getPrice();
            foodList.remove(position);
            mTotal.setText("Ընդամենը:"+inttotal);
            mAdapter.notifyItemRemoved(position);
            mAdapter.notifyItemRangeChanged(position,foodList.size());
        }
    };
}

