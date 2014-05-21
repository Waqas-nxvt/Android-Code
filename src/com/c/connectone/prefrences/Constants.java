package com.c.connectone.prefrences;

import java.util.List;

import com.c.connectone.bo.JobBO;
import com.c.connectone.bo.UserBO;

public class Constants {

	public static class ServerActions {

		public static String SERVER_URL = "http://jsc.connect1.us/android_connect/";

		// AccountActivity
		public static String INST_ALL_PRODUCTS = SERVER_URL
				+ "get_INST_products.php";
		public static String URL_LOAD_IMAGES = SERVER_URL
				+ "img.php?src=http://jsc.connect1.us";

		public static String URL_ALL_PRODUCTS = SERVER_URL
				+ "get_entire_products_android.php";
		public static String URL_CORP = SERVER_URL + "get_corp.php";
		// AssetIssueActivity
		public static String URL_CREATE_ENTRY = SERVER_URL
				+ "write_tech_assets.php";
		public static String ASSET_ALL_PRODUCTS = SERVER_URL
				+ "get_tech_assets.php";
		public static String ASSET_WRITE_PRODUCTS = SERVER_URL
				+ "write_tech_assets.php";
		// public static String URL_CORP = SERVER_URL + "get_corp.php"; /// Same
		// CameraActivity
		public static String URL_CREATE_IMAGES = SERVER_URL
				+ "create_product.php";
		// CHGAccountActivity
		public static String CHG_ALL_PRODUCTS = SERVER_URL
				+ "get_CHG_products.php";
		public static String UPLOAD_IMAGE = "http://jsc.connect1.us/handle_upload.php";

		// HhcActivity
		public static String HHC_URL = "https://techassist.cablevision.com/TechAssist/LoginRead.do?theme=trilithic";
		// INSTAccountActivity
		// public static String URL_ALL_PRODUCTS =
		// SERVER_URL+"get_INST_products.php";
		// LoginActivity
		public static String URL_LOGIN = SERVER_URL + "checkANDROIDwPW.php";
		// MenuActivity
		// public static String URL_CREATE_ENTRY = SERVER_URL +
		// "write_returned_assets.php";
		// MultiChoiceListActivity
		public static String URL_ALL_PRODUCTS_CODES = SERVER_URL
				+ "get_tc_codes.php";
		// RESTAccountActivity
		public static String REST_ALL_PRODUCTS = SERVER_URL
				+ "get_REST_products.php";

	}

	public static UserBO USER;
	public static JobBO JOBS;
	public static List<JobBO> jobslist;

	public static String Username;

	public static class Pref {

		public static final String PREFS_NAME = "MyPrefsFile";
		public static final String PREF_USERNAME = "usernameStored";
		public static final String PREF_PASSWORD = "passwordStored";
		public static final String PREF_CHK_STATE = "state";

	}

	public static class Extras {

		public static final String EXTRA_JOB_NAME = "jobname";
		public static final String EXTRA_JOB_TYPE = "jobtype";
		public static final String EXTRA_JOB_NUMBER = "jobnumber";
		public static final String EXTRA_JOB_CORP = "corp";

	}

}
