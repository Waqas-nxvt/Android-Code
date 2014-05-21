package com.c.connectone.bo;

import org.json.JSONObject;

public class JobBO {

	private String Name;
	private String account;
	private String corp;
	private String job_number;
	private String job_type;

	public String getJobName() {
		return Name;
	}

	public void setJobName(String jobName) {
		this.Name = jobName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getJob_number() {
		return job_number;
	}

	public void setJob_number(String job_number) {
		this.job_number = job_number;
	}

	public String getCorp() {
		return corp;
	}

	public void setCorp(String corp) {
		this.corp = corp;
	}

	public String getJob_type() {
		return job_type;
	}

	public void setJob_type(String job_type) {
		this.job_type = job_type;
	}

	public JobBO getJobsObvject(JSONObject JobsObject) {
		JobBO jobs = new JobBO();
		jobs.setJobName(JobsObject.optString("name"));
		jobs.setJob_number(JobsObject.optString("job_number"));
		jobs.setCorp(JobsObject.optString("corp"));
		jobs.setJob_type(JobsObject.optString("job_type"));
		jobs.setAccount(JobsObject.optString("account"));
		return jobs;
	}

}
