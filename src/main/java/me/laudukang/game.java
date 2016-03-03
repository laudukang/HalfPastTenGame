package me.laudukang;

import me.laudukang.model.Card;
import me.laudukang.util.Suits;

import java.util.*;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/2
 * <p>Time: 17:01
 * <p>Version: 1.0
 */
public class game {
    public final static int MAX_CARD = 5;

    public static void main(String[] args) {
        //初始化牌
        Card[] card = new Card[52];
        String suit = null;
        for (int i = 0, j = 0; i < 52; i++) {
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

        //洗牌
        Random random = new Random();
        int tmp;
        Card tmpCard;
        for (int i = 0; i < 52; i++) {
            tmp = random.nextInt(52);
            tmpCard = card[i];
            card[i] = card[tmp];
            card[tmp] = tmpCard;
        }

        //洗牌后结果
        //showCard(card,"洗牌后结果");

        int computerCardCount = 0;
        int[] userCardCount;//= new int[];

        Card[] computerCard = new Card[MAX_CARD];

        Map<String, Card[]> userCard;

        float computerResult = 0;
        float[] userResult;

        int cardIndex = 0;
        //System.out.println( hasTenWithJQK(card));

        Scanner scanner = new Scanner(System.in);
        do {
            int userCount = scanner.nextInt();
            userCardCount = new int[userCount];
            userCard = new HashMap<String, Card[]>(userCount);
            userResult = new float[userCount];


            //初始化用户空间
            for (int i = 0; i < userCount; i++) {
                userCard.put(String.valueOf(i), new Card[MAX_CARD]);
            }

            computerCard[computerCardCount++] = card[cardIndex++];
            showCard(computerCard, "庄家第一张牌");

            for (int i = 0; i < userCount; i++) {
                userCard.get(String.valueOf(i))[userCardCount[i]++] = card[cardIndex++];
                showCard(userCard.get(String.valueOf(i)), "玩家【" + (i + 1) + "】第一张牌");
            }
            for (int i = 0; i < userCount; i++) {
                System.out.println("玩家【" + (i + 1) + "】拿牌");
                while (scanner.next().equalsIgnoreCase("add" + i)) {
                    if (getCurrentPonit(userCard.get(String.valueOf(i))) < 10.5) {
                        System.out.println("玩家【" + (i + 1) + "】继续要牌");
                        Card temp = card[cardIndex++];
                        userCard.get(String.valueOf(i))[userCardCount[i]++] = temp;
                        userResult[i] += temp.getPoint();
                        if (getCurrentPonit(userCard.get(String.valueOf(i))) == 10.5 || userCardCount[i] > 4) {
                            System.out.println("玩家【" + (i + 1) + "】10.5，您不需要继续拿牌了");
                            if (hasTenWithJQK(userCard.get(String.valueOf(i)))) {
                                System.out.println("玩家【" + (i + 1) + "】10+J/Q/K，玩家胜出");
                            }
                            break;
                        }
                    } else {
                        System.out.println("您的点数超过10.5，不能继续获牌");
                        break;
                    }
                }
            }
            System.out.println("轮到庄家拿牌");
            while (scanner.next().equalsIgnoreCase("adminadd")) {
                if (getCurrentPonit(computerCard) < 10.5) {
                    System.out.println("庄家继续要牌");
                    Card temp = card[cardIndex++];
                    computerCard[computerCardCount++] = temp;
                    computerResult += temp.getPoint();
                    if (getCurrentPonit(computerCard) == 10.5) {
                        System.out.println("庄家10.5，不需要继续拿牌");
                    }
                } else {
                    System.out.println("您的点数超过10.5，不能继续获牌");
                    break;
                }
            }
            showCard(computerCard, "庄家最终牌");
            for (int i = 0; i < userCount; i++) {
                showCard(userCard.get(String.valueOf(i)), "玩家【" + (i + 1) + "】最终牌");
            }
            System.out.println("游戏结果");
            for (int i = 0; i < userCount; i++) {
                if (userCardCount[i] > 4) {//“五龙”
                    System.out.println("玩家【" + (i + 1) + "】对庄家：玩家赢");
                } else if (hasTenWithJQK(userCard.get(String.valueOf(i)))) {//闲家倘若为一只10加一只 J,Q或K 成十点半，庄家立赔。
                    System.out.println("玩家【" + (i + 1) + "】10+J/Q/K，玩家胜出");
                } else if (userResult[i] == 10.5 && computerResult == 10.5) {//庄家和闲家都10点半闲家要赔
                    System.out.println("玩家【" + (i + 1) + "】对庄家：庄家赢");
                } else if (userResult[i] > 10.5 && computerResult < 10.5) {//玩家“爆煲”
                    System.out.println("玩家【" + (i + 1) + "】对庄家：庄家赢");
                } else if (userResult[i] < 10.5 && computerResult < 10.5 && userResult[i] == computerResult) {//“食夹棍”
                    System.out.println("玩家【" + (i + 1) + "】对庄家：庄家赢");
                } else if (userCardCount[i] < 5 && computerCardCount < 5 && getCurrentPonit(userCard.get(String.valueOf(i))) > getCurrentPonit(computerCard)) {
                    System.out.println("玩家【" + (i + 1) + "】对庄家：玩家赢，5牌以下，玩家牌点数大");
                } else if (getCurrentPonit(userCard.get(String.valueOf(i))) > getCurrentPonit(computerCard)) {
                    System.out.println("玩家【" + (i + 1) + "】对庄家：庄家赢，超过10.5，庄家点数小");
                }
            }


            System.out.println("游戏结束，Q退出游戏");
        } while (!scanner.next().equalsIgnoreCase("q"));


    }

    public static void showCard(Card[] card, String msg) {
        System.out.println(msg);
        if (card.length > 0) {
            for (int i = 0; i < card.length; i++) {
                if (null != card[i])
                    System.out.println(card[i]);
            }
        }
        System.out.println();
    }

    public static float getCurrentPonit(Card[] card) {
        float result = 0;
        if (card.length > 0) {
            for (int i = 0; i < card.length; i++) {
                if (null != card[i])
                    result += card[i].getPoint();
            }
        }
        return result;
    }

    public static boolean hasTenWithJQK(Card[] card) {
        Set set = new HashSet<>(card.length);
        for (int i = 0; i < card.length; i++) {
            if (null != card[i]) {
                set.add(card[i].getNumber().toUpperCase());
            }
        }
        return set.contains("10") && (set.contains("J") || set.contains("Q") || set.contains("K"));
    }

}
