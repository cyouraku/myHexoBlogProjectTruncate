package com.lordbattle.ai;

/**
 * 游戏全局类
 * @author HD800241
 *
 */
public class GameSituation {

		//地主玩家
		int nDiZhuID = -1;
		//本局叫分
		int nLandScore = 0;
		//当前地主玩家——还未确定
		int nNowDiZhuID = -1;
		//当前本局叫分——还未确定
		int nNowLandScore = 0;
		//三张底牌,length = 3
		int[] DiPai = {0};
		//已经打出的牌——状态记录，便于一些计算，值域为该index牌对应的数量0~4,length = 18
		int[] value_aAllOutCardList = { 0 };
		//三名玩家已经打出的手牌记录,x.length = 3,y.length = 18
		int[][] value_aUnitOutCardList = { {0} };
		//三名玩家已经剩余手牌个数,length = 3
		int[] value_aUnitHandCardCount = { 0 };
		//本局当前底分倍数
		int nMultiple = 0;
		//当前控手对象（用于区分是否可以自身任意出牌以及是否地主已经放弃出牌从而不去管队友）
		int nCardDroit = 0;
		//当前打出牌的类型数据，被动出牌时玩家根据这里做出筛选
		CardGroupData uctNowCardGroup;
		//本局游戏是否结束
		boolean Over = false;


	/**
	 *  コンストラクタ
	 */
	public GameSituation(){}
}
