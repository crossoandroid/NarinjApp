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
    public Button mFoodQty;
    public  ImageView mDelBtn;
    public ImageView cartItemImg;
    public FoodRecyclerViewHolder(View itemView) {
        super(itemView);
    }
    public Button mPlusBtn, mMinusBtn;

    public void setData(Food food){
        mFoodName = (TextView) itemView.findViewById(R.id.basketFoodName);
        mFoodPrice = (TextView) itemView.findViewById(R.id.basketFoodPrice);
        mFoodQty=(Button)itemView.findViewById(R.id.btn_display);
        cartItemImg=(ImageView)itemView.findViewById(R.id.basketFoodImage);
        mDelBtn=(ImageView)itemView.findViewById(R.id.basketItemDelete);
        mPlusBtn=(Button)itemView.findViewById(R.id.btn_plus);
        mMinusBtn=(Button)itemView.findViewById(R.id.btn_minus);
        mFoodName.setText(food.getName()+"");
        mFoodPrice.setText(food.getPrice()+" դրամ");
        mFoodQty.setText(food.getCount()+"");
    }
}
