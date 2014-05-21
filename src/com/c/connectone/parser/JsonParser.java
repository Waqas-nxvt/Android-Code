package com.c.connectone.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.c.connectone.bo.JobBO;
import com.c.connectone.bo.UserBO;
import com.c.connectone.prefrences.Constants;

public class JsonParser {

	public int parseStatus(String response) {
		int result = 0;

		try {
			result = new JSONObject(response).getInt("success");
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return result;

	}

	public String[] parseUserObject(String jsonResponse) {
		String results[] = { "", "" };

		try {
			JSONObject jObject = new JSONObject(jsonResponse);
			results[0] = jObject.optString("status");
			if (results[0].equals("1")) {
				JSONObject userObject = jObject.getJSONObject("user");
				if (userObject != null) {
					Constants.USER = new UserBO();

					Constants.USER = Constants.USER.getUserObvject(userObject);
				}
			} else {
				results[1] = jObject.optString("message");
				results[0] = jObject.optString("status");

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;

	}

	public String[] parseJobsObject(String jsonResponse) {
		String results[] = { "", "" };
		try {
			JSONObject jObject = new JSONObject(jsonResponse);
			results[0] = jObject.optString("success");
			if (results[0].equals("1")) {

				JSONArray jobsarray = jObject.getJSONArray("products");
				if (jobsarray != null) {

					// created jobs business object
					Constants.JOBS = new JobBO();

					// created jobs list

					
					Constants.jobslist = new ArrayList<JobBO>();

					for (int i = 0; i < jobsarray.length(); i++) {

						JSONObject jobject = jobsarray.getJSONObject(i);
						Constants.jobslist.add(Constants.JOBS
								.getJobsObvject(jobject));

					}
				}

			} else {
				results[1] = jObject.optString("message");

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;

	}

}
