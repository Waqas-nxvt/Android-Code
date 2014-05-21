package com.c.connectone.DAO;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;

import com.c.connectone.CconnectApplication;
import com.c.connectone.R;
import com.c.connectone.network.ConnectionManager;
import com.c.connectone.prefrences.Constants;
import com.c.connectone.utils.Common;

public class CreateEntryTask extends AsyncTask<Void, Void, Void> {
	private String TAG = "CreateEntryTask";
	private ProgressDialog pDialog;
	private Context context;
	private Location location;
	private String userName;
	private String userId;
	private String imagName;
	private String jobType;
	private String corp;
	private double lat;
	private double lng;
	ConnectionManager connection;

	public CreateEntryTask(Context ctx, String username, String userid,
			String imagname, String jobtype, String corp) {
		this.context = ctx;
		this.userName = username;
		this.userId = userid;
		this.imagName = imagname;
		this.jobType = jobtype;
		this.corp = corp;

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		pDialog = new ProgressDialog(context);
		pDialog = new ProgressDialog(context);
		Common.progress(
				pDialog,
				context.getResources().getString(
						R.string.progress_wait_creating_entry));
		connection = ConnectionManager.getInstence();
		// ---------------------getting location
		
		

		location = Common.Getlocation(context);

		if (location != null) {
			lat = location.getLatitude();
			lng = location.getLongitude();
		}

	}

	@Override
	protected Void doInBackground(Void... p) {

		connection.addParam("name", userName);
		connection.addParam("userName", userId);
		connection.addParam("fileNumber", userName + "-" + imagName);
		connection.addParam("job_type", jobType);
		connection.addParam("gpsLatt", String.valueOf(CconnectApplication.getInstance().getLatitude()));
		connection.addParam("gpsLong", String.valueOf(CconnectApplication.getInstance().getLongitude()));
		connection.addParam("corp", corp);

		connection.UserLogin(Constants.ServerActions.URL_CREATE_ENTRY);
		Log.d(TAG,
				"parameters are " + userName + ", " + userId + ","
						+ String.valueOf(lat));

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {

		pDialog.dismiss();

		super.onPostExecute(result);
	}

}
