package com.orange_team.supplier.adapters.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.orange_team.supplier.R;
import com.orange_team.supplier.models.Body;

public class NewOrderViewHolder extends RecyclerView.ViewHolder {

    private TextView mUserName, mUserAddress;
    IOnClickListener mOnClickListener;

    public NewOrderViewHolder(View itemView) {
        super(itemView);
        mUserName = (TextView)itemView.findViewById(R.id.userNameNewOrderItems);
        mUserAddress = (TextView)itemView.findViewById(R.id.userAddressNewOrderItems);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickListener != null){
                    mOnClickListener.onItemClick(getAdapterPosition());
                }
            }
        });
    }

    public void setData(Body body){
        mUserName.setText(body.getName());
        mUserAddress.setText(body.getLocation());
    }

    public void setOnClickListener(IOnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public interface IOnClickListener {
        void onItemClick(int position);
    }
}
