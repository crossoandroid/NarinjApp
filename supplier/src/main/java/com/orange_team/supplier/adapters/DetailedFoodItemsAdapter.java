package com.orange_team.supplier.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orange_team.supplier.R;
import com.orange_team.supplier.adapters.viewholder.DetailedFoodItemsViewHolder;
import com.orange_team.supplier.models.OrderedDishAndCount;

import java.util.List;


public class DetailedFoodItemsAdapter extends RecyclerView.Adapter<DetailedFoodItemsViewHolder> {

    Context mContext;
    List<OrderedDishAndCount> mOrderedDishAndCountList;

    public DetailedFoodItemsAdapter(Context context, List<OrderedDishAndCount> orderedDishAndCountList) {
        mContext = context;
        mOrderedDishAndCountList = orderedDishAndCountList;
    }

    @Override
    public DetailedFoodItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_ordered_food_details, parent, false);
        DetailedFoodItemsViewHolder viewHolder = new DetailedFoodItemsViewHolder(view, mContext);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(DetailedFoodItemsViewHolder holder, int position) {
        holder.setData(mOrderedDishAndCountList.get(position));
    }

    @Override
    public int getItemCount() {
        return mOrderedDishAndCountList.size();
    }
}
