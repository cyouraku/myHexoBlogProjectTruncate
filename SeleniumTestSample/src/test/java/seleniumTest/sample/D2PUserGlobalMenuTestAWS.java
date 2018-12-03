package seleniumTest.sample;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import seleniumTest.robot.RobotCommonUtil;

public class D2PUserGlobalMenuTestAWS extends D2PUserBase {

	private static Logger logger = Logger
			.getLogger(D2PUserGlobalMenuTestAWS.class.getName());

	private Robot robot;

	/**
	 * マウスクリックイベントをテストする。
	 * ブラウザサイズ：800X600
	 * @throws InterruptedException
	 */
	@Test
	public void testGlobalMenu() throws InterruptedException {

		//Menlo Security Login Process
		loginMenlo();

		//Medパスをログイン処理
		loginMedyPass();

		//ログイン後
		//エラーメッセージのボタンを閉じる処理ロジック
//		closeErrorMsg("ログイン後");

		//1.各グローバルメニューをクリックする
		for(int i=0;i<tabList.size();i++){
			String tab =  tabList.get(i);
			//タブをクリック処理ロジック
			getElementById(tabList.get(i)).click();
			logger.info(String.format("Wait for 5 seconds to open %s \n", msgMap.get(tab)));
			Thread.sleep(5000);
			//エラーメッセージのボタンを閉じる処理ロジック
			closeErrorMsg(tab);
		}
	}


	/**
	 * MedPass Login Process
	 * @throws InterruptedException
	 */
	private void loginMedyPass() throws InterruptedException {
		//ログイン処理
		logger.info("Click \"medパスログイン\"ボタン");
		//「medパスログイン」ボタンをクリックする
		if(checkElement(getElementByClassNm("btn01"))){
			getElementByClassNm("btn01").click();
		}
		logger.info("Wait for 10 seconds.");
		Thread.sleep(10000);
		//MedyPass Login
		logger.info("MedyPassのユーザ名を入力する");
		//<div class="form_input_area form_begginner_area" style="margin: 10px">
		//<input id="user_id" name="user_id" type="text" autocomplete="off" maxlength="256" style="box-shadow: none;" aria-required="true" class="ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all" role="textbox" aria-disabled="false" aria-readonly="false" placeholder="メールアドレス or ユーザー名">
		//<input id="passwordInput" name="passwordInput" type="password" class="ui-inputfield ui-password ui-widget ui-state-default ui-corner-all ui-state-focus" style="box-shadow: none;" autocomplete="off" maxlength="30" aria-required="true" role="textbox" aria-disabled="false" aria-readonly="false">
		if(checkElement(getElementByClassNm("form_input_area"))){
			logger.info("MedyPassのユーザ名を入力する");
			getElementByClassNm("form_input_area").findElement(By.id("user_id")).sendKeys("liuliu_test");
			logger.info("MedyPassのPWを入力する");
			getElementByClassNm("form_input_area").findElement(By.id("passwordInput")).sendKeys("12345678");
		}
		logger.info("Click \"medパスでログイン\"ボタン");
		//<input id="login" type="submit" name="login" value="" class="login_Medpass doBlock">
		if(checkElement(getElementByClassNm("login_Medpass doBlock"))){
			getElementByClassNm("login_Medpass doBlock").click();
		}
		logger.info("Wait for 5 seconds.");
		Thread.sleep(5000);
	}

	/**
	 * Menlo Security Login
	 * @throws InterruptedException
	 */
	private void loginMenlo() throws InterruptedException{
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
		logger.info("Wait for 3 seconds to load login page.");
		Thread.sleep(3000);
		//Menlo user:l_zhang@ultmarc.jp  id login_username
		logger.info("Menoユーザ名を入力する");
		if(checkElement(getElementById("login_username"))){
			getElementById("login_username").sendKeys("l_zhang@ultmarc.jp");
		}
		logger.info("Wait for 5 seconds.");
		Thread.sleep(5000);//<input class="button" type="submit" value="次へ ➞">
		//「次へ」ボタンをクリックする////*[@id="login_name"]/form/input
		logger.info("「次へ」ボタンをクリックする");
//		if(checkElement(getElementByXpath("*[@id=\"login_name\"]/form/input"))){
//			getElementByXpath("*[@id=\"login_name\"]/form/input").click();
//		}
		int x = 966;
		int y = 624;
		int delay = 3000;
		RobotCommonUtil.clickLMouse(robot, x, y, delay);
		robot.waitForIdle();
		logger.info("Wait for 3 seconds.");
		Thread.sleep(3000);
		//Menlo pw: hury2018
		logger.info("Menoパスワードを入力する");
		if(checkElement(getElementById("password"))){
			getElementById("password").sendKeys("hury2018");
		}
		logger.info("Wait for 1 seconds.");
		Thread.sleep(1000);
		logger.info("Menlo：「ログイン」ボタンをクリックする");
		if(checkElement(getElementById("password_submit"))){
			getElementById("password_submit").click();
		}
		logger.info("Wait for 10 seconds.");
		Thread.sleep(10000);
	}
}
