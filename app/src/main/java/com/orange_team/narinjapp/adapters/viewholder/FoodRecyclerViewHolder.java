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
    public ImageView cartItemImg;
    public FoodRecyclerViewHolder(View itemView) {
        super(itemView);

        mFoodName = (TextView) itemView.findViewById(R.id.food_name);
        mFoodPrice = (TextView) itemView.findViewById(R.id.food_price);
        mFoodName=(TextView)itemView.findViewById(R.id.count);
        cartItemImg=(ImageView)itemView.findViewById(R.id.food_image);
        mDelBtn=(ImageView)itemView.findViewById(R.id.delete);

    }


    public void setData(Food food){
        mFoodName.setText(food.getName() + "");
        mFoodPrice.setText(food.getPrice() + "");
        mFoodName.setText(food.getCount());
    }
}
