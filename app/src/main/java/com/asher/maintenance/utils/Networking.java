package com.asher.maintenance.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class Networking {
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetworkInfo() != null;
    }
}
