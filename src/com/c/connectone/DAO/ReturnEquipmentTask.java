package com.c.connectone.DAO;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.c.connectone.R;
import com.c.connectone.AppListener.CConectAppListener;
import com.c.connectone.R.integer;
import com.c.connectone.network.ConnectionManager;
import com.c.connectone.utils.Common;

public class ReturnEquipmentTask extends AsyncTask<String, integer, String[]> {

	CConectAppListener listner;
	ConnectionManager connection;
	ProgressDialog pDialogz;
	Context ctx;

	public ReturnEquipmentTask(Context ct) {
		connection = ConnectionManager.getInstence();
		ctx = ct;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		pDialogz = new ProgressDialog(ctx);
		Common.progress(pDialogz,
				ctx.getResources().getString(R.string.progress_wait_msg));

	}

	@Override
	protected String[] doInBackground(String... url) {

		String urll = url[0];
		String message[] = {"",""};

		String response = connection.UserLogin(urll);

		message[0] = response;

		return message;
	}

	@Override
	protected void onPostExecute(String result[]) {
		listner.onComplete(result);
		pDialogz.dismiss();
		super.onPostExecute(result);
	}

	public void addParam(String key, String value) {

		connection.addParam(key, value);

	}

	public void setWebServiceListener(CConectAppListener listener) {
		this.listner = listener;
	}
}
