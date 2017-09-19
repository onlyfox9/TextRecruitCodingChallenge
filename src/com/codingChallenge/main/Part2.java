package com.codingChallenge.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.codingChallenge.util.CalculateMD5Sum;
import com.codingChallenge.util.ReadUtil;

public class Part2 {

	/**
	 * 
	 * @author Weixiang Zhang
	 * @Time 09-19-2017 Requirement:
	 * 
	 *       - Utilize Java 6 or later
	 * 
	 *       Features:
	 * 
	 *       - Read the data from the following URL and prase the JSON
	 * 
	 *       URL: https://gist.githubusercontent.com/anonymous/8f60e8f
	 *       49158efdd2e8fed6fa97373a4/raw/01add7ea44ed12f5d90180dc1367915af331492e/java-data2.js
	 *       o n
	 * 
	 *       - Using threads, for each item in the JSON 'items' array process
	 *       the MD5 checksum of the 'uid' value and present the MD5 result and
	 *       the current thread name
	 * 
	 * 
	 *       Notes:
	 * 
	 *       - Console Application or Web App (your call!) - Use appropriate
	 *       amounts of error handling - You can use external libraries to
	 *       support implementing the features (if desired or needed) - Create a
	 *       repo/gist on GitHub for your results
	 */

	public static void main(String[] args) throws InterruptedException {

		//get core number
		int cpuCoreNumber = Runtime.getRuntime().availableProcessors();
		List<CalculateMD5Sum> threadList = new ArrayList<CalculateMD5Sum>();

		String jsonStr = ReadUtil
				.getData("https://gist.githubusercontent.com/anonymous/8f60e8f49158efdd2e8fed6fa97373a4/raw/01add7ea44ed12f5d90180dc1367915af331492e/java-data2.json");
		// convert into objects
		JSONObject items = JSONObject.fromObject(jsonStr);
		// exact items from object
		JSONArray itemArray = items.getJSONArray("items");
		//assign threads depends on core numbers
		int base = itemArray.size() / cpuCoreNumber;

		long startTime = System.currentTimeMillis();
		for (int i = 0; i < cpuCoreNumber; i++) {
			threadList.add(new CalculateMD5Sum(i * base, (i + 1) * base,
					itemArray));
			threadList.get(i).start();
		}
		for (CalculateMD5Sum cur : threadList)
			cur.join();
		
		StringBuffer result = new StringBuffer();
		for (CalculateMD5Sum cur : threadList)
			result.append(cur.getResult());
		long endTime = System.currentTimeMillis();

		//output the results to Part2_Result.txt
		File Part2_Result = new File("Part2_Result.txt");
		try {
			if (!Part2_Result.exists())
				Part2_Result.createNewFile();
			FileWriter fileWriter = new FileWriter(Part2_Result);
			fileWriter.write(result.toString());
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("run time: " + (endTime - startTime) + " milliseconds");
	}

}
