package com.ellekay.lucie.diabetesms;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lucie on 7/27/2017.
 */

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "diabetes-welcome";
    private static final String IS_FIRST_TIME = "isFirst";

    public PrefManager(Context context){
        this._context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime){
        editor.putBoolean(IS_FIRST_TIME, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch(){
        return pref.getBoolean(IS_FIRST_TIME, true);
    }
}
