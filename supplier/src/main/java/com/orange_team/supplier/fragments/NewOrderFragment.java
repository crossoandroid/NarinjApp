package com.orange_team.supplier.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orange_team.supplier.R;
import com.orange_team.supplier.activity.MainActivity;
import com.orange_team.supplier.activity.NewOrderDetailsActivity;
import com.orange_team.supplier.adapters.NewOrderAdapter;
import com.orange_team.supplier.models.Body;
import com.orange_team.supplier.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewOrderFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private NewOrderAdapter mNewOrderAdapter;
    private List<Body> mBodies;
    private ProgressBar mProgressBar;
    Body mBody;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_order, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(MainActivity.LOG_TAG, "onViewCreated New Order Fragment");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.newOrderRecycler);
        mProgressBar = (ProgressBar) view.findViewById(R.id.newOrderProgressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        init();
    }

    private void init() {
        defineComponents();
        defineObject();
    }

    private void defineObject() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().getRef().child("Orders");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mBodies.clear();
                for (DataSnapshot dd : dataSnapshot.getChildren()) {
                    if (dd != null) {
                        Body body1 = dd.getValue(Body.class);
                        body1.setKey(dd.getKey());
                        if (TextUtils.equals(body1.getStatus(),"")) {
                            mBodies.add(body1);
                        }
                        mProgressBar.setVisibility(View.INVISIBLE);
                        mNewOrderAdapter = new NewOrderAdapter(getContext(), mBodies);
                        mNewOrderAdapter.setOnItemSelectedListener(mOnItemSelectedListener);
                        mRecyclerView.setAdapter(mNewOrderAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Նոր պատվերներ չկան", Toast.LENGTH_SHORT).show();
                Log.d("dbError", databaseError.getMessage());
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void defineComponents() {
        mBody = new Body();
        mBodies = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    NewOrderAdapter.IOnItemSelectedListener mOnItemSelectedListener = new NewOrderAdapter.IOnItemSelectedListener() {
        @Override
        public void onItemSelected(Body body) {
            Intent intent = new Intent(getActivity(), NewOrderDetailsActivity.class);
            intent.putExtra("Body", body);
            startActivity(intent);
        }
    };

}
