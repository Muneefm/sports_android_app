package com.mnf.sports.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.mnf.sports.Config;
import com.mnf.sports.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public class ImageViewActivity extends AppCompatActivity {

    private static final int REQUEST_WRITE_STORAGE = 112;
    ImageView image,down,share;
Context c;
//String url = Config.BASE_URL+Config.IMAGE_RESOURCE_URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        c = getApplicationContext();

        Intent intent = getIntent();
        final String key = intent.getExtras().getString("id");
        image = (ImageView) findViewById(R.id.img);
        down = (ImageView) findViewById(R.id.downlo);

     /*   Picasso.with(c)
                .load(key)
                .into(image);
*/
        Glide
                .with(c)
                .load(key)
                .placeholder(R.mipmap.placeholder)
                .crossFade()
                .into(image);


        boolean hasPermission = (ContextCompat.checkSelfPermission(ImageViewActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            Log.e("tag","inside no permission ");
            ActivityCompat.requestPermissions(ImageViewActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }else{
            Log.e("tag","inside yes permission ");

        }
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(image, "Saving to  /thegzis/", Snackbar.LENGTH_LONG).show();

                getImageDonwload(key);
               /* Picasso.with(c)
                        .load(key)
                        .into(getTarget());
                Log.e("tag","down press");
*/
            }
        });




    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //reload my activity with permission granted or use the features what required the permission
                } else
                {
                    Toast.makeText(ImageViewActivity.this, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }

    }


    public void getImageDonwload(String urlimg){
        Log.e("save","inside sownload function url = "+urlimg);
        Glide
                .with(c)
                .load(urlimg)
                .asBitmap()
                .toBytes(Bitmap.CompressFormat.JPEG, 80)
                .into(new SimpleTarget<byte[]>() {
                    @Override
                    public void onResourceReady(final byte[] resource, GlideAnimation<? super byte[]> glideAnimation) {
                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... params) {
                                File sdcard = Environment.getExternalStorageDirectory();
                                File file = new File(sdcard + "/thegzis/" + getRandomNum() + "-thegzis16.jpg");
                                Log.e("save", "inside do inBackground file = " + file.getName() + " name sdcard = " + sdcard);
                                File dir = file.getParentFile();
                                try {
                                    Log.e("save", "inside try");
                                    if (!dir.mkdirs() && (!dir.exists() || !dir.isDirectory())) {
                                        throw new IOException("Cannot ensure parent directory for file " + file);
                                    }
                                    BufferedOutputStream s = new BufferedOutputStream(new FileOutputStream(file));
                                    s.write(resource);
                                    s.flush();
                                    s.close();
                                    // Toast.makeText(c,"Image Saved",Toast.LENGTH_LONG).show();

                                } catch (IOException e) {
                                    Log.e("save", "inside catch e = " + e.getLocalizedMessage());
                                    e.printStackTrace();
                                }
                                return null;
                            }
                        }.execute();
                    }
                })
        ;
    }


    private static Target getTarget(){
        Target target = new Target(){

            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        File file = new File(Environment.getExternalStorageDirectory(),"Thegzis");
                      
                        try {
                            file.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                            ostream.flush();
                            ostream.close();
                        } catch (IOException e) {
                            Log.e("IOException", e.getLocalizedMessage());
                        }
                    }
                }).start();

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        return target;
    }


    public int getRandomNum(){

        int min = 65;
        int max = 2000;

        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;
        return i1;

    }

    public File checkExistFile(){
        File targetLocationFun = new File (Environment.getExternalStorageDirectory()+"/Thegzis/"+getRandomNum()+".jpg");

        if(targetLocationFun.exists()){
            checkExistFile();

        }else{
            return targetLocationFun;
        }
        return null;
    }


}
