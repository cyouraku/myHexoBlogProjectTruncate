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

package sample.searchArticle.app;

import java.util.HashMap;
import java.util.Map;

import net.arnx.jsonic.JSON;

import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Basic integration tests for JSP application.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
@ComponentScan(basePackages = "sample.searchArticle.controller, sample.searchArticle.app")
public class WelcomeControllerTest {

	private Logger logger = Logger.getLogger(WelcomeControllerTest.class.getName());
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testGetChannelMap() throws Exception {
		logger.info(String.format("%s start \n","testGetChannelMap()"));
		String url = String.format("/api/1/user/channel/get/channelMap");
		logger.info(String.format("url = %s \n", url ));
		Map<String, String> params = new HashMap<String, String>();
		params.put("appVersion", "null");
		params.put("bgflg", "1");
		ResponseEntity<String> entity = this.restTemplate.postForEntity(url, params, String.class);
//		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		String sBody = entity.getBody();
		logger.info(String.format("response body = %s \n", sBody));
//		ChannelListResponseDTO resp = JSON.decode(sBody, ChannelListResponseDTO.class);
//		logger.info(String.format("response result = %s \n", resp.getResult()));
		logger.info(String.format("%s end \n","testGetChannelMap()"));
	}

	@Test
	public void testGetChannelList() throws Exception {
		logger.info(String.format("%s start \n","testGetChannelList()"));
		String url = String.format("/api/1/user/channel/get/channellist");
		logger.info(String.format("url = %s \n", url ));
		Map<String, String> params = new HashMap<String, String>();
		params.put("appVersion", "null");
		params.put("bgflg", "1");
		ResponseEntity<String> entity = this.restTemplate.postForEntity(url, JSON.encode(params), String.class);
//		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		String sBody = entity.getBody();
		logger.info(String.format("response body = %s \n", sBody));
//		ChannelListResponseDTO resp = JSON.decode(sBody, ChannelListResponseDTO.class);
//		logger.info(String.format("response result = %s \n", resp.getResult()));
		logger.info(String.format("%s end \n","testGetChannelList()"));
	}


}
