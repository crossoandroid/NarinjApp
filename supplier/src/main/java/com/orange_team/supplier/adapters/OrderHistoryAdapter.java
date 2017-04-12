package com.orange_team.supplier.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orange_team.supplier.R;
import com.orange_team.supplier.adapters.viewholder.OrderHistoryViewHolder;
import com.orange_team.supplier.models.Body;

import java.util.List;


public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryViewHolder> {

    private Context mContext;
    private List<Body> mBodies;
    IOnItemSelectedListener mOnItemSelectedListener;

    public OrderHistoryAdapter(Context context, List<Body> bodies) {
        mContext = context;
        mBodies = bodies;
    }

    @Override
    public OrderHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_history, parent, false);
        OrderHistoryViewHolder viewHolder = new OrderHistoryViewHolder(view);
        viewHolder.setOnItemClickListener(new OrderHistoryViewHolder.IOnItemClick() {
            @Override
            public void itemClick(int position) {
                if(mOnItemSelectedListener!=null){
                    mOnItemSelectedListener.onItemSelected(mBodies.get(position));
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OrderHistoryViewHolder holder, int position) {
        holder.setData(mBodies.get(position));
    }

    @Override
    public int getItemCount() {
        return mBodies.size();
    }

    public void setOnItemSelectedListener(IOnItemSelectedListener onItemSelectedListener) {
        mOnItemSelectedListener = onItemSelectedListener;
    }

    public interface IOnItemSelectedListener {
        void onItemSelected(Body body);
    }

}
