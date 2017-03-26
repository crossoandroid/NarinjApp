package com.orange_team.supplier.adapters.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.orange_team.supplier.R;
import com.orange_team.supplier.models.OrderedDishAndCount;
import com.squareup.picasso.Picasso;


public class DetailedFoodItemsViewHolder extends RecyclerView.ViewHolder{


    ImageView mFoodItemImage;
    TextView mFoodName, mChefName, mFoodCount, mFoodPrice;
    Context mContext;
    public static final String DEFAULT_FOOD = "default_food";


    public DetailedFoodItemsViewHolder(View itemView, Context context) {
        super(itemView);

        mContext = context;
        mFoodName = (TextView)itemView.findViewById(R.id.foodItemName);
        mChefName = (TextView)itemView.findViewById(R.id.foodItemChefName);
        mFoodCount = (TextView)itemView.findViewById(R.id.foodItemCount);
        mFoodPrice = (TextView)itemView.findViewById(R.id.foodItemPrice);


    }

    public void setData(OrderedDishAndCount orderedDishAndCount) {
        mFoodName.setText(orderedDishAndCount.getDish().getName());
        mChefName.setText(orderedDishAndCount.getDish().getChefName());
        mFoodPrice.setText(orderedDishAndCount.getDish().getPrice()+"Դրամ");
        mFoodCount.setText(orderedDishAndCount.getCount()+"Հատ");

    }
}
