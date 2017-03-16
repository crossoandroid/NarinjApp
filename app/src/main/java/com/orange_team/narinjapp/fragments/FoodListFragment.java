package com.orange_team.narinjapp.fragments;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.orange_team.narinjapp.R;
import com.orange_team.narinjapp.activities.MainActivity;
import com.orange_team.narinjapp.adapters.FoodListAdapter;
import com.orange_team.narinjapp.application.NApplication;
import com.orange_team.narinjapp.constants.Constants;
import com.orange_team.narinjapp.db.DBDescription;
import com.orange_team.narinjapp.db.DataBaseHelper;
import com.orange_team.narinjapp.enums.OrderCategories;
import com.orange_team.narinjapp.interfaces.RetrofitInterface;
import com.orange_team.narinjapp.model.Food;
import com.orange_team.narinjapp.model.OrderedItem;
import com.orange_team.narinjapp.model.Result;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.orange_team.narinjapp.utils.DividerItemDecor;


public class FoodListFragment extends Fragment  {

    FoodListAdapter mFoodListAdapter;
    List<Food> mFoodList;
    int mOrderQuantity;
    int mPageValue = 0;
    static String mCurrentCategory;
    static long mCurrentChefId;
    RecyclerView mRecyclerView;
    View mDialogView;
    Food mFood;
    int qtybyId;
    int totalbyId;
    RetrofitInterface mRetrofitInterface;
    Call<List<Result.NFood>> mFoodListCall;
    android.os.Handler mHandler;
    OrderedItem mOrderedItem;
    List<OrderedItem> mOrderedItemsList;
    int value = 1;
    Button count;
    TextView itemPrice;
    int total = 0;
    SQLiteDatabase db;
    Boolean mChecker = false;
    DataBaseHelper myDbHelpel;
    MediaPlayer mMediaPlayer;
    ProgressBar mProgressBar;

    public static final String CHEF_ID = "Chef partnerId";
    public static final String CATEGORY_KEY = "Category";
    public static final String MAKE_ORDER = "Make Order";
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
    public static final String DEFAULT_FOOD = "default_food";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_items_list, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        init();
        Log.d(Constants.LOG_TAG, "onViewCreated");
    }




    public void init() {
        Log.d(Constants.LOG_TAG, "init");

        createObjects();
        defineAdapterContent();
        defineComponents();

    }

    private void createObjects() {
        Log.d(Constants.LOG_TAG, "createObjects");

        mFoodList = new ArrayList<>();
        mFoodListAdapter = new FoodListAdapter(getActivity());
        mMediaPlayer = MediaPlayer.create(getContext(), R.raw.click_one);
        mHandler = new android.os.Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case HANDLER_MESSAGE_0: {
                        if (mFoodList.size() == 0) {
                            Toast.makeText(getContext(), "The list is currently empty.", Toast.LENGTH_SHORT).show();
                        }
                        if (mFoodList.size() % (mPageValue*10) == 0) {
                            mFoodListCall = mRetrofitInterface.getChefFoodList(mCurrentChefId, mPageValue, COUNT_VALUE);
                            getObjects(mFoodListCall);
                        }else {
                            mProgressBar.setVisibility(View.GONE);
                            mFoodListAdapter.setFoodList(mFoodList);
                        }


                    }
                    break;

                    case HANDLER_MESSAGE_1: {
                        if (mFoodList.size() == 0) {
                            Toast.makeText(getContext(), "The list is currently empty.", Toast.LENGTH_SHORT).show();
                        }

                        if (mFoodList.size() % (mPageValue*10) == 0) {
                            mFoodListCall = mRetrofitInterface.getFoodByCategory(mCurrentCategory, mPageValue, COUNT_VALUE);
                            getObjects(mFoodListCall);
                        }
                        else {
                            mProgressBar.setVisibility(View.GONE);
                            mFoodListAdapter.setFoodList(mFoodList);
                        }

                    }
                    break;
                }
            }
        };
        mRetrofitInterface = ((NApplication) getActivity().getApplication()).getRetrofitInterface();

    }

    private void defineComponents() {
        Log.d(Constants.LOG_TAG, "defineComponents");

        mProgressBar = (ProgressBar)getActivity().findViewById(R.id.progressBarFoodListFragment);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.itemRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecor(getContext(), LinearLayoutManager.VERTICAL));
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
        }
        defineFoodListCall();
    }

    private void defineFoodListCall() {
        if (mCurrentCategory.equals(CHEF)) {
            mCurrentChefId = getArguments().getLong(CHEF_ID);
            mFoodListCall = mRetrofitInterface.getChefFoodList(mCurrentChefId, mPageValue, COUNT_VALUE);
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
                    if (food.files.size() > 0) {
                        mFood.setPicture(IMAGE_BASE_URL + food.files.get(0).path);
                    }else{
                        mFood.setPicture(DEFAULT_FOOD);
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

        }

        @Override
        public void onAddButtonClicked(Food food) {

            Log.d(Constants.LOG_TAG, food.getName() + " Button");
            createDialog(food);

        }
    };


    private void createDialog(final Food food) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(food.getName());
        mDialogView = getActivity().getLayoutInflater().inflate(R.layout.selected_food, null);
        alertDialog.setView(mDialogView);
        ((TextView) mDialogView.findViewById(R.id.descDialog)).setText(food.getDesc());
        itemPrice = (TextView) mDialogView.findViewById(R.id.itemPriceDialog);
        itemPrice.setText("" + food.getPrice());
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
                if (value <= 1) {
                    value = 1;
                    total = value * food.getPrice();
                    count.setText("" + value);
                    itemPrice.setText(String.valueOf(total));
                } else {
                    value--;
                    total = value * food.getPrice();
                    count.setText("" + value);
                    itemPrice.setText(String.valueOf(total));
                }
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value++;
                total = value * food.getPrice();
                count.setText("" + value);
                itemPrice.setText(String.valueOf(total));
            }
        });


        final long dishId = food.getId();


        alertDialog.setPositiveButton(MAKE_ORDER, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                qtybyId = Integer.parseInt(count.getText().toString());
                int q = qtybyId;
                totalbyId = Integer.parseInt(itemPrice.getText().toString());
                int t = totalbyId;
                mOrderQuantity = Integer.parseInt(count.getText().toString());

                myDbHelpel = new DataBaseHelper(getContext());
                db = myDbHelpel.getWritableDatabase();
                ContentValues values = new ContentValues();


                values.put(DBDescription.Cart.COLUMN_NAME, "" + food.getName());
                values.put(DBDescription.Cart.COLUMN_QTY, count.getText().toString());
                values.put(DBDescription.Cart.COLUMN_TOTAL, itemPrice.getText().toString());

                values.put(DBDescription.Cart.COLUMN_IMG_PATH, food.getPicture());
                db.insert(DBDescription.Cart.TABLE_NAME, null, values);

                myDbHelpel.close();
                Toast.makeText(getContext(), getString(R.string.orderplace) + food.getName(), Toast.LENGTH_LONG).show();

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
}
