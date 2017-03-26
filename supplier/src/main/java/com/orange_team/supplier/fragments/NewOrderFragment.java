package com.orange_team.supplier.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orange_team.supplier.R;
import com.orange_team.supplier.activity.MainActivity;
import com.orange_team.supplier.adapters.NewOrderAdapter;
import com.orange_team.supplier.models.Dish;
import com.orange_team.supplier.models.OrderDetails;
import com.orange_team.supplier.models.OrderedDishAndCount;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;


public class NewOrderFragment extends Fragment {

    RecyclerView mRecyclerView;
    NewOrderAdapter mNewOrderAdapter;
    List<OrderDetails> mOrderDetailsList;
    boolean isCreated = false;
    boolean isFirstTimeCreated = true;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_order, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(MainActivity.LOG_TAG, "onViewCreated New Order Fragment");
        mRecyclerView = (RecyclerView)getActivity().findViewById(R.id.newOrderRecycler);

        if(isFirstTimeCreated) {
            Log.d(MainActivity.LOG_TAG, "calling init by onViewCreated New Order Fragment");
            init();
            isFirstTimeCreated = false;
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if (isCreated){
                Log.d(MainActivity.LOG_TAG, "calling init by visibility New Order Fragment");
                init();
            }
            isCreated = true;
        }
    }

    private void init() {


        defineObject();
        defineComponents();

    }

    private void defineObject() {


        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference().getRef();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value=dataSnapshot.getValue(String.class);
                Log.d("TAG",value+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void defineComponents() {
        mNewOrderAdapter = new NewOrderAdapter(getContext(), mOrderDetailsList);
        mNewOrderAdapter.setOnItemSelectedListener(mOnItemSelectedListener);
        mRecyclerView.setAdapter(mNewOrderAdapter);
    }


    NewOrderAdapter.IOnItemSelectedListener mOnItemSelectedListener = new NewOrderAdapter.IOnItemSelectedListener() {
        @Override
        public void onItemSelected(OrderDetails orderDetails) {



        }
    };

}
