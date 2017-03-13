package com.orange_team.narinjapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by User on 12.03.2017.
 */

public class RoundedImageView extends ImageView {
    public RoundedImageView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();

        if(drawable==null)
        {
            return;
        }

        if(getWidth()==0 || getHeight()==0)
        {
            return;
        }

        Bitmap bitmap=((BitmapDrawable)drawable).getBitmap();

        if(bitmap!=null)
        {
            Bitmap bitmap1=bitmap.copy(Bitmap.Config.ARGB_8888,true);
            int width=getWidth();
            int height=getHeight();

            Bitmap roundBitmap=getRoundedCrop(bitmap1,width);
            canvas.drawBitmap(roundBitmap,0,0,null);
        }
    }

    public static Bitmap getRoundedCrop(Bitmap bitmap,int radius)
    {
        Bitmap lastBitmap;
        if(bitmap.getWidth()!=radius || bitmap.getHeight()!=radius)
        {
            lastBitmap=Bitmap.createScaledBitmap(bitmap,radius,radius,false);
        }
        else
        {
            lastBitmap=bitmap;
        }

        Bitmap output=Bitmap.createBitmap(lastBitmap.getWidth(),lastBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(output);

        final Paint paint=new Paint();
        final Rect rect=new Rect(0,0,lastBitmap.getWidth(),lastBitmap.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0,0,0,0);
        paint.setColor(Color.parseColor("#BDBDBD"));
        canvas.drawCircle(lastBitmap.getWidth()/2+0.7f,lastBitmap.getHeight()/2+0.7f,lastBitmap.getWidth()/2+0.1f,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(lastBitmap,rect,rect,paint);

        return output;
    }
}
