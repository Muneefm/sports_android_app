package com.mnf.sports.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.mnf.sports.Config;
import com.mnf.sports.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public class ImageViewActivity extends AppCompatActivity {

    ImageView image,down,share;
Context c;
String url = Config.BASE_URL+Config.IMAGE_RESOURCE_URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        c = getApplicationContext();

        Intent intent = getIntent();
        final String key = intent.getExtras().getString("id");
        image = (ImageView) findViewById(R.id.img);
        down = (ImageView) findViewById(R.id.downlo);

        Picasso.with(c)
                .load(url+key)
                .into(image);





        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(c)
                        .load(url+key)
                        .into(getTarget());
                Log.e("tag","down press");

            }
        });




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
