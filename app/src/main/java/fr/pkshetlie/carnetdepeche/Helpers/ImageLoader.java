package fr.pkshetlie.carnetdepeche.Helpers;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.pkshetlie.carnetdepeche.R;

/**
 * Created by pierrick on 17/03/2016.
 */
public class ImageLoader {

    ExecutorService executorService;

    public ImageLoader(Context context) {
        executorService = Executors.newFixedThreadPool(5);
    }

    final int stub_id = R.drawable.ic_launcher_web_rouge;
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return retVal;
    }
    public void DisplayImage(String url, ImageView imageView)  {
        //imageViews.put(imageView, url);
        Bitmap myBitmap = BitmapFactory.decodeFile(url);
        if (myBitmap != null) {
            ExifInterface ei = null;
            try {
                ei = new ExifInterface(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            switch(orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    myBitmap = rotateImage(myBitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    myBitmap = rotateImage(myBitmap, 180);
                    break;
            }
            imageView.setImageBitmap(myBitmap);
        } else {
            imageView.setImageResource(stub_id);

        }
    }


}
