package com.orange_team.narinjapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import com.orange_team.narinjapp.R;
import com.orange_team.narinjapp.adapters.viewholder.FoodRecyclerViewHolder;
import com.orange_team.narinjapp.model.Food;

import java.util.List;


public class ChoiceAdapter extends RecyclerView.Adapter<FoodRecyclerViewHolder> {

    private final static int FADE_DURATION = 1000;

    private Context context;
    private List<Food> foods;
    private int newPos;

    public ChoiceAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foods = foodList;
    }

    @Override
    public FoodRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.basket_food_item, parent, false);
        FoodRecyclerViewHolder foodRecyclerViewHolder = new FoodRecyclerViewHolder(view);

        return foodRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(final FoodRecyclerViewHolder holder, int position) {
        holder.setData(foods.get(position));
        holder.mDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPos = holder.getAdapterPosition();
                foods.remove(newPos);
                notifyItemRemoved(newPos);
                notifyItemRangeChanged(newPos, foods.size());
            }
        });

        setFadeAnimation(holder.itemView);

    }

    @Override
    public int getItemCount() {
        return foods.size();
    }


    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
}
