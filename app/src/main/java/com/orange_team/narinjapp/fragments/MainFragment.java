package com.orange_team.narinjapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import com.orange_team.narinjapp.R;
import com.orange_team.narinjapp.activities.MainActivity;
import com.orange_team.narinjapp.adapters.CatAdapter;
import com.orange_team.narinjapp.adapters.CustomSwipeAdapter;


public class MainFragment extends Fragment {

    ViewPager viewPager;
    CustomSwipeAdapter adapter;
    GridView gridView;
    CatAdapter catAdapter;
    String[] title = {
            "Ապուր",
            "Աղցան",
            "Բոլորը",
            "Ֆուրշետ",
            "Լանչ",
            "Թխվածք",
            "Ուտեստ",
            "Խավարտ",
            "Խոհարար"

    } ;

    int[] imageId = {
            R.drawable.apur,
            R.drawable.axcanner,
            R.drawable.bolory,
            R.drawable.furshetner,
            R.drawable.lunch,
            R.drawable.txvacqner,
            R.drawable.utestner,
            R.drawable.xavartner,
            R.drawable.xohararner

    };
    public MainFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        catAdapter = new CatAdapter(this.getContext(),title, imageId);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        gridView = (GridView) view.findViewById(R.id.category_menu);
        gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return event.getAction()==MotionEvent.ACTION_MOVE;
            }
        });
        adapter = new CustomSwipeAdapter(this.getActivity());
        viewPager.setAdapter(adapter);
        gridView.setAdapter(catAdapter);
        return view;
    }

}