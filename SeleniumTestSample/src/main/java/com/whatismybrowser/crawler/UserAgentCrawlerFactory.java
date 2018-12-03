package com.whatismybrowser.crawler;

import com.whatismybrowser.util.WebDriverConstants;

public class UserAgentCrawlerFactory {

	public UserAgentCrawlerFactory() {
	}

	/**
	 * 「Chrome User Agent Crawler」を取得する
	 *
	 * @return
	 */
	public UserAgentCrawler getChromeUserAgentCrawler() {
		return new UserAgentCrawlerImpl(WebDriverConstants.CHROME_DRIVER_PATH,
				WebDriverConstants.CHROME_USER_AGENT_URL,
				WebDriverConstants.CHROME_USER_AGENT_FILE);
	}

	/**
	 * 「Safari User Agent Crawler」を取得する
	 *
	 * @return
	 */
	public UserAgentCrawler getSafariUserAgentCrawler() {
		return new UserAgentCrawlerImpl(WebDriverConstants.CHROME_DRIVER_PATH,
				WebDriverConstants.SAFARI_USER_AGENT_URL,
				WebDriverConstants.SAFARI_USER_AGENT_FILE);
	}

}
