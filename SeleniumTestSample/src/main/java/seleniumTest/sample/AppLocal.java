package seleniumTest.sample;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


/**
 * Hello world!
 *
 */
public class AppLocal {

	private static Logger logger = Logger.getLogger(AppLocal.class.getName());

    public static void main( String[] args ) throws InterruptedException {
        //如果火狐浏览器没有默认安装在C盘，需要制定其路径
//        System.setProperty("webdriver.firefox.bin", "D:/Program Files (x86)/Mozilla Firefox/firefox.exe");

    	System.setProperty("webdriver.chrome.driver", "C:\\opt\\chromedriver.exe");

    	//Use System Proxy
//    	System.setProperty("java.net.useSystemProxies", "true");

        //定义驱动对象为 FirefoxDriver 对象
//        WebDriver driver = new FirefoxDriver();
        WebDriver driver = new ChromeDriver();

        //浏览器窗口变大
//        driver.manage().window().maximize();
        //set dimension 800X600
        driver.manage().window().setSize(new Dimension(800,600));

        //驱动的网址
//        driver.get("http://localhost:8088/_api/sharedRp/getAttributesWithoutParams");
        driver.get("http://localhost:8088/");
//        driver.get("http://www.baidu.com/");

        //定位输入框元素
//        WebElement txtbox = driver.findElement(By.name("wd"));
        //在输入框输入文本
//        txtbox.sendKeys("HelloWorld");
        //定位按钮元素
//        WebElement btn = driver.findElement(By.id("su"));
        //点击按钮
//        btn.click();

        WebElement response = driver.findElement(By.id("car"));
        logger.info(String.format("getText = %s \n",response.getText()));
        logger.info(String.format("getAttribute volvo = %s \n",response.getAttribute("volvo")));
        logger.info(String.format("getTagName()= %s \n",response.getTagName()));
        logger.info(String.format("getLocation point x= %d; y = %d \n",response.getLocation().x,response.getLocation().y));
        //Select "volvo"
        response.sendKeys(response.getAttribute("volvo"));


    	//Wait for 5 seconds
    	Thread.sleep(5000);
        //关闭驱动
//        driver.close();
        driver.quit();
    }
}
