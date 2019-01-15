package com.lordbattle.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.lordbattle.util.Card;
import com.lordbattle.util.PlayCards;
import com.lordbattle.util.Player;

public class RobotTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// 定义玩家
		Player p1 = new Player("一号");
		Player p2 = new Player("二号");
		Player p3 = new Player("三号");
		p1.setNext(p2);
		p1.setBefore(p3);
		p2.setNext(p3);
		p2.setBefore(p1);
		p3.setNext(p1);
		p3.setBefore(p2);
		Player[] players = { p1, p2, p3 };
		// 开局
		PlayCards play = new PlayCards(players);
		// 洗牌
		play.shuffle(null);
		// 分牌
		play.distributional();
		//Get first player
//		Player player = Arrays.stream(play.players).filter(Player::isFirst).findFirst().get();
//		Player player = players[0];
		for(Player player: players){
			System.out.println(String.format("This is player %s",player.getUsername()));
			//Show cards before sort
			System.out.println(String.format("Before sort:\n%s",showCards(player.getCards())));
			//Show cards after sort
			System.out.println(String.format("After sort:\n%s", showCards(PlayCards.sortCardListDesc(player.getCards()))));
		}

	}

    public static String showCards(LinkedList<Card> list){
    	List<String> cardlist = new ArrayList<String>();
    	for(Card card : list){
    		StringBuilder sb = new StringBuilder();
    		sb.append(String.format("%s:%s,",card.getColor(),card.getValue()));
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

}
