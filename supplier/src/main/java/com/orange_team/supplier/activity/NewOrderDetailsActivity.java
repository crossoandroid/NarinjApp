package com.orange_team.supplier.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orange_team.supplier.R;
import com.orange_team.supplier.adapters.CustomAdapter;
import com.orange_team.supplier.adapters.ListViewAdapter;
import com.orange_team.supplier.fragments.NewOrderFragment;
import com.orange_team.supplier.models.Body;

import java.util.ArrayList;
import java.util.List;

import static com.orange_team.supplier.activity.MainActivity.mViewPager;

public class NewOrderDetailsActivity extends AppCompatActivity {

    private TextView mNewOrderName, mNewOrderAddress, mNewOrderPhoneNumber, mNewOrderPrice;
    private Button mToTake;
    private FirebaseAuth mAuth;
    private ListView mDishOrdersList;
    private Body mBody;
    List<String> mDishesName, mDishesCount;
    ListViewAdapter mAdapter;
    List<String> mKeyList;
    public static boolean take = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_details);
        mBody = new Body();
        Intent intent = getIntent();
        mBody = (Body) intent.getSerializableExtra("Body");
        init();
        Log.d("newAct", mBody.getPhone());
    }

    private void init() {
        mDishesName = new ArrayList<>();
        mDishesCount = new ArrayList<>();
        mNewOrderName = (TextView) findViewById(R.id.newOrderDetailsName);
        mNewOrderAddress = (TextView) findViewById(R.id.newOrderDetailsAddress);
        mNewOrderPhoneNumber = (TextView) findViewById(R.id.newOrderDetailsPhoneNumber);
        mNewOrderPrice = (TextView) findViewById(R.id.newOrderDetailsPrice);
        mDishOrdersList = (ListView) findViewById(R.id.current_order_list);
        mAdapter = new ListViewAdapter(NewOrderDetailsActivity.this,0,mBody.getDishOrders());
        mDishOrdersList.setAdapter(mAdapter);

        mToTake = (Button) findViewById(R.id.ToTake);

        mNewOrderName.setText("Անուն։ " + mBody.getName());
        mNewOrderPhoneNumber.setText("Հեռ․։ " + mBody.getPhone());
        mNewOrderAddress.setText("Հասցե։ " + mBody.getLocation());
        mNewOrderPrice.setText("Գումար։ " + mBody.getPrice() + " դրամ");

        if (take) {
            mToTake.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    take = false;
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Orders").child(mBody.getKey());
                    mAuth = FirebaseAuth.getInstance();

                    FirebaseUser user = mAuth.getInstance().getCurrentUser();

                    if (user != null) {
                        String id = user.getUid();
                        myRef.child("Status").setValue("retrieved");
                        myRef.child("SupplierId").setValue(id);
                    }

                    FirebaseDatabase newDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference newRef = newDatabase.getReference().getRef().child("Notifications").child("RetrievedNot");
                    newRef.child("orderID").setValue(mBody.getKey());
                    //newRef.setValue(mBody.getKey());
                    newRef.child("status").setValue(0);

                    mViewPager.setCurrentItem(CustomAdapter.CURRENT_ORDER_FRAGMENT_POSITION);
                    finish();
                }
            });
        }else {
            Toast.makeText(NewOrderDetailsActivity.this, "Դուք արդեն ունեք ակտիվ պատվեր", Toast.LENGTH_SHORT).show();
        }
    }

}
