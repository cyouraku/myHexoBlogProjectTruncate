package seleniumTest.sample;

import static org.testng.Assert.*;

import java.util.logging.Logger;

import org.testng.annotations.Test;

import com.whatismybrowser.service.FetchSafariUserAgentInfo;

public class FetchSafariUserAgentInfoTest {

	private static Logger logger = Logger
			.getLogger(FetchSafariUserAgentInfoTest.class.getName());

	@Test
	public void testSafariUserAgentPageInfo() throws InterruptedException {
		long st = System.currentTimeMillis();
		logger.info("testSafariUserAgentPageInfo start!");
		//Page 1 to 230:
		int start = 51;
		int end = 230;
		FetchSafariUserAgentInfo safari = new FetchSafariUserAgentInfo();
		int result = safari.fetchUserAgentPageInfo(start, end);
		assertEquals(end, result);
		logger.info("testSafariUserAgentPageInfo end! Time spent = " + (System.currentTimeMillis() - st));
	}


}
