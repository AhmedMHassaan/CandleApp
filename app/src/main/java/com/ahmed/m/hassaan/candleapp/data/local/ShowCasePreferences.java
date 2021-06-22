package com.ahmed.m.hassaan.candleapp.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.ahmed.m.hassaan.candleapp.R;
import com.ahmed.m.hassaan.candleapp.utils.App;

public class ShowCasePreferences {

    private static ShowCasePreferences INSTANCE ;
    private SharedPreferences sharedPreferences;



    private ShowCasePreferences() {
        getSharedPreferences();
    }

    public static synchronized  ShowCasePreferences getInstance(){
        if (INSTANCE == null)
            INSTANCE = new ShowCasePreferences();
        return INSTANCE ;
    }

    private void getSharedPreferences() {
        this.sharedPreferences = App.mACTIVITY.getSharedPreferences(App.mACTIVITY.getString(R.string.shared_preference), Context.MODE_PRIVATE);
    }

    /**
     * dont show hints and helps again
     */
    public void dontShowAgain() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(App.mACTIVITY.getString(R.string.shoing_show_case), false);
        editor.apply();
    }



    public boolean startShowCase() {
        return sharedPreferences.getBoolean(App.mACTIVITY.getString(R.string.shoing_show_case), true);
    }


    public void clearPreference() {
        sharedPreferences.edit().clear().apply();
    }



}
