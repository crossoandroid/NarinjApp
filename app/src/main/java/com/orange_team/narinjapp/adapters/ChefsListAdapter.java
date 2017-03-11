package com.orange_team.narinjapp.adapters; //H

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import com.orange_team.narinjapp.R;
import com.orange_team.narinjapp.adapters.viewholder.ChefsListViewHolder;
import com.orange_team.narinjapp.model.Chef;
import java.util.ArrayList;
import java.util.List;


public class ChefsListAdapter extends RecyclerView.Adapter<ChefsListViewHolder> {

    Context mContext;
    List<Chef> mChefList;
    IOnItemSelectedListener mOnItemSelectedListener;
    public static final int FADE_DURATION = 500;


    public ChefsListAdapter(Context context) {
        mContext = context;
        mChefList = new ArrayList<>();

    }

    @Override
    public ChefsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chef_item, parent, false);
        ChefsListViewHolder viewHolder = new ChefsListViewHolder(view);
        viewHolder.setOnClickListener(new ChefsListViewHolder.IOnClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mOnItemSelectedListener != null){
                    mOnItemSelectedListener.onItemSelected(mChefList.get(position));
                }
            }
        });

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ChefsListViewHolder chefsListViewHolder, int i) {
        chefsListViewHolder.setData(mChefList.get(i));
        setFadeAnimation(chefsListViewHolder.itemView);
    }

    @Override
    public int getItemCount() {
            return mChefList.size();
    }

    public void setChefList(List<Chef> chefList){
        mChefList = chefList;
        notifyDataSetChanged();
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    public void setOnItemSelectedListener(IOnItemSelectedListener onItemSelectedListener) {
        mOnItemSelectedListener = onItemSelectedListener;
    }

    public interface IOnItemSelectedListener {
        void onItemSelected(Chef chef);
    }

}
