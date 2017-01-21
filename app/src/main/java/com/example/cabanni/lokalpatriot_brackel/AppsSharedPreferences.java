package com.example.cabanni.lokalpatriot_brackel;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by cabanni on 20.01.17.
 */

public class AppsSharedPreferences {

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;


    AppsSharedPreferences(Context context) {
        this.sharedPreferences = context.getSharedPreferences(Finals.APPDATA, Context.MODE_PRIVATE);
        this.editor = this.sharedPreferences.edit();
    }
}
