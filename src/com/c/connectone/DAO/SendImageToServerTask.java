package com.c.connectone.DAO;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.c.connectone.AppListener.CConectAppListener;
import com.c.connectone.prefrences.Constants;
import com.c.connectone.utils.Common;

public class SendImageToServerTask extends AsyncTask<String, String, String[]> {
	ProgressDialog pDialog;

	File imageFile;
	CConectAppListener listner;

	String Jobname;
	Context ctx;

	public SendImageToServerTask(Context context, File imgfile, String jobname) {

		this.imageFile = imgfile;
		this.ctx = context;
		this.Jobname = jobname;

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(ctx);
		Common.progress(pDialog, "Sending image to server..... ");

	}

	@Override
	protected String[] doInBackground(String... server) {

		String urll = server[0];
		String message[] = { "", "" };

		HttpURLConnection connection = null;
		DataOutputStream outputStream = null;

		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";

		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;

		try {
			FileInputStream fileInputStream = new FileInputStream(imageFile);

			URL url = new URL(urll);

			connection = (HttpURLConnection) url.openConnection();

			// Allow Inputs & Outputs
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(true);

			// Enable POST method
			connection.setRequestMethod("POST");

			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);

			outputStream = new DataOutputStream(connection.getOutputStream());
			outputStream.writeBytes(twoHyphens + boundary + lineEnd);
			outputStream
					.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
							+ Jobname
							+ "_"
							+ imageFile.getName()
							+ "\""
							+ lineEnd);
			outputStream.writeBytes(lineEnd);

			Log.d("Path To Out File: ", imageFile.toString() + "name"
					+ imageFile.getName());

			bytesAvailable = fileInputStream.available();
			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];

			// Read file
			bytesRead = fileInputStream.read(buffer, 0, bufferSize);

			while (bytesRead > 0) {

				outputStream.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			}

			outputStream.writeBytes(lineEnd);
			outputStream.writeBytes(twoHyphens + boundary + twoHyphens
					+ lineEnd);

			// Responses from the server (code and message)
			String serverResponseMessage = connection.getResponseMessage();

			Log.d("Server Response", serverResponseMessage);
			// Log.d("serverResponseCode",serverResponseCode);
			fileInputStream.close();
			outputStream.flush();
			outputStream.close();

			// Log.d("Server Response1",
			// bundle.getString(Constants.Extras.EXTRA_JOB_NAME) + userId +
			// JPGName);

			// File file = new
			// File(Environment.getExternalStorageDirectory()
			// .getPath() + "/DCIM/Camera/" + JPGName);
			// file.delete();

			// boolean deleteFile
			// (Environment.getExternalStorageDirectory().getPath()+"/DCIM/Camera/"+JPGName);
			message[0] = serverResponseMessage.toString();

		} catch (Exception ex) {
			// Exception handling
		}

		return message;
	}

	@Override
	protected void onPostExecute(String[] result) {

		listner.onComplete(result);
		pDialog.dismiss();
		super.onPostExecute(result);
	}

	public void setWebServiceListener(CConectAppListener listener) {
		this.listner = listener;
	}

}
