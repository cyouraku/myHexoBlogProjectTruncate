package org.geoHash.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* 爬虫类
* @author HD800241
*
*/
public class Crawler {

    //经度 -90 ~ 90
    private double longitude;//39.960767
    //纬度 -180 ~ 180
    private double latitude;//116.456299
    private int currentPage = 1;

    //高德API key
    private String key = "  ";
    private String requestUrl ;

    public Crawler( double latitude,double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;

        try {
            requestUrl = "http://restapi.amap.com/v3/place/around?key="+key+"&location="+String.valueOf(latitude)+","+String.valueOf(longitude)+"&output=json&radius=1000&keywords="+URLEncoder.encode("美食","utf-8")+"&offset=100&page="+currentPage;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
         System.out.println(requestUrl);
    }

    private String sendHttpRequest(){

        try {
            URL url=new URL(requestUrl);
            URLConnection connection =url.openConnection();
            InputStream IS=connection.getInputStream();
            InputStreamReader ISR =new InputStreamReader(IS,"UTF-8");
            BufferedReader BR =new BufferedReader(ISR);

            String line;
            StringBuilder builder=new StringBuilder();
            while((line=BR.readLine())!=null){
                builder.append(line);

            }

            BR.close();
            ISR.close();
            IS.close();

            return builder.toString();

        }catch (IOException e){
            e.printStackTrace();
        }

        return null;

    }

    public List<LocationBean> generateLocationList(){

        List<LocationBean> list = new ArrayList<LocationBean>();
        String jsonStr = sendHttpRequest();
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        JSONArray poisArray = jsonObject.getJSONArray("pois");

        if (poisArray.size()!=0){
            for (int i=0; i<poisArray.size();i++){

                JSONObject locationBean = poisArray.getJSONObject(i);

                String name = locationBean.getString("name");
                String address = locationBean.getString("address");
                String location = locationBean.getString("location");

                System.out.println(name);
                System.out.println(address);
                System.out.println(location);
                LocationBean bean = new LocationBean(name,calcLong(location),calcLat(location),address);
                System.out.println();

                FileUtil.writeToFile(name);
                FileUtil.writeToFile(address);
                FileUtil.writeToFile(location);
                FileUtil.writeToFile(bean.getGeoHash());
                FileUtil.writeToFile("\n");

                list.add(bean);

            }
        }

        return list;

    }


    private double calcLong(String location){
        String[] temp = location.split(",");
        return Double.valueOf(temp[1]);
    }

    private double calcLat(String location){
        String[] temp = location.split(",");
        return Double.valueOf(temp[0]);
    }

    public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public static void main(String[] args){
		//Test:
        new Crawler(121.459866,31.232913).generateLocationList();
    }
}
