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

import static org.assertj.core.api.Assertions.*;
import net.arnx.jsonic.JSON;

import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import sample.searchArticle.entity.SearchArticleResultDto;

/**
 * Basic integration tests for JSP application.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
@ComponentScan(basePackages = "sample.searchArticle.controller, sample.searchArticle.app")
public class SearchArticleControllerTest {

	private Logger logger = Logger.getLogger(SearchArticleControllerTest.class.getName());
	@Autowired
	private TestRestTemplate restTemplate;
	private String lastArticleId = "";

	@Test
	public void testSearchKeywords() throws Exception {
		String keywords = "喫煙,高齢者";
		String url = String.format("/SearchForKeywords?keywords=%s", keywords);
		logger.info(String.format("url = %s \n", url ));
		ResponseEntity<String> entity = this.restTemplate.getForEntity(
				url, String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		String sBody = entity.getBody();
		logger.info(String.format("response body = %s \n", sBody));
		assertThat(entity.getBody()).contains("114548");
		SearchArticleResultDto resp = JSON.decode(sBody, SearchArticleResultDto.class);
		assertThat(resp.response.numFound.equals("436"));
		assertThat(resp.response.docs.get(0).articleId.equals("114548"));
	}

	@Test
	public void testSearchAll() throws Exception {
		String keywords = "喫煙,高齢者";
		String url = String.format("/SearchForAll?keywords=%s", keywords);
		logger.info(String.format("url = %s \n", url ));
		ResponseEntity<String> entity = this.restTemplate.getForEntity(
				url, String.class);
		String sBody = entity.getBody();
		logger.info(String.format("response body = %s \n", sBody));
		SearchArticleResultDto resp = JSON.decode(sBody, SearchArticleResultDto.class);
		int max = Integer.parseInt(resp.response.numFound);
		this.lastArticleId = resp.response.docs.get( max -1 ).articleId;
		logger.info(String.format("lastArticleId = %s \n", this.lastArticleId));
		assertThat(resp.response.docs.get(max -1).articleId.equals(this.lastArticleId));
	}

	@Test
	public void testSearchArticleDetail() throws Exception {
		String articleId = "114548";
		String url = String.format("/SearchForArticles?articleId=%s", articleId);
		logger.info(String.format("url = %s \n", url ));
		ResponseEntity<String> entity = this.restTemplate.getForEntity(
				url, String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		String sBody = entity.getBody();
		logger.info(String.format("response body = %s \n", sBody));
		assertThat(entity.getBody()).contains("114548");
		SearchArticleResultDto resp = JSON.decode(sBody, SearchArticleResultDto.class);
		assertThat(resp.response.numFound.equals("1"));
		assertThat(resp.response.docs.get(0).articleId.equals("114548"));
	}


}
