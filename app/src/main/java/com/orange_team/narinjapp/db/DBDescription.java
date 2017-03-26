package com.orange_team.narinjapp.db;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by User on 14.03.2017.
 */

public class DBDescription {

    public static class Cart implements BaseColumns
    {
        public static final String TABLE_NAME="items";

        public static final String COLUMN_NAME="Name";
        public static final String COLUMN_QTY="Qty";
        public static final String COLUMN_TOTAL="Total";
        public static final String COLUMN_IMG_PATH="Img";
        public static final String COLUMN_DISH_ID="DishId";
        public static final String COLUMN_CHIEF_ID="ChiefId";
    }
}
