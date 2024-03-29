package com.mnf.sports.SplashFiles.utils;

import android.app.Activity;
import android.graphics.Typeface;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mnf.sports.SplashFiles.cnst.Flags;


/**
 * Created by Muneef on 21/02/16.
 */
public class UIUtil {

    public static int getRevealDirection(RelativeLayout rl, int flag){
        int result = 0;
        switch (flag){
            case Flags.REVEAL_BOTTOM:
                result = rl.getBottom();
                break;
            case Flags.REVEAL_TOP:
                result = rl.getTop();
                break;
            case Flags.REVEAL_LEFT:
                result = rl.getLeft();
                break;
            case Flags.REVEAL_RIGHT:
                result = rl.getRight();
                break;
        }

        return result;
    }


    public static void setFont(Activity a, TextView txtView, String font) {
        Typeface type = Typeface.createFromAsset(a.getAssets(), font);
        txtView.setTypeface(type);
    }
}