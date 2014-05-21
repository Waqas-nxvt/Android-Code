package com.c.connectone.actvities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.c.connectone.R;
import com.c.connectone.AppListener.CConectAppListener;
import com.c.connectone.DAO.LoginTask;
import com.c.connectone.DAO.ReturnEquipmentTask;
import com.c.connectone.prefrences.Constants;
import com.c.connectone.utils.Common;

public class MenuActivity extends Activity implements OnClickListener,
		CConectAppListener {
	private Button Selectjob;
	private Button ReturnEq;
	private Button HomeHeath;
	String returnContent;
	private static final int ZBAR_SCANNER_REQUEST = 0;
	private static final int ZBAR_QR_SCANNER_REQUEST = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.menu_activity);

		Selectjob = (Button) findViewById(R.id.btn_menu_select_job);
		ReturnEq = (Button) findViewById(R.id.btn_menu_return_eq);
		HomeHeath = (Button) findViewById(R.id.btn_menu_home_health);
		Selectjob.setOnClickListener(this);

		ReturnEq.setOnClickListener(this);
		HomeHeath.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		int id = v.getId();

		switch (id) {
		case R.id.btn_menu_select_job:
			Intent i = new Intent(MenuActivity.this, JobsListActivity.class);
			startActivity(i);

			break;
		case R.id.btn_menu_return_eq:

			try {

				Intent intent = new Intent(
						"com.google.zxing.client.android.SCAN");
				intent.putExtra("SCAN_MODE", "QR_CODE_MODE,PRODUCT_MODE");
				startActivityForResult(intent, 0);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), "ERROR:" + e, 1).show();

			}
			break;

		case R.id.btn_menu_home_health:

			Common.ShowToast(getApplicationContext(),
					getResources().getString(R.string.Toast_inDevelopment));

			// Intent intenthealth = new Intent(MenuActivity.this,
			// HomeHealthActivity.class);
			// startActivity(intenthealth);

			break;

		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				returnContent = data.getStringExtra("SCAN_RESULT");

				// --check internet
				if (Common.isNetworkAvailable(MenuActivity.this)) {

					// ---post barcode data to server
					ReturnEquipmentTask task = new ReturnEquipmentTask(
							MenuActivity.this);
					task.setWebServiceListener(MenuActivity.this);
					task.addParam("content", returnContent);
					task.execute(Constants.ServerActions.URL_CREATE_ENTRY);
				} else {
					// no ineternet toast
					Common.ShowToast(getApplicationContext(), getResources()
							.getString(R.string.Toast_no_internet));
				}
				Common.ShowToast(MenuActivity.this, returnContent);

				// Handle successful scan
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
			}
		}

	}

	@Override
	public void onComplete(String response[]) {
		Common.ShowToast(MenuActivity.this, response[0]);

	}

	@Override
	public void onError(String errorCode) {
		// TODO Auto-generated method stub

	}

}
