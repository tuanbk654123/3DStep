package com.example.a3dstep.View.Record;

import android.content.Context;
import android.location.Location;
import android.preference.PreferenceManager;

import java.text.DateFormat;
import java.util.Date;

public class Common {
    public static final String KEY_REQUESTING_LOCATION_UPDATE = "LocationUpdateEnable";

    public static String getLocationText(Location mLocation) {
        return mLocation == null ? "unknow Location": new StringBuilder().
                append(mLocation.getLatitude()).
                append("/").
                append(mLocation.getLongitude()).
                toString();
    }

    public static CharSequence getLocationTitle(MyBackgroundService myBackgroundService) {

        return String.format("Location Update: 1$s", DateFormat.getDateInstance().format(new Date()));
    }

    public static void setRequastingLocationUpdate(Context context, boolean b) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(KEY_REQUESTING_LOCATION_UPDATE,b)
                .apply();
    }

    public static boolean requestingLocationUpdate(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_REQUESTING_LOCATION_UPDATE,false);
    }
}
