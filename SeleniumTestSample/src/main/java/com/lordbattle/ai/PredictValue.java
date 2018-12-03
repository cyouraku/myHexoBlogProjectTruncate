package com.lordbattle.ai;

import java.util.Vector;

public class PredictValue {


	public HandCardValue get_HandCardValue(HandCardData clsHandCardData)
	{
		//首先清空出牌队列，因为剪枝时是不调用get_PutCardList的
		clsHandCardData.ClearPutCardList();

		HandCardValue uctHandCardValue = new HandCardValue();
	    //出完牌了，其实这种情况只限于手中剩下四带二且被动出牌的情况，因为四带二剪枝做了特殊处理。
		if (clsHandCardData.nHandCardCount == 0)
		{
			uctHandCardValue.SumValue = 0;
			uctHandCardValue.NeedRound = 0;
			return uctHandCardValue;
		}
		//————以下为剪枝：判断是否可以一手出完牌
		CardGroupData uctCardGroupData = ins_SurCardsType(clsHandCardData.value_aHandCardList);
		//————不到万不得已我们都不会出四带二，都尽量保炸弹
		if (uctCardGroupData.cgType != CardGroupType.cgERROR && uctCardGroupData.cgType != CardGroupType.cgFOUR_TAKE_ONE && uctCardGroupData.cgType != CardGroupType.cgFOUR_TAKE_TWO)
		{
			uctHandCardValue.SumValue = uctCardGroupData.nValue;
			uctHandCardValue.NeedRound = 1;
			return uctHandCardValue;
		}

		//非剪枝操作，即非一手能出完的牌

		/*只是获取出牌的序列，即clsHandCardData.value_nPutCardList及clsHandCardData.uctPutCardType
		其他成员均无改变，也不会调用出牌函数，get_PutCardList返回最优方案*/
//		get_PutCardList_2(clsHandCardData);

		//要保存当前的clsHandCardData.value_nPutCardList及clsHandCardData.uctPutCardType用于回溯
		CardGroupData NowPutCardType = clsHandCardData.uctPutCardType;
		Vector<Integer> NowPutCardList = clsHandCardData.value_nPutCardList;

		if (clsHandCardData.uctPutCardType.cgType == CardGroupType.cgERROR)
		{
			//TODO:cout << "PutCardType ERROR!" << endl;
			System.out.println("PutCardType ERROR!");
		}

		//---回溯↓
		while(NowPutCardList.iterator().hasNext())
		{
			clsHandCardData.value_aHandCardList[NowPutCardList.iterator().next()]--;
		}
		clsHandCardData.nHandCardCount -= NowPutCardType.nCount;
		//---回溯↑
		HandCardValue tmp_SurValue = get_HandCardValue(clsHandCardData);//递归剩余牌价值

	    //---回溯↓
		while (NowPutCardList.iterator().hasNext())
		{
			clsHandCardData.value_aHandCardList[NowPutCardList.iterator().next()]++;
		}
		clsHandCardData.nHandCardCount += NowPutCardType.nCount;
		//---回溯↑

		uctHandCardValue.SumValue = NowPutCardType.nValue + tmp_SurValue.SumValue;
		uctHandCardValue.NeedRound = tmp_SurValue.NeedRound + 1;

		return uctHandCardValue;
	}

