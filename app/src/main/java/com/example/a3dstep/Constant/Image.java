package com.example.a3dstep.Constant;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.drawable.PictureDrawable;

public class Image {
    //Convert Picture to Bitmap
    private static Bitmap pictureDrawable2Bitmap(Picture picture) {
        PictureDrawable pd = new PictureDrawable(picture);
        Bitmap bitmap = Bitmap.createBitmap(pd.getIntrinsicWidth(), pd.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawPicture(pd.getPicture());
        return bitmap;
    }
}
