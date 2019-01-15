package seleniumTest.sample;

import static org.testng.Assert.*;

import java.util.logging.Logger;

import org.testng.annotations.Test;

import com.whatismybrowser.service.FetchChromeUserAgentInfo;

public class FetchChromeUserAgentInfoTest {

	private static Logger logger = Logger
			.getLogger(FetchChromeUserAgentInfoTest.class.getName());

	@Test
	public void testFetchChromeUserAgentPageInfo() throws InterruptedException {
		long st = System.currentTimeMillis();
		logger.info("testFetchChromeUserAgentPageInfo start!");
		//Page 1 to 3218:
		int start = 1801;
		int end = 2000;
		FetchChromeUserAgentInfo safari = new FetchChromeUserAgentInfo();
		int result = safari.fetchUserAgentPageInfo(start, end);
		assertEquals(end, result);
		logger.info("testFetchChromeUserAgentPageInfo end! Time spent = " + (System.currentTimeMillis() - st));
	}


}
