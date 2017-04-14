package com.orange_team.supplier.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orange_team.supplier.R;
import com.orange_team.supplier.activity.MainActivity;
import com.orange_team.supplier.adapters.CustomAdapter;
import com.orange_team.supplier.adapters.ListViewAdapter;
import com.orange_team.supplier.adapters.NewOrderAdapter;
import com.orange_team.supplier.models.Body;
import com.orange_team.supplier.service.LocationService;

import java.util.ArrayList;
import java.util.List;


public class CurrentOrderFragment extends Fragment {

    private TextView mUserNameTV, mPhoneTV, mAddressTV, mNoCurrentJobsTV, mTotalPrice;
    private Button mDeliveredBtn,mWayButton;
    private List<Body> bodies;
    private ListViewAdapter mNewOrderAdapter;
    private Body body1, body2;
    private String mUserId;
    private ListView mCurrentOrdersList;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private List<String> mKeyList;
    String ss="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_order, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(MainActivity.LOG_TAG, "onViewCreated Current Fragment");

        mUserNameTV = (TextView) view.findViewById(R.id.userNameCurrentFragment);
        mPhoneTV = (TextView) view.findViewById(R.id.userPhoneCurrentFragment);
        mAddressTV = (TextView) view.findViewById(R.id.userAddressCurrentFragment);
        mNoCurrentJobsTV = (TextView) view.findViewById(R.id.noCurrentJobsCurrentFragment);
        mTotalPrice = (TextView) view.findViewById(R.id.totalPriceCurrentFragment);
        mDeliveredBtn = (Button) view.findViewById(R.id.deliveredBtn);
        mWayButton = (Button) view.findViewById(R.id.wayButton);
        mCurrentOrdersList = (ListView) view.findViewById(R.id.currentOrderListView);

        init();
    }

    private void init() {
        mKeyList = NewOrderFragment.getInstance();
        defineObject();
        defineComponents();
    }

    private void defineObject() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().getRef().child("Orders");
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getInstance().getCurrentUser();

        if (user != null) {
            mUserId = user.getUid();
        }

        bodies = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bodies.clear();
                    for (DataSnapshot dd : dataSnapshot.getChildren()) {
                        body1 = dd.getValue(Body.class);
                        body1.setKey(dd.getKey());
                        String status = body1.getStatus();
                        String userId = body1.getSupplierId();
                        if (TextUtils.equals(status,"retrieved") && TextUtils.equals(userId,mUserId)) {
                            mDeliveredBtn.setVisibility(View.VISIBLE);
                            mWayButton.setVisibility(View.VISIBLE);
                            body2 = body1;
                            mNewOrderAdapter = new ListViewAdapter(getContext(), 0, body2.getDishOrders());
                            mCurrentOrdersList.setAdapter(mNewOrderAdapter);
                            mUserNameTV.setText(body2.getName());
                            mAddressTV.setText(body2.getLocation());
                            mPhoneTV.setText(body2.getPhone());
                            mTotalPrice.setText(body2.getPrice() + " դրամ");
                        }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Նոր պատվերներ չկան", Toast.LENGTH_SHORT).show();
                Log.d("dbError", databaseError.getMessage());
            }
        });

    }

    private void defineComponents() {
        mDeliveredBtn.setOnClickListener(mOnClickListener);
        mWayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendId();
                getActivity().startService(new Intent(getActivity(), LocationService.class));
            }
        });
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
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            }).show();
        }
    };

    public void save() {
        ((ViewPager) getActivity().findViewById(R.id.viewPager)).setCurrentItem(CustomAdapter.ORDER_HISTORY_FRAGMENT_POSITION);



        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Orders").child(body2.getKey());
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getInstance().getCurrentUser();

        if (user != null) {
            myRef.child("Status").setValue("delivered");
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();

        FirebaseDatabase nDatabase = FirebaseDatabase.getInstance();
        DatabaseReference nRef = nDatabase.getReference().getRef().child("Notifications").child("DeliveredNot");

        FirebaseDatabase mData = FirebaseDatabase.getInstance();
        DatabaseReference mDR = mData.getReference().getRef().child("Notifications").child("RetrievedNot");

        mDR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ss=(String)dataSnapshot.child("orderID").getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        nRef.child("orderID").setValue(ss);
        nRef.child("status").setValue(0);

    }

    public void sendId(){
        FirebaseDatabase newDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = newDatabase.getReference().getRef().child("Notifications").child("SupplierGoNot");

        FirebaseDatabase mData = FirebaseDatabase.getInstance();
        DatabaseReference mDR = mData.getReference().getRef().child("Notifications").child("RetrievedNot");

        mDR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ss=(String)dataSnapshot.child("orderID").getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRef.child("orderID").setValue(ss);
        mRef.child("status").setValue(0);
    }
}
