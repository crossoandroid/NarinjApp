package com.orange_team.narinjapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orange_team.narinjapp.constants.Constants;
import com.orange_team.narinjapp.enums.OrderCategories;
import com.orange_team.narinjapp.fragments.ChefsListFragment;
import com.orange_team.narinjapp.fragments.FoodListFragment;
import com.orange_team.narinjapp.R;
import com.orange_team.narinjapp.utils.AlertDialogManager;
import com.orange_team.narinjapp.utils.InternetConnectionDetector;
import com.squareup.picasso.Picasso;


public class CatAdapter extends BaseAdapter {
    Context context;
    private final int[] imageId;
    private final String[] title;
    Boolean isInternetPresent = false;
    InternetConnectionDetector internetConnectionDetector;
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
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        convertView.setLayoutParams(new GridView.LayoutParams(params));
        if(convertView==null) {
            rowView = inflater.inflate(R.layout.category_items, null);
            holder.img = (ImageView) rowView.findViewById(R.id.lunch);
            holder.textView=(TextView)rowView.findViewById(R.id.grid_text);
            holder.textView.setText(title[position]);
            Picasso.with(context).load(imageId[position]).resize(150,150).into(holder.img);
        }
        else
        {
            rowView=convertView;
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                internetConnectionDetector=new InternetConnectionDetector(context);
                isInternetPresent=internetConnectionDetector.isConnectingToInternet();
                if(isInternetPresent) {

                    Bundle bundle = new Bundle();
                    if (title[position].equals(OrderCategories.ALL.toString())) {
                        bundle.putSerializable(FoodListFragment.CATEGORY_KEY, OrderCategories.ALL);
                    }
                    if (title[position].equals(OrderCategories.CAKE.toString())) {
                        bundle.putSerializable(FoodListFragment.CATEGORY_KEY, OrderCategories.CAKE);
                    }
                    if (title[position].equals(OrderCategories.CHEF.toString())) {
                        Log.d(Constants.LOG_TAG, "must come here");

                        ChefsListFragment chefsListFragment = new ChefsListFragment();
                        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_main, chefsListFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        return;

                    }
                    if (title[position].equals(OrderCategories.GARNISH.toString())) {
                        bundle.putSerializable(FoodListFragment.CATEGORY_KEY, OrderCategories.GARNISH);
                    }
                    if (title[position].equals(OrderCategories.HOT_DISHES.toString())) {
                        bundle.putSerializable(FoodListFragment.CATEGORY_KEY, OrderCategories.HOT_DISHES);
                    }
                    if (title[position].equals(OrderCategories.LUNCH.toString())) {
                        bundle.putSerializable(FoodListFragment.CATEGORY_KEY, OrderCategories.LUNCH);
                    }
                    if (title[position].equals(OrderCategories.RECEPTION.toString())) {
                        //bundle.putSerializable(FoodListFragment.CATEGORY_KEY, OrderCategories.RECEPTION);
                        Toast.makeText(context,"Կատեգորիան դատարկ է",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (title[position].equals(OrderCategories.SALAD.toString())) {
                        //bundle.putSerializable(FoodListFragment.CATEGORY_KEY, OrderCategories.SALAD);
                        Toast.makeText(context,"Կատեգորիան դատարկ է",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (title[position].equals(OrderCategories.SOUP.toString())) {
                        bundle.putSerializable(FoodListFragment.CATEGORY_KEY, OrderCategories.SOUP);
                    }
                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    FoodListFragment foodListFragment = new FoodListFragment();
                    foodListFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragment_main, foodListFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else
                {
                    AlertDialogManager alertDialogManager=new AlertDialogManager();
                    alertDialogManager.showAlertDialog(context,context.getString(R.string.enable_internet),context.getString(R.string.internet_access),true);
                }
            }
        });
        return rowView;
    }

}
