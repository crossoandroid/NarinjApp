package com.orange_team.narinjapp.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import com.orange_team.narinjapp.R;
import com.orange_team.narinjapp.adapters.viewholder.FoodRecyclerViewHolder;
import com.orange_team.narinjapp.db.DBDescription;
import com.orange_team.narinjapp.db.DataBaseHelper;
import com.orange_team.narinjapp.model.Food;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ChoiceAdapter extends RecyclerView.Adapter<FoodRecyclerViewHolder> {

    private final static int FADE_DURATION = 1000;

    private Context context;
    private List<Food> foods;
    private int newPos;
    static int inttotal;
    int value = 1;
    int total = 0;

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
    public void onBindViewHolder(final FoodRecyclerViewHolder holder, final int position) {
        holder.setData(foods.get(position));

        Picasso.with(context).load(foods.get(position).getPicture()).into(holder.cartItemImg);
        for (int i = 0; i < foods.size(); i++) {
            inttotal += foods.get(position).getPrice();
        }

        value = Integer.parseInt(foods.get(position).getCount());

        holder.mMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value <= 1) {
                    value = 1;
                    total = value * foods.get(position).getPrice();
                    holder.mFoodQtyBtn.setText("" + value);
                    holder.mFoodPrice.setText(String.valueOf(total));
                } else {
                    value--;
                    total = value * foods.get(position).getPrice();
                    holder.mFoodQtyBtn.setText("" + value);
                    holder.mFoodPrice.setText(String.valueOf(total));
                }
            }
        });

        holder.mPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value++;
                total = value * foods.get(position).getPrice();
                holder.mFoodQtyBtn.setText("" + value);
                holder.mFoodPrice.setText(String.valueOf(total));
            }
        });
        holder.mDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPos = holder.getAdapterPosition();
                deleteItem(foods.get(newPos).getName());
                inttotal -= foods.get(newPos).getPrice();
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

    public void deleteItem(String name) {
        DataBaseHelper myDbHelper = new DataBaseHelper(context);
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        db.delete(DBDescription.Cart.TABLE_NAME, DBDescription.Cart.COLUMN_NAME + " = ?", new String[]{name});
        db.close();
        myDbHelper.close();
    }

}
