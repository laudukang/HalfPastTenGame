package me.laudukang.model;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/2
 * <p>Time: 16:45
 * <p>Version: 1.0
 */
public class Card {
    String suit;
    String number;
    float point;

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "Suit:" + suit + " Number:" + number + " Point:" + point;
    }
}
