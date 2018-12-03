package seleniumTest.proxy;

import java.io.IOException;
import java.util.logging.Logger;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ProxyDemo {

	private static Logger logger = Logger.getLogger(ProxyDemo.class.getName());

	public static void main(String[] args) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault(); // 创建httpClient实例
        HttpGet httpGet=new HttpGet("http://www.baidu.com/"); // 创建httpget实例
        HttpHost proxy=new HttpHost("proxy0-ca825c79461434f1af7e58c339ec2b2b.menlosecurity.com", 3129);
        logger.info(String.format("Test proxy %s \n", proxy.getHostName()));
        //Get header info
        getHeadInfo(httpGet,proxy,httpClient);
        //Get Entity info
//        getEntityInfo(httpGet,proxy,httpClient);

        proxy=new HttpHost("127.0.0.1", 9110);
        logger.info(String.format("Test proxy %s \n", proxy.getHostName()));
        //Get header info
        getHeadInfo(httpGet,proxy,httpClient);
        //Get Entity info
//        getEntityInfo(httpGet,proxy,httpClient);

        httpClient.close(); // httpClient关闭
	}

	private static void getHeadInfo(HttpGet httpGet, HttpHost proxy, CloseableHttpClient httpClient) throws ClientProtocolException, IOException{
        RequestConfig requestConfig=RequestConfig.custom().setProxy(proxy).build();
        httpGet.setConfig(requestConfig);
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
        CloseableHttpResponse response = httpClient.execute(httpGet); // 执行http get请求
        Header[] head = response.getAllHeaders();
        if(head.length>0){
            for(int i=0;i<head.length;i++){
           	 System.out.println(head[i].getValue());
           }
        }
	}

	private static void getEntityInfo(HttpGet httpGet, HttpHost proxy, CloseableHttpClient httpClient) throws ClientProtocolException, IOException{
        RequestConfig requestConfig=RequestConfig.custom().setProxy(proxy).build();
        httpGet.setConfig(requestConfig);
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
        CloseableHttpResponse response = httpClient.execute(httpGet); // 执行http get请求
        HttpEntity entity = response.getEntity(); // 获取返回实体
        System.out.println("网页内容："+ EntityUtils.toString(entity, "utf-8")); // 获取网页内容
        response.close(); // response关闭
	}

}
