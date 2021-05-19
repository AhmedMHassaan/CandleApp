package com.ahmed.m.hassaan.candleapp.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.ahmed.m.hassaan.candleapp.R;
import com.ahmed.m.hassaan.candleapp.utils.App;

public class SlidesPreferenceManager {

    private static SlidesPreferenceManager SHARED_PREF_INSTANCE;
    private SharedPreferences sharedPreferences;

    private SlidesPreferenceManager() {
        getSharedPreferences();
    }

    public static synchronized  SlidesPreferenceManager getInstance(){
        if (SHARED_PREF_INSTANCE == null)
            SHARED_PREF_INSTANCE = new SlidesPreferenceManager();
        return SHARED_PREF_INSTANCE ;
    }

    private void getSharedPreferences() {
        this.sharedPreferences = App.mACTIVITY.getSharedPreferences(App.mACTIVITY.getString(R.string.shared_preference), Context.MODE_PRIVATE);
    }

    public void writePreference() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(App.mACTIVITY.getString(R.string.landing_shared_preference_key), true);
        editor.apply();
    }



    public boolean showNextPage() {
        return sharedPreferences.getBoolean(App.mACTIVITY.getString(R.string.landing_shared_preference_key), false);
    }


    public void clearPreference() {
        sharedPreferences.edit().clear().apply();
    }
}
