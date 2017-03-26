package com.orange_team.supplier.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orange_team.supplier.R;
import com.orange_team.supplier.adapters.viewholder.OrderHistoryViewHolder;
import com.orange_team.supplier.models.OrderDetails;

import java.util.List;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;


public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryViewHolder> {

    Context mContext;
    IOnItemSelectedListener mOnItemSelectedListener;
    RealmResults<OrderDetails> mOrderDetailsList;

    public OrderHistoryAdapter(Context context, RealmResults<OrderDetails> orderDetailsList) {
        mContext = context;
        mOrderDetailsList = orderDetailsList;
        mOrderDetailsList.addChangeListener(mRealmChangeListener);
    }

    RealmChangeListener mRealmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object element) {
            notifyDataSetChanged();
        }
    };

    @Override
    public OrderHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_history, parent, false);
        OrderHistoryViewHolder viewHolder = new OrderHistoryViewHolder(view);
        viewHolder.setOnItemClickListener(new OrderHistoryViewHolder.IOnItemClick() {
            @Override
            public void itemClick(int position) {
                if(mOnItemSelectedListener!=null){
                    mOnItemSelectedListener.onItemSelected(mOrderDetailsList.get(position));
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OrderHistoryViewHolder holder, int position) {
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
