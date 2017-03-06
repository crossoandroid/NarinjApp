package com.orange_team.narinjapp.adapters.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orange_team.user_application.narinj.R;
import com.orange_team.user_application.narinj.model.Food;


public class CategoriesViewHolder extends RecyclerView.ViewHolder {

    IOnClickListener mOnClickListener;

    ImageView mFoodImage;
    TextView mName, mDesc, mPrice;
    public Button mAdd;
    RelativeLayout mRelativeLayout;
    public static final String LOG_TAG = "MyLogs";


    public CategoriesViewHolder(View itemView) {
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
    //TODO
    public void setData(Food food){
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

//    public void setOnButtonClickListener(IOnButtonClickListener onButtonClickListener) {
//        mOnButtonClickListener = onButtonClickListener;
//    }
//
//    public interface IOnButtonClickListener {
//        void onItemButtonClick(int position);
//        void onItemButtonClick(int position);
//    }



}
