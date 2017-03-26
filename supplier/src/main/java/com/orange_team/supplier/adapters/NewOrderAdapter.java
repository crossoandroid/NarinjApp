package com.orange_team.supplier.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orange_team.supplier.R;
import com.orange_team.supplier.adapters.viewholder.NewOrderViewHolder;
import com.orange_team.supplier.models.OrderDetails;

import java.util.List;


public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderViewHolder> {

    Context mContext;
    List<OrderDetails> mOrderDetailsList;
    IOnItemSelectedListener mOnItemSelectedListener;

    public NewOrderAdapter(Context context, List<OrderDetails> orderDetailsList){
        mContext = context;
        mOrderDetailsList = orderDetailsList;
    }

    @Override
    public NewOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_new_order_details, parent, false);
        NewOrderViewHolder viewHolder = new NewOrderViewHolder(view);
        viewHolder.setOnClickListener(new NewOrderViewHolder.IOnClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mOnItemSelectedListener!=null){
                    mOnItemSelectedListener.onItemSelected(mOrderDetailsList.get(position));
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewOrderViewHolder holder, int position) {
        holder.setData(mOrderDetailsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mOrderDetailsList.size();
    }


    public void setOnItemSelectedListener(IOnItemSelectedListener onItemSelectedListener) {
        mOnItemSelectedListener = onItemSelectedListener;
    }

    public interface IOnItemSelectedListener {

        void onItemSelected(OrderDetails orderDetails);

    }
}
