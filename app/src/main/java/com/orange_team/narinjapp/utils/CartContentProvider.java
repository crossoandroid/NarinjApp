package com.orange_team.narinjapp.utils;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.orange_team.narinjapp.R;

import java.sql.SQLException;

public class CartContentProvider extends ContentProvider {
    private DataBaseHelper dbHelper;
    private static final UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);

    private static final int ONE_ITEM=1;
    private static final int ITEMS=2;

    static
    {
        uriMatcher.addURI(DBDescription.AUTHORITY, DBDescription.Cart.TABLE_NAME+"/#",ONE_ITEM);
        uriMatcher.addURI(DBDescription.AUTHORITY, DBDescription.Cart.TABLE_NAME,ITEMS);
    }

    @Override
    public boolean onCreate()
    {
        dbHelper=new DataBaseHelper(getContext());
        return  true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder=new SQLiteQueryBuilder();
        queryBuilder.setTables(DBDescription.Cart.TABLE_NAME);

        switch (uriMatcher.match(uri))
        {
            case ONE_ITEM:
                queryBuilder.appendWhere(DBDescription.Cart._ID+"="+uri.getLastPathSegment());
                break;
            case ITEMS:
                break;
            default:
                throw new UnsupportedOperationException();
        }
        Cursor cursor=queryBuilder.query(dbHelper.getReadableDatabase(),projection,selection,selectionArgs,null,null,sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri)
    {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri newUri=null;
        switch (uriMatcher.match(uri))
        {
            case ITEMS:
                long rowId=dbHelper.getWritableDatabase().insert(DBDescription.Cart.TABLE_NAME,null,values);
                if (rowId > 0)
                {
                    newUri= DBDescription.Cart.builtNoteUri(rowId);
                    getContext().getContentResolver().notifyChange(uri,null);
                }
                else
                {
                    try {
                        throw new SQLException();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return  newUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int numOfDeletedRows;
        switch (uriMatcher.match(uri))
        {
            case ONE_ITEM:
                String id=uri.getLastPathSegment();
                numOfDeletedRows=dbHelper.getWritableDatabase().delete(DBDescription.Cart.TABLE_NAME, DBDescription.Cart._ID+"="+id,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException();

        }

        if(numOfDeletedRows!=0)
        {
            getContext().getContentResolver().notifyChange(uri,null);

        }
        return numOfDeletedRows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int numOfUpdatedRows;

        switch (uriMatcher.match(uri))
        {
            case ONE_ITEM:
                String id=uri.getLastPathSegment();
                numOfUpdatedRows=dbHelper.getWritableDatabase().update(DBDescription.Cart.TABLE_NAME,values, DBDescription.Cart._ID+"="+id,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException();

        }
        if(numOfUpdatedRows!=0)
        {
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return numOfUpdatedRows;
    }
}
