package seleniumTest.sample;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import seleniumTest.Util.SeleniumServerUtil;
import seleniumTest.Util.UserAgentUtil;

public class ChromeUserAgentTest extends ChromeUserAgentBase {

	private static Logger logger = Logger
			.getLogger(ChromeUserAgentTest.class.getName());
	private static final String FILE_PATH_CHROME  = "C:\\data\\ChromeUserAgent01.txt";
	private static final String FILE_PATH_SAFARI = "C:\\data\\SafariUserAgent.txt";

	private List<ChromeDto> entityList = new ArrayList<ChromeDto>();

	/**
	 * マウスクリックイベントをテストする。
	 * ブラウザサイズ：800X600
	 * @throws InterruptedException
	 */
	@Test
	public void testChromeUserAgentPageInfo() throws InterruptedException {
		logger.info("Wait for 3 seconds.");
		Thread.sleep(3000);
		//Page 1 to 3218:
		int page = 501;
		while (page <= 600) {
			logger.info("Current Page = " + page);
			WebDriver webDriver = null;
			webDriver = createDesiredWebDriverSilent(Integer.toString(page),webDriver);
			logger.info("Wait for 3 seconds.");
			Thread.sleep(3000);
			printTalbeInfo(webDriver);
			page++;
			quitWebDriver(webDriver);
			logger.info("Wait for 3 seconds.");
			Thread.sleep(3000);
		}
		assertEquals(601, page);
	}

	@Test
	public void testReadChromeInfo(){
		//Test create user agent
		List<ChromeDto> cdList = readFileToChromeDtoList(FILE_PATH_CHROME);
		for (ChromeDto cd : cdList) {
			String header = cd.getUserAgent();
			String userAgent = UserAgentUtil.createLoginUserAgent(header);
			String browser = "";
			logger.info(String.format("header = %s \n", header));
			logger.info(String.format("userAgent = %s \n", userAgent));
	        if(userAgent.indexOf(UserAgentUtil.WINDOWS) != -1) {
	        	browser = UserAgentUtil.browserDecision(userAgent);
	            if("other".equals(browser)){
//	            	assertEquals(userAgent, UserAgentUtil.OTHER);
	            }else{
	            	assertEquals(userAgent, UserAgentUtil.WINDOWS + " " + browser);
	            }
	        }else if (userAgent.indexOf(UserAgentUtil.ANDROID) != -1) {
				assertEquals(userAgent,cd.getOs());
			} else if (userAgent.indexOf(UserAgentUtil.MAC) != -1) {
				if (userAgent.indexOf(UserAgentUtil.IPHONE) != -1
						|| userAgent.indexOf(UserAgentUtil.IPAD) != -1
						|| userAgent.indexOf(UserAgentUtil.IPOD) != -1) {
					assertEquals(userAgent,cd.getOs());
				}else{
	                browser = UserAgentUtil.browserDecision(userAgent);
		            if("other".equals(browser)){
		            	assertEquals(userAgent, UserAgentUtil.OTHER);
		            }else{
		            	assertEquals(userAgent, UserAgentUtil.MAC + " " + browser);
		            }
				}
			}else if(userAgent.indexOf(UserAgentUtil.LINUX) != -1) {
				browser = UserAgentUtil.browserDecision(userAgent);
	            if("other".equals(browser)){
	            	assertEquals(userAgent, UserAgentUtil.OTHER);
	            }else{
	            	assertEquals(userAgent, UserAgentUtil.LINUX + " " + browser);
	            }
	        }

		}
	}

	@Test
	public void testReadChromeInfoAndFindAndroid(){
		//Test create user agent
		List<ChromeDto> cdList = readFileToChromeDtoList(FILE_PATH_CHROME );
		int cnt = 0;
		for(ChromeDto cd : cdList){
			String ua = cd.getUserAgent();

			if(UserAgentUtil.createLoginUserAgent(ua).indexOf(UserAgentUtil.ANDROID) != -1){
				cnt++;
				logger.info(String.format("UserAgent = %s", UserAgentUtil.createLoginUserAgent(ua)));;
			}
		}
		logger.info(String.format("Total Android Amt = %d", cnt));;
	}

	@Test
	public void testReadChromeInfoAndFindiOS(){
		//Test create user agent
		List<ChromeDto> cdList = readFileToChromeDtoList(FILE_PATH_SAFARI);
		int cnt = 0;
		for(ChromeDto cd : cdList){
			String ua = cd.getUserAgent();

			if(UserAgentUtil.createLoginUserAgent(ua).indexOf(UserAgentUtil.IOS) != -1){
				cnt++;
				logger.info(String.format("UserAgent = %s", UserAgentUtil.createLoginUserAgent(ua)));;
			}
		}
		logger.info(String.format("Total iPhone Amt = %d", cnt));
	}

