package com.mnf.sports.SplashFiles.utils;


import com.mnf.sports.SplashFiles.cnst.Flags;
import com.mnf.sports.SplashFiles.model.ConfigSplash;

/**
 * Created by Muneef on 21/02/16.
 */
public class ValidationUtil {

    public static int hasPath(ConfigSplash cs) {
        if (cs.getPathSplash().isEmpty())
            return Flags.WITH_LOGO;
        else
            return Flags.WITH_PATH;
    }
}