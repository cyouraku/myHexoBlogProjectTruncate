package seleniumTest.sample;

import java.util.logging.Logger;

import org.testng.annotations.Test;

public class D2PUserGlobalMenuTest extends D2PUserBase {

	private static Logger logger = Logger
			.getLogger(D2PUserGlobalMenuTest.class.getName());

	/**
	 * マウスクリックイベントをテストする。
	 * ブラウザサイズ：800X600
	 * @throws InterruptedException
	 */
	@Test
	public void testGlobalMenu() throws InterruptedException {
		logger.info("Wait for 30 seconds to load login page.");
		Thread.sleep(30000);
		//ログイン処理
//		logger.info("Click \"medパスログイン\"ボタン");
		//「medパスログイン」ボタンをクリックする
//		if(checkElement(getElementByClassNm("btn01"))){
//			getElementByClassNm("btn01").click();
//		}
		logger.info("Wait for 30 seconds.");
		Thread.sleep(30000);

		//ログイン後
		//エラーメッセージのボタンを閉じる処理ロジック
//		closeErrorMsg("ログイン後");

		int cnt = 100;
		while (cnt > 0) {

			logger.info("Wait for 30 seconds.");
			Thread.sleep(30000);

//			// 1.各グローバルメニューをクリックする
//			for (int i = 0; i < tabList.size(); i++) {
//				String tab = tabList.get(i);
//				// タブをクリック処理ロジック
//				WebElement tabObj = getElementById(tabList.get(i));
//				if(checkElement(tabObj)){
//					tabObj.click();
//				}
//				logger.info(String.format("Wait for 10 seconds to open %s \n",
//						msgMap.get(tab)));
//				Thread.sleep(10000);
//				// エラーメッセージのボタンを閉じる処理ロジック
//				closeErrorMsg(tab);
//			}

			cnt--;

		}

	}

}
