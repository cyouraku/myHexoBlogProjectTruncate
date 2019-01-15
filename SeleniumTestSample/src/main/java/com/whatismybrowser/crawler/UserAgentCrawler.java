package com.whatismybrowser.crawler;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface UserAgentCrawler {

	/**
	 * 画面要素を取得できるか否かを判定する。
	 * @param element
	 * @return
	 */
	boolean checkElement(WebElement element);

	/**
	 * create default web driver
	 * @param type
	 * @param page
	 */
	void createDesiredWebDriver(WebDriver webDriver, int type);

	/**
	 * Create silent web driver
	 * @param type
	 * @param page
	 * @param webDriver
	 * @return
	 */
	WebDriver createDesiredWebDriverSilent(String page, WebDriver webDriver);

	/**
	 *  quit web driver
	 * @param webDriver
	 */
	void quitWebDriver(WebDriver webDriver);

	/**
	 * Print table info method
	 */
	void printTableInfo(WebDriver webDriver);


}
