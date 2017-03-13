package com.orange_team.narinjapp.fragments;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
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
import android.widget.Button;
import android.widget.ImageView;
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
import com.orange_team.narinjapp.db.DBDescription;
import com.squareup.picasso.Picasso;
import com.orange_team.narinjapp.db.DataBaseHelper;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FoodListFragment extends Fragment  {

    FoodListAdapter mFoodListAdapter;
    List<Food> mFoodList;
    int mOrderQuantity;
    int mPageValue = 0;
    String mCurrentCategory;
    OrderedItem mOrderedItem;
    List<OrderedItem> mOrderedItemsList;
    RecyclerView mRecyclerView;
    View mDialogView;
    Food mFood;
    Boolean mChecker = false;
    RetrofitInterface mRetrofitInterface;
    Call<List<Result.NFood>> mFoodListCall;
    android.os.Handler mHandler;
    int value = 1;
    Button count;
    TextView itemPrice;
    int total = 0;
    SQLiteDatabase db;
    DataBaseHelper myDbHelpel;
    MediaPlayer mMediaPlayer;

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
    public static final String CHEF = "chef";
    public static final String IMAGE_BASE_URL = "http://narinj.am/resources/site/assets/img/";
    public static final int COUNT_VALUE = 10;
    public static final int HANDLER_MESSAGE_0 = 0;
    public static final int HANDLER_MESSAGE_1 = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_items_list, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        init();
    }


    public void init() {

        createObjects();
        defineAdapterContent();
        defineComponents();

    }

    private void createObjects() {

        mFoodList = new ArrayList<>();
        mOrderedItemsList = new ArrayList<>();
        mFoodListAdapter = new FoodListAdapter(getActivity());
        mMediaPlayer = MediaPlayer.create(getContext(), R.raw.click_one);
        mHandler = new android.os.Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case HANDLER_MESSAGE_0: {
                        mFoodListAdapter.setFoodList(mFoodList);
                        if (mFoodList.size() == 0) {
                            Toast.makeText(getContext(), "The list is currently empty.", Toast.LENGTH_SHORT).show();
                        }
                        if (mFoodList.size() % 10 == 0) {
                            mFoodListCall = mRetrofitInterface.getChefFoodList(getArguments().getLong(CHEF_ID), mPageValue, COUNT_VALUE);
                            getObjects(mFoodListCall);
                        }
                    }
                    break;

                    case HANDLER_MESSAGE_1: {
                        mFoodListAdapter.setFoodList(mFoodList);
                        if (mFoodList.size() == 0) {
                            Toast.makeText(getContext(), "The list is currently empty.", Toast.LENGTH_SHORT).show();
                        }
                        if (mFoodList.size() % 10 == 0) {
                            mFoodListCall = mRetrofitInterface.getFoodByCategory(mCurrentCategory, mPageValue, COUNT_VALUE);
                            getObjects(mFoodListCall);
                        }
                    }
                    break;
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

        if (getArguments() != null) {

            OrderCategories request = (OrderCategories) getArguments().getSerializable(CATEGORY_KEY);
            switch (request) {
                case SOUP:
                    mCurrentCategory = SOUP;
                    break;

                case SALAD:
                    mCurrentCategory = SALAD;
                    break;

                case LUNCH:
                    mCurrentCategory = LUNCH;
                    break;

                case CAKE:
                    mCurrentCategory = CAKE;
                    break;

                case HOT_DISHES:
                    mCurrentCategory = HOT_DISHES;
                    break;

                case GARNISH:
                    mCurrentCategory = GARNISH;
                    break;

                case ALL:
                    mCurrentCategory = ALL;
                    break;

                case CHEF:
                    mCurrentCategory = CHEF;
                    break;
            }
            defineFoodListCall();
        }
    }

    private void defineFoodListCall() {
        if (mCurrentCategory.equals(CHEF)) {
            mFoodListCall = mRetrofitInterface.getChefFoodList(getArguments().getLong(CHEF_ID), mPageValue, COUNT_VALUE);
        } else {
            mFoodListCall = mRetrofitInterface.getFoodByCategory(mCurrentCategory, mPageValue, COUNT_VALUE);
        }
        getObjects(mFoodListCall);
    }

    private void getObjects(Call<List<Result.NFood>> FoodListCall) {

        FoodListCall.enqueue(new Callback<List<Result.NFood>>() {
            @Override
            public void onResponse(Call<List<Result.NFood>> call, Response<List<Result.NFood>> response) {

                List<Result.NFood> NFoodList = response.body();
                for (Result.NFood food : NFoodList) {
                    mFood = new Food();
                    mFood.setId(food.dishId);
                    mFood.setName(food.name);
                    mFood.setDesc(food.description);
                    mFood.setPrice(food.price);
                    if (food.files.get(0).path != null) {
                        mFood.setPicture(IMAGE_BASE_URL + food.files.get(0).path);
                    }
                    mFoodList.add(mFood);
                }
                mPageValue++;
                if (mCurrentCategory.equals(CHEF)) {
                    mHandler.sendEmptyMessage(HANDLER_MESSAGE_0);
                } else {
                    mHandler.sendEmptyMessage(HANDLER_MESSAGE_1);
                }
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

            Log.d(Constants.LOG_TAG, food.getName() + " Button");
            addOneItem(food);

        }
    };

    private void createDialog(final Food food) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(food.getName());
        mDialogView = getActivity().getLayoutInflater().inflate(R.layout.selected_food, null);
        alertDialog.setView(mDialogView);
        ((TextView) mDialogView.findViewById(R.id.descDialog)).setText(food.getDesc());
        itemPrice = (TextView) mDialogView.findViewById(R.id.itemPriceDialog);
        itemPrice.setText("" + food.getPrice() + " ԴՐԱՄ");
        ((TextView) mDialogView.findViewById(R.id.chefName)).setText(food.getChefName());
        Picasso.with(getContext()).load(food.getPicture()).resize(300, 200).centerCrop().into((ImageView) mDialogView.findViewById(R.id.foodImageDialog));
        Button minus = (Button) mDialogView.findViewById(R.id.btn_minus);
        count = (Button) mDialogView.findViewById(R.id.btn_display);
        Button plus = (Button) mDialogView.findViewById(R.id.btn_plus);
        value = 1;
        count.setText("" + value);

        minus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mMediaPlayer.isPlaying()){
                    mMediaPlayer.stop();
                }
                mMediaPlayer.start();
                if (value <= 1) {
                    value = 1;
                    total = value * food.getPrice();
                    count.setText("" + value);
                    itemPrice.setText(String.valueOf(total) + " ԴՐԱՄ");
                } else {
                    value--;
                    total = value * food.getPrice();
                    count.setText("" + value);
                    itemPrice.setText(String.valueOf(total) + " ԴՐԱՄ");
                }
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaPlayer.isPlaying()){
                    mMediaPlayer.stop();
                }
                mMediaPlayer.start();
                value++;
                total = value * food.getPrice();
                count.setText("" + value);
                itemPrice.setText(String.valueOf(total) + " ԴՐԱՄ");
            }
        });


        alertDialog.setPositiveButton(MAKE_ORDER, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mMediaPlayer.isPlaying()){
                    mMediaPlayer.stop();
                }
                mMediaPlayer.start();
                mOrderQuantity = Integer.parseInt(count.getText().toString());

                MainActivity.updateMenuCount(MainActivity.menuCount+mOrderQuantity);

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
        if (mMediaPlayer.isPlaying()){
            mMediaPlayer.stop();
        }
        mMediaPlayer.start();
        MainActivity.updateMenuCount(MainActivity.menuCount + SINGLE_ORDER_QUANTITY);
        total = mOrderQuantity * food.getPrice();
        ContentValues values = new ContentValues();
        values.put(DBDescription.Cart.COLUMN_NAME, "" + food.getName());
        values.put(DBDescription.Cart.COLUMN_QTY, value);
        values.put(DBDescription.Cart.COLUMN_TOTAL, total);
        values.put(DBDescription.Cart.COLUMN_IMG_PATH, total);
        getContext().getContentResolver().insert(DBDescription.Cart.NOTE_URI,values);
    }
}
