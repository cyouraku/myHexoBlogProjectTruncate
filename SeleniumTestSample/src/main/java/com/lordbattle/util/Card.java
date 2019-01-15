package com.lordbattle.util;

/**
 * 定义扑克牌
 */
public class Card implements Comparable<Card> {
    /**
     * 花色（黑桃、红桃、梅花、方块）
     */
    public String color;
    /**
     * 大小（2-10,J,Q,K,A,大王，小王）
     */
    public String value;

    public Card() {
    }

    public Card(String color, String value) {
        this.color = color;
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Card) {
            Card card = (Card) obj;
            if (card.value.equalsIgnoreCase(this.value) && card.color.equalsIgnoreCase(this.color)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return color.hashCode() + value.hashCode();
    }

    //定义大小规则
    @Override
    public int compareTo(Card card) {
        return PlayCards.findIndex(this) - PlayCards.findIndex(card);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Card{");
        sb.append("color='").append(color).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
