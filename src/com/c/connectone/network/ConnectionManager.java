package com.c.connectone.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.c.connectone.prefrences.Constants;

import android.util.Log;

public class ConnectionManager {

	private static ConnectionManager instance;
	private static List<NameValuePair> nameValuePairs;

	private ConnectionManager() {

	}

	public static ConnectionManager getInstence() {

		nameValuePairs = new ArrayList<NameValuePair>();

		if (instance == null) {
			instance = new ConnectionManager();
		}
		return instance;

	}

	public void addParam(String key, String value) {
		nameValuePairs.add(new BasicNameValuePair(key, value));
	}

	public String UserLogin(String url) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		String respone = null;
		try {

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// Execute HTTP Post Request
			Log.d("Userpass", nameValuePairs.toString());

			HttpResponse response = httpclient.execute(httppost);
			Log.d("Server", response.toString());

			HttpEntity entity = response.getEntity();

			if (entity != null) {

				InputStream instream = entity.getContent();
				respone = convertStreamToString(instream);

				Log.d("URL is : ", url);
				Log.d("Login Result: ", respone);

				// Closing the input stream will trigger connection release
				instream.close();
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

		return respone;
	}

	public String CreateEntry(String url, List<NameValuePair> pair) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		String respone = null;
		try {

			httppost.setEntity(new UrlEncodedFormEntity(pair));
			// Execute HTTP Post Request
			Log.d("name pair ", pair.toString());

			HttpResponse response = httpclient.execute(httppost);
			Log.d("Server", response.toString());

			HttpEntity entity = response.getEntity();

			if (entity != null) {

				InputStream instream = entity.getContent();
				respone = convertStreamToString(instream);
				Log.d("Login Result: ", respone);

				// Closing the input stream will trigger connection release
				instream.close();
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

		return respone;
	}

	public String postUrl(String url) throws ClientProtocolException,
			IOException {
		String respone = null;
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();

		if (entity != null) {

			InputStream instream = entity.getContent();
			respone = convertStreamToString(instream);

			// Closing the input stream will trigger connection release

			instream.close();
		}
		Log.d("Response: ", respone);
		return respone;
	}

	// -----------------GetRequest----------------

	public String getUrl(String url) throws ClientProtocolException,
			IOException {

		String respone = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String paramString = URLEncodedUtils.format(nameValuePairs, "utf-8");
		url += "?" + paramString;
		HttpGet httpGet = new HttpGet(url);
		Log.d("Get Request for products", url);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity entity = httpResponse.getEntity();

		if (entity != null) {

			InputStream instream = entity.getContent();
			respone = convertStreamToString(instream);

			// Closing the input stream will trigger connection release
			instream.close();
		}
		Log.d("Response: ", respone);
		return respone;
	}

	private String convertStreamToString(InputStream is) {
		ByteArrayOutputStream oas = new ByteArrayOutputStream();
		copyStream(is, oas);
		String t = oas.toString();
		try {
			oas.close();
			oas = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

	private void copyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

}