	@Test
	public void testReadChromeInfoAndFindMac(){
		//Test create user agent
		List<ChromeDto> cdList = readFileToChromeDtoList(FILE_PATH_SAFARI);
		int cnt = 0;
		for(ChromeDto cd : cdList){
			String ua = cd.getUserAgent();

			if(UserAgentUtil.createLoginUserAgent(ua).indexOf(UserAgentUtil.MAC) != -1){
				cnt++;
				logger.info(String.format("UserAgent = %s", UserAgentUtil.createLoginUserAgent(ua)));;
			}
		}
		logger.info(String.format("Total Mac Amt = %d", cnt));
	}



	/**
	 * Print table info method
	 */
	private void printTalbeInfo(WebDriver webDriver){
		WebElement tableElement=getElementByClassNm("table",webDriver);
		List<WebElement> rows=tableElement.findElements(By.tagName("tr"));
		logger.info("rows.size = " + rows.size());
		for (int i = 0; i < rows.size(); i++) {
			List<WebElement> cols=rows.get(i).findElements(By.tagName("td"));
			ChromeDto cd = new ChromeDto();
			for (int j = 0; j < cols.size(); j++) {
				String contents = cols.get(j).getText();
				switch(j){
					case 0:
						//User agent,j=0
						if(checkEmpty(contents)){
							cd.setUserAgent(contents);
//							logger.info(String.format("User agent = %s.",contents));
						}
						break;
					case 1:
						//Version,j=1
						if(checkEmpty(contents)){
							cd.setVersion(contents);
//							logger.info(String.format("Version = %s.",contents));
						}
						break;
					case 2:
						//OS,j=2
						if(checkEmpty(contents)){
							cd.setOs(contents);
//							logger.info(String.format("OS = %s.",contents));
						}
						break;
					case 3:
						//Hardware Type,j=3
						if(checkEmpty(contents)){
							cd.setHardWareType(contents);
//							logger.info(String.format("Hardware Type = %s.",contents));
						}
						break;
					case 4:
						//Popularity,j=4
						if(checkEmpty(contents)){
							cd.setPopularity(contents);
//							logger.info(String.format("Popularity = %s.",contents));
						}
						break;
				}
			}

			if(!"".equals(cd.getUserAgent())){
				entityList.add(cd);
				logger.info(String.format("user agent %d added.",i));
				//Save to CSV file
//				saveToTsvFile(entityList,FILE_PATH);
				//reset list
				entityList.clear();
			}

		}
	}

	/**
	 * List<ChromeDto>対象をTSVファイルに格納する
	 * @param entityList
	 * @param filePath
	 */
	private void saveToTsvFile(List<ChromeDto> entityList, String filePath){
		List<String> strList = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for(ChromeDto dto : entityList){
			if(checkEmpty(dto.getUserAgent())){
				sb.append(dto.getUserAgent());
				sb.append("\t");
			}
			if(checkEmpty(dto.getVersion())){
				sb.append(dto.getVersion());
				sb.append("\t");
			}
			if(checkEmpty(dto.getOs())){
				sb.append(dto.getOs());
				sb.append("\t");
			}
			if(checkEmpty(dto.getHardWareType())){
				sb.append(dto.getHardWareType());
				sb.append("\t");
			}
			if(checkEmpty(dto.getPopularity())){
				sb.append(dto.getPopularity());
			}
			sb.toString();
			strList.add(sb.toString());
		}
		SeleniumServerUtil.writerFile(strList, filePath);
	}

	/**
	 * 空文字検索
	 * @param input
	 * @return
	 */
	private boolean checkEmpty(String input){
		if(!"".equals(input)){
			return true;
		}
		return false;
	}

	/**
	 * ファイルを読み込んでList<ChromeDto>対象を取得する
	 * @return
	 */
	private List<ChromeDto> readFileToChromeDtoList(String filePath){
		List<String> resultList = SeleniumServerUtil.readFileToListStr(filePath);
		List<ChromeDto> cdList = new ArrayList<ChromeDto>();
		for(String str : resultList){
//			logger.info(String.format("str = %s \n", str));
			ChromeDto cd = new ChromeDto();
			String[] sp = str.split("\t");
			if(sp.length == 5){
				cd.setUserAgent(sp[0]);
				cd.setVersion(sp[1]);
				cd.setOs(sp[2]);
				cd.setHardWareType(sp[3]);
				cd.setPopularity(sp[4]);
			}
			cdList.add(cd);
		}
		return cdList;
	}

	private class ChromeDto {
		private String userAgent = "";
		private String version = "";
		private String os = "";
		private String hardWareType = "";
		private String popularity = "";

		public ChromeDto(){};

		public String getUserAgent() {
			return userAgent;
		}
		public void setUserAgent(String userAgent) {
			this.userAgent = userAgent;
		}
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
		public String getOs() {
			return os;
		}
		public void setOs(String os) {
			this.os = os;
		}
		public String getHardWareType() {
			return hardWareType;
		}
		public void setHardWareType(String hardWareType) {
			this.hardWareType = hardWareType;
		}
		public String getPopularity() {
			return popularity;
		}
		public void setPopularity(String popularity) {
			this.popularity = popularity;
		}


	}

}
