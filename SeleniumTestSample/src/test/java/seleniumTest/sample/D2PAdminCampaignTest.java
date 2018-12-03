package seleniumTest.sample;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import seleniumTest.Util.SeleniumServerUtil;

public class D2PAdminCampaignTest extends D2PAdminBase {

	private static Logger logger = Logger
			.getLogger(D2PAdminCampaignTest.class.getName());

	private WebElement btn = null;
	private WebElement link = null;

	/**
	 * キャンペーン新規登録画面を開くテスト
	 * @throws InterruptedException
	 */
	@Test
	public void testCampaignInsert() throws InterruptedException {
		link = SeleniumServerUtil
				.getWebElementByClassNm(this.webDriver, "side-campaign-list");
		link.click();
		logger.info("wait 5 seconds.");
		Thread.sleep(5000);
		//<a class="Bluebutton fontS" onclick="doRegist()">新規登録</a>
		btn = SeleniumServerUtil
				.getWebElementByClassNm(this.webDriver, "Bluebutton");
		btn.click();
		//マウス制御テスト
		try {
			mouseClickEvent();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		logger.info("wait 5 seconds.");
		Thread.sleep(5000);
	}

//	@Override
//	@BeforeClass
//	public void beforeClass() {
//		if (this.webDriver == null) {
//			this.webDriver = SeleniumServerUtil.getLocalWebDrive(
//					CommonConstants.DRIVER_CHROME, this.driverPath,
//					this.testUrl, new Dimension(1920, 1080));
//		}
//		try {
//			getLogin();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}

	private void mouseClickEvent() throws AWTException {
		Robot robot = new Robot();
		robot.mouseMove(80, 10);
		robot.mousePress(KeyEvent.BUTTON1_MASK);
		logger.info("robot wait 5 seconds.");
		robot.delay(5000);
//		logger.info("robot thread wait 5 seconds.");
//		Thread.sleep(5000);
		robot.mouseMove(600, 600);
		robot.mouseRelease(KeyEvent.BUTTON1_MASK);
	}



}
