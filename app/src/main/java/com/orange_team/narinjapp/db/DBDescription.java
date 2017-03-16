package com.orange_team.narinjapp.db;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by User on 14.03.2017.
 */

public class DBDescription {
    public static final String AUTHORITY="com.orange_team.narinjapp.db";
    private static final Uri BASE_CONTENT_URI=Uri.parse("content://"+AUTHORITY);
    public static class Cart implements BaseColumns
    {
        public static final String TABLE_NAME="items";
        public static final Uri ITEM_URI=BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String COLUMN_NAME="Name";
        public static final String COLUMN_QTY="Qty";
        public static final String COLUMN_TOTAL="Total";
        public static final String COLUMN_IMG_PATH="Img";

    }
}
