package com.c.connectone.actvities;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.c.connectone.CconnectApplication;
import com.c.connectone.R;
import com.c.connectone.AppListener.CConectAppListener;
import com.c.connectone.DAO.CreateEntryTask;
import com.c.connectone.DAO.SendImageToServerTask;
import com.c.connectone.prefrences.Constants;
import com.c.connectone.utils.Common;

import com.c.connectone.utils.PreviewDialog;

public class JobDetailActivity extends Activity implements OnClickListener,
		CConectAppListener {

	private static final int PICK_CAMERA_IMAGE = 0;
	private static final int PICK_CAMERA_IMAGE_ONE = 1;
	private static final int PICK_CAMERA_IMAGE_TWO = 2;
	private static final int PICK_CAMERA_IMAGE_THREE = 3;

	Bundle bundle;
	private ImageButton btn_jobdetail_back;
	private Button btn_captureSnap;
	private Button btn_closejob;
	private ImageView imageone, imagetwo, imagethree;
	int picCount = 1;
	private TextView tvJobName, tvJobType, tvJobnumber;
	private String TAG = "JobDetailActivity";
	File ImageFile;
	private String[] imagePathArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.job_detail_activity);
		tvJobName = (TextView) findViewById(R.id.tv_jobdetail_name);
		tvJobnumber = (TextView) findViewById(R.id.tv_jobdetail_jobnumber);
		tvJobType = (TextView) findViewById(R.id.tv_jobdetail_jobtype);
		btn_jobdetail_back = (ImageButton) findViewById(R.id.image_btn_jobdetail_back);
		btn_captureSnap = (Button) findViewById(R.id.btn_jobdetail_take_snap);
		btn_closejob = (Button) findViewById(R.id.btn_jobdetail_close_job);
		imageone = (ImageView) findViewById(R.id.iv_jobdetails_one);
		imagetwo = (ImageView) findViewById(R.id.iv_jobdetails_two);
		imagethree = (ImageView) findViewById(R.id.iv_jobdetails_three);
		btn_captureSnap.setOnClickListener(this);
		btn_jobdetail_back.setOnClickListener(this);
		btn_closejob.setOnClickListener(this);
		imageone.setOnClickListener(this);
		imagetwo.setOnClickListener(this);
		imagethree.setOnClickListener(this);

		bundle = getIntent().getExtras();
		tvJobName.setText(bundle.getString(Constants.Extras.EXTRA_JOB_NAME));
		tvJobType.setText(bundle.getString(Constants.Extras.EXTRA_JOB_TYPE));
		tvJobnumber
				.setText(bundle.getString(Constants.Extras.EXTRA_JOB_NUMBER));
		imagePathArray = new String[3];

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image_btn_jobdetail_back:
			finish();
			break;

		case R.id.btn_jobdetail_take_snap:

			// Common.ShowToast(getApplicationContext(),
			// getResources().getString(R.string.Toast_inDevelopment));

			if (picCount < 4) {

				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(i, PICK_CAMERA_IMAGE);
			} else {

				Common.ShowToast(getApplicationContext(), getResources()
						.getString(R.string.Toast_no_more_pic));
			}

			break;

		case R.id.btn_jobdetail_close_job:

			// Common.ShowToast(getApplicationContext(),
			// getResources().getString(R.string.Toast_inDevelopment));
			Location loc = Common.Getlocation(JobDetailActivity.this);

			if (loc != null) {

				Common.ShowToast(getApplicationContext(),
						String.valueOf(loc.getLatitude()) + "<=Lat,long=>"
								+ String.valueOf(loc.getLongitude()));
			} else {

			}

			for (int i = 1; i < 4; i++) {

				switch (i) {
				case 1:

					saveImage(imageone);

					break;
				case 2:
					saveImage(imagetwo);

					break;
				case 3:
					saveImage(imagethree);

					break;

				}
			}

			// if (picCount - 1 >= 3) {
			//
			// Common.ShowToast(getApplicationContext(),
			// "will Post to server ");
			//
			// } else {
			//
			// Common.ShowToast(getApplicationContext(), getResources()
			// .getString(R.string.Toast_take_snaps_warning));
			//
			// }

			break;

		case R.id.iv_jobdetails_one:

			showImagePreview(imageone, PICK_CAMERA_IMAGE_ONE);

			break;

		case R.id.iv_jobdetails_two:

			showImagePreview(imagetwo, PICK_CAMERA_IMAGE_TWO);

			break;
		case R.id.iv_jobdetails_three:

			showImagePreview(imagethree, PICK_CAMERA_IMAGE_THREE);

			break;

		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case PICK_CAMERA_IMAGE:
				Bitmap photo = (Bitmap) data.getExtras().get("data");

				// CALL THIS METHOD TO GET THE URI FROM THE BITMAP
				Uri tempUri = Common
						.getImageUri(getApplicationContext(), photo);

				// CALL THIS METHOD TO GET THE ACTUAL PATH
				ImageFile = new File(Common.getRealPathFromURI(
						getApplicationContext(), tempUri));

				Log.d(TAG, tempUri + " path " + ImageFile);

				// --------Send image to server
				SendImageToServerTask sendimag = new SendImageToServerTask(
						JobDetailActivity.this, ImageFile,
						bundle.getString(Constants.Extras.EXTRA_JOB_NAME));
				sendimag.setWebServiceListener(JobDetailActivity.this);
				sendimag.execute(Constants.ServerActions.UPLOAD_IMAGE);

				if (picCount == 1) {

					// save image path in array

					if (imagePathArray != null) {
						imagePathArray[0] = ImageFile.getAbsolutePath();

					}
					imageone.setImageBitmap(photo);
				} else if (picCount == 2) {

					if (imagePathArray != null) {
						imagePathArray[1] = ImageFile.getAbsolutePath();
					}
					imagetwo.setImageBitmap(photo);
				} else if (picCount == 3) {

					if (imagePathArray != null) {
						imagePathArray[2] = ImageFile.getAbsolutePath();
					}
					imagethree.setImageBitmap(photo);
				}

				picCount++;

				break;
			case PICK_CAMERA_IMAGE_ONE:
				setImagefrmCamera(data, imageone);

				break;
			case PICK_CAMERA_IMAGE_TWO:
				setImagefrmCamera(data, imagetwo);

				break;
			case PICK_CAMERA_IMAGE_THREE:
				setImagefrmCamera(data, imagethree);
				break;

			default:
				break;
			}

		}
	}

	// method to save image

	public void saveImage(ImageView imageview) {
		Bitmap b = ((BitmapDrawable) imageview.getDrawable()).getBitmap();
		Common.saveImage(b);
	}

	// method to set image from camera

	public void setImagefrmCamera(Intent intent, ImageView imag) {
		Bitmap photo = (Bitmap) intent.getExtras().get("data");
		imag.setImageBitmap(photo);

	}

	// method to start camera with diffrent requests
	public void showImagePreview(ImageView imag, final int requestcode) {

		if (picCount > 1) {

			final PreviewDialog d = new PreviewDialog(JobDetailActivity.this,
					((BitmapDrawable) imag.getDrawable()).getBitmap(),
					JobDetailActivity.this);

			d.show();

			Button Retake = d.reTakeButton();
			Button cancel = d.cancelButton();
			Retake.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					startActivityForResult(i, requestcode);
					d.dismiss();

				}
			});

			cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					d.dismiss();
				}
			});
		} else {
			Common.ShowToast(getApplicationContext(),
					getResources().getString(R.string.Toast_take_snaps_warning));

		}
	}

	@Override
	public void onComplete(String[] response) {

		if (response[0].equalsIgnoreCase("OK")) {

			CreateEntryTask task = new CreateEntryTask(JobDetailActivity.this,
					bundle.getString(Constants.Extras.EXTRA_JOB_NAME),
					Constants.Username, ImageFile.getName(),
					bundle.getString(Constants.Extras.EXTRA_JOB_TYPE),
					bundle.getString(Constants.Extras.EXTRA_JOB_CORP));

			task.execute();
		}

		Common.ShowToast(getApplicationContext(), response[0]);

	}

	@Override
	public void onError(String errorCode) {
		// TODO Auto-generated method stub

	}

}