	/**
	 * 检查剩余的牌是否只是一手牌
	 * 是：  返回手牌类型数据
	 * 不是：返回错误类型（cgERROR）
	 * @param value_aHandCardList
	 * @return
	 */
	public CardGroupData ins_SurCardsType(int[] arr){
		CardGroupData retCardGroupData = new CardGroupData(CardGroupType.cgERROR,0,0,0);
		int nCount = 0;
		for (int i = 3; i < 18; i++)
		{
			nCount += arr[i];
		}
		retCardGroupData.setnCount(nCount);

		//单牌类型
		if (nCount == 1)
		{
			//用于验证的变量
			int prov = 0;
			int SumValue = 0;
			for (int i = 3; i < 18; i++)
			{
				if (arr[i] == 1)
				{
					SumValue = i - 10;
					prov++;
					retCardGroupData.nMaxCard = i;
				}
			}
			if (prov == 1)
			{
				retCardGroupData.cgType = CardGroupType.cgSINGLE;
				retCardGroupData.nValue= SumValue;
				return retCardGroupData;
			}
		}
		//对牌类型
		if (nCount == 2)
		{
			//用于验证的变量
			int prov = 0;
			int SumValue = 0;
			int i = 0;
			for (i = 3; i < 16; i++)
			{
				if (arr[i] == 2)
				{
					SumValue = i - 10;
					prov++;
					retCardGroupData.nMaxCard = i;
				}
			}
			if (prov == 1)
			{
				retCardGroupData.cgType = CardGroupType.cgDOUBLE;
				retCardGroupData.nValue = SumValue;
				return retCardGroupData;
			}
		}
		//三条类型
		if (nCount == 3)
		{
			//用于验证的变量
			int prov = 0;
			int SumValue = 0;
			int i = 0;
			for (i = 3; i < 16; i++)
			{
				if (arr[i] == 3)
				{
					SumValue = i - 10;
					prov++;
					retCardGroupData.nMaxCard = i;
				}
			}
			if (prov == 1)
			{
				retCardGroupData.cgType = CardGroupType.cgTHREE;
				retCardGroupData.nValue = SumValue;
				return retCardGroupData;
			}
		}
		//三带一单
		if (nCount == 4)
		{
			//用于验证的变量
			int prov1 = 0;
			int prov2 = 0;
			int SumValue = 0;
			for (int i = 3; i < 18; i++)
			{
				if (arr[i] == 3)
				{
					SumValue = i - 10;
					prov1++;
					retCardGroupData.nMaxCard = i;

				}
				if (arr[i] == 1)
				{
					prov2++;
				}

			}
			if (prov1 == 1 && prov2 == 1)
			{
				retCardGroupData.cgType = CardGroupType.cgTHREE_TAKE_ONE;
				retCardGroupData.nValue = SumValue;
				return retCardGroupData;
			}
		}
		//三带一对
		if (nCount == 5)
		{
			//用于验证的变量
			int prov1 = 0;
			int prov2 = 0;
			int SumValue = 0;
			for (int i = 3; i < 16; i++)
			{
				if (arr[i] == 3)
				{
					SumValue = i - 10;
					prov1++;
					retCardGroupData.nMaxCard = i;

				}
				if (arr[i] == 2)
				{
					prov2++;

				}
			}
			if (prov1 == 1 && prov2 == 1)
			{
				retCardGroupData.cgType = CardGroupType.cgTHREE_TAKE_TWO;
				retCardGroupData.nValue = SumValue;
				return retCardGroupData;
			}
		}
		//四带两单
		if (nCount == 6)
		{
			//用于验证的变量
			int prov1 = 0;
			int prov2 = 0;
			int SumValue = 0;
			for (int i = 3; i < 18; i++)
			{
				if (arr[i] == 4)
				{
					SumValue = (i - 3) / 2;
					prov1++;
					retCardGroupData.nMaxCard = i;

				}
				if (arr[i] == 1|| arr[i] == 2)
				{
					prov2+= arr[i];
				}
			}

			if (prov1 == 1 && prov2 == 2)
			{
				retCardGroupData.cgType = CardGroupType.cgFOUR_TAKE_ONE;
				retCardGroupData.nValue = SumValue;
				return retCardGroupData;
			}
		}
		//四带两对
		if (nCount == 8)
		{
			//用于验证的变量
			int prov1 = 0;
			int prov2 = 0;
			int SumValue = 0;
			for (int i = 3; i < 16; i++)
			{
				if (arr[i] == 4)
				{
					SumValue = (i - 3) / 2;

					prov1++;
					retCardGroupData.nMaxCard = i;
				}
				if (arr[i] == 2|| arr[i] == 4)
				{
					prov2+= arr[i]/2;

				}
			}
	               //注意这里prov2==4因为四牌也是两个对
			if (prov1 == 1 && prov2 == 4)
			{
				retCardGroupData.cgType = CardGroupType.cgFOUR_TAKE_TWO;
				retCardGroupData.nValue = SumValue;
				return retCardGroupData;
			}
		}
		//炸弹类型
		if (nCount == 4)
		{
			//用于验证的变量
			int prov = 0;
			int SumValue = 0;
			for (int i = 3; i < 16; i++)
			{
				if (arr[i] == 4)
				{
					SumValue += i - 3 + 7;
					prov++;
					retCardGroupData.nMaxCard = i;
				}
			}
			if (prov == 1)
			{
				retCardGroupData.cgType = CardGroupType.cgBOMB_CARD;
				retCardGroupData.nValue = SumValue;
				return retCardGroupData;
			}
		}
		//王炸类型
		if (nCount == 2)
		{
			int SumValue = 0;
			if (arr[17] > 0 && arr[16] > 0)
			{
				SumValue = 20;
				retCardGroupData.nMaxCard = 17;
				retCardGroupData.cgType = CardGroupType.cgKING_CARD;
				retCardGroupData.nValue = SumValue;
				return retCardGroupData;
			}
		}
		//单连类型
		if (nCount >= 5)
		{
			//用于验证的变量
			int prov = 0;
			int SumValue = 0;
			int i;
			for (i = 3; i < 15; i++)
			{
				if (arr[i] == 1)
				{
					prov++;
				}
				else
				{
					if (prov != 0)
					{
						break;
					}

				}
			}
			SumValue = i - 10;

			if (prov == nCount)
			{
				retCardGroupData.nMaxCard = i-1;
				retCardGroupData.cgType = CardGroupType.cgSINGLE_LINE;
				retCardGroupData.nValue = SumValue;
				return retCardGroupData;
			}
		}
		//对连类型
		if (nCount >= 6)
		{
			//用于验证的变量
			int prov = 0;
			int SumValue = 0;
			int i;
			for (i = 3; i < 15; i++)
			{
				if (arr[i] == 2)
				{
					prov++;
				}
				else
				{
					if (prov != 0)
					{
						break;
					}

				}
			}
			SumValue = i - 10;

			if (prov * 2 == nCount)
			{
				retCardGroupData.nMaxCard = i - 1;
				retCardGroupData.cgType = CardGroupType.cgDOUBLE_LINE;
				retCardGroupData.nValue = SumValue;
				return retCardGroupData;
			}
		}
		//三连类型
		if (nCount >= 6)
		{
			//用于验证的变量
			int prov = 0;

			int SumValue = 0;
			int i;
			for (i = 3; i < 15; i++)
			{
				if (arr[i] == 3)
				{
					prov++;
				}
				else
				{
					if (prov != 0)
					{
						break;
					}

				}
			}
			SumValue = (i - 3) / 2;

			if (prov * 3 == nCount)
			{
				retCardGroupData.nMaxCard = i - 1;
				retCardGroupData.cgType = CardGroupType.cgTHREE_LINE;
				retCardGroupData.nValue = SumValue;
				return retCardGroupData;
			}
		}
		//三带一连类型
		if (nCount >= 8)
		{
			//用于验证的变量
			int prov1 = 0;
			int SumValue = 0;
			int i, j;
			for (i = 3; i < 15; i++)
			{
				if (arr[i] >= 3)
				{
					prov1++;
				}
				else
				{
					if (prov1 != 0)
					{
						break;
					}

				}
			}
			SumValue = (i - 3)/2;
			if (prov1 * 4 == nCount)
			{
				retCardGroupData.nMaxCard = i - 1;
				retCardGroupData.cgType = CardGroupType.cgTHREE_TAKE_ONE_LINE;
				retCardGroupData.nValue = SumValue;
				return retCardGroupData;
			}

		}
		//三带二连类型
		if (nCount >= 10)
		{
			//用于验证的变量
			int prov1 = 0;
			int prov2 = 0;
			int SumValue = 0;
			int i, j;
			for (i = 3; i < 15; i++)
			{
				if (arr[i] == 3)
				{
					prov1++;
				}
				else
				{
					if (prov1 != 0)
					{
						break;
					}
				}
			}
			for (j = 3; j < 16; j++)
			{
				if (arr[j] == 2|| arr[j] == 4)
				{
					prov2+= arr[j]/2;
				}
			}
			SumValue = (i - 3) / 2;
			if (prov1 == prov2&&prov1 * 5 == nCount)
			{
				retCardGroupData.nMaxCard = i - 1;
				retCardGroupData.cgType = CardGroupType.cgTHREE_TAKE_TWO_LINE;
				retCardGroupData.nValue = SumValue;
				return retCardGroupData;
			}
		}
		retCardGroupData.cgType = CardGroupType.cgERROR;
		return retCardGroupData;
	}

