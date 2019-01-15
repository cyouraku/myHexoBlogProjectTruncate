package com.lordbattle.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import com.lordbattle.ai.HandCardData;
import com.lordbattle.robot.RobotUtil;

public class LordBattle {

	private static final Logger logger = Logger.getLogger(LordBattle.class.getName());
	private static Scanner sc;
	private static LinkedList<Card> total;
	private static LinkedList<Card> spent;
	private static Map<String,Card> spent_bef;
//	private static LinkedList<Card> remains = new LinkedList<Card>();
	/*Robot Policy Min Constant*/
	private static final String ROBOT_POLICY_MIN= "min";
	/*Robot Policy Max Constant*/
	private static final String ROBOT_POLICY_MAX= "max";
	/*current Robot Policy*/
	private static final String POLICY_IN_USE = ROBOT_POLICY_MAX;

	static{
		sc = new Scanner(System.in);
		total = PlayCards.getCardListTotal();
		spent = new LinkedList<Card>();
		spent_bef = new HashMap<String,Card>();
	}

	public static void main(String[] args) {
      //定义玩家
      Player p1 = new Player("一号");
      Player p2 = new Player("二号");
      Player p3 = new Player("三号");
      p1.setNext(p2);
      p1.setBefore(p3);
      p2.setNext(p3);
      p2.setBefore(p1);
      p3.setNext(p1);
      p3.setBefore(p2);
      Player[] players = {p1,p2,p3};
//      Scanner scr = new Scanner(System.in);
      boolean play = true;
      while(play){
    	  start(players);
    	  System.out.println("再来一局？y/n \n");
    	  String line;
    	  while(!(line = sc.next()).equalsIgnoreCase("n")) {
    		  logger.info("line input = " + line);
    		  if(line.equalsIgnoreCase("y")){
    			  break;
    		  }
    	  }
    	  if(line.equalsIgnoreCase("n")){
    		  play = false;
    	  }
      }
      sc.close();
	}

