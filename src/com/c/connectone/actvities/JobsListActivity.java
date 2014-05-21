package com.c.connectone.actvities;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.c.connectone.R;
import com.c.connectone.adapter.JobsListAdapter;
import com.c.connectone.bo.JobBO;
import com.c.connectone.network.ConnectionManager;
import com.c.connectone.parser.JsonParser;
import com.c.connectone.prefrences.Constants;
import com.c.connectone.utils.Common;

public class JobsListActivity extends Activity implements OnItemClickListener {
	private ImageButton imagebtn_back;
	private ListView jobslist;
	private JobsListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jobs_list_activity);
		imagebtn_back = (ImageButton) findViewById(R.id.image_btn_job_list_back);
		jobslist = (ListView) findViewById(R.id.list_jobs);
		jobslist.setOnItemClickListener(this);
		
		imagebtn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();

			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (Common.isNetworkAvailable(getApplicationContext())) {
			GetJobs jobstask = new GetJobs();
			jobstask.execute();
		} else {

			Common.ShowToast(getApplicationContext(),
					getResources().getString(R.string.Toast_no_internet));
		}

		
		super.onResume();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View convertview,
			int position, long arg3) {

		JobBO jobsobject = adapter.getItem(position);
		Intent intent = new Intent(getApplicationContext(),
				JobDetailActivity.class);

		intent.putExtra(Constants.Extras.EXTRA_JOB_NAME,
				jobsobject.getJobName());
		intent.putExtra(Constants.Extras.EXTRA_JOB_NUMBER,
				jobsobject.getJob_number());
		intent.putExtra(Constants.Extras.EXTRA_JOB_TYPE,
				jobsobject.getJob_type());
		intent.putExtra(Constants.Extras.EXTRA_JOB_CORP,
				jobsobject.getCorp());
		startActivity(intent);

	}

	public JobBO getobj(String name) {

		JobBO jo = new JobBO();
		jo.setJobName(name);

		return jo;

	}

	class GetJobs extends AsyncTask<String, Integer, String[]> {
		ProgressDialog pDialogz;
		String[] mesage;
		List<JobBO> dummyJobList;

		@Override
		protected void onPreExecute() {

			pDialogz = new ProgressDialog(JobsListActivity.this);
			Common.progress(pDialogz, getResources().getString(R.string.progress_wait_load_products));
			super.onPreExecute();

		}

		@Override
		protected String[] doInBackground(String... arg0) {

			int result = 0;
			String server_msg = "";
			dummyJobList = new ArrayList<JobBO>();

			JsonParser json = new JsonParser();
			// --Get-user Id from prefrence
			ConnectionManager connection = ConnectionManager.getInstence();
			connection.addParam("user_id", String.valueOf(Constants.Username));

			try {
				String res = connection
						.getUrl(Constants.ServerActions.URL_ALL_PRODUCTS);
				mesage = json.parseJobsObject(res);
				if (mesage[0].contains("0")) {

				} else if (mesage[0].equalsIgnoreCase("1")) {

					json.parseJobsObject(res);
				}

			} catch (ClientProtocolException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
			return mesage;
		}

		@Override
		protected void onPostExecute(String[] result) {
			super.onPostExecute(result);
			if (result[0].equalsIgnoreCase("1")) {

				adapter = new JobsListAdapter(JobsListActivity.this,
						Constants.jobslist);
				jobslist.setAdapter(adapter);

			} else {

				Common.ShowToast(JobsListActivity.this, result[1]);

			}

			pDialogz.dismiss();

		}

	}

}
