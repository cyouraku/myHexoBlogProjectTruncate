package com.lordbattle.robot;

import java.util.LinkedList;
import java.util.logging.Logger;

import com.lordbattle.ai.CardGroupType;
import com.lordbattle.util.Card;
import com.lordbattle.util.PlayCards;

public class RobotUtil {

	private static final Logger logger = Logger.getLogger(RobotUtil.class.getName());

	/**
	 * Check input type by last player
	 * @param card_list
	 * @return CardGroupType
	 */
	public static CardGroupType checkCardType(LinkedList<Card> card_list) {
		int type = card_list.size();
		CardGroupType result = CardGroupType.cgERROR;
		switch (type) {
		case 0:
			result = CardGroupType.cgZERO;
			break;
		case 1:
			result = CardGroupType.cgSINGLE;
			break;
		case 2:
			if(PlayCards.checkRocket(card_list)){
				result = CardGroupType.cgKING_CARD;
			}else{
				result = CardGroupType.cgDOUBLE;
			}
			break;
		case 3:
			result = CardGroupType.cgTHREE;
			break;
		case 4:
			if(PlayCards.checkBomb(card_list)){
				result = CardGroupType.cgBOMB_CARD;
			}else{
				result = CardGroupType.cgTHREE_TAKE_ONE;
			}
			break;
		}

		return result;
	}



	/**
	 * Get minimum big card
	 * @param putcard_list
	 * @param handcard_list
	 * @return
	 */
	public static LinkedList<Card> getMinBigCardList(LinkedList<Card> putcard_list,LinkedList<Card> handcard_list){
		CardGroupType type = CardGroupType.cgERROR;
		if(putcard_list == null){
			type =  CardGroupType.cgZERO;
		}else{
			type = checkCardType(putcard_list);
		}
		LinkedList<Card> result = new LinkedList<Card>();
		//debug:
//		logger.info(String.format("type = %d",type.getNumber()));
		switch (type.getNumber()) {
		case 0:
			// Actic role poliy,sort card asc
			handcard_list = PlayCards.sortCardList(handcard_list);
			if (!handcard_list.isEmpty()) {
				result.add(handcard_list.getFirst());
			}
			break;
		case 1:
			//sort card asc
			handcard_list = PlayCards.sortCardList(handcard_list);
			for (Card card : handcard_list) {
				if (PlayCards.findIndex(card) > PlayCards
						.findIndex(putcard_list.getFirst())) {
					result.add(card);
					return result;
				}
			}
			break;
		case 2:
			//find rocket

			//find bomb

			//find min big double

			break;
		case 3:
			break;
		}
		return result;
	}

	/**
	 * Get maximum big card
	 * @param putcard_list
	 * @param handcard_list
	 * @return
	 */
	public static LinkedList<Card> getMaxBigCardList(LinkedList<Card> putcard_list,LinkedList<Card> handcard_list){
		CardGroupType type = CardGroupType.cgERROR;
		if(putcard_list == null){
			type =  CardGroupType.cgZERO;
		}else{
			type = checkCardType(putcard_list);
		}
		LinkedList<Card> result = new LinkedList<Card>();
		//debug:
//		logger.info(String.format("type = %d",type.getNumber()));
		switch (type.getNumber()){
		case 0:
			//Actic role poliy,sort card asc
			handcard_list = PlayCards.sortCardList(handcard_list);
			if(!handcard_list.isEmpty()){
				result.add(handcard_list.getFirst());
			}
			break;
		case 1:
			//sort card desc
			handcard_list = PlayCards.sortCardListDesc(handcard_list);
			if(PlayCards.findIndex(handcard_list.getFirst()) > PlayCards.findIndex(putcard_list.getFirst())){
				result.add(handcard_list.getFirst());
				return result;
			}
			break;
		}
		return result;
	}
}
