package le.finduser.log;

import java.util.LinkedList;

public class UserDto implements Comparable<UserDto>{

	private String userId;

	/**
	 * count for login times
	 */
	private int loginTimes;

	/**
	 * count for user agents.
	 */
	private int countUserAgents;

	private LinkedList<String> userAgents;

	public UserDto(){}

	public UserDto(String userId, int loginTimes){
		this.userId = userId;
		this.loginTimes = loginTimes;
	}

	public UserDto(String userId, int loginTimes, int countUserAgents){
		this.userId = userId;
		this.loginTimes = loginTimes;
		this.countUserAgents = countUserAgents;
	}

	public UserDto(String userId, int loginTimes,LinkedList<String> userAgents){
		this.userId = userId;
		this.loginTimes = loginTimes;
		this.userAgents = userAgents;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(int loginTimes) {
		this.loginTimes = loginTimes;
	}

	public int getCountUserAgents() {
		return countUserAgents;
	}

	public void setCountUserAgents(int countUserAgents) {
		this.countUserAgents = countUserAgents;
	}

	public LinkedList<String> getUserAgents() {
		return userAgents;
	}

	public void setUserAgents(LinkedList<String> userAgents) {
		this.userAgents = userAgents;
	}

	@Override
	public int compareTo(UserDto o) {
		return this.getLoginTimes() - o.getLoginTimes();
	}


}
