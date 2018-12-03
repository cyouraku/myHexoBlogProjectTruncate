package com.whatismybrowser.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import seleniumTest.Util.SeleniumServerUtil;
import seleniumTest.constants.CommonConstants;

import com.whatismybrowser.entity.UserAgentDto;
import com.whatismybrowser.util.CrawlerCommonUtil;

public class UserAgentCrawlerImpl implements UserAgentCrawler {

	private static Logger logger = Logger
			.getLogger(UserAgentCrawlerImpl.class.getName());

	/**
	 * UserAgentDtoリスト
	 */
	private List<UserAgentDto> entityList = new ArrayList<UserAgentDto>();
	/**
	 * ブラウザドライバのパス
	 */
	private String driverPath = "";
	/**
	 * D2PUserWebサイトURL
	 */
	private String testUrl = "";
	/**
	 * ファイル格納パス
	 */
	private String filePath = "";

	public UserAgentCrawlerImpl(){}

	public UserAgentCrawlerImpl(String driverPath, String testUrl, String filePath){
		this.driverPath = driverPath;
		this.testUrl = testUrl;
		this.filePath = filePath;
	}

	/**
	 * 画面要素を取得できるか否かを判定する。
	 * @param element
	 * @return
	 */
	public boolean checkElement(WebElement element){
		if(element != null){
			try{
				if(element.isDisplayed()){
					return true;
				}else{
					logger.info(String.format("Error: Element not detected! TagName = %s \n", element.getTagName()));
				}
			}catch(StaleElementReferenceException e){
				logger.info("Error: elementis not attached to the page document");
			}
		}
		return false;
	}

	/**
	 * Create silent web driver
	 * @param type
	 * @param page
	 * @param webDriver
	 * @return
	 */
	public void createDesiredWebDriver(WebDriver webDriver, int type) {
		switch (type) {
			case 0:
				//800X600 local web driver
				if (webDriver == null) {
					webDriver = SeleniumServerUtil.getLocalWebDrive(
							CommonConstants.DRIVER_CHROME, this.driverPath,
							this.testUrl,new Dimension(800,600));
				};
			case 1:
				//Maximize local web driver
				if (webDriver == null) {
					webDriver = SeleniumServerUtil.getLocalWebDriveMaximize(
							CommonConstants.DRIVER_CHROME, this.driverPath,
							this.testUrl);
				};
			case 2:
				//Maximize proxy web driver
				if (webDriver == null) {
					webDriver = SeleniumServerUtil. getLocalWebDriveProxy(
							CommonConstants.DRIVER_CHROME, this.driverPath,
							this.testUrl,"60.255.186.169:8888","");
				};
		}
	}

	/**
	 * Create new WebDriver
	 * @param type
	 * @param page
	 * @param webDriver
	 * @return
	 */
	public WebDriver createDesiredWebDriverSilent(String page, WebDriver webDriver) {
		// Maximize local web driver
		if (webDriver == null) {
			webDriver = SeleniumServerUtil.getLocalWebDriveSilent(
					CommonConstants.DRIVER_CHROME, this.driverPath,
					CrawlerCommonUtil.createTestUrl(this.testUrl, page));

		};
		return webDriver;
	}

	/**
	 * quit web driver
	 * @param webDriver
	 */
	public void quitWebDriver(WebDriver webDriver){
		if (webDriver != null) {
			webDriver.quit();
		}
	}

	/**
	 * Print table info method
	 */
	public void printTableInfo(WebDriver webDriver){
			WebElement tableElement= SeleniumServerUtil.getWebElementByClassNm(webDriver, "table");
			List<WebElement> rows=tableElement.findElements(By.tagName("tr"));
			logger.info("rows.size = " + rows.size());
			for (int i = 0; i < rows.size(); i++) {
				List<WebElement> cols=rows.get(i).findElements(By.tagName("td"));
				UserAgentDto cd = new UserAgentDto();
				for (int j = 0; j < cols.size(); j++) {
					String contents = cols.get(j).getText();
					switch(j){
						case 0:
							//User agent,j=0
							if(CrawlerCommonUtil.checkEmpty(contents)){
								cd.setUserAgent(contents);
//								logger.info(String.format("User agent = %s.",contents));
							}
							break;
						case 1:
							//Version,j=1
							if(CrawlerCommonUtil.checkEmpty(contents)){
								cd.setVersion(contents);
//								logger.info(String.format("Version = %s.",contents));
							}
							break;
						case 2:
							//OS,j=2
							if(CrawlerCommonUtil.checkEmpty(contents)){
								cd.setOs(contents);
//								logger.info(String.format("OS = %s.",contents));
							}
							break;
						case 3:
							//Hardware Type,j=3
							if(CrawlerCommonUtil.checkEmpty(contents)){
								cd.setHardWareType(contents);
//								logger.info(String.format("Hardware Type = %s.",contents));
							}
							break;
						case 4:
							//Popularity,j=4
							if(CrawlerCommonUtil.checkEmpty(contents)){
								cd.setPopularity(contents);
//								logger.info(String.format("Popularity = %s.",contents));
							}
							break;
					}
				}

				if(!"".equals(cd.getUserAgent())){
					entityList.add(cd);
					logger.info(String.format("user agent %d added.",i));
					//Save to CSV file
					CrawlerCommonUtil.saveToTsvFile(entityList,filePath);
					//reset list
					entityList.clear();
				}

			}
	}


}
