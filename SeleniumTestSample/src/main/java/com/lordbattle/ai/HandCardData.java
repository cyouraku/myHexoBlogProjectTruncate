package com.lordbattle.ai;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Vector;

import com.lordbattle.util.Card;
import com.lordbattle.util.PlayCards;

/**
 * 手牌数据类
 * @author HD800241
 *
 */
public class HandCardData {

	//最多手牌
	public static final int HandCardMaxLen = 20;
	//价值最小值
	public static final int  MinCardsValue = -25;
	//价值最大值
	public static final int  MaxCardsValue = 106;

	// 手牌序列——无花色，值域3~17,length = 15
	public static Vector<Integer> value_nHandCardList = getValuenHandCardList();
	// 手牌序列——状态记录，便于一些计算，值域为该index牌对应的数量0~4,length = 18
	public int[] value_aHandCardList = {0};
	// 手牌序列——有花色，按照从大到小的排列 56，52：大王小王……4~0：红3黑3方3花3
	public Vector<Integer> color_nHandCardList = new Vector<Integer>();
	// 手牌个数
	public int nHandCardCount = 17;
	// 玩家角色地位 0：地主 1：农民——地主下家 2：农民——地主上家
	public int nGameRole = -1;
	// 玩家座位ID
	public int nOwnIndex = -1;
	// 玩家要打出去的牌类型
//	public CardGroupData uctPutCardType = new CardGroupData(CardGroupType.cgERROR, 0, 0);
	public CardGroupData uctPutCardType = new CardGroupData();
	// 要打出去的牌——无花色
	public Vector<Integer> value_nPutCardList = new Vector<Integer>();
	// 要打出去的牌——有花色
	public Vector<Integer> color_nPutCardList = new Vector<Integer>();
	// 手牌权值
	public HandCardValue uctHandCardValue = new HandCardValue();

	/**
	 * コンストラクタ
	 */
	public HandCardData(){}

	/**
	 * initial value_nHandcardList
	 * @return
	 */
	public static Vector<Integer> getValuenHandCardList(){
		Vector<Integer> value = new Vector<Integer>(15);
		for(int i=3;i<18;i++){
			value.addElement(i);
		}
		return value;
	}


	/**
	 * Get hand card value from LinkedList<Card>
	 * @param cardlist
	 * @return
	 */
	public static int getHandCardValue(LinkedList<Card> cardlist){
		int value = 0;
		for(Card card : cardlist){
			int idx = PlayCards.findIndex(card);
			value+=value_nHandCardList.get(idx);
		}
		return value;
	}

	//要打出的牌序列清空
	public void ClearPutCardList(){
		color_nPutCardList.clear();
		value_nPutCardList.clear();
		if(uctPutCardType != null){
			uctPutCardType.setCgType(CardGroupType.cgERROR);
			uctPutCardType.nCount = 0;
			uctPutCardType.nMaxCard = -1;
			uctPutCardType.nValue = 0;
		}
	};

	/*降序排序对比*/
//	int cmp(int a, int b) { return a > b ? 1 : 0; };

		//手牌排序，大牌靠前
	public void  SortAsList(Vector<Integer> arr){
//		Arrays.stream(arr.toArray()).sorted().toArray();
		Integer[] tmp = new Integer[arr.size()];
		while(arr.iterator().hasNext()){
			for(int i=0;i<arr.size();i++){
				tmp[i]=arr.iterator().next();
			}
		}
		Arrays.sort(arr.toArray(tmp),Comparator.reverseOrder());
	};

		//出一张牌，返回操作是否合法
	public boolean PutOneCard(int value_nCard, int color_nCard){
		boolean ret = false;
		//value状态数组处理
		value_aHandCardList[value_nCard]--;
		if (value_aHandCardList[value_nCard] < 0){
			return false;
		}
		//value列表数组处理
		while (value_nHandCardList.iterator().hasNext())
		{
			if (value_nHandCardList.iterator().next() == value_nCard)
			{
				value_nHandCardList.removeElement(value_nHandCardList.iterator().next());
				ret = true;
				break;
			}
		}
		// color列表数组处理
		int k = (value_nCard - 3) * 4;      //数值转换
		while (color_nHandCardList.iterator().hasNext()){
			for (int i = k; i < k + 4; i++)
			{
				if (color_nHandCardList.iterator().next() == i)
				{
					color_nCard = i;
					color_nHandCardList.removeElement(color_nHandCardList.iterator().next());
					return ret;
				}
			}
		}
		return false;
	};

		//出一组牌，返回操作是否合法
	public boolean PutCards(){
		while(value_nPutCardList.iterator().hasNext()){
			int color_nCard = -1;
			if (PutOneCard(value_nPutCardList.iterator().next(), color_nCard)){
				//same as push_back,add element to the end of vector
				color_nPutCardList.addElement(color_nCard);
			}else{
				return false;
			}
		}
		nHandCardCount -= value_nPutCardList.size();
		return true;
	};

		//通过有花色手牌获取无花色手牌（包含两种结构）
	public void get_valueHandCardList(){
		//清零
		value_nHandCardList.clear();
		while(color_nHandCardList.listIterator().hasNext()){
			int temp = color_nHandCardList.listIterator().next();
			temp = temp/4+3;
			//add element to the end of vector
			color_nHandCardList.addElement(temp);
			value_aHandCardList[temp]++;
		}
	};

		//初始化
	public void Init(){
		//根据花色手牌获取权值手牌
		get_valueHandCardList();
		//手牌 排序
		SortAsList(color_nHandCardList);
		SortAsList(value_nHandCardList);
		//当前手牌个数
		nHandCardCount = value_nHandCardList.size();
	};

		//输出所有成员变量，用于测试
	public void PrintAll(){};

}
