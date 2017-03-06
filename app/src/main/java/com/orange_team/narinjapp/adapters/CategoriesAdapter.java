package com.orange_team.narinjapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import com.orange_team.narinjapp.R;
import com.orange_team.narinjapp.adapters.viewholder.CategoriesViewHolder;
import com.orange_team.narinjapp.model.Food;


import java.util.ArrayList;
import java.util.List;


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesViewHolder> {

    int mAdapterPosition;
    Context mContext;
    //TODO
    List<Food> mFoodList;
    IOnItemSelectedListener mOnItemSelectedListener;

    public static final int FADE_DURATION = 500;
    public static final String LOG_TAG = "MyLogs";


    public CategoriesAdapter(Context context) {
        mContext = context;
        mFoodList = new ArrayList<>();

    }

    @Override
    public CategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item, parent, false);
        CategoriesViewHolder viewHolder = new CategoriesViewHolder(view);
        viewHolder.setOnClickListener(new CategoriesViewHolder.IOnClickListener() {

            @Override
            public void onItemClick(int position) {
                if(mOnItemSelectedListener != null);
                mOnItemSelectedListener.onItemSelected(mFoodList.get(position));
            }

            @Override
            public void onButtonClick(int position) {
                if(mOnItemSelectedListener!=null);
                mOnItemSelectedListener.onAddButtonClicked(mFoodList.get(position));
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoriesViewHolder holder, int position) {
        holder.setData(mFoodList.get(position));
        setScaleAnimation(holder.itemView);


    }

    @Override
    public int getItemCount() {
        return mFoodList.size();
    }


    public void setFoodList(List<Food> foodList){
        mFoodList = foodList;
        notifyDataSetChanged();
    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);

    }
    public void setOnItemSelectedListener(IOnItemSelectedListener onItemSelectedListener) {
        mOnItemSelectedListener = onItemSelectedListener;
    }

    public interface IOnItemSelectedListener {
        //TODO
        void onItemSelected(Food food);
        void onAddButtonClicked(Food food);
    }

}
