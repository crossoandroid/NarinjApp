package com.orange_team.supplier.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.orange_team.supplier.R;
import com.orange_team.supplier.models.DishOrders;

import org.w3c.dom.Text;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter {

    private Context mContext;
    private List<DishOrders> mDishOrdersList;

    public ListViewAdapter(Context context, int textViewResourceId, List<DishOrders> dishOrdersList) {
        super(context, textViewResourceId, dishOrdersList);
        try {
            this.mContext = context;
            this.mDishOrdersList = dishOrdersList;
        }catch (Exception e){

        }
    }

    public int getCount(){
        return mDishOrdersList.size();
    }

    public DishOrders getItem(DishOrders position){
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder{
        public TextView dishName;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vi = inflater.inflate(R.layout.item_dish_orders_list, null);
                holder = new ViewHolder();
                holder.dishName = (TextView) vi.findViewById(R.id.dishName);
                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            holder.dishName.setText(mDishOrdersList.get(position).getDish().getName() + " ,"
            + mDishOrdersList.get(position).getCount() + " հատ");

        } catch (Exception e) {

        }
        return vi;
    }
}
