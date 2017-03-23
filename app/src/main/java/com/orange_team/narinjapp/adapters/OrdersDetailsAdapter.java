package com.orange_team.narinjapp.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orange_team.narinjapp.R;
import com.orange_team.narinjapp.adapters.viewholder.OrdersDetailsViewHolder;
import com.orange_team.narinjapp.fragments.MessageFragment;
import com.orange_team.narinjapp.model.OrdersDetails;

import java.util.Calendar;
import java.util.List;

public class OrdersDetailsAdapter extends RecyclerView.Adapter<OrdersDetailsViewHolder> {

    Context mContext;
    List<OrdersDetails> mOrderDetails;

    public OrdersDetailsAdapter(Context mContext, List<OrdersDetails> mOrderNumber) {
        this.mContext = mContext;
        this.mOrderDetails = mOrderNumber;
    }

    @Override
    public OrdersDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.orders_details_item, parent, false);
        OrdersDetailsViewHolder ordersDetailsViewHolder = new OrdersDetailsViewHolder(view, mContext);

        return ordersDetailsViewHolder;
    }

    @Override
    public void onBindViewHolder(OrdersDetailsViewHolder holder, int position) {

        holder.setData(mOrderDetails.get(position));
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                MessageFragment messageFragment = new MessageFragment();
//                Log.v("OrdersFrag", "Open Message Fragment");
//                fragmentTransaction.replace(R.id.orders_details_fragment, messageFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mOrderDetails.size();
    }

}
