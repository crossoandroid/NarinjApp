package com.orange_team.narinjapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orange_team.narinjapp.R;
import com.squareup.picasso.Picasso;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Holder> {
    private final int[] mImageId;
    private final String[] mTitle;
    private LayoutInflater mInflater;
    private ItemClickListener mOnClickListener;
    public CategoryAdapter(Context mContext, String[] title, int[] imageId) {
        this.mInflater=LayoutInflater.from(mContext);
        this.mTitle=title;
        this.mImageId=imageId;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.category_items,parent,false);
        Holder holder=new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        int imgId=mImageId[position];
        String title=mTitle[position];
        Picasso.with(mInflater.getContext()).load(imgId).into(holder.mImg);
        holder.mTitle.setText(title);
    }

    @Override
    public int getItemCount() {
        return mTitle.length;
    }

    public void setmOnClickListener(ItemClickListener itemClickListener)
    {
        this.mOnClickListener=itemClickListener;
    }

    public interface ItemClickListener
    {
        void onItemClick(View view,int position);
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView mImg;
        TextView mTitle;

        public Holder(View itemView) {
            super(itemView);
            mImg = (ImageView) itemView.findViewById(R.id.lunch);
            mTitle=(TextView)itemView.findViewById(R.id.grid_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnClickListener != null) mOnClickListener.onItemClick(v, getAdapterPosition());
        }
    }

}
