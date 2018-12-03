package seleniumTest.sample;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import seleniumTest.Util.SeleniumServerUtil;
import seleniumTest.constants.CommonConstants;

public class D2PUserBase {

	private static Logger logger = Logger
			.getLogger(D2PUserBase.class.getName());
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
//	public String testUrl = "http://192.168.158.161:7010/top/";
//	public String testUrl = "http://localhost:8081/";
	public String testUrl = "http://news2-2.medy-id.jp";
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
	 * <li class="menuBox menu active" onclick="clickHeaderMenu();">
	 * 「各種設定」メニューをクリックするメソッド
	 */
	public boolean clickSettingMenu(){
		if(checkElement(getElementByClassNm("menu"))){
			//「各種設定」メニューをクリックする。
			getElementByClassNm("menu").click();
			logger.info("「Class Name: menu」にて「各種設定」メニューをクリックしました。 \n");
		}else if(checkElement(getElementByXpath("//li[@onclick='clickHeaderMenu();']"))){
			//「各種設定」メニューをクリックする。
			getElementByXpath("//li[@onclick='clickHeaderMenu();']").click();
			logger.info("「Xpath: clickHeaderMenu()」にて「各種設定」メニューをクリックしました。 \n");
		}else if(checkElement(getElementByClassNm("burger"))){
			//「各種設定」メニューをクリックする。
			getElementByClassNm("burger").click();
			logger.info("「Class Name: burger」にて「各種設定」メニューをクリックしました。 \n");
		}else{
			logger.info("「各種設定」メニューを見つかりませんでした。 \n");
			return false;
		}
		return true;
	}

	/**
	 * エラーメッセージのボタンを閉じるメソッド
	 * <a href="javascript:void(0);" class="ButtonStyle BlueButton">閉じる</a>
	 * @param target
	 */
	public void closeErrorMsg(String target){
		WebElement closeBtn = getCloseBtnByLinkTxt("閉じる");
		if(closeBtn != null && checkElement(closeBtn)){
			closeBtn.click();
			logger.info(String.format("Detect error message alert for %s! \n", target));
		}else{
			logger.info("No error message alert detected! \n");
		}
		closeBtn = getCloseBtnByClassNm("BlueButton");
		if(closeBtn != null && checkElement(closeBtn)){
			closeBtn.click();
			logger.info(String.format("Detect error message alert for %s! \n", target));
		}else{
			logger.info("No error message alert detected! \n");
		}
	}

	/**
	 * リンクテキストにて閉じるボタンを取得する
	 * @param btnStr
	 * @return
	 */
	public WebElement getCloseBtnByLinkTxt(String btnStr){
		WebElement closeBtn = null;
		try{
			closeBtn = getElementByLinkText(btnStr);
		}catch(NoSuchElementException e){
			logger.info("no such element: Unable to locate element");
		}
		return closeBtn;
	}

	/**
	 * クラスネームにて閉じるボタンを取得する
	 * @param btnStr
	 * @return
	 */
	public WebElement getCloseBtnByClassNm(String btnStr){
		WebElement closeBtn = null;
		try{
			closeBtn = getElementByClassNm(btnStr);
		}catch(NoSuchElementException e){
			logger.info("no such element: Unable to locate element");
		}
		return closeBtn;
	}

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
	 * データセットアップするメソッド
	 */
	public void createTabList(){
		tabList.add("tab_100");
		tabList.add("tab_400");
		tabList.add("tab_200");
		tabList.add("tab_600");
		tabList.add("tab_300");
		tabList.add("tab_500");
		tabList.add("tab_700");
		msgMap.put("tab_700", "「ライフスタイル」メニュー");
		msgMap.put("tab_500", "「医学生ナビ」メニュー");
		msgMap.put("tab_300", "「特集・企画」メニュー");
		msgMap.put("tab_600", "「アンケート」メニュー");
		msgMap.put("tab_200", "「医療ニュース」メニュー");
		msgMap.put("tab_400", "「臨床Q&A」メニュ");
		msgMap.put("tab_100", "「TOP」メニュー");
		linkTextList.add("プロフィール設定");
		linkTextList.add("マイページ");
		linkTextList.add("所有MEDYポイント・MEDYチケットの確認");
		linkTextList.add("メルマガ配信設定");
		linkTextList.add("クリップリスト");
	}

	@BeforeClass
	public void beforeClass() {
		createTabList();
		createDesiredWebDriver(3);
	}

	@SuppressWarnings("deprecation")
	private void createDesiredWebDriver(int type) {
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
							this.testUrl);
				};
			case 2:
				//Maximize proxy web driver
				if (this.webDriver == null) {
					this.webDriver = SeleniumServerUtil. getLocalWebDriveProxy(
							CommonConstants.DRIVER_CHROME, this.driverPath,
							this.testUrl,"60.255.186.169:8888","");
				};
			case 3:
				//Android
				if(this.webDriver == null) {
					this.webDriver = SeleniumServerUtil.getLocalWebDriveSetUserAgentAndroid(
							CommonConstants.DRIVER_CHROME, this.driverPath,
							this.testUrl);
				};
			case 4:
				//iOS
				if(this.webDriver == null) {
					this.webDriver = SeleniumServerUtil.getLocalWebDriveSetUserAgentIOS(
							CommonConstants.DRIVER_CHROME, this.driverPath,
							this.testUrl);
				};
			case 5:
				//Android with cookie
				if(this.webDriver == null) {
					String ulsValue = "WXxbrFyqK2RBpYQ/ugfXlrPxjnJ2BwiWApH5ggxg6x09t0ak3ZwQ3d0UCJ110k2Qp14Qdp4AYKqTGTdEdErxC7/0Zd3SooHwj+KNp+dFYovot+Mu20055fZyZVry5XEyXbS/kO//5nFZtYe2MSjVhfsRVDvp/FlYurpNw/MXF769iFtKR11X8FR2jg/ErUKxnSJbpdmeLOTJszuJ6UeYzE6y6pxN8OxhVa+kNZzLNw7rCNfAlH5926Fj6CTiSwTlTW6DDL+/Z36O38p/5c9OI17qtv9NcthzvaqeXC+eCo7feEZwYj9wF7j4DRi2k17LafhQijkebNeN5atPjKtdc8FOKDJsY7XzJQAfmLo3Zg660e7bd5BHRLGadRKCXfXgbP7DnXpNY8rVuIso15Grl9t3L7mqqs8CzHHpo6snjlE=";
//					Cookie cookie = new Cookie("ULS",ulsValue);
					Cookie cookie = new Cookie.Builder("ULS",ulsValue)
				    .domain("news2-2.medy-id.jp")
				    .expiresOn(new Date(2020, 10, 28))
				    .isHttpOnly(true)
				    .isSecure(false)
				    .path("/")
				    .build();

					this.webDriver = SeleniumServerUtil.getLocalWebDriveSetCookiesForAndroid(
							CommonConstants.DRIVER_CHROME, this.driverPath,
							this.testUrl,cookie);
				};
		}
	}

	@AfterClass
	public void afterClass() {
		if (this.webDriver != null) {
			this.webDriver.quit();
		}
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
