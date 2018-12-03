package seleniumTest.sample;

import java.util.logging.Logger;

import org.testng.annotations.Test;

public class D2PUserSettingMenuTest extends D2PUserBase {

	private static Logger logger = Logger
			.getLogger(D2PUserSettingMenuTest.class.getName());

	/**
	 * マウスクリックイベントをテストする。
	 * ブラウザサイズ：800X600
	 * @throws InterruptedException
	 */
	@Test
	public void testSettingMenu() throws InterruptedException {
		logger.info("Wait 5 seconds to load login page.");
		Thread.sleep(5000);
		//ログイン処理
		logger.info("Click \"medパスログイン\"ボタン");
		//「medパスログイン」ボタンをクリックする
		if(checkElement(getElementByClassNm("btn01"))){
			getElementByClassNm("btn01").click();
		}
		logger.info("Wait 5 seconds to load login page.");
		Thread.sleep(5000);

		//ログイン後
		//エラーメッセージのボタンを閉じる処理ロジック
		closeErrorMsg("ログイン後");

		//「各種設定」メニューをクリックする
		clickSettingMenu();
		logger.info("Wait 1 seconds to open 「各種設定」メニュー.");
		Thread.sleep(1000);
		String linkTxt = "";
		boolean isFlag = true;
		//2.各種設定メニューをクリックする
		for(int i=0;i<linkTextList.size();i++){
			linkTxt = linkTextList.get(i);
			//エラーメッセージのボタンを閉じる処理ロジック
			closeErrorMsg(linkTxt);
			//「各種設定」メニューをクリックする
			if(!isFlag){
				isFlag = clickSettingMenu();
			}
			logger.info(String.format("linkTxt = %s \n",linkTxt));
			//各種設定リンクをクリックする。
			if(checkElement(getElementByLinkText(linkTxt))){
				getElementByLinkText(linkTxt).click();
				logger.info(String.format("Wait 5 seconds to open 「%s」画面. \n",linkTxt));
				Thread.sleep(5000);
			}

			//「各種設定」メニューをクリックする
			isFlag = clickSettingMenu();
			if(!isFlag){
				//Go to Top page
				if(checkElement(getElementById("tab_0"))){
					getElementById("tab_0").click();
					logger.info(String.format("Wait for 5 seconds to open 「%s」画面. \n",msgMap.get("tab_0")));
					Thread.sleep(5000);
				}
			}else{
				logger.info("Wait for 1 seconds to open 「各種設定」メニュー.");
				Thread.sleep(1000);
			}
		}

	}

}
