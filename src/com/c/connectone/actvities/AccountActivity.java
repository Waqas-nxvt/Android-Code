package com.c.connectone.actvities;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.c.connectone.R;
import com.c.connectone.AppListener.CConectAppListener;
import com.c.connectone.network.ConnectionManager;
import com.c.connectone.parser.JsonParser;
import com.c.connectone.prefrences.Constants;
import com.c.connectone.utils.Common;
import com.c.connectone.utils.PrefrenceUtil;

public class AccountActivity extends Activity implements OnClickListener,
		CConectAppListener {
	private static final int PICK_CAMERA_IMAGE = 0;
	private Spinner sp;
	private Button btn_take_snap;
	private ImageView imageview_one;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.acount_activity);
		sp = (Spinner) findViewById(R.id.spinner1);
		btn_take_snap = (Button) findViewById(R.id.btn_account_take_snap);
		imageview_one = (ImageView) findViewById(R.id.iv_acount_one);

		btn_take_snap.setOnClickListener(this);
		String usrId = PrefrenceUtil.getPrefrence(AccountActivity.this,
				Constants.Pref.PREF_USERNAME);

		// ---check internet
		if (Common.isNetworkAvailable(getApplicationContext())) {

			// ---load all jobs --
			// MyAsyntask task = new MyAsyntask(AccountActivity.this);
			// task.addParam("user_id", String.valueOf(Constants.USER.getId()));
			// task.setWebServiceListener(AccountActivity.this);
			// task.execute(Constants.ServerActions.URL_ALL_PRODUCTS);

			GetJobs jobs = new GetJobs();
			jobs.execute();
		} else {

			Common.ShowToast(getApplicationContext(),
					getResources().getString(R.string.Toast_no_internet));

		}

		String[] items = new String[] { "One", "Two", "Three" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, items);

		sp.setAdapter(adapter);

	}

	@Override
	public void onClick(View arg0) {

		int id = arg0.getId();
		switch (id) {
		case R.id.btn_account_take_snap:
			Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(i, PICK_CAMERA_IMAGE);

			break;

		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {

			if (data == null) {

				Toast.makeText(getApplicationContext(), "Null data", 1).show();
			} else {

				Bitmap photo = (Bitmap) data.getExtras().get("data");
				imageview_one.setImageBitmap(photo);

			}
		}
	}

	class GetJobs extends AsyncTask<String, Integer, String> {
		ProgressDialog pDialogz;
		String[] mesage;

		@Override
		protected void onPreExecute() {

			pDialogz = new ProgressDialog(AccountActivity.this);
			Common.progress(pDialogz, "Loading products");
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... arg0) {

			int result = 0;
			String server_msg = "";

			JsonParser json = new JsonParser();
			// --Get-user Id from prefrence
			String usrId = PrefrenceUtil.getPrefrence(AccountActivity.this,
					Constants.Pref.PREF_USERNAME);

			ConnectionManager connection = ConnectionManager.getInstence();
			connection.addParam("user_id",
					String.valueOf(Constants.USER.getId()));

			try {
				String res = connection
						.getUrl(Constants.ServerActions.URL_ALL_PRODUCTS);
				result = json.parseStatus(res);
				mesage = json.parseUserObject(res);
				server_msg = mesage[1];
				if (mesage[0].contains("0")) {
					server_msg = mesage[1];

				}

				if (result != 0) {

				}

			} catch (ClientProtocolException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
			return server_msg;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Common.ShowToast(AccountActivity.this, result);
			pDialogz.dismiss();

		}

	}

	@Override
	public void onComplete(String response[]) {

		// Common.ShowToast(AccountActivity.this, response);
		// finish();

	}

	@Override
	public void onError(String errorCode) {
		// TODO Auto-generated method stub

	}

}
