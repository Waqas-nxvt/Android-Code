package com.c.connectone.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.c.connectone.R;
import com.c.connectone.bo.JobBO;

public class JobsListAdapter extends BaseAdapter {

	List<JobBO> jobList;
	LayoutInflater inflater;

	public JobsListAdapter(Activity a, List<JobBO> list) {
		jobList = list;
		inflater = a.getLayoutInflater();

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return jobList.size();
	}

	@Override
	public JobBO getItem(int position) {

		return jobList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup arg2) {

		convertview = inflater.inflate(R.layout.adapter_jobs_list, arg2, false);

		TextView jobname = (TextView) convertview
				.findViewById(R.id.tv_adapter_jobs_name);
		TextView jobtype = (TextView) convertview
				.findViewById(R.id.tv_adapter_jobs_type);
		TextView jobmove = (TextView) convertview
				.findViewById(R.id.tv2_adapter_jobs_list);
		JobBO jobs = getItem(position);

		if (jobs != null) {
			jobname.setText("Job Name :" + jobs.getJobName());
			jobtype.setText("Job Type :" + jobs.getJob_type());
			jobmove.setText(">");

		}

		return convertview;
	}

}
