package com.c.connectone.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.c.connectone.R;

public class PreviewDialog extends Dialog {

	Context context;
	private ImageView fullimag;
	private Bitmap image;
	public Activity a;
	int screenWidth;
	int screenHeight;
	Button btn_retake, btn_cancel;

	public PreviewDialog(Context context, Bitmap img, Activity activity) {
		super(context);
		this.context = context;
		image = img;
		a = activity;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.img_preview);
		fullimag = (ImageView) findViewById(R.id.img_preview);
		btn_retake = (Button) findViewById(R.id.btn_preview_retake);
		btn_cancel = (Button) findViewById(R.id.btn_preview_cancel);
		fullimag.setImageBitmap(image);
		getScreenSize();

		setSize(screenWidth * 0.8, screenHeight * 0.7);

	}

	public Button reTakeButton() {
		return btn_retake;
	}

	public Button cancelButton() {
		return btn_cancel;
	}

	public void setSize(double width, double height) {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = (int) width;
		lp.height = (int) height;
		getWindow().setAttributes(lp);
	}

	public void getScreenSize() {

		DisplayMetrics displaymetrics = new DisplayMetrics();

		a.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		screenHeight = displaymetrics.heightPixels;
		screenWidth = displaymetrics.widthPixels;

	}

}
