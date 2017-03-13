package com.orange_team.narinjapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME="Cart.db";
	private static final int DATABASE_VERSION=1;

	public DataBaseHelper(Context context)
	{
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		final String CREATE_NOTES_TABLE =
				"CREATE TABLE " + DBDescription.Cart.TABLE_NAME + "(" +
						DBDescription.Cart._ID + " integer primary key, " +
						DBDescription.Cart.COLUMN_NAME + " TEXT, " +
						DBDescription.Cart.COLUMN_QTY + " TEXT, " +
						DBDescription.Cart.COLUMN_TOTAL + " TEXT, "+
						DBDescription.Cart.COLUMN_IMG_PATH+" TEXT);";
		db.execSQL(CREATE_NOTES_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
	{

	}

}