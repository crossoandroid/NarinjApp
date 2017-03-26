package com.orange_team.supplier.adapters.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.orange_team.supplier.R;
import com.orange_team.supplier.models.OrderDetails;


public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {


    TextView mDateAndTime;
    IOnItemClick mIOnItemClick;

    public OrderHistoryViewHolder(View itemView) {
        super(itemView);

        mDateAndTime = (TextView) itemView.findViewById(R.id.dateAndTimeTextView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIOnItemClick !=null){
                    mIOnItemClick.itemClick(getAdapterPosition());
                }
            }
        });
    }

    public void setData(OrderDetails orderDetails){
        mDateAndTime.setText(orderDetails.getDateAndTime());
    }


    public interface IOnItemClick{
         void itemClick(int position);
    }

    public void setOnItemClickListener(IOnItemClick onItemClick){
        mIOnItemClick = onItemClick;
    }
}
