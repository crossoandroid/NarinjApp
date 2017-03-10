package com.orange_team.narinjapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.orange_team.narinjapp.constants.Constants;
import com.orange_team.narinjapp.enums.OrderCategories;
import com.orange_team.narinjapp.fragments.FoodListFragment;
import com.orange_team.narinjapp.R;

/**
 * Created by User on 02.03.2017.
 */

public class CatAdapter extends BaseAdapter {
    private static final String key="Category";
    Context context;
    private final int[] imageId;
    private final String[] title;
    public CatAdapter(Context mContext,String[] title, int[] imageId) {
        this.context=mContext;
        this.title=title;
        this.imageId=imageId;
    }


    public class Holder
    {
        ImageView img;
        TextView textView;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null) {
            rowView=new View(context);
            rowView = inflater.inflate(R.layout.category_items, null);
            holder.img = (ImageView) rowView.findViewById(R.id.lunch);
            holder.textView=(TextView)rowView.findViewById(R.id.grid_text);
            holder.textView.setText(title[position]);
            holder.img.setImageResource(imageId[position]);
        }
        else
        {
            rowView=convertView;
        }
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if(title[position].equals(OrderCategories.ALL.toString())) {
                    bundle.putSerializable(FoodListFragment.CATEGORY_KEY, OrderCategories.ALL);
                }
                if(title[position].equals(OrderCategories.CAKE.toString())) {
                    bundle.putSerializable(FoodListFragment.CATEGORY_KEY, OrderCategories.CAKE);
                }
                if(title[position].equals(OrderCategories.CHEF.toString())) {
                    bundle.putSerializable(FoodListFragment.CATEGORY_KEY, OrderCategories.CHEF);
                }
                if(title[position].equals(OrderCategories.GARNISH.toString())) {
                    bundle.putSerializable(FoodListFragment.CATEGORY_KEY, OrderCategories.GARNISH);
                }
                if(title[position].equals(OrderCategories.HOT_DISHES.toString())) {
                    bundle.putSerializable(FoodListFragment.CATEGORY_KEY, OrderCategories.HOT_DISHES);
                }
                if(title[position].equals(OrderCategories.LUNCH.toString())) {
                    bundle.putSerializable(FoodListFragment.CATEGORY_KEY, OrderCategories.LUNCH);
                }
                if(title[position].equals(OrderCategories.RECEPTION.toString())) {
                    bundle.putSerializable(FoodListFragment.CATEGORY_KEY, OrderCategories.RECEPTION);
                }
                if(title[position].equals(OrderCategories.SALAD.toString())) {
                    bundle.putSerializable(FoodListFragment.CATEGORY_KEY, OrderCategories.SALAD);
                }
                if(title[position].equals(OrderCategories.SOUP.toString())) {
                    bundle.putSerializable(FoodListFragment.CATEGORY_KEY, OrderCategories.SOUP);
                }
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FoodListFragment foodListFragment =new FoodListFragment();
                foodListFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_main, foodListFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return rowView;
    }

}
