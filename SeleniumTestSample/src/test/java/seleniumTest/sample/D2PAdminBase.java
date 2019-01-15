package seleniumTest.sample;

import java.util.logging.Logger;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import seleniumTest.Util.SeleniumServerUtil;
import seleniumTest.constants.CommonConstants;

public class D2PAdminBase {

	private static Logger logger = Logger.getLogger(D2PAdminBase.class.getName());
	private long start = 0L;
	public WebDriver webDriver = null;
	public String driverPath = "C:\\opt\\chromedriver.exe";
	public String testUrl = "http://d2padmin-2.medy-id.jp/";
	private WebElement admin = null;
	private WebElement pw = null;
	private WebElement btn = null;

	/**
	 * データセットアップするメソッド
	 */
	public void createTabList(){
	}

  @BeforeClass
  public void beforeClass() {
	  if(this.webDriver == null){
		  this.webDriver = SeleniumServerUtil.getLocalWebDrive(CommonConstants.DRIVER_CHROME,this.driverPath,this.testUrl, new Dimension(800,600));
			//max size
//			this.webDriver.manage().window().maximize();
	  }
		//ユーザログイン
		try {
			getLogin();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
  }

  @AfterClass
  public void afterClass() {
	  if(this.webDriver != null){
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

	/**
	 * 管理サイトをログインする
	 * ブラウザサイズ：1920X1080
	 * @throws InterruptedException
	 */
	public void getLogin() throws InterruptedException {
		logger.info("Wait for 3 seconds to load login page.");
		Thread.sleep(3000);
		admin = SeleniumServerUtil
				.getWebElementByNm(this.webDriver, "login_id");
		admin.sendKeys("test1234");
		pw = SeleniumServerUtil.getWebElementByNm(this.webDriver, "password");
		pw.sendKeys("test1234");
		btn = SeleniumServerUtil.getWebElementByClassNm(this.webDriver,
				"GreenButton");
		btn.click();
		logger.info("wait 5 seconds.");
		Thread.sleep(5000);
	}

}
