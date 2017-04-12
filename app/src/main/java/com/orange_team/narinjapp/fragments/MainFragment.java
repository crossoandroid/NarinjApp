package com.orange_team.narinjapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.orange_team.narinjapp.R;
import com.orange_team.narinjapp.adapters.CategoryAdapter;
import com.orange_team.narinjapp.adapters.CustomSwipeAdapter;
import com.orange_team.narinjapp.constants.Constants;
import com.orange_team.narinjapp.enums.OrderCategories;
import com.orange_team.narinjapp.utils.NetworkDialogManager;
import com.orange_team.narinjapp.utils.InternetConnectionDetector;


public class MainFragment extends Fragment implements CategoryAdapter.ItemClickListener{

    ViewPager mViewPager;
    CustomSwipeAdapter mAdapter;
    RecyclerView mRecyclerView;
    Boolean isInternetPresent = false;
    InternetConnectionDetector internetConnectionDetector;
    CategoryAdapter mCategoryAdapter;
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
        mCategoryAdapter = new CategoryAdapter(getContext(),title, imageId);
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.category_menu);
        int numOfColumns=3;
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),numOfColumns));
        mAdapter = new CustomSwipeAdapter(this.getActivity());
        mViewPager.setAdapter(mAdapter);
        mCategoryAdapter.setmOnClickListener(this);
        mRecyclerView.setAdapter(mCategoryAdapter);
        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        internetConnectionDetector=new InternetConnectionDetector(getContext());
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
                FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
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
                Toast.makeText(getContext(),"Կատեգորիան դատարկ է",Toast.LENGTH_SHORT).show();
                return;
            }
            if (title[position].equals(OrderCategories.SALAD.toString())) {
                //bundle.putSerializable(FoodListFragment.CATEGORY_KEY, OrderCategories.SALAD);
                Toast.makeText(getContext(),"Կատեգորիան դատարկ է",Toast.LENGTH_SHORT).show();
                return;
            }
            if (title[position].equals(OrderCategories.SOUP.toString())) {
                bundle.putSerializable(FoodListFragment.CATEGORY_KEY, OrderCategories.SOUP);
            }
            FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FoodListFragment foodListFragment = new FoodListFragment();
            foodListFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_main, foodListFragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        else
        {
            NetworkDialogManager networkDialogManager =new NetworkDialogManager();
            networkDialogManager.showAlertDialog(getContext(),getContext().getString(R.string.enable_internet),getContext().getString(R.string.internet_access),true);
        }
    }
}