package com.orange_team.narinjapp.adapters.viewholder; //H

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.orange_team.narinjapp.R;
import com.orange_team.narinjapp.model.Chef;


public class ChefsListViewHolder extends RecyclerView.ViewHolder {

    IOnClickListener mOnClickListener;
    ImageView mChefAvatar;
    TextView mChefName, mTelNumber;


    public ChefsListViewHolder(View itemView) {
        super(itemView);

        mChefAvatar = (ImageView) itemView.findViewById(R.id.chefAvatar);
        mChefName = (TextView) itemView.findViewById(R.id.chefFullName);
        mTelNumber = (TextView) itemView.findViewById(R.id.chefPhoneNumber);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickListener != null){
                    mOnClickListener.onItemClick(getAdapterPosition());
                }
            }
        });
    }

    public void setData(Chef chef){
//TODO set chef's avatar picture
        mChefName.setText(chef.getName());
        mTelNumber.setText(chef.getPhone());

    }

    public void setOnClickListener(IOnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public interface IOnClickListener {
        void onItemClick(int position);
    }

}
