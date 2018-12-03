package com.whatismybrowser.service;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;

import seleniumTest.sample.ChromeUserAgentTest;

import com.whatismybrowser.crawler.UserAgentCrawler;
import com.whatismybrowser.crawler.UserAgentCrawlerFactory;

public class FetchSafariUserAgentInfo {

	private static Logger logger = Logger
			.getLogger(ChromeUserAgentTest.class.getName());
	private UserAgentCrawler uac = new UserAgentCrawlerFactory().getSafariUserAgentCrawler();

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
			uac.printTableInfo(webDriver);
			page++;
			uac.quitWebDriver(webDriver);
			logger.info("Wait for 3 seconds.");
			Thread.sleep(3000);
		}
		int result = page -1;
		logger.info("Result page = " + result);
		return result;
	}





}
