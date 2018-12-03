package org.geoHash.app;

import org.geoHash.util.Crawler;

/**
 * 爬虫APP
 * @author HD800241
 *
 */
public class CrawlerApp {

    public static void main(String[] args) {
        System.out.println("This is crawler APP!");
        double lat = 121.439266;
        double longi = 31.227188;

        for (int i=0;i<100;i++){
            for (int j=0;j<100;j++){
                new Crawler(lat,longi).generateLocationList();
                longi -= 0.001;
            }
            lat += 0.001;

        }



    }
}
