package com.codingChallenge.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class ReadUtil {
	
	/**
	 * 
	 * @param address
	 * @return data in Json format
	 */
	public static String getData(String address) {
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(address);
			URLConnection URLconnection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				System.out.println("connected to address: " + address);
				InputStream in = httpConnection.getInputStream();
				InputStreamReader isr = new InputStreamReader(in);
				BufferedReader bufr = new BufferedReader(isr);
				String str;
				while ((str = bufr.readLine()) != null) {
					sb.append(str);
				}
				bufr.close();
			} else {
				System.err.println("failed to connect to address: " + address);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
