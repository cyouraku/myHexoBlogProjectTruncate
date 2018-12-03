package le.finduser.log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import seleniumTest.Util.SeleniumServerUtil;

import com.whatismybrowser.entity.UserAgentDto;

public class FindUser {

	private static final Logger logger = Logger.getLogger(FindUser.class.getName());
	/**
	 * total log tsv file
	 * "'2018/11/02 19:02:15.111'	'88412'	'Mozilla/5.0 (iPhone; CPU iPhone OS 11_0_1 like Mac OS X) AppleWebKit/604.2.10 (KHTML,like Gecko) Mobile/15A8391'"
	 */
	private static final String file = "C:\\data\\total_records.tsv";
	/**
	 * totoal user list
	 * "userId = 81214"
	 */
	private static final String userFile = "C:\\data\\TotalUserList.txt";
	/**
	 * total user agents per user file
	 * "37468	D2P iOS/iPad/12.0.1"
	 */
	private static final String userAgentsFile = "C:\\data\\TotalAgentsPerUser.txt";
	/**
	 * output file in any pattern
	 */
	private static final String outputFile = "C:\\data\\findUserAgentsDesc.txt";
	/**
	 * users in file1
	 * "userId = 81214"
	 */
	private static final String file1 = "C:\\data\\20181101.txt";
	/**
	 * users in file2
	 * "userId = 81214"
	 */
	private static final String file2 = "C:\\data\\20181110.txt";

	public static void main(String[] args) throws ParseException {

		//https://news2.medy-id.jp/clinical_qa/156615
		int cmd = 1;
		switch(cmd){
		case 0:
			//Find user agents by uid
			findUserAgentsByUserId(userAgentsFile,userFile,"31842");
			break;
		case 1:
			//count user agents,save user agent file and print the list.
			findUserAgentsList(file,userFile,outputFile);
			break;
		case 2:
			//basic find
			findLoginTimesDaily(file,userFile,outputFile);
			break;
		case 3:
			//save to Asc
			saveAsc(findLoginTimesDaily(file,userFile,outputFile));
			break;
		case 4:
			//save to desc
			saveDesc(findUserAgentsList(file,userFile,outputFile));
			break;
		case 5:
			//find duplicated user list both in file1 and file2
			findDouplicatedUser(file1,file2);
			break;
		case 6:
			//find user agent of safari
			findUserAgentSafari("Mozilla/5.0 (iPhone; CPU iPhone OS 12_0_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/16A404");
			break;
		default:
			logger.info("No command detected!");
			break;
		}

	}

	public static void saveAsc(LinkedList<UserDto> list) throws ParseException {
		List<String> output = new ArrayList<String>();
		//Sort asc
		list.sort(UserDto::compareTo);
		System.out.println("Asc by login times");
		for(UserDto sorted : list){
			System.out.println(String.format("user=%s,login=%d",sorted.getUserId(),sorted.getLoginTimes()));
			output.add(String.format("user=%s,login=%d",sorted.getUserId(),sorted.getLoginTimes()));
		}
		//Save to file
		SeleniumServerUtil.writerFile(output, outputFile);
	}

	public static void saveDesc(LinkedList<UserDto> list) throws ParseException{
		List<String> output = new ArrayList<String>();
		//Sort desc
    	Collections.sort(list, new Comparator<UserDto>() {
    	    @Override
    	    public int compare(UserDto o1, UserDto o2) {
    	        return o2.getLoginTimes() - o1.getLoginTimes();
    	    }
    	});
    	System.out.println("Desc by login times");
		for(UserDto sorted : list){
			System.out.println(String.format("user=%s,login=%d",sorted.getUserId(),sorted.getLoginTimes()));
			output.add(String.format("user=%s,login=%d",sorted.getUserId(),sorted.getLoginTimes()));
		}
		//Save to file
		SeleniumServerUtil.writerFile(output, outputFile);
	}

	/**
	 * count user login times from tsv file as below:
	 * 'create_time'	'user_id'	'user_agent'
	 * @param logFile
	 * @param userFile
	 * @param outputFile
	 * @return
	 * @throws ParseException
	 */
	public static LinkedList<UserDto> findLoginTimesDaily(String logFile,String userFile,String outputFile) throws ParseException{
		//Get LogDto List
		List<LogDto> logDtoList = readFileToLogDtoList(logFile);
		//distinct user
		List<String> userList = createDistinctUserList(userFile);
		//Time list
		List<String> timeList = new ArrayList<String>();
		//Output list
		List<String> output = new ArrayList<String>();
		//Result list
		LinkedList<UserDto> resultList = new LinkedList<UserDto>();
		//Sort
		userList.stream().sorted();

		int cntSize = 0;
		//user loop
		for(String user : userList){
			for(LogDto log : logDtoList){
				String uid = log.getUserId().replaceAll("'","").trim();
				if(uid.equals(user.trim())){
//					logger.info(String.format("uid = %s",uid));
					timeList.add(log.getDateTime().replaceAll("'",""));
				}
			}

			if(timeList.size()>0){
				cntSize++;
				//cnt
				int cnt =  countLoginTimesDailyDesc(timeList);
				if(cnt>0){
					String content = String.format("user = %s;login times = %d;",user,cnt);
					resultList.add(new UserDto(user,cnt));
					logger.info(content);
					output.add(content);
				}
			}

			//Clear list for next loop
			timeList.clear();
		}
		logger.info("cntSize = "+ cntSize);

		//Write to file
//		SeleniumServerUtil.writerFile(output, outputFile);

		return resultList;
	}


