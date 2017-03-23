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

    public TextView mFoodName, mFoodPrice;
    public  ImageView mDelBtn;
    public ImageView cartItemImg;
    int value;
    int total = 0;

    public Button mPlusBtn,mMinusBtn,mFoodQtyBtn;

    public FoodRecyclerViewHolder(View itemView) {
        super(itemView);
        mFoodName = (TextView) itemView.findViewById(R.id.basketFoodName);
        mFoodPrice = (TextView) itemView.findViewById(R.id.basketFoodPrice);
        cartItemImg=(ImageView)itemView.findViewById(R.id.basketFoodImage);
        mDelBtn=(ImageView)itemView.findViewById(R.id.basketItemDelete);
        mPlusBtn = (Button) itemView.findViewById(R.id.btn_plus);
        mMinusBtn = (Button) itemView.findViewById(R.id.btn_minus);
        mFoodQtyBtn = (Button) itemView.findViewById(R.id.btn_display);


    }


    public void setData(final Food food){

        value = Integer.parseInt(food.getCount());

//        mMinusBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (value <= 1) {
//                    value = 1;
//                    total = value * food.getPrice();
//                    mFoodQtyBtn.setText("" + value);
//                    mFoodPrice.setText(String.valueOf(total));
//                } else {
//                    value--;
//                    total = value * food.getPrice();
//                    mFoodQtyBtn.setText("" + value);
//                    mFoodPrice.setText(String.valueOf(total));
//                }
//            }
//        });
//
//        mPlusBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                value++;
//                total = value * food.getPrice();
//                mFoodQtyBtn.setText("" + value);
//                mFoodPrice.setText(String.valueOf(total));
//            }
//        });




        mFoodName.setText(food.getName());
        mFoodPrice.setText(food.getPrice() + "");
        mFoodQtyBtn.setText(food.getCount());
    }
}
