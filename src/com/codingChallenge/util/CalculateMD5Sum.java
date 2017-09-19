package com.codingChallenge.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CalculateMD5Sum extends Thread {
	private long start;
	private long end;
	private JSONArray array;
	private StringBuffer result = new StringBuffer();
	

	public CalculateMD5Sum(long start, long end, JSONArray jsonArray) {
		super();
		this.start = start;
		this.end = end;
		this.array = jsonArray;
	}

	public synchronized void run() {
		for (long i = start; i < end; i++) {
			JSONObject itemData = array.getJSONObject((int) i);
			try {
				MessageDigest messageDigest = MessageDigest.getInstance("MD5");
				byte[] inputByteArray = itemData.getString("uid").getBytes();
				messageDigest.update(inputByteArray);
				byte[] resultByteArray = messageDigest.digest();
				result.append(Thread.currentThread().getName() + "  index: " + itemData.getString("index")
						+ " MD5 Sum: " + byteArrayToHex(resultByteArray) + "\n");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @param byteArray
	 * @return Hex in String
	 */
	public static String byteArrayToHex(byte[] byteArray) {
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		char[] resultCharArray = new char[byteArray.length * 2];
		int index = 0;

		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}

		return new String(resultCharArray);
	}

	public String getResult() {
		
		return result.toString();
	}
	
}
