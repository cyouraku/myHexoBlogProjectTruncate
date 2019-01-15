package sample.medyPass.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
//import org.apache.commons.httpclient.methods.GetMethod;

public class HttpClientUtil {


	/**
	 * create HttpPost by List<NameValuePair>
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	private static HttpPost performFormPost(String url, Map<String, String> params) throws IOException {
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList <NameValuePair>();
        Set<String> keySet = params.keySet();
        for(String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps, "UTF-8");
//        entity.setContentType("application/x-www-form-urlencoded");//post form data
        httpost.setEntity(entity);
        //Content type 'application/json;charset=UTF-8'
//        httpost.setHeader("Content-Type", ContentType.TEXT_HTML.toString());
        //Content type 'application/x-www-form-urlencoded'
        httpost.setHeader("Content-Type", ContentType.APPLICATION_FORM_URLENCODED.toString());
        return httpost;
    }

	/**
	 * create HttpPost by MultipartEntityBuilder
	 * @param url
	 * @param filePath
	 * @return
	 */
	private static HttpPost sendFormPostFrFile(String url, String filePath){
		HttpPost httpost = new HttpPost(url);


		return httpost;
	}

	public static HttpGet perforRequestGet(String url) throws IOException {
		 HttpGet httpget = new HttpGet(url);
		 return httpget;
	}

	//org.apache.commons.httpclient.methods.GetMethod HttpClient 3.1 API
//	public static String executeStringByGetMethod(String url){
//		GetMethod getMethod = new GetMethod(url);
//	}

	public static String executeStringByGet(String url, final Charset charset) {
        String result = "";
		HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        try {
        	result = client.execute(get, new ResponseHandler<String>() {
            @Override
            public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                HttpEntity entity = response.getEntity();
                if(entity != null) {
                    if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        return new String(EntityUtils.toByteArray(entity),  charset);
                    }
                }
                return "";
            }
        	});
        }catch(Exception e){
        	//
        }
        return result;
     }

	/**
	 * Post form data by List<NameValuePair>
	 * @param url
	 * @param param
	 * @param charset
	 * @throws IOException
	 */
	public static void executeStringByHttpPost(String url, Map<String, String> param, final Charset charset) throws IOException {
		HttpPost httpost = performFormPost(url,param);
		HttpClient client = HttpClients.createDefault();
		client.execute(httpost);
	}


	/**
	 * Post form data by MultipartEntityBuilder
	 * @param url
	 * @param filePath
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static void executeHttpPostFrFile(String url, String filePath) throws ClientProtocolException, IOException{
		HttpPost httpost = sendFormPostFrFile(url,filePath);
		HttpClient client = HttpClients.createDefault();
		client.execute(httpost);
	}

	public static Charset getCharsetOfUTF8(){
		return Charset.forName("UTF-8");
	}

}


