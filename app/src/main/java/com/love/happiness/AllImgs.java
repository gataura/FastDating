package com.love.happiness;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public final class AllImgs {
    static void saveToInternalStorage(Context context, ImageView image, long prefix) {

        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "profile_" + prefix + ".jpg");
//        String mypath = context.getFilesDir().getPath() + "imageDir";

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        directory.getAbsolutePath();
    }

    static void loadImageFromStorage(Context context, ImageView img, long prefix) {
        ContextWrapper cw = new ContextWrapper(context);
        File path = cw.getDir("imageDir", Context.MODE_PRIVATE);

        try {
            File f = new File(path, "profile_" + prefix + ".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            img.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    //уменьшает размер картинки. пока не нужно, заменил на библиотеку(CircularImageView) которая все далеат сама
    public static void loadResizedImageFromStorage(ImageView img) {
        String path = "/data/data/com.dream.best/app_imageDir";

        try {
            File f = new File(path, "profile_resized.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            img.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
