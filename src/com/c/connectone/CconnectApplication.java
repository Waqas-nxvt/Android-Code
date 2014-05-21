package com.c.connectone;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.WindowManager;

import com.c.connectone.bl.MyLocationManager;

public class CconnectApplication extends Application {

	private static CconnectApplication instance;

	private double latitude;
	private double longitude;

	public static CconnectApplication getInstance() {
		return instance;
	}

	public void onCreate() {
		super.onCreate();
		instance = this;

		new MyLocationManager(this);
		// startService(new Intent(this, GetgpsCoordinates.class));
	}

	public void setLatitude(double arg) {
		latitude = arg;
	}

	public void setLongitude(double arg) {
		longitude = arg;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public static Context getContext() {
		return getInstance().getApplicationContext();
	}

	

	public static String getStringRes(int id) {
		return getInstance().getString(id);
	}

	public static void hideKeyboard(Activity arg) {
		arg.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

}
