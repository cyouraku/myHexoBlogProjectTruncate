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

package sample.searchArticle.controller;

import java.io.IOException;

import net.arnx.jsonic.JSON;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sample.searchArticle.entity.SearchArticleResultDto;
import sample.searchArticle.util.HttpClientUtil;

@Controller
public class SearchArticleController {

	private Logger logger = Logger.getLogger(SearchArticleController.class.getName());

	@Value("${application.solrHost:http://localhost:8983/solr/sample_core_001}")
	private String solrHost;

	private static final int DEFAULT_MAX_ROW = 30;

	/**
	 * Search keywords for max 30 rows.
	 * Return articleId list : SearchKeywordResultDto.class
	 * @param keywords
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/SearchForKeywords")
	public @ResponseBody String searchKeywords(@RequestParam("keywords") String keywords) throws IOException {
		logger.info(String.format("request param = %s \n", keywords));
		String url = "";
		//http://localhost:8983/solr/sample_core_001/select?df=keyword&fl=articleId,title,summary,keyword,url&fq=title:喫煙~0.8&q=喫煙,高齢者&start=0&rows=10
//		url = String.format("%s/select?df=keyword&fl=articleId,title,summary,keyword,url&fq=title:%s~0.8&q=%s", solrHost, this.getFirstKeyword(keywords),keywords);
		url = String.format("%s/select?df=keyword&fl=articleId&fq=title:%s~0.8&q=%s&start=0&rows=%d", solrHost, this.getFirstKeyword(keywords), keywords, DEFAULT_MAX_ROW );
		logger.info(String.format("url = %s \n", url));
		return HttpClientUtil.executeStringByGet(url,HttpClientUtil.getCharsetOfUTF8());
	}


	/**
	 * Search keywords for all result rows.
	 * Return articleId list : SearchKeywordResultDto.class
	 * @param keywords
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/SearchForAll")
	public @ResponseBody String searchKeywordsAndReturnAll(@RequestParam("keywords") String keywords) throws IOException {
		logger.info(String.format("request param = %s \n", keywords));
		String url = "";
		String sBody = "";
		url = String.format("%s/select?df=keyword&fl=articleId&fq=title:%s~0.8&q=%s", solrHost, this.getFirstKeyword(keywords), keywords);
		logger.info(String.format("url = %s \n", url));
		sBody = HttpClientUtil.executeStringByGet(url,HttpClientUtil.getCharsetOfUTF8());
		SearchArticleResultDto resp = JSON.decode(sBody, SearchArticleResultDto.class);
		int maxRows = Integer.parseInt(resp.response.numFound);
		if(maxRows > DEFAULT_MAX_ROW ){
			url = String.format("%s/select?df=keyword&fl=articleId&fq=title:%s~0.8&q=%s&start=0&rows=%d", solrHost, this.getFirstKeyword(keywords), keywords, maxRows);
			logger.info(String.format("url = %s \n", url));
			sBody = HttpClientUtil.executeStringByGet(url,HttpClientUtil.getCharsetOfUTF8());
		}
		return sBody;
	}

	/**
	 * Return article details : SearchArticleResultDto.class
	 * @param articldId
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/SearchForArticles")
	public @ResponseBody String searchArticles(@RequestParam("articleId") String articldId) throws IOException {
		logger.info(String.format("request param = %s \n", articldId));
		String url = "";
		url = String.format("%s/select?df=articleId&fl=articleId,title,keyword,summary,url&q=%s", solrHost, articldId);
		logger.info(String.format("url = %s \n", url));
		return HttpClientUtil.executeStringByGet(url,HttpClientUtil.getCharsetOfUTF8());
	}



	/**
	 * Get first keyword
	 * @param keywords
	 * @return
	 */
	private String getFirstKeyword(String keywords){
		String[] keys = keywords.split(",");
		if(keys.length > 0){
			return keys[0];
		}
		return keywords;
	}



}
