package com.orange_team.narinjapp.recyclers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.orange_team.user_application.narinj.tester.R;
import com.orange_team.user_application.narinj.tester.model.Food;
import com.orange_team.user_application.narinj.tester.recyclers.viewholder.CategoriesViewHolder;

import java.util.ArrayList;
import java.util.List;


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesViewHolder> {

    int mAdapterPosition;
    Context mContext;
    //TODO
    List<Food> mFoodList;
    IOnItemSelectedListener mOnItemSelectedListener;
    //IOnItemButtonSelectedListener mIOnItemButtonSelectedListener;
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
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CategoriesViewHolder holder, int position) {
        holder.setData(mFoodList.get(position));
        setScaleAnimation(holder.itemView);

        holder.mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapterPosition = holder.getAdapterPosition();
                Log.d(LOG_TAG, "Button "+ mFoodList.get(mAdapterPosition).getName());
            }
        });
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
    }
//    public void setOnItemButtonSelectedListener(IOnItemButtonSelectedListener onItemButtonSelectedListener) {
//        mIOnItemButtonSelectedListener = onItemButtonSelectedListener;
//    }
//
//    public interface IOnItemButtonSelectedListener {
//        //TODO
//        void onItemButtonSelected(Food food);
//    }
}
