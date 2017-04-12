package com.orange_team.supplier.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orange_team.supplier.R;
import com.orange_team.supplier.activity.HistoryActivity;
import com.orange_team.supplier.activity.MainActivity;
import com.orange_team.supplier.adapters.OrderHistoryAdapter;
import com.orange_team.supplier.models.Body;
import com.orange_team.supplier.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class OrderHistoryFragment extends Fragment {

    private OrderHistoryAdapter mOrderHistoryAdapter;
    private RecyclerView mRecyclerView;
    private List<Body> mBodyList;
    private FirebaseAuth mAuth;
    private String mUserId;
    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(MainActivity.LOG_TAG, "onViewCreated History Fragment");
        mBodyList = new ArrayList<>();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.orderHistoryRecycler);
        mProgressBar = (ProgressBar) view.findViewById(R.id.historyProgressBar);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        mProgressBar.setVisibility(View.VISIBLE);
        init();
    }

    private void init() {
        defineComponents();
        defineObject();
    }

    private void defineObject() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().getRef().child("Orders");

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getInstance().getCurrentUser();

        if (user != null) {
            mUserId = user.getUid();
        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    mBodyList.clear();
                    for(DataSnapshot dd : dataSnapshot.getChildren()){

                        Body body1 = dd.getValue(Body.class);
                        body1.setKey(dd.getKey());
                        String status = body1.getStatus();
                        String userId = body1.getSupplierId();
                        if (TextUtils.equals(status,"delivered") && TextUtils.equals(userId,mUserId)) {
                            mBodyList.add(body1);
                            mProgressBar.setVisibility(View.GONE);
                        }
                        mOrderHistoryAdapter = new OrderHistoryAdapter(getContext(), mBodyList);
                        mOrderHistoryAdapter.setOnItemSelectedListener(mOnItemSelectedListener);
                        mRecyclerView.setAdapter(mOrderHistoryAdapter);
                    }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void defineComponents() {
        mOrderHistoryAdapter = new OrderHistoryAdapter(getContext(), mBodyList);
        mOrderHistoryAdapter.setOnItemSelectedListener(mOnItemSelectedListener);
        mRecyclerView.setAdapter(mOrderHistoryAdapter);
    }

    OrderHistoryAdapter.IOnItemSelectedListener mOnItemSelectedListener = new OrderHistoryAdapter.IOnItemSelectedListener() {
            @Override
            public void onItemSelected(Body body) {
                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                intent.putExtra("OrderFromHistory",body);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
            }
    };
}