	/**
	 * 检查剩余的牌是否只是一手牌（vector重载）
	 * 是：  返回手牌类型数据
	 * 不是：返回错误类型（cgERROR）
	 * @param list
	 * @return
	 */
	public CardGroupData ins_SurCardsType(Vector<Integer> list){
		int[] arr = new int[18];
//		memset(arr, 0, sizeof(arr));
		while (list.iterator().hasNext())
		{
			arr[list.iterator().next()]++;
		}
		return ins_SurCardsType(arr);
	}

	//TODO:
	public void get_PutCardList_2(HandCardData clsHandCardData){

	}

	/**
	 *
	 *2.0版本策略  根据场上形势决定当前预打出的手牌——被动出牌
	 * @param clsGameSituation
	 * @param clsHandCardData
	 */
	public void get_PutCardList_2_limited(GameSituation clsGameSituation, HandCardData clsHandCardData){
		clsHandCardData.ClearPutCardList();


		/*王炸——当前策略只处理王炸作为倒数第二手的优先出牌逻辑，后续版本会在此基础上优化*/
		if (clsHandCardData.value_aHandCardList[17] > 0 && clsHandCardData.value_aHandCardList[16] > 0)
		{

			clsHandCardData.value_aHandCardList[17] --;
			clsHandCardData.value_aHandCardList[16] --;
			clsHandCardData.nHandCardCount -= 2;
			HandCardValue tmpHandCardValue = get_HandCardValue(clsHandCardData);
			clsHandCardData.value_aHandCardList[16] ++;
			clsHandCardData.value_aHandCardList[17] ++;
			clsHandCardData.nHandCardCount += 2;
			if (tmpHandCardValue.NeedRound == 1)
			{
				clsHandCardData.value_nPutCardList.addElement(17);
				clsHandCardData.value_nPutCardList.addElement(16);
				clsHandCardData.uctPutCardType = new CardGroupData(CardGroupType.cgKING_CARD, 17, 2);
//				clsGameSituation.uctNowCardGroup = new CardGroupData(CardGroupType.cgKING_CARD, 17, 2);
				return;
			}
		}


		//错误牌型  不出
		if (clsGameSituation.uctNowCardGroup.cgType == CardGroupType.cgERROR)
		{
			clsHandCardData.uctPutCardType = new CardGroupData(CardGroupType.cgERROR, 0, 0);
			return;
		}
		//不出牌型，在被动出牌策略里也是错误数据 不出
		else if (clsGameSituation.uctNowCardGroup.cgType ==CardGroupType. cgZERO)
		{
			clsHandCardData.uctPutCardType = new CardGroupData(CardGroupType.cgZERO, 0, 0);
			return;
		}
		//单牌类型
		else if (clsGameSituation.uctNowCardGroup.cgType == CardGroupType.cgSINGLE)
		{
		    //管不上
		    clsHandCardData.uctPutCardType = new CardGroupData(CardGroupType.cgZERO, 0, 0);
			return;

		}
		//对牌类型
		else if (clsGameSituation.uctNowCardGroup.cgType == CardGroupType.cgDOUBLE)
		{
			//管不上
			clsHandCardData.uctPutCardType = new CardGroupData(CardGroupType.cgZERO, 0, 0);
			return;
		}
		//三牌类型
		else if (clsGameSituation.uctNowCardGroup.cgType == CardGroupType.cgTHREE)
		{

			//管不上
			clsHandCardData.uctPutCardType = new CardGroupData(CardGroupType.cgZERO, 0, 0);
			return;
		}
		//单连类型
		else if (clsGameSituation.uctNowCardGroup.cgType == CardGroupType.cgSINGLE_LINE)
		{
			//管不上
			clsHandCardData.uctPutCardType = new CardGroupData(CardGroupType.cgZERO, 0, 0);
			return;
		}
		//对连类型
		else if (clsGameSituation.uctNowCardGroup.cgType == CardGroupType.cgDOUBLE_LINE)
		{
			//管不上
			clsHandCardData.uctPutCardType = new CardGroupData(CardGroupType.cgZERO, 0, 0);
			return;

		}
		//三连类型
		else if (clsGameSituation.uctNowCardGroup.cgType == CardGroupType.cgTHREE_LINE)
		{
		//管不上
			clsHandCardData.uctPutCardType = new CardGroupData(CardGroupType.cgZERO, 0, 0);
			return;
		}
		//三带一单
		else if (clsGameSituation.uctNowCardGroup.cgType == CardGroupType.cgTHREE_TAKE_ONE)
		{
			//管不上
			clsHandCardData.uctPutCardType = new CardGroupData(CardGroupType.cgZERO, 0, 0);
			return;
		}
		//三带一对
		else if (clsGameSituation.uctNowCardGroup.cgType == CardGroupType.cgTHREE_TAKE_TWO)
		{
			//管不上
			clsHandCardData.uctPutCardType = new CardGroupData(CardGroupType.cgZERO, 0, 0);
			return;
		}
		//三带一单连
		else if (clsGameSituation.uctNowCardGroup.cgType == CardGroupType.cgTHREE_TAKE_ONE_LINE)
		{
			//管不上
			clsHandCardData.uctPutCardType = new CardGroupData(CardGroupType.cgZERO, 0, 0);
			return;
		}
		//三带一对连
		else if (clsGameSituation.uctNowCardGroup.cgType == CardGroupType.cgTHREE_TAKE_TWO_LINE)
		{
			//管不上
			clsHandCardData.uctPutCardType = new CardGroupData(CardGroupType.cgZERO, 0, 0);
			return;
		}
		//四带两单
		else if (clsGameSituation.uctNowCardGroup.cgType == CardGroupType.cgFOUR_TAKE_ONE)
		{
			//管不上
			clsHandCardData.uctPutCardType = new CardGroupData(CardGroupType.cgZERO, 0, 0);
			return;
		}
		//四带两对
		else if (clsGameSituation.uctNowCardGroup.cgType == CardGroupType.cgFOUR_TAKE_TWO)
		{
			//管不上
			clsHandCardData.uctPutCardType = new CardGroupData(CardGroupType.cgZERO, 0, 0);
			return;
		}
		//炸弹类型
		else if (clsGameSituation.uctNowCardGroup.cgType == CardGroupType.cgBOMB_CARD)
		{
			//管不上
			clsHandCardData.uctPutCardType = new CardGroupData(CardGroupType.cgZERO, 0, 0);
			return;
		}
		//王炸类型 人都王炸了你还出个毛
		else if (clsGameSituation.uctNowCardGroup.cgType == CardGroupType.cgKING_CARD)
		{
			clsHandCardData.uctPutCardType = new CardGroupData(CardGroupType.cgZERO, 0, 0);
			return;
		}
		//异常处理 不出
		else
		{
			clsHandCardData.uctPutCardType = new CardGroupData(CardGroupType.cgZERO, 0, 0);
		}

	}



}

