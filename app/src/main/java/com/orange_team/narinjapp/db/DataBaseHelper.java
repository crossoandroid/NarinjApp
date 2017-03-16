package com.orange_team.narinjapp.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {

	private static String DB_NAME = "Cart4.DB";
	private static final int DATABASE_VERSION=1;

	private SQLiteDatabase myDataBase;

	private final Context myContext;

	public DataBaseHelper(Context context) {

		super(context, DB_NAME, null, DATABASE_VERSION);
		this.myContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		final String CREATE_ITEMS_TABLE =
				"CREATE TABLE " + DBDescription.Cart.TABLE_NAME + "(" +
						DBDescription.Cart._ID + " integer primary key, " +
						DBDescription.Cart.COLUMN_NAME + " TEXT, " +
						DBDescription.Cart.COLUMN_QTY + " TEXT, " +
						DBDescription.Cart.COLUMN_TOTAL + " TEXT, " +
						DBDescription.Cart.COLUMN_IMG_PATH + " TEXT);";
		db.execSQL(CREATE_ITEMS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}


}