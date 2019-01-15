package com.lordbattle.ai;

/**
 *
 * 手牌权值结构Data
 * @author HD800241
 *
 */
public class HandCardValue {

	//手牌总价值
	public int SumValue;

	//需要打几手牌
	public int NeedRound;

	public int getSumValue() {
		return SumValue;
	}

	public void setSumValue(int sumValue) {
		SumValue = sumValue;
	}

	public int getNeedRound() {
		return NeedRound;
	}

	public void setNeedRound(int needRound) {
		NeedRound = needRound;
	}

}
