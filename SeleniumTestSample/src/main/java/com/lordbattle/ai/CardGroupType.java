package com.lordbattle.ai;

import java.io.Serializable;

/**
 * 手牌组合枚举
 * @author HD800241
 *
 */
public enum CardGroupType implements Serializable {

	// 错误类型
	cgERROR(-1),
	// 不出类型
	cgZERO(0),
	// 单牌类型
	cgSINGLE(1),
	// 对牌类型
	cgDOUBLE(2),
	// 三条类型
	cgTHREE(3),
	// 单连类型
	cgSINGLE_LINE(4),
	// 对连类型
	cgDOUBLE_LINE(5),
	// 三连类型
	cgTHREE_LINE(6),
	// 三带一单
	cgTHREE_TAKE_ONE(7),
	// 三带一对
	cgTHREE_TAKE_TWO(8),
	// 三带一单连
	cgTHREE_TAKE_ONE_LINE(9),
	// 三带一对连
	cgTHREE_TAKE_TWO_LINE(10),
	// 四带两单
	cgFOUR_TAKE_ONE(11),
	// 四带两对
	cgFOUR_TAKE_TWO(12),
	// 炸弹类型
	cgBOMB_CARD(13),
	// 王炸类型
	cgKING_CARD(14);

	private final int number;

	/**
	 * コンストラクタ
	 * @param number
	 */
	private CardGroupType(final int number){
		this.number = number;
	}

	public int getNumber() {
		return number;
	}


}
