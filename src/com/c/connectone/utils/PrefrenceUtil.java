package com.c.connectone.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.c.connectone.prefrences.Constants;

public class PrefrenceUtil {

	// ===================Get Prefrence==============

	public static boolean getPrefrenceBoolean(Context ctx, String key) {
		SharedPreferences pref = ctx.getSharedPreferences(
				Constants.Pref.PREFS_NAME, ctx.MODE_PRIVATE);
		boolean result = pref.getBoolean(key, false);
		return result;
	}

	public static String getPrefrence(Context ctx, String key) {
		SharedPreferences pref = ctx.getSharedPreferences(
				Constants.Pref.PREFS_NAME, ctx.MODE_PRIVATE);
		String result = pref.getString(key, null);
		return result;
	}

	// ===================Save Prefrence==============
	public static void SavePrefrence(Context ctx, String Key, String value) {
		ctx.getSharedPreferences(Constants.Pref.PREFS_NAME, ctx.MODE_PRIVATE)
				.edit().putString(Key, value).commit();

	}

	public static void SavePrefrenceBoolean(Context ctx, String Key,
			boolean value) {
		ctx.getSharedPreferences(Constants.Pref.PREFS_NAME, ctx.MODE_PRIVATE)
				.edit().putBoolean(Key, value).commit();

	}

	public static void clearPrefrence(Context ctx, String prefrencename) {
		SharedPreferences settings = ctx.getSharedPreferences(prefrencename,
				Context.MODE_PRIVATE);
		settings.edit().clear().commit();

	}

}
