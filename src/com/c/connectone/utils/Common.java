package com.c.connectone.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.widget.Toast;

import com.c.connectone.prefrences.Constants;

public class Common {

	public static void ShowToast(Context ctx, String text) {
		Toast.makeText(ctx, text, Toast.LENGTH_LONG).show();
	}

	public static boolean isCam() {
		boolean iscam = false;

		int numCameras = Camera.getNumberOfCameras();
		if (numCameras > 0) {
			iscam = true;
		} else {
			iscam = false;
		}
		return iscam;
	}

	public static void progress(ProgressDialog pdialog, String msg) {

		pdialog.setMessage(msg);
		pdialog.setIndeterminate(false);
		pdialog.setCancelable(false);
		pdialog.show();
	}
	
	
	public static boolean isNetworkAvailable(Context context) {
		return ((ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo() != null;
	}
	
	// ----------------getLocation------

	public static Location Getlocation(Context ctx) {
		final String gpsLocationProvider = LocationManager.GPS_PROVIDER;
		final String networkLocationProvider = LocationManager.NETWORK_PROVIDER;

		LocationManager locationManager = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);

		Location lastKnownLocation_byGps = locationManager
				.getLastKnownLocation(gpsLocationProvider);
		Location lastKnownLocation_byNetwork = locationManager
				.getLastKnownLocation(networkLocationProvider);
	

		if (lastKnownLocation_byGps == null) {

			if (lastKnownLocation_byNetwork == null) {

			} else {

				return lastKnownLocation_byNetwork;

			}

		} else {

			return lastKnownLocation_byGps;

		}

		return lastKnownLocation_byNetwork;

	}

	// ----------------image Util

	public static Uri getImageUri(Context inContext, Bitmap inImage) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		String path = Images.Media.insertImage(inContext.getContentResolver(),
				inImage, "Title", null);
		return Uri.parse(path);
	}

	public static String getRealPathFromURI(Context ctx, Uri uri) {
		Cursor cursor = ctx.getContentResolver().query(uri, null, null, null,
				null);
		cursor.moveToFirst();
		int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
		return cursor.getString(idx);
	}

	// ------------Save image

	public static boolean saveImage(Bitmap bitmap) {
		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + "/JobSanpC");
		myDir.mkdirs();
		Random generator = new Random();
		int n = 10000;
		n = generator.nextInt(n);
		String fname = "JobSnapC-" + n + ".jpg";
		File file = new File(myDir, fname);
		if (file.exists())
			file.delete();
		try {
			FileOutputStream out = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}
		return true;
	}

}
