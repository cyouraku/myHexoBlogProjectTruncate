package com.whatismybrowser.entity;

public class UserAgentDto {
	private String userAgent = "";
	private String version = "";
	private String os = "";
	private String hardWareType = "";
	private String popularity = "";

	public UserAgentDto(){};

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