	/**
	 * count user agents from tsv file as below:
	 * 'create_time'	'user_id'	'user_agent'
	 * @param logFile
	 * @param userFile
	 * @param outputFile
	 * @return
	 * @throws ParseException
	 */
	public static LinkedList<UserDto> findUserAgentsList(String logFile,String userFile,String outputFile) throws ParseException{
		//Get LogDto List
		List<LogDto> logDtoList = readFileToLogDtoList(logFile);
		//distinct user
		List<String> userList = createDistinctUserList(userFile);
		//User Agents list
		List<String> userAgentsList = new ArrayList<String>();
		//Output list
		List<String> output = new ArrayList<String>();
		//Result list
		LinkedList<UserDto> resultList = new LinkedList<UserDto>();
		//Sort
		userList.stream().sorted();

		int cntSize = 0;
		//user loop
		for(String user : userList){
			for(LogDto log : logDtoList){
				String uid = log.getUserId().replaceAll("'","").trim();
				String ua = log.getUserAgent().replaceAll("'","");
				if(uid.equals(user.trim())){
//					logger.info(String.format("uid = %s",uid));
					userAgentsList.add(ua);
				}
			}

			if(userAgentsList.size()>0){
				cntSize++;

				//cnt
				int cnt =  countUserAgentsList(userAgentsList,user,userAgentsFile);
				if(cnt>0){
					String content = String.format("user = %s;user agents = %d;",user,cnt);
					//count result into countUserAgents as count result
					resultList.add(new UserDto(user,0,cnt));
					logger.info(content);
//					output.add(content);
				}
			}

			//Clear list for next loop
			userAgentsList.clear();
		}
		logger.info("cntSize = "+ cntSize);

		//sort desc
		//Sort desc
    	Collections.sort(resultList, new Comparator<UserDto>() {
    	    @Override
    	    public int compare(UserDto o1, UserDto o2) {
    	    	//compare int field
    	        return o2.getCountUserAgents() - o1.getCountUserAgents();
    	    }
    	});
		for(UserDto sorted : resultList){
			output.add(String.format("user = %s,user agents = %d",sorted.getUserId(),sorted.getCountUserAgents()));
		}
		//Write to file
		SeleniumServerUtil.writerFile(output, outputFile);

		return resultList;
	}

	/**
	 * Find user agents by userId.
	 * @param logFile
	 * @param userFile
	 * @param userId
	 */
	public static void findUserAgentsByUserId(String userAgentsFile,String userFile,String userId){
		List<UserDto> list = readFileToUserDtoWithUserAgentsInfo(userAgentsFile,userFile);
		for(UserDto ud : list){
			if(userId.equals(ud.getUserId())){
				System.out.println(String.format("user=%s,ua size=%d", userId,ud.getUserAgents().size()));
				for(String ua : ud.getUserAgents()){
					System.out.println(String.format("user=%s,ua=%s", userId,ua));
				}
				break;
			}
		}
	}

	public static List<Date> createDateList(List<String> timeList) throws ParseException{
		List<Date> dateList = new ArrayList<Date>();
		String tmp = "";
	    for(String str : timeList){
		    Date date = createDate(str);
		    String tmp2 = tmp;
		    tmp = date.toString();
		    if(!tmp2.equals(tmp)){
//		    	logger.info(tmp);
		    	dateList.add(date);
		    }
	    }
//	    logger.info("size = " + dateList.size());
		return dateList;
	}

	public static Date createDate(String time) throws ParseException{
		String str =time.replaceAll("'", "").replaceAll("/", "-").substring(0, 10);
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
		return date;
	}


