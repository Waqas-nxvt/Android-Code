package com.c.connectone.actvities;

import org.apache.http.util.EncodingUtils;

import com.c.connectone.R;
import com.c.connectone.prefrences.Constants;
import com.c.connectone.utils.Common;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HomeHealthActivity extends Activity {
	private WebView HHWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_health_activity);

		HHWebView = (WebView) findViewById(R.id.hh_webview);

		WebSettings webSettings = HHWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		HHWebView.setWebViewClient(new WebViewClient());

		String postData = "technicianId=0500&login=201402&password=83399";
		
		// ---check internet
		if (Common.isNetworkAvailable(HomeHealthActivity.this)) {
			// --- Object webview;
			HHWebView.postUrl(Constants.ServerActions.HHC_URL,
					EncodingUtils.getBytes(postData, "BASE64"));
		} else {
			// no internet 
			Common.ShowToast(getApplicationContext(),
					getResources().getString(R.string.Toast_no_internet));

		}

	}

}