    /**
     * 比赛流程
     * @param players 参加的玩家
     */
	public static void start(Player[] players) {
        //开局
        PlayCards play = new PlayCards(players);
        //洗牌
        play.shuffle(null);
        //分牌
        play.distributional();

//        Scanner sc = new Scanner(System.in);
        boolean exit = checkExit(play);
        int round = 0;
        while (exit) {
            Player player = Arrays.stream(play.players).filter(Player::isFirst).findFirst().get();
            //显示上两家出牌
            int bef = player.before.getPutCards().size();
            int bef2 = player.before.before.getPutCards().size();
            int cur = player.getPutCards().size();
            int cnt =  player.before.getCards().size();
            int cnt2 = player.before.before.getCards().size();
            int curCnt = player.getCards().size();
            //Record play history
            spent.addAll(player.getPutCards());
            spent.addAll(player.before.getPutCards());
            spent.addAll(player.before.before.getPutCards());
            spent.sort(Card::compareTo);
            //debug:
            logger.info(String.format("round=%d;bef=%d;bef2=%d;cur=%d",round,bef,bef2,cur));
//            if(bef > 0 ){
//                System.out.println(String.format("上家出牌为 \n %s : %s",player.before.getUsername(), player.before.getPutCards()));
//            }
//            if(bef2 > 0){
//                System.out.println(String.format("上上家出牌为 \n %s : %s",player.before.before.getUsername(),player.before.before.getPutCards()));
//            }
//            if(cur > 0 && (bef + bef2) == 0){
//                System.out.println(String.format("您上次出牌为 \n %s : %s",player.getUsername(), player.getPutCards()));
//            }
            if(cnt<10){
            	System.out.println(String.format("上家剩余牌数: %d",cnt));
            }
            if(cnt2<10){
            	System.out.println(String.format("上上家剩余牌数: %d",cnt2));
            }
            if(curCnt<10){
            	System.out.println(String.format("您剩余牌数: %d",curCnt));
            }
            //当前玩家输出的一组牌
            LinkedList<Card> putCards = player.getPutCards();
            //从手里清除打出的牌
            player.getCards().removeAll(putCards);
            //清除上次出牌记录
            putCards.clear();
            //restart flag
            boolean exit_Flag = true;

            if(!"三号".equals(player.getUsername())){
            	LinkedList<Card> robotPlay = new LinkedList<Card>();

            	//TODO:Robot play cards
            	if(bef>0){
            		robotPlay = getResultByRobotPolicy(player.before.getPutCards(), player.getCards(),POLICY_IN_USE);
            	}else{
            		if(bef2>0){
            			robotPlay = getResultByRobotPolicy(player.before.before.getPutCards(), player.getCards(),POLICY_IN_USE);
            		}else{
            			//play active role
            			robotPlay = getResultByRobotPolicy(null, player.getCards(),POLICY_IN_USE);
            		}
            	}

            	//show robot play
            	if(!robotPlay.isEmpty()){
                	//Debug:
//                	logger.info(String.format("robotPlay size = %d",robotPlay.size()));
                	for(Card card : robotPlay){
                		putCards.add(card);
                	}
            	}
            	if(!putCards.isEmpty()){
            		System.out.println(String.format("Robot %s play:\n%s",player.getUsername(),showCards(putCards)));
            	}else{
            		System.out.println(String.format("Robot %s:%s",player.getUsername(),"不出牌"));
            	}

            }else{
                System.out.println(player.getUsername() + "玩家请出牌");
                System.out.println(String.format("您当前持有的卡牌：%d张牌  \n%s",player.getCards().size(), showCards(player.getCards())));
                //控制台输入输出的每张牌
                String line;
                while (!(line = sc.nextLine()).equalsIgnoreCase("end")) {
                	logger.info(String.format("DEBUG:110 line = %s",line));
                	//check wheather restart Game.
                	if(line.equalsIgnoreCase("restart")){
                		exit_Flag = false;
                		exit = false;
                		break;
                	}
                	//Check before game
                	if("ShowMeThePast".equalsIgnoreCase(line)
                			|| "ShowMeTheFuture".equalsIgnoreCase(line)
                			|| "ShowMeNext".equalsIgnoreCase(line)
                			|| "ShowMeDoubleNext".equalsIgnoreCase(line)
             				|| "ShowHandCardValue".equalsIgnoreCase(line)){
                		checkBeforeGame(line,player);
                		continue;
                	}
                	//Check exit
                	if("exit".equalsIgnoreCase(line)){
                		exit = false;
                		break;
                	}
                	//game start:
                	String[] strList = line.split("[,]");
                	if (strList.length < 0) {
                		logger.info(String.format("DEBUG:128 line, strList.length = %d",strList.length));
                        System.out.println("129 line,请按规则输入");
                        continue;
                    }
                	for(String str : strList){
                        String[] split = str.split("[:|：]");
                        if (split.length != 2) {
                        	logger.info(String.format("DEBUG:135 line, strList.length = %d",strList.length));
                            System.out.println("136 line,请按规则输入");
                            continue;
                        }
                        //Debug:
//                        logger.info(String.format("split[0] = %s",split[0].trim()));
                        //Debug:
//                        logger.info(String.format("split[1] = %s",split[1].trim()));
                        Card card = new Card(split[0].trim(), split[1].trim());
                        putCards.add(card);
                	}
                }

                if(putCards.isEmpty()){
                	System.out.println(String.format("玩家 %s:%s",player.getUsername(),"不出牌"));
                }

                //Debug:
            }

        	//Debug:
//        	logger.info(String.format("compliance = %d",play.compliance(player)));
//        	if(play.compareSize(player)){
//        		//Debug:
//        		logger.info("compareSize is true");
//        	}else{
//        		//Debug:
//        		logger.info("compareSize is false");
//        	}

            //如果当前玩家出牌符合规定，则下一位玩家出牌，否则重新出牌
            if (play.compliance(player) > -1 && play.compareSize(player)) {
                player.setFirst(false);
                player.getNext().setFirst(true);
            } else {
                putCards.clear();
                System.out.println("请选择正确的牌组重新出牌");
            }

            //check winner
            if(checkWinner(player)){
            	System.out.println(String.format("%s：获胜！",player.getUsername()));
            	break;
            }

            //check exit again
            if(exit_Flag){
            	exit = checkExit(play);
            }

            //Add round
            round++;

        }

//        sc.close();
    }


