package com.orange_team.narinjapp.adapters.viewholder; //H

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.orange_team.narinjapp.R;
import com.orange_team.narinjapp.model.Food;


public class FoodListViewHolder extends RecyclerView.ViewHolder {

    IOnClickListener mOnClickListener;
    ImageView mFoodImage;
    TextView mName, mDesc, mPrice;
    Button mAdd;


    public FoodListViewHolder(View itemView) {
        super(itemView);

        mFoodImage = (ImageView) itemView.findViewById(R.id.foodImageRec);
        mName = (TextView) itemView.findViewById(R.id.foodNameRec);
        mDesc = (TextView) itemView.findViewById(R.id.desc);
        mPrice = (TextView) itemView.findViewById(R.id.price);
        mAdd = (Button) itemView.findViewById(R.id.addButton);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickListener != null){
                    mOnClickListener.onItemClick(getAdapterPosition());
                }
            }
        });

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickListener != null){
                    mOnClickListener.onButtonClick(getAdapterPosition());
                }
            }
        });


    }

    public void setData(Food food){
//TODO Food image must be set
        mName.setText(food.getName());
        mDesc.setText(food.getDesc());
        mPrice.setText(food.getPrice());

    }

    public void setOnClickListener(IOnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public interface IOnClickListener {
        void onItemClick(int position);
        void onButtonClick(int position);
    }





}
