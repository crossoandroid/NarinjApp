package com.orange_team.narinjapp.utils;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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