package seleniumTest.sample;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


/**
 * Hello world!
 *
 */
public class AppRemote
{
	/**
	 * 2.1.2 远程模式--》：
	 *0) 下载seleniumserver.jar，下载地址：https://www.seleniumhq.org/download/
	 *1) 启动hub，默认端口4444
	 * java -jar E:\apps\selenium\seleniumserver\selenium-server-standalone-3.x.x.jar-role hub
	 *2)  启动node，默认端口 5555
	 * java -jar E:\apps\selenium\seleniumserver\selenium-server-standalone-3.x.x.jar-role node
	 * 2-1) If 2) failed then use below instead:
	 * java -Dwebdriver.chrome.driver=C:\\opt\\selenium-chrome-driver\\chromedriver  -jar C:\\opt\selenium-server-standalone-3.x.x.jar -role node
	 *
	 * #Start all browser
	 * Java -Dwebdriver.ie.driver=IEDriverServer.exe      -Dwebdriver.edge.driver=MicrosoftWebDriver.exe      -Dwebdriver.chrome.driver=chromedriver.exe      -Dwebdriver.firefox.driver=geckodriver.exe      -jar selenium-server-standalone-3.14.0.jar -port 4444
	 * #Start chrome broser
	 * java -Dwebdriver.chrome.driver=chromedriver.exe -jar selenium-server-standalone-3.14.0.jar -port 4444
	 * #Start hub
	 * java -Dwebdriver.chrome.driver=chromedriver.exe -jar selenium-server-standalone-3.14.0.jar -role hub
	 *
	 * @param args
	 * @throws MalformedURLException
	 * @throws InterruptedException
	 */
    public static void main( String[] args ) throws MalformedURLException, InterruptedException
    {
        DesiredCapabilities dc = DesiredCapabilities.chrome();
        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),dc);
        //set dimension 800X600
        driver.manage().window().setSize(new Dimension(800,600));
        //驱动的网址
//        driver.get("http://www.baidu.com/");
        driver.get("http://localhost:8088/_api/sharedRp/getAttributesWithoutParams");
//        driver.navigate().to("http://www.baidu.com/");
        //定位输入框元素
//        WebElement txtbox = driver.findElement(By.name("wd"));
        //在输入框输入文本
//        txtbox.sendKeys("HelloWorld");
        //定位按钮元素
//        WebElement btn = driver.findElement(By.id("su"));
        //点击按钮
//        btn.click();

        for(int i=0;i<5;i++){
        	//Wait for 5 seconds
        	Thread.sleep(5000);
        	//refresh the page
        	driver.navigate().refresh();
        }
        //关闭驱动
//      driver.quit();
      driver.close();
    }
}
