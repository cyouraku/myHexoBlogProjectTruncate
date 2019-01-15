package com.lordbattle.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;


/**
 * 玩扑克牌
 *
 * @author sunyiran
 * @date 2018-10-19
 */
public class PlayCards {

	private static final Logger logger = Logger.getLogger(PlayCards.class.getName());
    /**
     * 扑克牌的值
     */
	public static final String[] cardVlues = {"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2", "小王",
            "大王"};

    /**
     * 扑克牌的花色
     */
    public static final String[] cardColors = {"梅花", "红桃", "黑桃", "方形"};

    /**
     * 一副扑克牌
     */
    public ArrayList<Card> cardList = new ArrayList<>(54);

    /**
     * 参加玩家
     */
    public Player[] players;

    public PlayCards() {
    }

    public PlayCards(Player[] players) {
        getCardArr();
        this.players = players;
    }

    /**
     * 获取一副扑克牌的算法
     */
    public void getCardArr() {
        //前52张牌具有花色
        for (int i = 0; i < 52; i++) {
            Card card = new Card();
            card.setColor(cardColors[i % 4]);
            card.setValue(cardVlues[i % 13]);
            cardList.add(card);
        }

        cardList.add(new Card("黑白", "小王"));
        cardList.add(new Card("彩色", "大王"));
    }


