package com.orange_team.supplier.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.orange_team.supplier.R;
import com.orange_team.supplier.activity.MainActivity;
import com.orange_team.supplier.adapters.CustomAdapter;
import com.orange_team.supplier.adapters.DetailedFoodItemsAdapter;
import com.orange_team.supplier.models.OrderDetails;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;


public class CurrentOrderFragment extends Fragment {

    Realm mRealm;
    OrderDetails mOrderDetails;
    TextView mUserNameTV, mPhoneTV, mAddressTV, mNoCurrentJobsTV;
    RecyclerView mRecyclerView;
    DetailedFoodItemsAdapter mDetailedFoodItemsAdapter;
    Button mDeliveredBtn;
    boolean mInitializer = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_order, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        Log.d(MainActivity.LOG_TAG, "onViewCreated Current Fragment");
        mRealm.init(getContext());
        mRealm=Realm.getDefaultInstance();
        mUserNameTV = (TextView)getActivity().findViewById(R.id.userNameCurrentFragment);
        mPhoneTV = (TextView)getActivity().findViewById(R.id.userPhoneCurrentFragment);
        mAddressTV = (TextView)getActivity().findViewById(R.id.userAddressCurrentFragment);
        mNoCurrentJobsTV = (TextView)getActivity().findViewById(R.id.noCurrentJobsCurrentFragment);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerCurrentFragment);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mDeliveredBtn = (Button) getActivity().findViewById(R.id.deliveredBtn);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            init();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    private void init() {

        if(mInitializer) {
            Log.d(MainActivity.LOG_TAG, ">>>>>>>>>>>>>>>>initializing until current order occurs<<<<<<<<<<<<<<");
            defineObject();
            if (mOrderDetails != null) {
                mInitializer=false;
                Log.d(MainActivity.LOG_TAG, ">>>>>>>>>>>>>>>>initializer set to false, not init will be done until order finalisation<<<<<<<<<<<<<<<");
                defineComponents();
            }
        }
    }

    private void defineObject() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mOrderDetails = mRealm.where(OrderDetails.class)
                        .equalTo(OrderDetails.REALM_IDENTIFIER, OrderDetails.CURRENT_OBJECT)
                        .findFirst();

            }
        });
    }

    private void defineComponents() {

        mUserNameTV.setVisibility(View.VISIBLE);
        mPhoneTV.setVisibility(View.VISIBLE);
        mAddressTV.setVisibility(View.VISIBLE);
        mNoCurrentJobsTV.setVisibility(View.INVISIBLE);
        mDeliveredBtn.setVisibility(View.VISIBLE);

        mUserNameTV.setText(mOrderDetails.getUserName());
        mPhoneTV.setText(mOrderDetails.getPhone());
        mAddressTV.setText(mOrderDetails.getAddress());
        mDetailedFoodItemsAdapter = new DetailedFoodItemsAdapter(getContext(), mOrderDetails.getOrderedDishAndCountList());
        mRecyclerView.setAdapter(mDetailedFoodItemsAdapter);
        mDeliveredBtn.setOnClickListener(mOnClickListener);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Are you sure the order is completed?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    save();

                    return;
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   return;
                }
            }).show();




        }
    };

    public void save(){
        mInitializer=true;

        Log.d(MainActivity.LOG_TAG, ">>>>>>>>>>>>>>>>initializer set to true, for further init()<<<<<<<<<<<<<<<");
        mUserNameTV.setVisibility(View.INVISIBLE);
        mPhoneTV.setVisibility(View.INVISIBLE);
        mAddressTV.setVisibility(View.INVISIBLE);
        mNoCurrentJobsTV.setVisibility(View.VISIBLE);
        mDeliveredBtn.setVisibility(View.INVISIBLE);
        mRecyclerView.setAdapter(null);

        mRealm.beginTransaction();
        mOrderDetails.setRealmIdentifier(OrderDetails.HISTORY_OBJECT);
        DateFormat df = new SimpleDateFormat("dd MM yyyy, HH:mm");
        mOrderDetails.setDateAndTime((df.format(Calendar.getInstance().getTime())));
        mRealm.commitTransaction();

        ((ViewPager)getActivity().findViewById(R.id.viewPager)).setCurrentItem(CustomAdapter.ORDER_HISTORY_FRAGMENT_POSITION);

    }



}