	/**
	 * ログインログ時刻の降順でユーザログイン回数をカウントする。
	 * @param timeList
	 * @return
	 * @throws ParseException
	 */
	public static int countLoginTimesDailyDesc(List<String> timeList) throws ParseException{
	    Long[] longList = new Long[timeList.size()];
	    for(int i=0;i<timeList.size();i++){
	    	String str = timeList.get(i).replaceAll("'", "").replaceAll("/", "-");
		    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").parse(str);
//		    logger.info(String.format("time=%s,date=%s",str,date.toString()));
		    long time = date.getTime();
	    	longList[i] = time;
	    }
	    Arrays.stream(longList).distinct().sorted(Long::compareTo);
	    int cnt = 1;
	    int maxIdx = longList.length -1;
	    for(int i= maxIdx;i>0;i--){
	    		long tmp = longList[i] - longList[i-1];
//	    		logger.info(String.format("v1=%d,v2=%d,r=%d",longList[i],longList[i-1],tmp));
	    		if(tmp > 60000){
	    			cnt++;
	    		}
	    }
//	    logger.info(String.format("cnt = %d",cnt));
	    return cnt;
	}


	/**
	 * Count user agents per user
	 * @param userAgentsList
	 * @return
	 */
	public static int countUserAgentsList(List<String> userAgentsList,String userId,String outputFile){
		Map<String,String> userAgentMap = new HashMap<String,String>();
		for(String ua: userAgentsList){
			userAgentMap.putIfAbsent(ua, "");
		}
		Object[] uaList = userAgentMap.keySet().toArray();
		LinkedList<String> distinctUserAgents = new LinkedList<String>();
		for(Object uaObj : uaList){
			distinctUserAgents.add(uaObj.toString());
		}
		//Write to file
		List<String> content = new ArrayList<String>();
		for(String ua:distinctUserAgents){
			content.add(String.format("%s	%s",userId,ua));
		}
		SeleniumServerUtil.writerFile(content, outputFile);
		return distinctUserAgents.size();
	}

	/**
	 * Create sql sentences to insert into user_log_history table.
	 * @param file
	 */
	public static void createInsertSqlLogDto(String file){
		List<LogDto>  cdList = readFileToLogDtoList(file);
		int cnt = 0;
//		StringBuilder sb = new StringBuilder();
		List<String> listStr = new ArrayList<String>();
		for(LogDto cd : cdList){
			cnt++;
			String tmp = String.format("insert into user_log_history values (%d,%s,%s,%s);",cnt,cd.getUserId(),cd.getUserAgent(),cd.getDateTime());
//			sb.append(tmp);
			listStr.add(tmp);
			//insert into user_log_history values (1,'1234','Mozila 5','(now)');
//			System.out.println(String.format("insert into user_log_history values (%d,%s,%s,%s);",cnt,cd.getUserId(),cd.getUserAgent(),cd.getDateTime()));
			//Print
//			System.out.println(String.format("Cnt = %d: Time = %s,ID = %s,UA = %s",cnt,cd.getDateTime(),cd.getUserID(),cd.getUserAgent()));
		}
//		System.out.println(sb.toString());
		//Write to file
		SeleniumServerUtil.writerFile(listStr, "C:\\data\\insertUserLogHistory.txt");
	}


	/**
	 * Create distinct user list
	 * @param file
	 * @return
	 */
	public static List<String> createDistinctUserList(String file){
		Map<String,String> userMap = new HashMap<String,String>();
		List<UserDto>  cdList = readFileToUserDtoList(file);
		for(UserDto cd : cdList){
			String id = cd.getUserId();
			userMap.putIfAbsent(id, "");
		}
		Object[] id01 = userMap.keySet().toArray();
		Arrays.stream(id01).distinct().sorted();
		LinkedList<String> distinctUsers = new LinkedList<String>();
		for(Object str: id01){
			distinctUsers.add(str.toString());
//			System.out.println(str.toString());
		}
//		logger.info(String.format("Total users on %s = %d", file1.substring(8, 16), userMap.size()));
		return distinctUsers;
	}


	/**
	 * Find douplicated users both in file1 and file2
	 * @param file1
	 * @param file2
	 */
	public static void findDouplicatedUser(String file1,String file2){
		Map<String,String> userMap = new HashMap<String,String>();
		List<UserDto>  cdList = readFileToUserDtoList(file1);
		for(UserDto cd : cdList){
			String id = cd.getUserId();
			userMap.putIfAbsent(id, "");
		}
		Object[] id01 = userMap.keySet().toArray();
		Arrays.stream(id01).distinct().sorted();
		LinkedList<String> firstDayUser = new LinkedList<String>();
		for(Object str: id01){
			firstDayUser.add(str.toString());
//			System.out.println(str.toString());
		}
		logger.info(String.format("Total users on %s = %d", file1.substring(8, 16), userMap.size()));
		cdList.clear();
		userMap.clear();
		cdList = readFileToUserDtoList(file2);
		for(UserDto cd : cdList){
			String id = cd.getUserId();
			userMap.putIfAbsent(id, "");
		}
		Object[] id02 = userMap.keySet().toArray();
		Arrays.stream(id02).sorted().distinct();
		LinkedList<String> secondDayUser = new LinkedList<String>();
		for(Object str: id02){
			secondDayUser.add(str.toString());
//			System.out.println(str.toString());
		}
		logger.info(String.format("Total users on %s = %d", file2.substring(8, 16), userMap.size()));
		List<String> result = new ArrayList<String>();
		secondDayUser.containsAll(firstDayUser);
		secondDayUser.contains("");
		for(int i=0;i<firstDayUser.size();i++){
			String temp = firstDayUser.get(i);
			if(secondDayUser.contains(temp)){
				result.add(temp);
			}
		}
		logger.info(String.format("Total duplicated users on %s = %d", file2.substring(8, 16), result.size()));
//		for(String id : result){
//			System.out.println(String.format("%s", id));
//		}
	}

