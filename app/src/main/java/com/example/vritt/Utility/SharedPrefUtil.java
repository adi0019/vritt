package com.example.vritt.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SharedPrefUtil {

    public static final String PREFERENCE_NAME = "vritt_app_prefs";

    private final Context mContext;

    private SharedPrefUtil() {
        throw new Error("Do not need instantiate!");
    }

    public String getAccessToken(String key, String defValue, Context context) {
        return getSharedPreferences(context).getString(key, defValue);
    }


    /**
     * @param key
     * @param defValue
     * @param context
     */
    public static String getString(String key, String defValue, Context context) {
        return getSharedPreferences(context).getString(key, defValue);
    }

    public static void putBoolean(String key, boolean value, Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }


    /**
     * @param key
     * @param defValue
     * @param context
     */
    public static boolean getBoolean(String key, boolean defValue, Context context) {
        return getSharedPreferences(context).getBoolean(key, defValue);
    }

    public static void putInt(String key, int value, Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(key, value);
        editor.apply();
    }


    /**
     * @param key
     * @param defValue
     * @param context
     */
    public static int getInt(String key, int defValue, Context context) {
        return getSharedPreferences(context).getInt(key, defValue);
    }

    /**
     * @param key
     * @param defaultValue
     * @param context
     */
    public static void putString(String key, String defaultValue, Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, defaultValue);
        editor.apply();
    }

    /**
     * @param context
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private static SharedPreferences getSharedPreferences(Context context) {
        String masterKeyAlias;
        SharedPreferences sharedPreferences = null;
        try {
            masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPreferences = EncryptedSharedPreferences.create(
                    PREFERENCE_NAME,
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return sharedPreferences;

    }

    public static void clearSharedPreferences(Context mContext) {
        SharedPreferences pref = mContext.getSharedPreferences(SharedPrefUtil.PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }

    public static void clearAccessToken(Context mContext){
        SharedPreferences pref = mContext.getSharedPreferences("", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }



}
