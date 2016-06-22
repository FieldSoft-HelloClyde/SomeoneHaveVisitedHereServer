package model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Location {
	public static String getLocationString(double Latitude,double Longitude){
		String url = "http://maps.google.cn/maps/api/geocode/json";
		Map<String, String> GetMap = new HashMap<String, String>();
		GetMap.put("latlng", Latitude + "," + Longitude);
		GetMap.put("sensor", "true");
		GetMap.put("language", "zh-CN");
		String response = new HttpHostConnection().sendGetData(url, GetMap, "utf-8");
		//System.out.println(response);
		if (response != null) {
			try {
				JSONObject jsonObject = new JSONObject(response);
				// 获取results节点下的位置信息
				JSONArray resultArray = jsonObject.getJSONArray("results");
				if (resultArray.length() > 0) {
					JSONObject obj = resultArray.getJSONObject(0);
					// 取出格式化后的位置数据
					String address = obj.getString("formatted_address");
					return address;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
