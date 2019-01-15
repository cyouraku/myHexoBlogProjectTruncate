package com.whatismybrowser.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import seleniumTest.sample.ChromeUserAgentTest;

import com.whatismybrowser.crawler.UserAgentCrawler;
import com.whatismybrowser.crawler.UserAgentCrawlerFactory;

public class FetchChromeUserAgentInfo {

	private static Logger logger = Logger
			.getLogger(ChromeUserAgentTest.class.getName());
	private UserAgentCrawler uac = new UserAgentCrawlerFactory().getChromeUserAgentCrawler();
	private List<Integer> failedPageList = new ArrayList<Integer>();

	public int fetchUserAgentPageInfo(int firstPage, int lastPage) throws InterruptedException {
		logger.info("Wait for 3 seconds.");
		Thread.sleep(3000);
		int page = firstPage;
		while (page <= lastPage) {
			logger.info("Current Page = " + page);
			WebDriver webDriver = null;
			webDriver = uac.createDesiredWebDriverSilent(Integer.toString(page), webDriver);
			logger.info("Wait for 3 seconds.");
			Thread.sleep(3000);
			try{
				uac.printTableInfo(webDriver);
			}catch(NoSuchElementException e){
				logger.info(String.format("Error: %s \n Failed Page: %d \n",e.getMessage(),page));
				failedPageList.add(page);
			}
			page++;
			uac.quitWebDriver(webDriver);
			logger.info("Wait for 3 seconds.");
			Thread.sleep(3000);
		}
		int result = page -1;
		logger.info("Result page = " + result);
		for(Integer failed : failedPageList){
			logger.info("Failed page = " + failed);
		}
		return result;
	}

//	private int retry(int page) throws InterruptedException{
//		int result = 1;
//		logger.info("Current Page = " + page);
//		WebDriver webDriver = null;
//		webDriver = uac.createDesiredWebDriverSilent(Integer.toString(page), webDriver);
//		logger.info("Wait for 3 seconds.");
//		Thread.sleep(3000);
//		try{
//			uac.printTableInfo(webDriver);
//			result = 0;
//		}catch(NoSuchElementException e){
//			logger.info(String.format("Error: %s \n Failed Page: %d \n",e.getMessage(),page));
//		}
//		page++;
//		uac.quitWebDriver(webDriver);
//		logger.info("Wait for 3 seconds.");
//		Thread.sleep(3000);
//		return result;
//	}





}
