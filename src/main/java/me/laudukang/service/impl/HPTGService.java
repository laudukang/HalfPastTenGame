package me.laudukang.service.impl;

import me.laudukang.model.Card;
import me.laudukang.service.IHPTGService;
import me.laudukang.util.Suits;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/2
 * <p>Time: 16:00
 * <p>Version: 1.0
 */
@Service
public class HPTGService implements IHPTGService {

    public final static int MAX_CARD = 5;
    public final static int CARD_NUM = 52;
    Card[] card;

    int computerCardCount = 0;
    int[] userCardCount;

    Card[] computerCard = new Card[MAX_CARD];

    Map<String, Card[]> userCard;

    float computerResult = 0;
    float[] userResult;

    int cardIndex = 0;

    @Override
    public void initCard() {
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
    }

    @Override
    public boolean hasTenWithJQK(Card[] card) {
        Set set = new HashSet<>(card.length);
        for (int i = 0; i < card.length; i++) {
            if (null != card[i]) {
                set.add(card[i].getNumber().toUpperCase());
            }
        }
        return set.contains("10") && (set.contains("J") || set.contains("Q") || set.contains("K"));
    }

    @Override
    public float getCurrentPonit(Card[] card) {
        float result = 0;
        if (card.length > 0) {
            for (int i = 0; i < card.length; i++) {
                if (null != card[i])
                    result += card[i].getPoint();
            }
        }
        return result;
    }

    @Override
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

    @Override
    public void shuffle(Card[] card) {
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
    }

    @Override
    public Card computerGetOneCard() {
        Card temp = card[cardIndex++];
        computerCard[computerCardCount++] = temp;
        computerResult += temp.getPoint();
        return temp;
    }

    @Override
    public Card userGetOneCard(int userid) {
        Card temp = card[cardIndex++];
        userCard.get(String.valueOf(userid))[userCardCount[userid]++] = temp;
        userResult[userid] += temp.getPoint();
        return temp;
    }

    @Override
    public void initSpace(int userCount) {
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

    @Override
    public Map<String, String> checkBeforeGetCardUser(int userid) {
        Map<String, String> map = new HashMap<>(2);
        float currentResult = getCurrentPonit(userCard.get(String.valueOf(userid)));
        if (currentResult < 10.5) {
            map.put("isGet", "true");
            map.put("msg", "允许继续拿牌");
        } else if (currentResult == 10.5 || userCardCount[userid] > 4) {
            map.put("isGet", "false");
            if (hasTenWithJQK(userCard.get(String.valueOf(userid)))) {
                map.put("msg", "您不需要继续拿牌了,10+J/Q/K，您提前胜出");
            } else {
                map.put("msg", "您不需要继续拿牌了");
            }
        } else {
            map.put("isGet", "false");
            map.put("msg", "不允许继续拿牌");
        }
        return map;
    }

    @Override
    public Map<String, String> checkBeforeGetCardComputer() {
        Map<String, String> map = new HashMap<>(2);
        float currentResult = getCurrentPonit(computerCard);
        if (currentResult < 10.5) {
            map.put("isGet", "true");
            map.put("msg", "允许继续拿牌");
        } else if (currentResult == 10.5) {
            map.put("isGet", "false");
            map.put("msg", "您不需要继续拿牌了");
        } else {
            map.put("isGet", "false");
            map.put("msg", "不允许继续拿牌");
        }
        return map;
    }

    @Override
    public Map<String, Object> whoWinTheGame(int userCount) {
        Map<String, Object> map = new HashMap<>();
        map.put("c", computerCard);
        for (int i = 0; i < userCount; i++) {
            if (userCardCount[i] > 4) {//“五龙”
                map.put("ua" + i, "您胜出");
                map.put("au" + i, "对玩家【" + (i + 1) + "】，您输了");
            } else if (hasTenWithJQK(userCard.get(String.valueOf(i)))) {//闲家倘若为一只10加一只 J,Q或K 成十点半，庄家立赔。
                map.put("ua" + i, "10+J/Q/K,您胜出");
                map.put("au" + i, "对玩家【" + (i + 1) + "】，您输了");
            } else if (userResult[i] == 10.5 && computerResult == 10.5) {//庄家和闲家都10点半闲家要赔
                map.put("ua", "您输了");
                map.put("au" + i, "对玩家【" + (i + 1) + "】，您胜出");
            } else if (userResult[i] > 10.5 && computerResult < 10.5) {//玩家“爆煲”
                map.put("ua", "您输了");
                map.put("au" + i, "对玩家【" + (i + 1) + "】，您胜出");
            } else if (userResult[i] < 10.5 && computerResult < 10.5 && userResult[i] == computerResult) {//“食夹棍”
                map.put("ua", "您输了");
                map.put("au" + i, "对玩家【" + (i + 1) + "】，您胜出");
            } else if (userCardCount[i] < 5 && computerCardCount < 5 && userResult[i] > computerResult) {
                map.put("ua", "您胜出");
                map.put("au" + i, "对玩家【" + (i + 1) + "】，您输了");
            } else if (userResult[i] > computerResult) {
                map.put("ua", "您输了");
                map.put("au" + i, "对玩家【" + (i + 1) + "】，您胜出");
            }
            map.put("c" + i, userCard.get(String.valueOf(i)));
        }
        return map;
    }
}
