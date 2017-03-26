/*
package com.orange_team.user_application.narinj.suppliertest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.orange_team.user_application.narinj.suppliertest.R;
import com.orange_team.user_application.narinj.suppliertest.activity.MainActivity;
import com.orange_team.user_application.narinj.suppliertest.models.OrderDetails;

import io.realm.Realm;


*/
/**
 * Created by Hayk on 20-Mar-17.
 *//*


public class NewOrderClaimFragment  extends Fragment{  //TODO will be replaced by NewOrderActivity

    Realm mRealm;
    OrderDetails mOrderDetails;
    TextView mUserNameTV, mPhoneTV, mAddressTV;
    Button mDeliveredBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_order_claim, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(MainActivity.LOG_TAG, "onViewCreated New Order Claim Fragment");

        init();
    }


    private void init() {

        defineObject();
        defineComponents();
        defineItemsList();

    }

    private void defineObject() {
        if(getArguments()!=null){
            mOrderDetails = getArguments().getParcelable(DetailedFoodItemsFragment.ARGS_KEY);
        }
    }

    private void defineComponents() {

        mUserNameTV = (TextView)getActivity().findViewById(R.id.userNameNewOrderClaim);
        mPhoneTV = (TextView)getActivity().findViewById(R.id.userPhoneNewOrderClaim);
        mAddressTV = (TextView)getActivity().findViewById(R.id.userAddressNewOrderClaim);
        mDeliveredBtn = (Button) getActivity().findViewById(R.id.claimOrderBtn);

        mUserNameTV.setText(mOrderDetails.getUserName());
        mPhoneTV.setText(mOrderDetails.getPhone());
        mAddressTV.setText(mOrderDetails.getAddress());
        mDeliveredBtn.setOnClickListener(mOnClickListener);

    }

    private void defineItemsList() {

        DetailedFoodItemsFragment detailedFoodItemsFragment = new DetailedFoodItemsFragment();
        Bundle args = new Bundle();
        args.putParcelable(DetailedFoodItemsFragment.ARGS_KEY, mOrderDetails);
        detailedFoodItemsFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.detailedFoodItemsContainer, detailedFoodItemsFragment);
        transaction.commit();
    }


    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {    //TODO send claim to server

            mOrderDetails.setRealmIdentifier(OrderDetails.CURRENT_OBJECT);
            mRealm.init(getContext());
            mRealm= Realm.getDefaultInstance();
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    mRealm.copyToRealm(mOrderDetails);
                }
            });
        }
    };
}
*/
