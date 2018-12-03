package seleniumTest.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewNotificationFlagTest {


	public static void main(String[] args) {

		Map<String,String> topFiveMap = new HashMap<String,String>();
		List<String> oldlist =  createListRound1();
		List<String> newlist =  createListRound2();

		//Test:新着ありのテスト
		//新着があれば、「１」を出力する。
		System.out.println(checkNewValues2(newlist,oldlist));
		if(1 == checkNewValues2(newlist,oldlist)){
			//Top 5件取得
			for(int i=0;i<newlist.size();i++){
				topFiveMap.put(Integer.toString(i),newlist.get(i));
				System.out.println(topFiveMap.get(Integer.toString(i)));
			}
		}

		//Test:新着なしのテスト
		//新着なしの場合、「0」を出力する。
		newlist.clear();
		newlist =  createListRound3();
		System.out.println(checkNewValues2(newlist,oldlist));
		if(0 == checkNewValues2(newlist,oldlist)){
			System.out.println("新着なしのテスト");
			//Top 5件取得
			for(int i=0;i<oldlist.size();i++){
				topFiveMap.put(Integer.toString(i),newlist.get(i));
				System.out.println(topFiveMap.get(Integer.toString(i)));
			}
		}



	}

	private static List<String> createListRound1(){
		List<String> list = new ArrayList<String>();
		list.add("value5");
		list.add("value4");
		list.add("value3");
		list.add("value2");
		list.add("value1");
		return list;
	}

	private static List<String> createListRound2(){
		List<String> list = new ArrayList<String>();
		list.add("value6");
		list.add("value5");
		list.add("value4");
		list.add("value3");
		list.add("value2");
		return list;
	}

	private static List<String> createListRound3(){
		List<String> list = new ArrayList<String>();
		list.add("value5");
		list.add("value4");
		list.add("value3");
		list.add("value2");
		list.add("value1");
		return list;
	}

	private static int checkNewValues2(List<String> newlist, List<String> oldlist){
		boolean isFlag = true;
		for (int i = 0; i < newlist.size(); i++) {
			if (!oldlist.contains(newlist.get(i))) {
				isFlag = false;
			}
		}
		if (isFlag) {
			return 0;
		}
		return 1;
	}

	private static int checkNewValues(List<String> newlist, List<String> oldlist){
		boolean isFlag = false;
		if(newlist.size()>oldlist.size()){
			isFlag = false;
		}else{
			for(int i=0; i<newlist.size(); i++){
				if(oldlist.contains(newlist.get(i))){
					isFlag = true;
				}else{
					isFlag = false;
				}
			}
		}
		if(isFlag){
			return 0;
		}
		return 1;
	}

	/**
	 * Check key already exists or not.
	 * @param menu
	 * @param key
	 * @return
	 */
	private boolean checkKeyExists(Map<String,String> menu, String key){
		if(menu.containsKey(key)){
			return true;
		}
		return false;
	}

	/**
	 * Check value already exists or not.
	 * @param menu
	 * @param value
	 * @return
	 */
	private boolean checkValueExists(Map<String,String> menu, String value){
		if(menu.containsValue(value)){
			return true;
		}
		return false;
	}

	/**
	 * Check key already exists or not.
	 * If key exists then return true.
	 * @param menu
	 * @param key
	 * @param value
	 * @return
	 */
	private boolean checkExisting(Map<String,String> menu, String key, String value){
		if(menu.putIfAbsent(key, value)!=null){
			return true;
		}
		return false;
	}

}
