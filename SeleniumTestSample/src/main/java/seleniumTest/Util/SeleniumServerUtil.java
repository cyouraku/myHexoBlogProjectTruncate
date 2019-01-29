package seleniumTest.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SeleniumServerUtil {

	 /**
	  * リモート「Chrome」WebDriverを取得する
	  * @param driverNm
	  * @param driverPath
	  * @param url
	  * @return
	 * @throws MalformedURLException
	  */
	  public static WebDriver getRemoteWebDriveChrome(String driverNm, String driverPath, String remoteUrl, String testUrl) throws MalformedURLException{
	      DesiredCapabilities dc = DesiredCapabilities.chrome();
	      WebDriver driver = new RemoteWebDriver(new URL(remoteUrl),dc);
		  driver.manage().window().setSize(new Dimension(800,600));
		  driver.get(testUrl);
		  return driver;
	  }

	  /**
	   * ローカルWebDriverを取得する
	   * @param driverNm
	   * @param driverPath
	   * @param url
	   * @return
	   */
	  public static WebDriver getLocalWebDrive(String driverNm, String driverPath, String testUrl, Dimension size){
		  System.setProperty(driverNm, driverPath);
		  WebDriver driver = new ChromeDriver();
		  driver.manage().window().setSize(size);
		  driver.get(testUrl);
		  return driver;
	  }

	  /**
	   * MaximizeローカルWebDriverを取得する
	   * @param driverNm
	   * @param driverPath
	   * @param testUrl
	   * @return
	   */
	  public static WebDriver getLocalWebDriveMaximize(String driverNm, String driverPath, String testUrl){
		  System.setProperty(driverNm, driverPath);
		  WebDriver driver = new ChromeDriver();
		  driver.manage().window().maximize();
		  driver.get(testUrl);
		  return driver;
	  }

	  /**
	   * AndoridのUserAgentを偽装してローカルWebDriverを取得する
	   * @param driverNm
	   * @param driverPath
	   * @param testUrl
	   * @return
	   */
	  public static WebDriver getLocalWebDriveSetUserAgentAndroid(String driverNm, String driverPath, String testUrl){
		  System.setProperty(driverNm, driverPath);
		  ChromeOptions options = new ChromeOptions();
		  //Androidの場合：
		  options.addArguments("--user-agent=Mozilla/5.0 (Linux; Android 6.0.1; SM-G532G Build/MMB29T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.83 Mobile Safari/537.36");
		  WebDriver driver = new ChromeDriver(options);
		  driver.manage().window().maximize();
		  driver.get(testUrl);
		  return driver;
	  }

	  /**
	   * AndoridのUserAgentを偽装してCookiesをセットしてローカルWebDriverを取得する
	   * @param driverNm
	   * @param driverPath
	   * @param testUrl
	   * @param cookie
	   * @return
	   */
	  public static WebDriver getLocalWebDriveSetCookiesForAndroid(String driverNm, String driverPath, String testUrl,Cookie cookie){
		  System.setProperty(driverNm, driverPath);
		  ChromeOptions options = new ChromeOptions();
		  //iOSの場合：
		  options.addArguments("--user-agent=Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_4 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) CriOS/35.0.1916.38 Mobile/10B350 Safari/8536.25");
		  WebDriver driver = new ChromeDriver(options);
		  driver.manage().window().maximize();
		  driver.manage().addCookie(cookie);
		  driver.get(testUrl);
		  return driver;
	  }

	  /**
	   * iOSのUserAgentを偽装してローカルWebDriverを取得する
	   * @param driverNm
	   * @param driverPath
	   * @param testUrl
	   * @return
	   */
	  public static WebDriver getLocalWebDriveSetUserAgentIOS(String driverNm, String driverPath, String testUrl){
		  System.setProperty(driverNm, driverPath);
		  ChromeOptions options = new ChromeOptions();
		  //iOSの場合：
		  options.addArguments("--user-agent=Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_4 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) CriOS/35.0.1916.38 Mobile/10B350 Safari/8536.25");
		  WebDriver driver = new ChromeDriver(options);
		  driver.manage().window().maximize();
		  driver.get(testUrl);
		  return driver;
	  }



	  /**
	   * iOSのUserAgentを偽装してCookiesをセットしてローカルWebDriverを取得する
	   * @param driverNm
	   * @param driverPath
	   * @param testUrl
	   * @param cookie
	   * @return
	   */
	  public static WebDriver getLocalWebDriveSetCookiesForiOS(String driverNm, String driverPath, String testUrl,Cookie cookie){
		  System.setProperty(driverNm, driverPath);
		  ChromeOptions options = new ChromeOptions();
		  //iOSの場合：
		  options.addArguments("--user-agent=Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_4 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) CriOS/35.0.1916.38 Mobile/10B350 Safari/8536.25");
		  WebDriver driver = new ChromeDriver(options);
		  driver.manage().window().maximize();
		  driver.manage().addCookie(cookie);
		  driver.get(testUrl);
		  return driver;
	  }


	  /**
	   * Start a silent ChromeDriver
	   * @param driverNm
	   * @param driverPath
	   * @param testUrl
	   * @return
	   */
	  public static WebDriver getLocalWebDriveSilent(String driverNm, String driverPath, String testUrl){
		System.setProperty(driverNm, driverPath);
		// 让浏览器后台运行
		ChromeOptions option = new ChromeOptions();
		option.addArguments("headless");
		// 取消"Chrome正在受到自动软件的控制"提示
		option.addArguments("disable-infobars");
		WebDriver driver = new ChromeDriver(option);
		driver.get(testUrl);
		return driver;
	  }


	  @SuppressWarnings("deprecation")
	  public static WebDriver getLocalWebDriveProxy(String driverNm, String driverPath, String testUrl, String httpProxy, String httpsProxy){
		  System.setProperty(driverNm, driverPath);
		  Proxy proxy = new Proxy();
		  proxy.setHttpProxy(httpProxy);//yoururl:portno
		  proxy.setSslProxy(httpsProxy);//yoururl:portno
		  DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		  capabilities.setCapability("proxy", proxy);
		  ChromeOptions options = new ChromeOptions();
		  options.addArguments("start-maximized");
		  capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		  WebDriver driver = new ChromeDriver(capabilities);
		  driver.manage().window().maximize();
		  driver.get(testUrl);
		  return driver;
	  }

	  /**
	   * Element IDにてWebEementを取得する
	   * @param driver
	   * @param elementId
	   * @return
	   */
	  public static WebElement getWebElementById(WebDriver driver, String elementId){
		  WebElement webElement = driver.findElement(By.id(elementId));
		  return webElement;
	  }

	  /**
	   * Element NameにてWebEementを取得する
	   * @param driver
	   * @param elementNm
	   * @return
	   */
	  public static WebElement getWebElementByNm(WebDriver driver, String elementNm){
		  WebElement webElement = driver.findElement(By.name(elementNm));
		  return webElement;
	  }

	  /**
	   * Class NameにてWebEementを取得する
	   * @param driver
	   * @param classNm
	   * @return
	   */
	  public static WebElement getWebElementByClassNm(WebDriver driver, String classNm){
		  WebElement webElement = driver.findElement(By.className(classNm));
		  return webElement;
	  }

	  /**
	   * XpathにてWebEementを取得する
	   * @param driver
	   * @param xpath
	   * @return
	   */
	  public static WebElement getWebElementByXpath(WebDriver driver, String xpath){
		  WebElement webElement = driver.findElement(By.xpath(xpath));
		  return webElement;
	  }

	  /**
	   * PartialLinkTextにてWebEementを取得する
	   * @param driver
	   * @param partialLinkText
	   * @return
	   */
	  public static WebElement getWebElementByPartialLinkText(WebDriver driver, String partialLinkText){
		  WebElement webElement = driver.findElement(By.partialLinkText(partialLinkText));
		  return webElement;
	  }

	  /**
	   * LinkTextにてWebEementを取得する
	   * @param driver
	   * @param linkText
	   * @return
	   */
	  public static WebElement getWebElementByLinkText(WebDriver driver, String linkText){
		  WebElement webElement = driver.findElement(By.linkText(linkText));
		  return webElement;
	  }

	/**
	 * ローカルファイルを読み込み
	 *
	 * @param filepath
	 * @return
	 */
	public static String readFile(String filepath) {
		File file02 = new File(filepath);
		FileInputStream is = null;
		StringBuilder stringBuilder = null;
		try {
			if (file02.length() != 0) {
				is = new FileInputStream(file02);
				InputStreamReader streamReader = new InputStreamReader(is);
				BufferedReader reader = new BufferedReader(streamReader);
				String line;
				stringBuilder = new StringBuilder();
				while ((line = reader.readLine()) != null) {
					stringBuilder.append(line);
				}
				reader.close();
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(stringBuilder);
	}

	/**
	 * Read file to List<String>
	 * @param filepath
	 * @return
	 */
	public static List<String> readFileToListStr(String filepath) {
		File file02 = new File(filepath);
		FileInputStream is = null;
		List<String> strList = new ArrayList<String>();
		try {
			if (file02.length() != 0) {
				is = new FileInputStream(file02);
				InputStreamReader streamReader = new InputStreamReader(is);
				BufferedReader reader = new BufferedReader(streamReader);
				String line;
				while ((line = reader.readLine()) != null) {
					strList.add(line);
				}
				reader.close();
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strList;
	}

	/**
	 * Write a big list of Strings to a file - Use a BufferedWriter
	 */
	public static void writerFile(List<String> content, String filePath) {
		File file = new File(filePath);
		Writer fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			//set false to disable append mode
			fileWriter = new FileWriter(file,false);
			bufferedWriter = new BufferedWriter(fileWriter);
			// Write the lines one by one
			for (String line : content) {
//				line += System.getProperty("line.separator");
				line += "\n";
				bufferedWriter.write(line);
				// alternatively add bufferedWriter.newLine() to change line
			}
		} catch (IOException e) {
			System.err.println("Error writing the file : ");
			e.printStackTrace();
		} finally {
			if (bufferedWriter != null && fileWriter != null) {
				try {
					bufferedWriter.close();
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


}
