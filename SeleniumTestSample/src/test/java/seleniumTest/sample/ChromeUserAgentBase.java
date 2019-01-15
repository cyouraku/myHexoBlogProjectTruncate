package seleniumTest.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import seleniumTest.Util.SeleniumServerUtil;
import seleniumTest.constants.CommonConstants;

public class ChromeUserAgentBase {

	private static Logger logger = Logger
			.getLogger(ChromeUserAgentBase.class.getName());
	private long start = 0L;
	/**
	 * Webドライバ
	 */
	public WebDriver webDriver = null;
	/**
	 * ブラウザドライバのパス
	 */
	public String driverPath = "C:\\opt\\chromedriver.exe";
	/**
	 * D2PUserWebサイトURL
	 */
//	public String testUrl = "https://developers.whatismybrowser.com/useragents/explore/software_name/chrome/1";
	public String testUrl = "https://developers.whatismybrowser.com/useragents/explore/software_name/chrome/";
	/**
	 * グローバルメニューIDリスト
	 */
	public List<String> tabList = new ArrayList<String>();
	/**
	 * 「グローバルメニューID：グローバルメニュー名」を格納するマップ
	 */
	public Map<String, String> msgMap = new HashMap<String,String>();
	/**
	 * 各種設定リンクテキストリスト
	 */
	public List<String> linkTextList = new ArrayList<String>();

	/**
	 * 「LinkText」にて画面要素を取得する。
	 * @param partialLinkText
	 * @return
	 */
	public WebElement getElementByLinkText(String linkText){
		return SeleniumServerUtil.getWebElementByLinkText(
				this.webDriver, linkText);
	}

	/**
	 * getElementByPartialLinkText("プロフィール設定").click();//OK:tested
	 * 「PartialLinkText」にて画面要素を取得する。
	 * @param partialLinkText
	 * @return
	 */
	public WebElement getElementByPartialLinkText(String partialLinkText){
		return SeleniumServerUtil.getWebElementByPartialLinkText(
				this.webDriver, partialLinkText);
	}

	/**
	 * getElementByXpath("//a[@onclick='goPage('profileUpdate');']").click();//Error:invalid xpath expression
	 * 「Xpath」にて画面要素を取得する。
	 * @param id
	 * @return
	 */
	public WebElement getElementByXpath(String xpath){
		return SeleniumServerUtil.getWebElementByXpath(
				this.webDriver, xpath);
	}

	/**
	 * 「Element ID」にて画面要素を取得する。
	 * @param id
	 * @return
	 */
	public WebElement getElementById(String id){
		return SeleniumServerUtil.getWebElementById(
				this.webDriver, id);
	}

	/**
	 * 「Element ClassName」にて画面要素を取得する。
	 * @param id
	 * @return
	 */
	public WebElement getElementByClassNm(String classNm){
		return SeleniumServerUtil.getWebElementByClassNm(
				this.webDriver, classNm);
	}

	/**
	 * 「Element ClassName」にて画面要素を取得する。
	 * @param id
	 * @return
	 */
	public WebElement getElementByClassNm(String classNm,WebDriver webDriver){
		return SeleniumServerUtil.getWebElementByClassNm(
				webDriver, classNm);
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


	@BeforeClass
	public void beforeClass() {
//		createDesiredWebDriver(1,"1");
	}

	public String createTestUrl(String page){
		return String.format("%s%s",this.testUrl,page);
	}

	public void createDesiredWebDriver(int type,String page) {
		switch (type) {
			case 0:
				//800X600 local web driver
				if (this.webDriver == null) {
					this.webDriver = SeleniumServerUtil.getLocalWebDrive(
							CommonConstants.DRIVER_CHROME, this.driverPath,
							this.testUrl,new Dimension(800,600));
				};
			case 1:
				//Maximize local web driver
				if (this.webDriver == null) {
					this.webDriver = SeleniumServerUtil.getLocalWebDriveMaximize(
							CommonConstants.DRIVER_CHROME, this.driverPath,
							createTestUrl(page));
				};
			case 2:
				//Maximize proxy web driver
				if (this.webDriver == null) {
					this.webDriver = SeleniumServerUtil. getLocalWebDriveProxy(
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
					createTestUrl(page));

		};
		return webDriver;
	}

	public void quitWebDriver(WebDriver webDriver){
		if (webDriver != null) {
			webDriver.quit();
		}
	}

	@AfterClass
	public void afterClass() {
		quitWebDriver(this.webDriver);
	}

	@BeforeSuite
	public void beforeSuite() {
		this.start = System.currentTimeMillis();
		logger.info(String.format("This is beforeSuite() method! start = %d ",start));
	}

	@AfterSuite
	public void afterSuite() {
		long end = System.currentTimeMillis();
		logger.info(String.format("This is afterSuite() method! time spent = %d seconds.",(end - start)/1000));
	}

}
