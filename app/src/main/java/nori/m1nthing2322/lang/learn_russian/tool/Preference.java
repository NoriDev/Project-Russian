package nori.m1nthing2322.lang.learn_russian.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preference {
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    public Preference(Context mContext) {
        mPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        mEditor = mPref.edit();
    }

    public int getInt(String key, int defValue) {
        return mPref.getInt(key, defValue);
    }

    public void putInt(String key, int value) {
        mEditor.putInt(key, value).commit();
    }

}