	/**
	 * Check input before game
	 * @param line
	 * @param player
	 */
	private static String checkBeforeGame(String line, Player player){
    	//ShowMeThePast
    	if(line.equalsIgnoreCase("ShowMeThePast")){
    		showMeThePast(player);
    	}
    	//ShowMeTheFuture
    	if(line.equalsIgnoreCase("ShowMeTheFuture")){
    		showMeTheFuture(player);
    	}
    	//ShowMeNext
    	if(line.equalsIgnoreCase("ShowMeNext")){
    		showMeNext(player);
    	}
    	//ShowMeDoubleNext
    	if(line.equalsIgnoreCase("ShowMeDoubleNext")){
    		showMeDoubleNext(player);
    	}
    	//show hand card value
    	if(line.equalsIgnoreCase("ShowHandCardValue")){
    		showHandCardValue(player.getCards());
    	}
    	return "";
	}

    /**
     * Check exit
     * @param play
     * @return
     */
    private static boolean checkExit(PlayCards play){
    	return play.players[0].getCards().size() > 0 && play.players[1].getCards().size() > 0 && play
                .players[2].getCards().size() > 0;
    }


    /**
     * Check winner
     * @param play
     * @return
     */
    private static boolean checkWinner(Player player){
      return player.getCards().size() == 0;
    }

    /**
     * Print cards in orders.
     * @param list
     */
    public static String showCards(LinkedList<Card> list){
    	List<String> cardlist = new ArrayList<String>();
    	for(String str : PlayCards.cardVlues){
    		StringBuilder sb = new StringBuilder();
    		for(Card card : list){
    			if(str.equals(card.getValue())){
    				sb.append(String.format("%s:%s,",card.getColor(),card.getValue()));
    			}
    		}
    		if(!sb.toString().isEmpty()){
    			cardlist.add(sb.toString());
    		}
    	}
    	StringBuilder sb = new StringBuilder();
    	for(String str:cardlist){
    		sb.append(str + "\n");
    	}
    	return sb.toString();
    }

    /**
     * Print spent cards
     * @param spent
     * @return
     */
    private static String showSpentCards(Map<String,Card> spent){
    	LinkedList<Card> list = new LinkedList<Card>();
    	for(Card card:spent.values()){
    		list.add(card);
    	}
    	return showCards(list);
    }

    /**
     * ShowMeThePast
     * @param player
     */
    private static void showMeThePast(Player player){
//        spent.addAll(player.getPutCards());
//        spent.addAll(player.before.getPutCards());
//        spent.addAll(player.before.before.getPutCards());
//        spent.sort(Card::compareTo);
        for(Card card : spent){
        	String key = String.format("%s%s",card.getColor(),card.getValue());
        	spent_bef.putIfAbsent(key, card);
        }
        System.out.println(String.format("已出：%d张牌 \n%s",spent_bef.keySet().size(),showSpentCards(spent_bef)));
    }

    /**
     * ShowMeTheFuture
     * @param player
     */
    private static void showMeTheFuture(Player player){
//        spent.addAll(player.getPutCards());
//        spent.addAll(player.before.getPutCards());
//        spent.addAll(player.before.before.getPutCards());
//        spent.sort(Card::compareTo);
        total.removeAll(spent);
        System.out.println(String.format("还剩：%d张牌 \n%s",total.size(),showCards(total)));
    }

    /**
     * ShowMeNext
     * @param player
     */
    private static void showMeNext(Player player){
    	System.out.println(String.format("Next player持有的卡牌：%d张牌  \n%s",player.next.getCards().size(), showCards(player.next.getCards())));
    }

    /**
     * ShowMeDoubleNext
     * @param player
     */
    private static void showMeDoubleNext(Player player){
    	System.out.println(String.format("Next next player持有的卡牌：%d张牌  \n%s",player.next.next.getCards().size(),showCards(player.next.next.getCards())));
    }

    /**
     * Show hand card value
     * @param list
     */
    private static void showHandCardValue(LinkedList<Card> list){
    	 HandCardData.getHandCardValue(list);
    	 System.out.println(String.format("Total Hand card value = %d",HandCardData.getHandCardValue(list)));
    }

    /**
     * Get result list due to robot policy
     * @param putCards
     * @param getCards
     * @param robotPolicy
     * @return
     */
    private static LinkedList<Card> getResultByRobotPolicy(LinkedList<Card> putCards, LinkedList<Card> getCards, String robotPolicy){
    	if(ROBOT_POLICY_MIN.equalsIgnoreCase(robotPolicy)){
    		return RobotUtil.getMinBigCardList(putCards, getCards);
    	}else if(ROBOT_POLICY_MAX.equalsIgnoreCase(robotPolicy)){
    		return RobotUtil.getMaxBigCardList(putCards, getCards);
    	}
    	return null;
    }

}
