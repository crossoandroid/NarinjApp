package com.orange_team.supplier.adapters.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.orange_team.supplier.R;
import com.orange_team.supplier.models.Body;


public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {


    TextView mHistoryOrderName,mHistoryOrderAddress;
    IOnItemClick mIOnItemClick;

    public OrderHistoryViewHolder(View itemView) {
        super(itemView);

        mHistoryOrderName = (TextView) itemView.findViewById(R.id.historyOrderName);
        mHistoryOrderAddress = (TextView) itemView.findViewById(R.id.historyOrderAddress);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIOnItemClick !=null){
                    mIOnItemClick.itemClick(getAdapterPosition());
                }
            }
        });
    }

    public void setData(Body body){
        mHistoryOrderName.setText(body.getName());
        mHistoryOrderAddress.setText(body.getLocation());
    }

    public interface IOnItemClick{
         void itemClick(int position);
    }

    public void setOnItemClickListener(IOnItemClick onItemClick){
        mIOnItemClick = onItemClick;
    }
}
