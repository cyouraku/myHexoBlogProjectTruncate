package com.lordbattle.util;

import java.util.LinkedList;

/**
 * 定义玩家
 */
public class Player {

    /**
     * 用户名
     */
    public String username;

    /**
     * 是否为第一个出牌
     */
    public boolean first;

    /**
     * 持有牌
     */
    LinkedList<Card> cards = new LinkedList<>();

    /**
     * 当前选择出牌
     */
    LinkedList<Card> putCards = new LinkedList<>();

    /**
     * 上一位出牌人
     */
    public Player before;
    /**
     * 下一位出牌人
     */
    public Player next;

    public Player() {
    }

    public Player(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void setCards(LinkedList<Card> cards) {
        this.cards = cards;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public LinkedList<Card> getCards() {
        return cards;
    }

    public LinkedList<Card> getPutCards() {
        return putCards;
    }

    public void setPutCards(LinkedList<Card> putCards) {
        this.putCards = putCards;
    }

    public Player getBefore() {
        return before;
    }

    public void setBefore(Player before) {
        this.before = before;
    }

    public Player getNext() {
        return next;
    }

    public void setNext(Player next) {
        this.next = next;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Player{");
        sb.append("username='").append(username).append('\'');
        sb.append(", cards=").append(cards);
        sb.append(", putCards=").append(putCards);
        sb.append('}');
        return sb.toString();
    }

}
