package com.lordbattle.ai;

/**
 * 牌型组合数据结构
 * @author HD800241
 *
 */
public class CardGroupData {
	//枚举类型
	public CardGroupType cgType= CardGroupType.cgERROR;

	//该牌的价值
	public int nValue=0;

	//含牌的个数
	public int nCount=0;

	//牌中决定大小的牌值，用于对比
	public int nMaxCard=0;

	public CardGroupData(){};

	public CardGroupData(CardGroupType cgType,int nValue,int nCount){
		this.cgType = cgType;
		this.nValue = nValue;
		this.nCount = nCount;
	}

	public CardGroupData(CardGroupType cgType,int nValue,int nCount,int nMaxCard){
		this.cgType = cgType;
		this.nValue = nValue;
		this.nCount = nCount;
		this.nMaxCard = nMaxCard;
	}

	public CardGroupType getCgType() {
		return cgType;
	}

	public void setCgType(CardGroupType cgType) {
		this.cgType = cgType;
	}

	public int getnValue() {
		return nValue;
	}

	public void setnValue(int nValue) {
		this.nValue = nValue;
	}

	public int getnCount() {
		return nCount;
	}

	public void setnCount(int nCount) {
		this.nCount = nCount;
	}

	public int getnmaxCard() {
		return nMaxCard;
	}

	public void setnmaxCard(int nmaxCard) {
		this.nMaxCard = nmaxCard;
	}

	/*评分逻辑思维：
	0.由于新策略引入手牌轮次参数，所以不再考虑拆分价值。
	1.牌力基础价值：我们认为10属于中等位置，即<10单牌价值为负，大于10的单牌价值为正
	2.控手的价值定义：我们认为一次控手权可以抵消一次手中最小牌型，最小牌型（3）的价值为-7，即我们定义一次控手权的价值为7
	3.单牌的价值定义：该牌的基础价值
	4.对牌的价值定义：我们认为对牌与单牌价值相等（均可以被三牌带出）故其价值为该牌的基础价值
	5.三牌的价值定义：
	  三不带：     该牌的基础价值
	  三带一：     我们认为通常带出去的牌价值一定无正价值故其价值为该牌的基础价值
	  三带二：     我们认为通常带出去的牌价值一定无正价值故其价值为该牌的基础价值
	6.四牌的价值定义：
	  炸弹：       我们认为炸弹会享有一次控手权利且炸弹被管的概率极小，故其无负价值，非负基础价值+7
	  四带二单：   我们认为四带二单管人与被管的概率极小，故其无负价值，其价值为非负基础价值/2（该值最大为6小于一个轮次7)
	  四带二对：   我们认为四带二对管人与被管的概率极小，故其无负价值，其价值为非负基础价值/2（该值最大为6小于一个轮次7)
	7.王炸的价值定义：已知炸2价值为15-3+7=19分，故王炸价值为20分。
	8.顺子的价值定义：
	  单顺：       我们认为单顺的价值等于其最大牌的单体价值，且2不参与顺子，故顺子的权值依次提升1
	  双顺：       我们认为双顺的价值等于其最大牌的单体价值，且2不参与顺子，故顺子的权值依次提升1
	  飞机不带：   由于飞机牌型管人与被管的概率极小，故其无负价值，其价值为非负基础价值/2（该值最大为6小于一个轮次7)
	  飞机带双翅： 由于飞机牌型管人与被管的概率极小，故其无负价值，其价值为非负基础价值/2（该值最大为6小于一个轮次7)
	  飞机带单翅： 由于飞机牌型管人与被管的概率极小，故其无负价值，其价值为非负基础价值/2（该值最大为6小于一个轮次7)
	9.根据数值分布，我们暂定：   <10不叫分，10-14叫一分，15-19叫两分，20以上叫三分
	PS.以上逻辑纯属扯淡，谁信谁SB。。。。。
	*/

	public CardGroupData getCardGroupData(int count,int maxCard){

		CardGroupData uctCardGroupData = new CardGroupData();

		uctCardGroupData.cgType = cgType;
		uctCardGroupData.nCount = count;
		uctCardGroupData.nMaxCard = maxCard;

		//不出牌型
		if (cgType == CardGroupType.cgZERO)
			uctCardGroupData.nValue = 0;
		//单牌类型
		else if (cgType == CardGroupType.cgSINGLE)
			uctCardGroupData.nValue = maxCard - 10;
		//对牌类型
		else if (cgType == CardGroupType.cgDOUBLE)
			uctCardGroupData.nValue = maxCard - 10;
		//三条类型
		else if (cgType == CardGroupType.cgTHREE)
			uctCardGroupData.nValue = maxCard - 10;
		//单连类型
		else if (cgType == CardGroupType.cgSINGLE_LINE)
			uctCardGroupData.nValue = maxCard - 10 + 1;
		//对连类型
		else if (cgType == CardGroupType.cgDOUBLE_LINE)
			uctCardGroupData.nValue = maxCard - 10 + 1;
		//三连类型
		else if (cgType == CardGroupType.cgTHREE_LINE)
			uctCardGroupData.nValue = (maxCard - 3 + 1)/2;
		//三带一单
		else if (cgType == CardGroupType.cgTHREE_TAKE_ONE)
			uctCardGroupData.nValue = maxCard - 10;
		//三带一对
		else if (cgType == CardGroupType.cgTHREE_TAKE_TWO)
			uctCardGroupData.nValue = maxCard - 10;
		//三带一单连
		else if (cgType == CardGroupType.cgTHREE_TAKE_ONE_LINE)
			uctCardGroupData.nValue = (maxCard - 3 + 1) / 2;
		//三带一对连
		else if (cgType == CardGroupType.cgTHREE_TAKE_TWO_LINE)
			uctCardGroupData.nValue = (maxCard - 3 + 1) / 2;
		//四带两单
		else if (cgType == CardGroupType.cgFOUR_TAKE_ONE)
			uctCardGroupData.nValue = (maxCard - 3 ) / 2;
		//四带两对
		else if (cgType == CardGroupType.cgFOUR_TAKE_TWO)
			uctCardGroupData.nValue = (maxCard - 3 ) / 2;
		//炸弹类型
		else if (cgType == CardGroupType.cgBOMB_CARD)
			uctCardGroupData.nValue = maxCard - 3 + 7;
		//王炸类型
		else if (cgType == CardGroupType.cgKING_CARD)
			uctCardGroupData.nValue = 20;
		//错误牌型
		else
			uctCardGroupData.nValue = 0;

		return uctCardGroupData;
	}

}
