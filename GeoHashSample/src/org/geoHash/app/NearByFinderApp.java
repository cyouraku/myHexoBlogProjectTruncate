package org.geoHash.app;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.geoHash.util.Bean;
import org.geoHash.util.GeoHash;

/**
*
* @author HD800241
*
*/
public class NearByFinderApp {

    private String geoHash = "";
    private List<Bean> dataBase;
    private double latitude = 0;
    private double longitude = 0;

    public NearByFinderApp(double latitude, double longitude) {
        geoHash = new GeoHash(longitude,latitude,20).encodeHash();
        dataBase = new ArrayList<Bean>();
        this.dataBase =readDataBase();
        this.latitude = latitude;
        this.longitude =longitude;
        System.out.println("DataBase read complete! " + dataBase.size() + "item added\n");
    }

    public String locationToString(){
        return "Your Geo Location:" +  latitude +", " +longitude + "Your Geo Hash: "+geoHash + "\n";
    }

    private List<Bean> readDataBase(){
        List<Bean> list = new ArrayList<Bean>();
        String filePath ="C:\\Users\\HD800241\\Documents\\Trunk\\LS09_Works\\GeoHashSample\\data\\DB.txt";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8")); // 指定读取文件的编码格式，要和写入的格式一致，以免出现中文乱码,

           for (int i=0;i<1988;i++){
               String name = reader.readLine();
               String address = reader.readLine();
               String location = reader.readLine();
               String hash = reader.readLine();
               Bean bean = new Bean(name,address,location,hash);
               list.add(bean);
               reader.readLine();
               reader.readLine();
           }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    private boolean isNearBy(String hash){
        String sub = geoHash.substring(0,6);
       // System.out.println("Sub:" + hash.contains(sub));
        return hash.contains(sub);
    }

    public List<Bean> getNearByList(){
        List<Bean> nearByList = new ArrayList<Bean>();
        for (int i=0;i<dataBase.size();i++){
            Bean bean = dataBase.get(i);
            if (isNearBy(bean.hash)){
                nearByList.add(bean);
            }
        }
        return nearByList;
    }

    public static void main(String[] args){
        NearByFinderApp nearByFinder = new NearByFinderApp(121.455649,31.217747);
        List<Bean> list = nearByFinder.getNearByList();
        System.out.println(nearByFinder.locationToString());
        System.out.println("周围有:"+list.size()+" 家餐馆:\n");
        for (int i=0;i<list.size();i++){
            System.out.println(list.get(i).toString()+"\n");
        }
    }

}
