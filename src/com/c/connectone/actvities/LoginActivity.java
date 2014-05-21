package com.c.connectone.actvities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.c.connectone.R;
import com.c.connectone.AppListener.CConectAppListener;
import com.c.connectone.DAO.LoginTask;
import com.c.connectone.prefrences.Constants;
import com.c.connectone.utils.Common;
import com.c.connectone.utils.PrefrenceUtil;

public class LoginActivity extends Activity implements CConectAppListener {
	private Button LoginButton;
	private CheckBox savepass;

	private EditText UsrId;
	private EditText UsrPass;
	private String Username, UserPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		initializeVariable();

		// ------login -=-------
		LoginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				Username = UsrId.getText().toString();
				UserPassword = UsrPass.getText().toString();

				// ---check-empty-text

				if (!TextUtils.isEmpty(Username)
						&& !TextUtils.isEmpty(UserPassword)) {

					// ---check internet--
					if (Common.isNetworkAvailable(LoginActivity.this)) {
						LoginTask logintask = new LoginTask(LoginActivity.this);
						logintask.setWebServiceListener(LoginActivity.this);
						logintask.addParam("username", Username);
						logintask.addParam("password", UserPassword);
						logintask.execute(Constants.ServerActions.URL_LOGIN);

					} else {

						// ---case no internet
						Common.ShowToast(
								getApplicationContext(),
								getResources().getString(
										R.string.Toast_no_internet));

					}

				} else {
					// ---case empty text
					Common.ShowToast(getApplicationContext(), getResources()
							.getString(R.string.Toast_empty_use));

				}

			}
		});

	}

	// --initialize all variables--
	private void initializeVariable() {

		LoginButton = (Button) findViewById(R.id.btn_login);
		savepass = (CheckBox) findViewById(R.id.chkbox_savepass);
		UsrId = (EditText) findViewById(R.id.et_usr_id);
		UsrPass = (EditText) findViewById(R.id.et_usr_pass);

		// -- check checkbox state from prefrences
		boolean chkstate = PrefrenceUtil.getPrefrenceBoolean(
				LoginActivity.this, Constants.Pref.PREF_CHK_STATE);

		if (chkstate) {
			// change checkbox state to checked
			savepass.setChecked(true);
		} else {
			// clear all prefrences
			PrefrenceUtil.clearPrefrence(LoginActivity.this,
					Constants.Pref.PREFS_NAME);
		}

		String usernameStored = PrefrenceUtil.getPrefrence(LoginActivity.this,
				Constants.Pref.PREF_USERNAME);
		String passwordStored = PrefrenceUtil.getPrefrence(LoginActivity.this,
				Constants.Pref.PREF_PASSWORD);

		if (usernameStored != null && passwordStored != null) {
			UsrId.setText(usernameStored);
			UsrPass.setText(passwordStored);

		}

	}

	@Override
	public void onComplete(String results[]) {

		if (results[0].equals("1")) {
			if (savepass.isChecked()) {

				// ------Savecredentials
				PrefrenceUtil.SavePrefrence(LoginActivity.this,
						Constants.Pref.PREF_USERNAME, Username);
				PrefrenceUtil.SavePrefrence(LoginActivity.this,
						Constants.Pref.PREF_PASSWORD, UserPassword);
				PrefrenceUtil.SavePrefrenceBoolean(LoginActivity.this,
						Constants.Pref.PREF_CHK_STATE, true);

				Constants.Username = Username;

			} else {
				PrefrenceUtil.SavePrefrenceBoolean(LoginActivity.this,
						Constants.Pref.PREF_CHK_STATE, false);

			}
			// Start menu activity
			// Intent intent = new Intent(LoginActivity.this,
			// MenuActivity.class);
			// startActivity(intent);
			// finish();

			Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
			startActivity(intent);

		} else {
			Common.ShowToast(getApplicationContext(), results[1]);
		}
		// int i = 0;
		//
		// i = Integer.parseInt(response);
		//
		// switch (i) {
		// case 0:
		// Common.ShowToast(getApplicationContext(), "Login Failed with "
		// + response);
		//
		// if (savepass.isChecked()) {
		//
		// // save password chacked
		// Common.SavePrefrenceBoolean(LoginActivity.this,
		// Constants.Pref.PREF_CHK_STATE, true);
		// } else {
		// // save password not chacked
		// Common.SavePrefrenceBoolean(LoginActivity.this,
		// Constants.Pref.PREF_CHK_STATE, false);
		// }
		//
		// break;
		// case 1:
		//
		// if (savepass.isChecked()) {
		//
		// // ------Savecredentials
		// Common.SavePrefrence(LoginActivity.this,
		// Constants.Pref.PREF_USERNAME, Username);
		// Common.SavePrefrence(LoginActivity.this,
		// Constants.Pref.PREF_PASSWORD, UserPassword);
		// Common.SavePrefrenceBoolean(LoginActivity.this,
		// Constants.Pref.PREF_CHK_STATE, true);
		//
		// } else {
		// Common.SavePrefrenceBoolean(LoginActivity.this,
		// Constants.Pref.PREF_CHK_STATE, false);
		//
		// }
		// // Start menu activity
		// // Intent intent = new Intent(LoginActivity.this,
		// // MenuActivity.class);
		// // startActivity(intent);
		// // finish();
		//
		// Intent intent = new Intent(LoginActivity.this,
		// JobsListActivity.class);
		// startActivity(intent);
		// finish();
		//
		// break;
		//
		// }

	}

	@Override
	public void onError(String errorCode) {
		// TODO Auto-generated method stub

	}

}
