package com.spatialTool.gis;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

public class CalcDrivingDistance {
	//计算驾车的导航距离
	@SuppressWarnings("rawtypes")
	private double obtainDrivingNav(String origin,String dest,String originCity,String destCity){
		String urlStr = "http://api.map.baidu.com/direction/v1?mode=driving&origin=#origin#&destination=#dest#&origin_region=#originCity#&destination_region=#destCity#&output=json&ak=19c13ae49dfbf889e044f75cab7acd8b";
		String inurl=urlStr.replace("#origin#", origin)
				.replace("#originCity#",originCity)
				.replace("#destCity#", destCity)
				.replace("#dest#", dest);;
				HttpURLConnection httpurlconnection = null;
				URL url = null;
				try {
					url = new URL(inurl);
					// 以post方式请求
					httpurlconnection = (HttpURLConnection) url.openConnection();
					httpurlconnection.setDoOutput(true);
					httpurlconnection.setConnectTimeout(5000);
					httpurlconnection.setReadTimeout(5000);
					// 获取页面内容
					InputStream in = httpurlconnection.getInputStream();
					ObjectMapper mapper = new ObjectMapper();
					Map resultMap = mapper.readValue(in, Map.class);
					if (resultMap.get("result") != null) {
						Map result = (Map)resultMap.get("result");
						if(result.get("routes")!=null){
							ArrayList list = (ArrayList) result.get("routes");
							Map first = (Map)list.get(0);
							int d =   (Integer) first.get("distance");   //导航距离  单位米
							int t = (Integer)first.get("duration");      //导航时间  单位秒
							return d;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 0;
	}
	
	public static void main(String[] args) {
		double distance = new CalcDrivingDistance().obtainDrivingNav("39.913033,116.370121", "39.914195,116.413455", "上海", "上海");
		System.out.println("导航距离："+distance);
	}

}
