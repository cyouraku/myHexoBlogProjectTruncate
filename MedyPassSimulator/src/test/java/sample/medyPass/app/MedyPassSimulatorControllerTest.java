/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.medyPass.app;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

import org.apache.http.Header;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

import sample.medyPass.controller.MedyPassSimulatorController;
import sample.medyPass.entity.Attributes;
import sample.medyPass.entity.CodeAndState;
import sample.medyPass.entity.SubAttributes;
import sample.medyPass.entity.TokenResponseDto;
import sample.medyPass.entity.UserAttribute;
import sample.medyPass.util.MedyPassUtil;
import sample.medyPass.util.UserAttributeTransUtil;

/**
 * Basic integration tests for JSP application.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
@ComponentScan(basePackages = "sample.medyPass.controller, sample.medyPass.app")
public class MedyPassSimulatorControllerTest {

	private Logger logger = Logger.getLogger(MedyPassSimulatorControllerTest.class.getName());
	private static final String DEFAULT_TOKEN  = "cde9c85aaf9e848541ffe79560952edb";
	@Autowired
	private TestRestTemplate restTemplate;
	@Value("${application.hostMedy:localhost:8080}")
	private String hostMedy;
	@Value("${application.realm:http://192.168.56.1:8081}")
	private String realm;
	@Value("${application.returnTo:http://192.168.56.1:8081/api/1/medy?rl=http://192.168.56.1:8081/}")
	private String returnTo;
	@Value("${application.r:http://192.168.56.1:8081}")
	private String r;

	@Test
	public void testPrepareAuthenticate() throws Exception {
		String apiVersion = "1.0";
		String url = String.format("/_api/sharedRp/prepareAuthenticate?apiVersion=%s&realm=%s&returnTo=%s&r=%s", apiVersion, realm, returnTo, r);
		logger.info(String.format("url = %s \n", url ));
		ResponseEntity<String> entity = this.restTemplate.getForEntity(
				url, String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void testAuthenticate() throws Exception {
		String url = String.format("/_api/sharedRp/authenticate?token=%s",  DEFAULT_TOKEN);
		logger.info(String.format("url = %s \n", url ));
		ResponseEntity<String> entity = this.restTemplate.getForEntity(
				url, String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		String sBody = entity.getBody();
		logger.info(String.format("sBody = %s \n",sBody));
	}

	@Test
	public void testauthenticate_post(){
		String url = String.format("/_api/sharedRp/authenticatePost?token=%s",  DEFAULT_TOKEN);
		logger.info(String.format("url = %s \n", url ));
		this.restTemplate.getForEntity(url, String.class);
		logger.info(String.format("testauthenticate_post completed!"));
	}

	@Test
	public void testauthenticate_postTest() throws UnsupportedEncodingException, JSONException{
		String url = String.format("/_api/sharedRp/authenticatePostTest");
		logger.info(String.format("url = %s \n", url ));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map= UserAttributeTransUtil.transUserAttrToMultiValueMap(MedyPassUtil.getUserAttribute());
		map.add("email", "first.last@example.com");
		HttpEntity<MultiValueMap<String, String>> param = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> sBody = this.restTemplate.postForEntity(url, param, String.class);
		logger.info(String.format("testauthenticate_postTest completed! sBody = %s",sBody));
		assertThat(sBody.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void testCheckAlterData() throws Exception {
		MedyPassSimulatorController msc = new MedyPassSimulatorController();
		UserAttribute data = MedyPassUtil.getUserAttribute();
		String target = URLEncoder.encode(JSON.encode(data), "UTF-8");
		String md = "2324842a70a759c62c0cc48f8016e8badced8524139bf6252c97cbbd1c8e2232";
		String url = String.format("/_api/sharedRp/checkFalsification?apiVersion=1.0&realm=%s&target=%s&md=%s", realm, target, md);
		logger.info(String.format("url = %s \n", url ));
		ResponseEntity<String> entity = this.restTemplate.getForEntity(
				url, String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void testGetAttribute()  throws Exception {
		String contractId = "24f1ce0d-0bb1-46d7-a6b8-20fc56548c7e";
		String url = String.format("/_api/sharedRp/getAttributes?apiVersion=1.0&realm=%s&contractId=%s", realm, contractId);
		logger.info(String.format("url = %s \n", url ));
		ResponseEntity<String> entity = this.restTemplate.getForEntity(
				url, String.class);
		String sBody = entity.getBody();
		logger.info(String.format("sBody = %s \n",sBody));
		//Decode JSON with URLDecoder
//		UserAttribute jspon = JSON.decode(URLDecoder.decode(sBody,"UTF-8"), UserAttribute.class);
		//Decode JSON only
		UserAttribute jspon = JSON.decode(sBody, UserAttribute.class);


		Attributes attr = jspon.getResponseData().attributes;
		if(attr != null){
			logger.info(String.format("MD = %s \n", jspon.messageDigest));
			logger.info(String.format("OK:jspon.getResponseData().getAttributes() is not null! User Name= %s \n",attr.userName));
		}else{
			logger.info(String.format("NG:jspon.getResponseData().getAttributes() is null! \n"));
		}
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void testJsonDecode() {
		long start = System.currentTimeMillis();
		logger.info(String.format("testJsonDecode starts at %d \n", start));
		String input = JSON.encode(MedyPassUtil.getUserAttribute());
		logger.info(String.format("input = %s \n", input));
		UserAttribute jspon = JSON.decode(input, UserAttribute.class);
		Attributes attr = jspon.responseData.attributes;
		if(attr != null){
			logger.info(String.format("MD = %s \n", jspon.messageDigest));
			logger.info(String.format("OK:jspon.getResponseData().getAttributes() is not null! BirthDate = %s Status = %s \n",attr.birthDate,jspon.status));
		}else{
			logger.info(String.format("NG:jspon.getResponseData().getAttributes() is null! \n"));
		}
		long end = System.currentTimeMillis();
		logger.info(String.format("testJsonDecode completed at %d; time spent = %d \n", end, (end - start)));
	}

	/**
	 * scope = openid2, openid2_realm is not empty or null.
	 */
	@Test
	public void testGetMedPassAuthorize(){
		long start = System.currentTimeMillis();
		logger.info(String.format("testGetMedPassAuthorize starts at %d \n", start));
		//"/opid/authorize"
		String url = String
				.format("/opid/authorize?scope=%s&response_type=%s&client_id=%s&redirect_uri=%s&state=%s&nonce=%s&prompt=%s&max_age=%s&login_hint=%s&login_type=%s&openid2_realm=%s",
						"openid2", "response_type","client_id","redirect_uri","0","nonce","login","max_age","login_hint","login_type","openid2_realm");
		logger.info(String.format("url = %s \n", url ));
		ResponseEntity<String> entity = this.restTemplate.getForEntity(
				url, String.class);
		String sBody = entity.getBody();
		logger.info(String.format("sBody = %s \n",sBody));
		assertEquals(JSON.encode(MedyPassUtil.getCodeAndState("code4openid2","0")), sBody);
		//Decode JSON
		CodeAndState jspon = JSON.decode(sBody, CodeAndState.class);
		logger.info(String.format("code = %s \n",jspon.getCode()));
		assertEquals("code4openid2",jspon.getCode());
		long end = System.currentTimeMillis();
		logger.info(String.format("testGetMedPassAuthorize completed at %d; time spent = %d \n", end, (end - start)));
	}

	/**
	 * scope = openid Connect
	 * grant_type = refresh_token
	 */
	@Test
	public void testGetMedPassToken(){
		long start = System.currentTimeMillis();
		logger.info(String.format("testGetMedPassToken starts at %d \n", start));
		//"/opid/authorize"
		String url = String
				.format("/opid/authorize?scope=%s&response_type=%s&client_id=%s&redirect_uri=%s&state=%s&nonce=%s&prompt=%s&max_age=%s&login_hint=%s&login_type=%s&openid2_realm=%s",
						"openid Connect", "response_type","client_id","redirect_uri","0","nonce","prompt","max_age","login_hint","login_type","openid2_realm");
		logger.info(String.format("url = %s \n", url ));
		ResponseEntity<String> entity1 = this.restTemplate.getForEntity(
				url, String.class);
		String sBody1 = entity1.getBody();
		logger.info(String.format("sBody = %s \n",sBody1));
		//Decode JSON
		CodeAndState jspon1 = JSON.decode(sBody1, CodeAndState.class);
		logger.info(String.format("code = %s \n",jspon1.getCode()));
		//"/opid/token"
		url = String.format("/opid/token?grant_type=%s&code=%s&redirect_uri=%s&refresh_token=%s","refresh_token",jspon1.getCode(),"redirect_uri","refresh_token");
		logger.info(String.format("url = %s \n", url ));
		ResponseEntity<String> entity = this.restTemplate.getForEntity(
				url, String.class);
		String sBody = entity.getBody();
		logger.info(String.format("sBody = %s \n",sBody));
		//Decode JSON
		TokenResponseDto jspon = JSON.decode(sBody, TokenResponseDto.class);
		logger.info(String.format("id_token = %s \n",jspon.getId_token()));
		assertEquals("xxx.xxx.xxx",jspon.getId_token());
		logger.info(String.format("refresh_token = %s \n",jspon.getRefresh_token()));
		assertEquals("refresh222312sfhtffg",jspon.getRefresh_token());
		long end = System.currentTimeMillis();
		logger.info(String.format("testGetMedPassToken completed at %d; time spent = %d \n", end, (end - start)));
	}

	/**
	 * scope = openid2
	 * grant_type = authorization_code
	 */
	@Test
	public void testGetMedPassTokenClaimedId(){
		long start = System.currentTimeMillis();
		logger.info(String.format("testGetMedPassTokenClaimedId starts at %d \n", start));
		//"/opid/authorize"
		String url = String
				.format("/opid/authorize?scope=%s&response_type=%s&client_id=%s&redirect_uri=%s&state=%s&nonce=%s&prompt=%s&max_age=%s&login_hint=%s&login_type=%s&openid2_realm=%s",
						"openid2", "response_type","client_id","redirect_uri","0","nonce","prompt","max_age","login_hint","login_type","openid2_realm");
		logger.info(String.format("url = %s \n", url ));
		ResponseEntity<String> entity1 = this.restTemplate.getForEntity(
				url, String.class);
		String sBody1 = entity1.getBody();
		logger.info(String.format("sBody = %s \n",sBody1));
		//Decode JSON
		CodeAndState jspon1 = JSON.decode(sBody1, CodeAndState.class);
		logger.info(String.format("code = %s \n",jspon1.getCode()));
		//"/opid/token"
		url = String.format("/opid/token?grant_type=%s&code=%s&redirect_uri=%s&refresh_token=%s","authorization_code",jspon1.getCode(),"redirect_uri","refresh_token");
		logger.info(String.format("url = %s \n", url ));
		ResponseEntity<String> entity = this.restTemplate.getForEntity(
				url, String.class);
		String sBody = entity.getBody();
		logger.info(String.format("sBody = %s \n",sBody));
		//Decode JSON
		TokenResponseDto jspon = JSON.decode(sBody, TokenResponseDto.class);
		logger.info(String.format("id_token = %s \n",jspon.getId_token()));
		assertEquals("xxx.dao44cfcwpyl.xxx",jspon.getId_token());
		logger.info(String.format("claimedId = %s \n",jspon.getId_token().split("[.]")[1]));
		assertEquals("dao44cfcwpyl",jspon.getId_token().split("[.]")[1]);
		logger.info(String.format("access_token = %s \n",jspon.getAccess_token()));
		assertEquals("access2342asdfe",jspon.getAccess_token());
		long end = System.currentTimeMillis();
		logger.info(String.format("testGetMedPassTokenClaimedId completed at %d; time spent = %d \n", end, (end - start)));
	}


	/**
	 * SubAttributes属性を取得するテスト
	 */
	@Test
	public void testGetMedPassUserInfo(){
		long start = System.currentTimeMillis();
		logger.info(String.format("testGetMedPassUserInfo starts at %d \n", start));
		//"/opid/userInfo"
		String url = String.format("/opid/userInfo");
		logger.info(String.format("url = %s \n", url ));
		//set header info start
	    Header header = new BasicHeader("Authorization", "access2342asdfe");//Bearer ${access_token}
	    List<Header> headers = new ArrayList<Header>();
	    headers.add(header);
	    CloseableHttpClient httpClient = HttpClients.custom().setDefaultHeaders(headers).build();
	    this.restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
	    //set header info end
		ResponseEntity<String> entity = this.restTemplate.getForEntity(
				url, String.class);
		String sBody = entity.getBody();
		logger.info(String.format("sBody = %s \n",sBody));
		assertEquals(JSON.encode(MedyPassUtil.getSubAttributes()), sBody);
		//Decode JSON
		SubAttributes jspon = JSON.decode(sBody, SubAttributes.class);
		logger.info(String.format("sub = %s \n",jspon.sub));
		assertEquals("6578349645",jspon.sub);
		long end = System.currentTimeMillis();
		logger.info(String.format("testGetMedPassUserInfo completed at %d; time spent = %d \n", end, (end - start)));
	}

	/**
	 * scope = openid2
	 * grant_type = authorization_code
	 */
	@Test
	public void testOpenIDConnect(){
		long start = System.currentTimeMillis();
		logger.info(String.format("testOpenIDConnect starts at %d \n", start));
		//"/opid/authorize"
		String url = String
				.format("/opid/authorize?scope=%s&response_type=%s&client_id=%s&redirect_uri=%s&state=%s&nonce=%s&prompt=%s&max_age=%s&login_hint=%s&login_type=%s&openid2_realm=%s",
						"openid2", "response_type","client_id","redirect_uri","0","nonce","prompt","max_age","login_hint","login_type","openid2_realm");
		logger.info(String.format("url = %s \n", url ));
		ResponseEntity<String> entity1 = this.restTemplate.getForEntity(
				url, String.class);
		String sBody1 = entity1.getBody();
		logger.info(String.format("sBody = %s \n",sBody1));
		//Decode JSON
		CodeAndState jspon1 = JSON.decode(sBody1, CodeAndState.class);
		logger.info(String.format("code = %s \n",jspon1.getCode()));
		//"/opid/token"
		url = String.format("/opid/token?grant_type=%s&code=%s&redirect_uri=%s&refresh_token=%s","authorization_code",jspon1.getCode(),"redirect_uri","refresh_token");
		logger.info(String.format("url = %s \n", url ));
		ResponseEntity<String> entity = this.restTemplate.getForEntity(
				url, String.class);
		String sBody = entity.getBody();
		logger.info(String.format("sBody = %s \n",sBody));
		//Decode JSON
		TokenResponseDto jspon = JSON.decode(sBody, TokenResponseDto.class);
		logger.info(String.format("id_token = %s \n",jspon.getId_token()));
		assertEquals("xxx.dao44cfcwpyl.xxx",jspon.getId_token());
		logger.info(String.format("claimedId = %s \n",jspon.getId_token().split("[.]")[1]));
		assertEquals("dao44cfcwpyl",jspon.getId_token().split("[.]")[1]);
		logger.info(String.format("access_token = %s \n",jspon.getAccess_token()));
		assertEquals("access2342asdfe",jspon.getAccess_token());
		//"/opid/userInfo"
		url = String.format("/opid/userInfo");
		logger.info(String.format("url = %s \n", url ));
		String bearer_access_token = String.format("Bearer %s",jspon.getAccess_token());
		//set header info start
	    Header header = new BasicHeader("Authorization", bearer_access_token);
	    List<Header> headers = new ArrayList<Header>();
	    headers.add(header);
	    CloseableHttpClient httpClient = HttpClients.custom().setDefaultHeaders(headers).build();
	    this.restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
	    //set header info end
		entity = this.restTemplate.getForEntity(
				url, String.class);
		sBody = entity.getBody();
		logger.info(String.format("sBody = %s \n",sBody));
		assertEquals(JSON.encode(MedyPassUtil.getSubAttributes()), sBody);
		//Decode JSON
		SubAttributes jspon2 = JSON.decode(sBody, SubAttributes.class);
		logger.info(String.format("sub = %s \n",jspon2.sub));
		assertEquals("6578349645",jspon2.sub);
		long end = System.currentTimeMillis();
		logger.info(String.format("testOpenIDConnect completed at %d; time spent = %d \n", end, (end - start)));
	}

}
