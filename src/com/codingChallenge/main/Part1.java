package com.codingChallenge.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import com.codingChallenge.util.ReadUtil;

/**
 * 
 * @author Weixiang Zhang
 * @Time 09-19-2017
 * 
 *       Requirement:
 * 
 *       - Utilize Java 6 or later
 * 
 *       Features:
 * 
 *       - Retrieve data from the following URL and parse the JSON
 * 
 *       URL: https://gist.githubusercontent.com/jed204/92f90060d0fabf65792d6d479c45f31c/raw/346c44a23762749ede009623db37f4263e94ef2a/java2.json
 * 
 *       - Add all the recv and sent values for totals of each field and present
 *       the results
 * 
 * 
 *       Notes:
 * 
 *       - Console Application or Web App (your call!) 
 *       - Use appropriate amounts of error handling - You can use external libraries to support implementing the features (if desired or needed) 
 *       - Create a repo/gist on GitHub for your results
 */
public class Part1 {

	public static void main(String[] args) {
		int sents_total = 0;
		int recvs_total = 0;
		Map<String, Map<String, Integer>> c_map = new LinkedHashMap<String, Map<String, Integer>>();

		try {
			// Retrieve data, return as string
			String jsonStr = ReadUtil
					.getData("https://gist.githubusercontent.com/jed204/92f90060d0fabf65792d6d479c45f31c/raw/346c44a23762749ede009623db37f4263e94ef2a/java2.json");
			// c levels of the data
			JSONObject c_lvls = JSONObject.fromObject(jsonStr);
			// get all the keys in c levels
			@SuppressWarnings("unchecked")
			Iterator<String> c_keys = c_lvls.keys();
			// Iterate c levels through c levels keys
			while (c_keys.hasNext()) {
				String cur_ckey = c_keys.next().toString();
				// m levels in each c level
				JSONObject m_lvls = c_lvls.getJSONObject(cur_ckey);
				// get all the keys in current m level
				@SuppressWarnings("unchecked")
				Iterator<String> m_keys = m_lvls.keys();
				
				//reset the counters
				int cur_m_sents_total = 0;
				int cur_m_recv_total = 0;
				// Iterate m levels through m levels keys
				while (m_keys.hasNext()) {
					String cur_mkey = m_keys.next().toString();
					JSONObject object = m_lvls.getJSONObject(cur_mkey);
					// sum current m levels sent and recv
					cur_m_sents_total += Integer.parseInt(String.valueOf(object
							.get("sent")));
					cur_m_recv_total += Integer.parseInt(String.valueOf(object
							.get("recv")));
					// sum total sent and recv
					sents_total += Integer.parseInt(String.valueOf(object
							.get("sent")));
					recvs_total += Integer.parseInt(String.valueOf(object
							.get("recv")));
				}

				Map<String, Integer> cur_map = new LinkedHashMap<String, Integer>();
				cur_map.put("sent", cur_m_sents_total);
				cur_map.put("recv", cur_m_recv_total);
				c_map.put(cur_ckey, cur_map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//output results to Part1_Result.txt
		File Part1_Result = new File("Part1_Result.txt");
		try {
			if (!Part1_Result.exists())
				Part1_Result.createNewFile();
			FileWriter fileWriter = new FileWriter(Part1_Result);
			
			//write total sent and recv
			fileWriter.write("sent total: " + sents_total + "\n");
			fileWriter.write("recv total: " + recvs_total + "\n\n");

			//write sum of sent and recv in each c level
			for (Map.Entry<String, Map<String, Integer>> c_entry : c_map
					.entrySet()) {
				fileWriter.write(c_entry.getKey() + ":\n");
				Map<String, Integer> m_map = c_entry.getValue();
				for (Entry<String, Integer> m_entry : m_map.entrySet()) {
					fileWriter.write("    " + m_entry.getKey() + ": "
							+ m_entry.getValue() + "\n");
				}
			}
			fileWriter.close();
			System.out.println("Part 1 complete.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
