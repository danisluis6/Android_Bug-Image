package com.lorence.notificationdemo.test;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements IAnayzeImage, IView, ICheckPermission {

    private ByteArrayOutputStream output;
    private ImageView imgTest;
    private static int READ_PERMISSIONS_REQUEST_CODE = 1;
    private static int WRITE_PERMISSIONS_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViewFromXML();

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            setPermissions();
        } else {
            Bitmap bitmap = BitmapFactory.decodeFile(getPathImageFromDevice());
            String x = getFileFromPath(getPathImageFromDevice()).length() + "";
            Log.d("TAG", x);

            // Scale Bitmap in Android
            Matrix m = new Matrix();
            int reqWidth = (int) (bitmap.getWidth() * Constant.SCALE);
            int reqHeight = (int) (bitmap.getHeight() * Constant.SCALE);
            m.setRectToRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), new RectF(0, 0, reqWidth, reqHeight), Matrix.ScaleToFit.CENTER);

            File file = convertBitmapToFile(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true));
            String y = file.length() + "";
            setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        }
    }

    @Override
    public String getPathImageFromDevice() {
        return "/storage/emulated/0/Pictures/JPEG_20170626_132923_-263098197.jpg";
    }

    @Override
    public File getFileFromPath(String path) {
        return new File(path);
    }

    @Override
    public void getViewFromXML() {
        imgTest = (ImageView) this.findViewById(R.id.activity_main_image);
    }

    @Override
    public void setImageBitmap(Bitmap bitmap) {
        imgTest.setImageBitmap(bitmap);
    }

    @Override
    public void setPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_PERMISSIONS_REQUEST_CODE);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSIONS_REQUEST_CODE);
    }

    private File convertBitmapToFile(Bitmap bitmap) {
        File filesDir = getApplicationContext().getFilesDir();
        File duplicate = new File(filesDir, getFileFromPath(getPathImageFromDevice()).getName());
        OutputStream os;
        try {
            os = new FileOutputStream(duplicate);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return duplicate;
    }
}

/**
 *
 * Get Path of File {@link android.media.Image}
 * Convert to Bitmap
 * Scale image
 * Return file
 * Done
 * - TIME
 * - HOW MANY TIME DO YOU DONE?
 */