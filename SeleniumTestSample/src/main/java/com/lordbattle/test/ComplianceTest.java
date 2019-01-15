package com.lordbattle.test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Logger;

import com.lordbattle.util.Card;
import com.lordbattle.util.LordBattle;
import com.lordbattle.util.PlayCards;
import com.lordbattle.util.Player;

public class ComplianceTest {

	private static final Logger logger = Logger.getLogger(ComplianceTest.class.getName());
	private static LinkedList<Card> total = PlayCards.getCardListTotal();
	private static LinkedList<Card> putCards = new LinkedList<Card>();
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		Player player = new Player("test player");
		System.out.println(String.format("Total cards:\n%s",LordBattle.showCards(total)));
		boolean test = true;
	      while(test){
	    	  start(player);
	    	  String line;
	    	  while(!(line = sc.next()).equalsIgnoreCase("n")) {
	    		  logger.info("line input = " + line);
	    		  if(line.equalsIgnoreCase("y")){
	    			  break;
	    		  }
	    	  }
	    	  if(line.equalsIgnoreCase("n")){
	    		  test = false;
	    	  }
	      }
	      sc.close();
	}


	private static String getMaxCard(LinkedList<Card> list){
		Integer[] toArray = new Integer[list.size()];
		int idx = 0;
		for(Card card : list){
			logger.info(String.format("card value = %s",card.value));
			toArray[idx] = PlayCards.findIndex(card);
			idx++;
		}
		Arrays.sort(toArray,Comparator.reverseOrder());
		int maxIdx = toArray[0];
		return String.format("max index = %d;card value = %s",maxIdx,PlayCards.cardVlues[maxIdx]);
	}

	private static void start(Player player){
		PlayCards play = new PlayCards();

		boolean isExit = false;
		while (!isExit) {
			System.out.println("Please input cards for testing: \n");
			String line;
			while (!(line = sc.next()).equalsIgnoreCase("end")) {
				logger.info("line input = " + line);
				if("compare".equals(line)){
					//Test compare size
					testCompareSize("梅花:4,红桃:4,黑桃:4,方形:4","梅花:3,红桃:3,黑桃:3,方形:3,红桃:5,黑桃:5,方形:6,梅花:6");
				}
				String[] strList = line.split("[,]");
				if (strList.length < 0) {
					System.out.println("请按规则输入");
					continue;
				}
				for (String str : strList) {
					String[] split = str.split("[:|：]");
					if (split.length != 2) {
						System.out.println("请按规则输入");
						continue;
					}
					Card card = new Card(split[0].trim(), split[1].trim());
					putCards.add(card);
				}
				player.setCards(putCards);
				player.setPutCards(putCards);
			}
			// Debug:
			logger.info(getMaxCard(putCards));
			logger.info(String.format("getCards = %s",
					LordBattle.showCards(player.getCards())));
			logger.info(String.format("putCards = %s",
					LordBattle.showCards(player.getPutCards())));
			logger.info(String.format("compliance = %d",
					play.compliance(player)));
			String v1 = String.valueOf(play.compliance(player));
			logger.info(String.format("amount = %d",Integer.parseInt(v1.substring(v1.length() - 2))));
			putCards.clear();
			player.setCards(putCards);
			player.setPutCards(putCards);
			if(line.equalsIgnoreCase("exit")){
				isExit = true;
			}
		}
	}

//	private static void  SortAsList(Vector<Integer> arr){
//		Integer[] tmp = new Integer[arr.size()];
//		while(arr.iterator().hasNext()){
//			for(int i=0;i<arr.size();i++){
//				tmp[i]=arr.iterator().next();
//			}
//		}
//		Arrays.sort(arr.toArray(tmp),Comparator.reverseOrder());
//	};


	private static void testCompareSize(String line1,String line2){
	      //定义玩家
	      Player p1 = new Player("一号");
	      Player p2 = new Player("二号");
	      Player p3 = new Player("三号");
	      p3.setFirst(true);
	      p1.setNext(p2);
	      p1.setBefore(p3);
	      p2.setNext(p3);
	      p2.setBefore(p1);
	      p3.setNext(p1);
	      p3.setBefore(p2);
		LinkedList<Card> putCards = new LinkedList<Card>();
		LinkedList<Card> putCardsBef = new LinkedList<Card>();
		LinkedList<Card> putCardsBef2 = new LinkedList<Card>();
		String[] strList = line1.split("[,]");
		for (String str : strList) {
			String[] split = str.split("[:|：]");
			logger.info("split.length = " + split.length);
			logger.info("str = " + str);
			Card card = new Card(split[0].trim(), split[1].trim());
			putCards.add(card);
		}
		p1.setCards(putCards);
		p1.setPutCards(putCards);
		String[] strList2 = line2.split("[,]");
		for (String str2 : strList2) {
			String[] split2 = str2.split("[:|：]");
			logger.info("split2.length = " + split2.length);
			logger.info("str2 = " + str2);
			Card card2 = new Card(split2[0].trim(), split2[1].trim());
			putCardsBef.add(card2);
		}
		p2.setCards(putCardsBef);
		p2.setPutCards(putCardsBef);
		//Set before2
		p3.setCards(putCardsBef2);
		p3.setPutCards(putCardsBef2);
	    Player[] players = {p1,p2,p3};
		PlayCards play = new PlayCards(players);
		//Test compliance && compare size
		boolean result = play.compliance(p1) > -1 && play.compareSize(p1);
		if(result){
			System.out.println("player OK!");
		}else{
			System.out.println("player NG!");
		}
	}

}
