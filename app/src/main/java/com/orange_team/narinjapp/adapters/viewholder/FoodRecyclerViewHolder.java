package com.orange_team.narinjapp.adapters.viewholder;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.orange_team.narinjapp.R;
import com.orange_team.narinjapp.model.Food;


public class FoodRecyclerViewHolder extends RecyclerView.ViewHolder{

    private TextView mFoodName, mFoodPrice;
    public  ImageView mDelBtn;

    private EditText mCount_et;
    private int mCount;
    private Button mPlusBtn,mMinusBtn;

    public FoodRecyclerViewHolder(View itemView) {
        super(itemView);

        mFoodName = (TextView) itemView.findViewById(R.id.food_name);
        mFoodPrice = (TextView) itemView.findViewById(R.id.food_price);

        mCount_et = (EditText) itemView.findViewById(R.id.count);
        mPlusBtn = (Button) itemView.findViewById(R.id.btn_plus);
        mMinusBtn = (Button) itemView.findViewById(R.id.btn_minus);

        mPlusBtn.setBackgroundColor(Color.TRANSPARENT);
        mMinusBtn.setBackgroundColor(Color.TRANSPARENT);

        mDelBtn = (ImageView) itemView.findViewById(R.id.delete);


        mCount = 1;

        mPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCount ++;
                mCount_et.setText(mCount + "");
            }
        });

        mMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCount > 1 ){
                    mCount--;
                    mCount_et.setText(mCount+"");
                }else {
                    mCount_et.setText(mCount+"");
                }
            }
        });
    }


    public void setData(Food food){
        mFoodName.setText(food.getName() + "");
        mFoodPrice.setText(food.getPrice() + "");
    }


}
