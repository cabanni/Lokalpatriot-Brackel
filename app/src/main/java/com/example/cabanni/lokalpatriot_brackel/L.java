package com.example.cabanni.lokalpatriot_brackel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by cabanni on 31.12.16.
 */

public class L {
    public static void m(String message) {
        Log.d("CABANNI", message);
    }

    public static void l(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG);
    }
}
