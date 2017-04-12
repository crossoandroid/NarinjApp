package com.orange_team.narinjapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.orange_team.narinjapp.constants.Constants;

/**
 * Created by User on 29.03.2017.
 */

public class CartPreferences {
    private Context mContext;

    public CartPreferences(Context context){
        this.mContext = context;
    }

    public static void saveInt(Context context,String key, int value) {

        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
                .edit();
        editor.putInt("count", value);
        editor.apply();
    }
}