    /**
     * 获取扑克牌大小位置
     *
     * @param card
     * @return
     */
    public static int findIndex(Card card) {
        for (int i = 0; i < cardVlues.length; i++) {
            if (card.value.equalsIgnoreCase(cardVlues[i])) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Sort cards asc
     * @param cardList
     * @return
     */
    public static LinkedList<Card> sortCardList(LinkedList<Card> cardList){
    	//Sort asc classic way:
//    	int idx = cardList.size();
//    	for(int i=0;i<idx;i++){
//    		for(int j=0;j<idx-1;j++){
//    			Card now = cardList.get(j);
//    			Card next = cardList.get(j+1);
//    			if(now.compareTo(next) > 0){
//    				cardList.set(j, next);
//    				cardList.set(j+1,now);
//    			}
//    		}
//
//    	}
    	//Sort asc easy way:
    	cardList.sort(Card::compareTo);
    	return cardList;
    }

    /**
     * Sort cards desc
     * @param cardList
     * @return
     */
    public static LinkedList<Card> sortCardListDesc(LinkedList<Card> cardList){
    	//Sort desc classic way:
//    	int idx = cardList.size();
//    	for(int i=0;i<idx;i++){
//    		for(int j=0;j<idx-1;j++){
//    			Card now = cardList.get(j);
//    			Card next = cardList.get(j+1);
//    			if(now.compareTo(next) < 0){
//    				cardList.set(j, next);
//    				cardList.set(j+1,now);
//    			}
//    		}
//
//    	}
    	//Sort desc easy way:
    	Collections.sort(cardList, new Comparator<Card>() {
    	    @Override
    	    public int compare(Card o1, Card o2) {
    	        return PlayCards.findIndex(o2) - PlayCards.findIndex(o1);
    	    }
    	});
    	return cardList;
    }

    public static LinkedList<Card> getCardListTotal(){
    	LinkedList<Card> cardList = new LinkedList<Card>();
        //前52张牌具有花色
        for (int i = 0; i < 52; i++) {
            Card card = new Card();
            card.setColor(cardColors[i % 4]);
            card.setValue(cardVlues[i % 13]);
            cardList.add(card);
        }
        cardList.add(new Card("黑白", "小王"));
        cardList.add(new Card("彩色", "大王"));
    	return cardList;
    }


    /**
     * 洗牌
     *
     * @param player 上轮赢家
     */
    public List<Card> shuffle(Player player) {
        //清除玩家手里的牌
        Arrays.stream(players).forEach(x -> {
            LinkedList<Card> cards = x.cards;
            cards.clear();
        });
        //赢家取消优先级
        if (player != null)
            player.setFirst(false);
        //采用随机策略洗牌
        Random random = new Random();
        for (int i = 0; i < cardList.size(); i++) {
            Card temp = cardList.get(i);
            //要交换的索引
            int changeIndex = random.nextInt(54);
            cardList.set(i, cardList.get(changeIndex));
            cardList.set(changeIndex, temp);
        }
        return cardList;
    }

    /**
     * 分牌
     */
    public void distributional() {
    	//reset fist
    	for(Player p:players){
    		p.setFirst(false);
    	}
        //留下三张给地主
        for (int i = 0; i < 51; i++) {
            if (i < 17) {
                players[0].getCards().add(cardList.get(i));

            }
            if (i >= 17 && i < 34) {
                players[1].getCards().add(cardList.get(i));
            }
            if (i >= 34) {
                players[2].getCards().add(cardList.get(i));
            }
        }
        //随机确定地主
//        players[new Random().nextInt(3)].setFirst(true);
        //Set player[3] as land lord player
        players[2].setFirst(true);
        //地主拿三张
        Arrays.stream(players).filter(Player::isFirst).forEach(x -> {
            LinkedList<Card> cards = x.getCards();
            cards.add(cardList.get(51));
            cards.add(cardList.get(52));
            cards.add(cardList.get(53));
            //Show 3 cards:
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%s:%s,",cardList.get(51).getColor(),cardList.get(51).getValue()));
            sb.append(String.format("%s:%s,",cardList.get(52).getColor(),cardList.get(52).getValue()));
            sb.append(String.format("%s:%s,",cardList.get(53).getColor(),cardList.get(53).getValue()));
            System.out.println(String.format("地主拿三张：%s\n当前地主：%s", sb.toString(), x.getUsername()));
        });
        //按顺序排序
        players[0].getCards().sort(Card::compareTo);
        players[1].getCards().sort(Card::compareTo);
        players[2].getCards().sort(Card::compareTo);

    }

    /**
     * 判断出牌是否合法(根据出牌规则)
     *
     * @param player
     * @return 个位代表出牌个数，个位相同时，值高低也代表大小级别(可以用Map优化)
     */
    public int compliance(Player player) {

        LinkedList<Card> cards = player.getPutCards();
        LinkedList<Card> hasCards = player.getCards();
        //可以不出牌
        if (cards.size() == 0) {
            return 0;
        }

        //每张牌必须是自己手里的牌
        long count = cards.stream().filter(x -> !hasCards.contains(x)).count();
        if (count > 0) {
        	logger.info("Error on hasCards");
            return -1;
        }

        //(1) 单张：前面提到过，大小顺序从3(最小)到大怪(最大)；
        if (cards.size() == 1) {
            return 1 + (findIndex(cards.get(0)) + 1) * 10;
        }

        //(2) 一对：两张大小相同的牌，从3(最小)到2(最大)；
        if (cards.size() == 2 && cards.get(0).value.equals(cards.get(1).value)) {
            return 2 + (findIndex(cards.get(0)) + 1) * 100;
        }

        //(12) 火箭：一对怪，这是最大的组合，能够盖过包括炸弹在内的任何牌型；
        if(checkRocket(cards)){
        	return  20000002;
        }

        //(3) 三张：三张大小相同的牌；
        if (cards.size() == 3) {
            if(checkTriple(cards)){
            	return 3 + (findIndex(cards.get(0)) + 1) * 1000;
            }
        }

        //如果是四张，则为三带一，或者炸弹
        if (cards.size() == 4) {
            //判断是否为炸弹
            if (checkBomb(cards)) {
            	//(11) 炸弹：四张大小相同的牌，炸弹能盖过除火箭外的其他牌型，大的炸弹能盖过小的炸弹；
                return 4 + (findIndex(cards.get(0)) + 1) * 100000;
            } else {
            	 //(4) 三张带一张：三张并带上任意一张牌，例如6-6-6-8，根据三张的大小来比较，例如9-9-9-3盖过8-8-8-A；
            	int cnt0 = 0;
            	int cnt3 = 0;
            	for(Card card : cards){
                    if(findIndex(card) == findIndex(cards.get(0))){
                    	cnt0+=1;
                    }
                    if(findIndex(card) == findIndex(cards.get(3))){
                    	cnt3+=1;
                    }
            	}
            	if(cnt0==3){
            		//第一张牌不为单张
            		return 4 + (findIndex(cards.get(0)) + 1) * 1000;
            	}else if(cnt3==3){
            		//第一张牌为单张
            		return 4 + (findIndex(cards.get(3)) + 1) * 1000;
            	}
            }
        }



        //(5) 三张带一对：三张并带上一对，类似扑克中的副路(Full House)，根据三张的大小来比较，例如Q-Q-Q-6-6盖过10-10-10-K-K；

        //(6) 顺子：至少5张连续大小(从3到A，2和怪不能用)的牌，例如8-9-10-J-Q；
        if (cards.size() >= 5 && checkSingleJoint(cards)) {
            //check 2,little joker,big joker
            if(findIndex(cards.getLast())>=12){
            	logger.info("Error on patter 06 check 2 & joker");
            	return -1;
            }
            return cards.size() + (findIndex(cards.get(0)) + 1) * 1000;
        }

        //(7) 连对,至少3个连续大小(从3到A，2和怪不能用)的对子，例如10-10-J-J-Q-Q-K-K；p:6,8,10,12,14,16,18,20
        if((cards.size() %2 == 0 && cards.size() >=6 && cards.size() <= 20) &&  checkDoubleJoint(cards)){
            //check 2,little joker,big joker
            if(findIndex(cards.getLast())>=12){
            	logger.info("Error on patter 07 check 2 & joker");
            	return -1;
            }
            return cards.size() + (findIndex(cards.get(0)) + 1) * 1000;
        }

        //(8) 三张的顺子,至少2个连续大小(从3到A)的三张，例如4-4-4-5-5-5；p 6,9,12,15,18
        if((cards.size() %3 == 0 && cards.size() >=6 && cards.size() <= 18) && checkThreeJoint(cards)){
            //check 2,little joker,big joker
            if(findIndex(cards.getLast())>=12){
            	logger.info("Error on patter 08 check 2 & joker");
            	return -1;
            }
            return cards.size() + (findIndex(cards.get(0)) + 1) * 1000;
        }

        //(9) 三张带一张的顺子,每个三张都带上额外的一个单张，例如7-7-7-8-8-8-3-6，尽管三张2不能用，但能够带上单张2和怪；
        if((cards.size() %4 == 0 && cards.size() >=8 && cards.size() <= 20)  && (findIndex(cards.get(3)) == findIndex(cards.get(0)) + 1)){
        	//check three joint
        	//pattern check:6+2
        	if(cards.size() == 8){
                for (int i = 3; i < 6; i++) {
                    if (findIndex(cards.get(i)) != findIndex(cards.get(i - 3)) + 1) {
                    	logger.info("Error on patter 09-8");
                        return -1;
                    }
                }
        	}
        	//pattern check:9+3
        	if(cards.size() == 12){
                for (int i = 3; i < 9; i++) {
                    if (findIndex(cards.get(i)) != findIndex(cards.get(i - 3)) + 1) {
                    	logger.info("Error on patter 09-12");
                        return -1;
                    }
                }
        	}
        	//pattern check:12+4
        	if(cards.size() == 16){
                for (int i = 3; i < 12; i++) {
                    if (findIndex(cards.get(i)) != findIndex(cards.get(i - 3)) + 1) {
                    	logger.info("Error on patter 09-16");
                        return -1;
                    }
                }
        	}
        	//pattern check:15+5(max)
        	if(cards.size() == 20){
                for (int i = 3; i < 15; i++) {
                    if (findIndex(cards.get(i)) != findIndex(cards.get(i - 3)) + 1) {
                    	logger.info("Error on patter 09-20");
                        return -1;
                    }
                }
        	}
            //check 2-2-2 invalid
        	if(!checkThripleTwo(cards)){
        		logger.info("Error on patter 09-222");
        		return -1;
        	}
            return cards.size() + (findIndex(cards.get(0)) + 1) * 1000;
        }

        //(10) 三张带一对的顺子,每个三张都带上额外的一对，只需要三张是连续的就行，例如8-8-8-9-9-9-4-4-J-J，尽管三张2不能用，但能够带上一对2【不能带一对怪，因为两张怪的大小不一样】
        //p:10,15,20
        if((cards.size() %5 == 0 && cards.size() >=10 && cards.size() <= 20)  && (findIndex(cards.get(3)) == findIndex(cards.get(0)) + 1)){
        	//check three joint
        	//pattern check:6+4
        	if(cards.size() == 10){
                for (int i = 3; i < 6; i++) {
                    if (findIndex(cards.get(i)) != findIndex(cards.get(i - 3)) + 1) {
                    	logger.info("Error on patter 10-8");
                        return -1;
                    }
                }
        	}
        	//pattern check:9+6
        	if(cards.size() == 10){
                for (int i = 3; i < 9; i++) {
                    if (findIndex(cards.get(i)) != findIndex(cards.get(i - 3)) + 1) {
                    	logger.info("Error on patter 10-10");
                        return -1;
                    }
                }
        	}
        	//pattern check:15+5(max)
        	if(cards.size() == 15){
                for (int i = 3; i < 15; i++) {
                    if (findIndex(cards.get(i)) != findIndex(cards.get(i - 3)) + 1) {
                    	logger.info("Error on patter 10-10");
                        return -1;
                    }
                }
        	}
            //check 2-2-2 invalid
        	if(!checkThripleTwo(cards)){
        		logger.info("Error on patter 10-222");
        		return -1;
        	}
        	return cards.size() + (findIndex(cards.get(0)) + 1) * 1000;
        }

        //(13) 四张套路(四带二),有两种牌型，一个四张带上两个单张，例如6-6-6-6-8-9，或一个四张带上两对，例如J-J-J-J-9-9-Q-Q，四带二只根据四张的大小来比较，只能盖过比自己小的同样牌型的四带二【四张带二张和四张带二对属于不同的牌型，不能彼此盖过】，不能盖过其他牌型，四带二能被比自己小的炸弹盖过。
        //一个四张带上两个单张
        if(cards.size() == 6){
            boolean fourJoint = cards.get(0).value.equals(cards.get(1).value) && cards.get(1).value.equals(cards.get(2)
                    .value) && cards.get(2).value.equals(cards.get(3).value);
            if(fourJoint){
            	return 6 + (findIndex(cards.get(0)) + 1) * 100000;
            }
        }
        //一个四张带上两对
        if(cards.size() == 8){
            boolean fourJoint = cards.get(0).value.equals(cards.get(1).value) && cards.get(1).value.equals(cards.get(2)
                    .value) && cards.get(2).value.equals(cards.get(3).value);
            if(fourJoint){
            	return 8 + (findIndex(cards.get(0)) + 1) * 100000;
            }
        }



        logger.info("Error on no patter matched");
        return -1;

    }

    /**
     * 判断出牌是否合法(根据大小规则)
     *
     * @param player
     * @return
     */
    public boolean compareSize(Player player) {

        //出牌一定比上两家大
        //自己
        int self = compliance(player);
        String v1 = String.valueOf(self);
        //上方第一家
        int before1 = compliance(player.before);

        String v2 = String.valueOf(self);
        //上方第二家
        int before2 = compliance(player.before.before);
        String v3 = String.valueOf(self);

        if (before1 == 0 && before2 == 0 && self > 0) {
            return true;
        }
        if (self == 0) {
            return true;
        }

        int amout1 = Integer.parseInt(v1.substring(v1.length() - 2));
        int amout2 = Integer.parseInt(v2.substring(v2.length() - 2));
        int amout3 = Integer.parseInt(v3.substring(v3.length() - 2));

        //Debug:
//        logger.info(String.format("amount1 = %d; v1 = %s; v1.length = %d", amout1,v1,v1.length()));
//        logger.info(String.format("amount2 = %d; v2 = %s; v2.length = %d", amout2,v2,v2.length()));
//        logger.info(String.format("amount3 = %d; v3 = %s; v3.length = %d", amout3,v3,v3.length()));

        if (self == 20000002) {
            return true;
        }

        if (before1 > 0 && (amout1 == amout2 || self > 100003) && self > before1) {
            return true;
        }
        if (before1 == 0 && before2 > 0 && (amout1 == amout3 || self > 100003) && self > before2) {
            return true;
        }
        //bomb greater than 4plus2
        if(before1 > 0 && (amout2 == 6 || amout2 == 8) && amout1 == 4 && self >= before1){
        	return true;
        }

        System.out.println("您的出牌没能大于上家");
        return false;
    }


    /**
     * Check single joint pattern
     * @param cards
     * @return
     */
    public static boolean checkSingleJoint(LinkedList<Card> cards){
        //check joint
    	for (int i = 1; i < cards.size(); i++) {
            if (findIndex(cards.get(i)) != findIndex(cards.get(i - 1)) + 1) {
            	return false;
            }
        }
        return true;
    }

    /**
     * Check double joint pattern
     * @param cards
     * @return
     */
    public static boolean checkDoubleJoint(LinkedList<Card> cards){
    	//check three joint
        for (int i = 2; i < cards.size(); i++) {
            if (findIndex(cards.get(i)) != findIndex(cards.get(i - 2)) + 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check three joint pattern
     * @param cards
     * @return
     */
    public static boolean checkThreeJoint(LinkedList<Card> cards){
    	//check three joint
        for (int i = 3; i < cards.size(); i++) {
            if (findIndex(cards.get(i)) != findIndex(cards.get(i - 3)) + 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check tree joint two
     * @param cards
     * @return
     */
    public static boolean checkThripleTwo(LinkedList<Card> cards){
        //check 2-2-2 invalid
    	int cnt12 = 0;
    	for(Card card : cards){
            if(findIndex(card) == 12){
            	cnt12+=1;
            }
    	}
    	if(cnt12 >= 3){
    		return false;
    	}
    	return true;
    }

    /**
     * Check rocket
     * @param cards
     * @return
     */
    public static boolean checkRocket(LinkedList<Card> cards){
        if (cards.size() == 2 && cards.contains(new Card("彩色", "大王")) && cards.contains(new Card("黑白", "小王"))) {
            return true;
        }
        return false;
    }

    /**
     * Check triple cards
     * @param cards
     * @return
     */
    public static boolean checkTriple(LinkedList<Card> cards){
    	return cards.get(0).value.equals(cards.get(1).value) && cards.get(1).value.equals(cards.get(2)
                .value);
    }

    /**
     * Check bomb
     * @param cards
     * @return
     */
    public static boolean checkBomb(LinkedList<Card> cards){
        return cards.get(0).value.equals(cards.get(1).value) && cards.get(1).value.equals(cards.get(2)
    	    .value) && cards.get(2).value.equals(cards.get(3).value);
    }
}
