package com.orange_team.narinjapp.adapters.viewholder;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orange_team.narinjapp.R;
import com.orange_team.narinjapp.constants.Constants;
import com.orange_team.narinjapp.fragments.MessageFragment;
import com.orange_team.narinjapp.model.OrdersDetails;

import java.util.Calendar;


public class OrdersDetailsViewHolder extends RecyclerView.ViewHolder {

    public TextView mOrderNumberr, mDate;
    FragmentManager fragmentManager;
    Calendar calendar = Calendar.getInstance();

    Context mContext;

    public OrdersDetailsViewHolder(View itemView, Context context) {
        super(itemView);
        mContext=context;
        mOrderNumberr = (TextView) itemView.findViewById(R.id.orderNumber);
        mDate = (TextView) itemView.findViewById(R.id.date);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                MessageFragment messageFragment = new MessageFragment();
                fragmentTransaction.replace(R.id.fragment_main, messageFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                Log.d(Constants.LOG_TAG , "message fragment");

            }
        });


    }

    public void setData(OrdersDetails ordersDetails){
        mOrderNumberr.setText(ordersDetails.getId() + "");
        mDate.setText(ordersDetails.getDate());
    }
}