	/**
	 * Find user agent target for safari
	 * @param target
	 */
	public static void findUserAgentSafari(String target){
		List<UserAgentDto> cdList = readFileToUserAgentDtoList("C:\\data\\SafariUserAgent.txt");
		int cnt = 0;
		CharSequence s = "12_0_1";
		for(UserAgentDto uad : cdList){
//			if("target".equals(uad.getUserAgent())){
//				System.out.println(String.format("HadWareType=%s;OS=%s;UserAgent=%s;Version=%s;Popularity=%s",
//						uad.getHardWareType(),uad.getOs(),uad.getUserAgent(),uad.getVersion(),uad.getPopularity()));
//				cnt++;
//			}
			if(uad.getUserAgent().contains(s)){
				System.out.println(String.format("HadWareType=%s;OS=%s;UserAgent=%s;Version=%s;Popularity=%s",
						uad.getHardWareType(),uad.getOs(),uad.getUserAgent(),uad.getVersion(),uad.getPopularity()));
				cnt++;
			}
		}
		logger.info(String.format("cnt=%d", cnt));
	}


	/**
	 * Read fiel to UserDto list with user agents info.
	 * @param filePath
	 * @param userFile
	 * @return
	 */
	private static List<UserDto> readFileToUserDtoWithUserAgentsInfo(String userAgentsFile,String userFile){
		List<String> resultList = SeleniumServerUtil.readFileToListStr(userAgentsFile);
		LinkedList<UserDto> cdList = new LinkedList<UserDto>();
		List<String> userList = createDistinctUserList(userFile);
		//Debug;
		logger.info(String.format("userList.size()=%d",userList.size()));
		for(String user : userList){
			UserDto cd = new UserDto(user,0);
			cd.setUserAgents(new LinkedList<String>());
			cdList.add(cd);
		}
		for(UserDto dao : cdList){
			for(String str : resultList){
				String[] sp = str.split("	");
				if(sp.length == 2 && sp[0].equals(dao.getUserId())){
					dao.getUserAgents().addLast(sp[1]);
				}
			}
		}
		logger.info(String.format("cdList.size()=%d",cdList.size()));
		return cdList;
	}

	/**
	 * Read file to UserDto list
	 * @param filePath
	 * @return
	 */
	private static List<UserDto> readFileToUserDtoList(String filePath){
		List<String> resultList = SeleniumServerUtil.readFileToListStr(filePath);
		List<UserDto> cdList = new ArrayList<UserDto>();
		for(String str : resultList){
			UserDto cd = new UserDto();
			String[] sp = str.split(" = ");
//			logger.info(String.format("id = %s",sp[1]));
			if(sp.length == 2){
				cd.setUserId(sp[1].trim());
			}
			cdList.add(cd);
		}
		return cdList;
	}


	/**
	 * Read file to UserAgentDto list
	 * @param filePath
	 * @return
	 */
	private static List<UserAgentDto> readFileToUserAgentDtoList(String filePath){
		List<String> resultList = SeleniumServerUtil.readFileToListStr(filePath);
		List<UserAgentDto> cdList = new ArrayList<UserAgentDto>();
		for(String str : resultList){
//			logger.info(String.format("str = %s \n", str));
			UserAgentDto cd = new UserAgentDto();
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

	/**
	 * Read file to LogDto list
	 * @param filePath
	 * @return
	 */
	private static List<LogDto> readFileToLogDtoList(String filePath){
		List<String> resultList = SeleniumServerUtil.readFileToListStr(filePath);
		List<LogDto> cdList = new ArrayList<LogDto>();
		for(String str : resultList){
			LogDto cd = new LogDto();
			String[] sp = str.split("	");
//			logger.info(String.format("id = %s",sp[1]));
			if(sp.length == 3){
				cd.setDateTime(sp[0].trim());
				cd.setUserId(sp[1].trim());
				cd.setUserAgent(sp[2].trim());
			}
			cdList.add(cd);
		}
		return cdList;
	}

}
