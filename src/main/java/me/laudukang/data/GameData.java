package me.laudukang.data;

import me.laudukang.model.Card;
import me.laudukang.util.Suits;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/2
 * <p>Time: 23:12
 * <p>Version: 1.0
 */
public class GameData {
    public final static int MAX_CARD = 7;
    public final static int CARD_NUM = 52;
    Card[] card;

    int computerCardCount = 0;
    int[] userCardCount;

    Card[] computerCard = new Card[MAX_CARD];

    Map<String, Card[]> userCard;

    float computerResult = 0;
    float[] userResult;

    int cardIndex = 0;

    public GameData() {
    }

    public GameData(int userCount) {
        card = new Card[CARD_NUM];
        String suit = null;
        for (int i = 0, j = 0; i < card.length; i++) {
            if (i % 13 == 0) {
                suit = Suits.values()[j++].toString();
            }
            Card card1 = new Card();
            card1.setSuit(suit);
            int tmp = i % 13;
            switch (tmp) {
                case 0:
                    card1.setNumber("A");
                    break;
                case 9:
                    card1.setNumber("10");
                    break;
                case 10:
                    card1.setNumber("J");
                    break;
                case 11:
                    card1.setNumber("Q");
                    break;
                case 12:
                    card1.setNumber("K");
                    break;
                default:
                    card1.setNumber(String.valueOf(tmp + 1));
            }
            if (tmp > 9) {
                card1.setPoint((float) 0.5);
            } else {
                card1.setPoint(tmp + 1);
            }
            card[i] = card1;
        }
        //初始化牌结果
        //showCard(card,"初始化牌结果");

        Random random = new Random();
        int tmp;
        Card tmpCard;
        for (int i = 0; i < card.length; i++) {
            tmp = random.nextInt(card.length);
            tmpCard = card[i];
            card[i] = card[tmp];
            card[tmp] = tmpCard;
        }
        //洗牌后结果
        //showCard(card,"洗牌后结果");

        userCardCount = new int[userCount];
        userCard = new HashMap<String, Card[]>(userCount);
        userResult = new float[userCount];

        //初始化用户空间
        for (int i = 0; i < userCount; i++) {
            userCard.put(String.valueOf(i), new Card[MAX_CARD]);
        }

        computerCardCount = 0;
        computerResult = 0;
        cardIndex = 0;
    }

    public Card computerGetOneCard() {
        Card temp = card[cardIndex++];
        computerCard[computerCardCount++] = temp;
        computerResult += temp.getPoint();
        return temp;
    }

    public Card userGetOneCard(int userid) {
        Card temp = card[cardIndex++];
        userCard.get(String.valueOf(userid))[userCardCount[userid]++] = temp;
        userResult[userid] += temp.getPoint();
        return temp;
    }


    public void showCard(Card[] card, String msg) {
        System.out.println(msg);
        if (card.length > 0) {
            for (int i = 0; i < card.length; i++) {
                if (null != card[i])
                    System.out.println(card[i]);
            }
        }
        System.out.println();
    }

    public Card[] getCard() {
        return card;
    }

    public void setCard(Card[] card) {
        this.card = card;
    }

    public int getComputerCardCount() {
        return computerCardCount;
    }

    public void setComputerCardCount(int computerCardCount) {
        this.computerCardCount = computerCardCount;
    }

    public int[] getUserCardCount() {
        return userCardCount;
    }

    public void setUserCardCount(int[] userCardCount) {
        this.userCardCount = userCardCount;
    }

    public Card[] getComputerCard() {
        return computerCard;
    }

    public void setComputerCard(Card[] computerCard) {
        this.computerCard = computerCard;
    }

    public Map<String, Card[]> getUserCard() {
        return userCard;
    }

    public void setUserCard(Map<String, Card[]> userCard) {
        this.userCard = userCard;
    }

    public float getComputerResult() {
        return computerResult;
    }

    public void setComputerResult(float computerResult) {
        this.computerResult = computerResult;
    }

    public float[] getUserResult() {
        return userResult;
    }

    public void setUserResult(float[] userResult) {
        this.userResult = userResult;
    }

    public int getCardIndex() {
        return cardIndex;
    }

    public void setCardIndex(int cardIndex) {
        this.cardIndex = cardIndex;
    }
}
