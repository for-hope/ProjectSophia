package com.forhope.lamine.sophia;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by lamine on 11/09/2017.
 */

public class Prefrences {
        static void setLevel(Context context, int level, int buttonLv ) {
        SharedPreferences sharedPref = context.getSharedPreferences("saveGame",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        //editor.putInt("savedDay",day);
        editor.putInt("savedQ",level);
        editor.putInt("savedB",buttonLv);
        editor.apply();
    }
        static void setDay(Context context,int day) {
        SharedPreferences sharedPref = context.getSharedPreferences("saveGame",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        //editor.putInt("savedDay",day);
        editor.putInt("savedDay",day);
        editor.apply();
    }     static void saveName(Context context,String name) {
        SharedPreferences sharedPref = context.getSharedPreferences("saveGame",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        //editor.putInt("savedDay",day);
        editor.putString("playerName",name);
        editor.apply();
    }


}
