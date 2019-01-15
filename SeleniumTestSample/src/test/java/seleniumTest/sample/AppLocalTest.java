package seleniumTest.sample;

import java.util.logging.Logger;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import seleniumTest.Util.SeleniumServerUtil;
import seleniumTest.constants.CommonConstants;

public class AppLocalTest {

	private static Logger logger = Logger.getLogger(AppLocalTest.class.getName());
	private WebDriver webDriver = null;
	private WebElement webElement = null;
	private String driverPath = "C:\\opt\\chromedriver.exe";
	private String testUrl = "http://localhost:8088/";
	private String elementId = "car";


  @Test
  public void f01() {
      logger.info(String.format("getText = %s \n",this.webElement.getText()));
  }
  @Test
  public void f02() {
      logger.info(String.format("getAttribute volvo = %s \n",this.webElement.getAttribute("volvo")));
  }
  @Test
  public void f03() {
      logger.info(String.format("getTagName()= %s \n",this.webElement.getTagName()));
  }
  @Test
  public void f04() {
      logger.info(String.format("getLocation point x= %d; y = %d \n",this.webElement.getLocation().x, this.webElement.getLocation().y));
  }
  @BeforeClass
  public void beforeClass() {
	  if(this.webDriver == null){
		  this.webDriver = SeleniumServerUtil.getLocalWebDrive(CommonConstants.DRIVER_CHROME,this.driverPath,this.testUrl, new Dimension(800,600));
		  if(this.webElement == null){
			  this.webElement = SeleniumServerUtil.getWebElementById(this.webDriver,this.elementId);
		  }
	  }
  }

  @AfterClass
  public void afterClass() {
	  if(this.webDriver != null){
		  this.webDriver.quit();
	  }
  }

}
