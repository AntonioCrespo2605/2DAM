package com.example.erp.dataTransformers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class ImageCustomized {
    public static Bitmap fromBlobToBitmap(byte[]imgByte){
        return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
    }

    //it transforms a Bitmap to blob(byteArray)
    public static byte[] fromBitmapToBlob(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public static Bitmap fromIntToBitmap(int img, Context context){
        return BitmapFactory.decodeResource(context.getResources(), img);
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int width, int height) {
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        float scaleWidth = (float) width / bitmapWidth;
        float scaleHeight = (float) height / bitmapHeight;

        float scale = Math.min(scaleWidth, scaleHeight);

        int scaledWidth = Math.round(bitmapWidth * scale);
        int scaledHeight = Math.round(bitmapHeight * scale);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
        return scaledBitmap;
    }

    public static Bitmap cropBitmapToSquare(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = Math.min(width, height);
        int newHeight = newWidth;

        int left = (width - newWidth) / 2;
        int top = (height - newHeight) / 2;
        int right = left + newWidth;
        int bottom = top + newHeight;

        Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, left, top, newWidth, newHeight);
        return croppedBitmap;
    }

    public static Bitmap getBitmapFromImageView(ImageView imageView) {
        imageView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
        imageView.setDrawingCacheEnabled(false);
        return bitmap;
    }
}
