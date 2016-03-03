package me.laudukang.service.impl;

import me.laudukang.data.GameData;
import me.laudukang.model.Card;
import me.laudukang.service.IHPTGService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/2
 * <p>Time: 16:00
 * <p>Version: 1.0
 */
@Service
@Scope("session")
public class HPTGService implements IHPTGService {

    private GameData gameData;

    //重置牌
    @Override
    public void resetCard(int userCount) {
        gameData = null;
        gameData = new GameData(userCount);
    }

    //10加J或Q或K？
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

    //庄家获牌
    @Override
    public Card computerGetOneCard() {
        return gameData.computerGetOneCard();
    }

    //玩家获牌
    @Override
    public Card userGetOneCard(int userid) {
        return gameData.userGetOneCard(userid);
    }

    //public float getCurrentPonit(Card[] card) {
    //    float result = 0;
    //    if (card.length > 0) {
    //        for (int i = 0; i < card.length; i++) {
    //            if (null != card[i])
    //                result += card[i].getPoint();
    //        }
    //    }
    //    return result;
    //}

    //庄家牌点数
    @Override
    public float getComputerCurrentPonit() {
        return gameData.getComputerResult();
    }

    //玩家牌点数
    @Override
    public float getUserCurrentPonit(int userid) {
        return gameData.getUserResult()[userid];
    }

    //玩家能否继续拿牌
    @Override
    public Map<String, Object> checkBeforeGetCardUser(int userid) {
        Map<String, Object> map = new HashMap<>(2);
        float currentResult = gameData.getUserResult()[userid];
        if (currentResult < 10.5) {
            map.put("isGet", true);
            map.put("msg", "允许继续拿牌");
        } else if (currentResult == 10.5 || gameData.getUserCardCount()[userid] > 4) {
            map.put("isGet", false);
            if (hasTenWithJQK(gameData.getUserCard().get(String.valueOf(userid)))) {
                map.put("msg", "10.5，您不需要继续拿牌了,10+J/Q/K，您提前胜出");
            } else {
                map.put("msg", "10.5，您不需要继续拿牌了");
            }
        } else {
            map.put("isGet", false);
            map.put("msg", "总点数超过10.5，不允许继续拿牌");
        }
        return map;
    }

    //庄家能否继续拿牌
    @Override
    public Map<String, Object> checkBeforeGetCardComputer() {
        Map<String, Object> map = new HashMap<>(2);
        float currentResult = gameData.getComputerResult();
        if (currentResult < 10.5) {
            map.put("isGet", true);
            map.put("msg", "允许继续拿牌");
        } else if (currentResult == 10.5) {
            map.put("isGet", false);
            map.put("msg", "10.5，您不需要继续拿牌了");
        } else {
            map.put("isGet", false);
            map.put("msg", "总点数超过10.5，不允许继续拿牌");
        }
        return map;
    }

    //获取当前局游戏结果
    @Override
    public Map<String, Object> whoWinTheGame(int userCount) {
        Map<String, Object> map = new HashMap<>();
        //返回庄家所有牌
        //map.put("c", gameData.getComputerCard());
        for (int i = 0; i < userCount; i++) {
            if (gameData.getUserCardCount()[i] > 4) {//“五龙”
                map.put("u" + i, "五龙，您胜出");
                map.put("au" + i, "对【玩家" + (i + 1) + "】，您输了");
            } else {
                float userResult = gameData.getUserResult()[i];
                float computerResult = gameData.getComputerResult();

                if (userResult == 10.5 && hasTenWithJQK(gameData.getUserCard().get(String.valueOf(i)))) {//闲家倘若为一只10加一只 J,Q或K 成十点半，庄家立赔。
                    map.put("u" + i, "您胜出");
                    map.put("au" + i, "对【玩家" + (i + 1) + "】，您输了");
                }
                //else if (userResult == computerResult && 10.5 == computerResult) {//庄家和闲家都10点半闲家要赔
                //    map.put("u" + i, "您输了");
                //    map.put("au" + i, "对【玩家" + (i + 1) + "】，您胜出");
                //}
                else if (computerResult < userResult && userResult <= 10.5) {//自定义都未爆煲情况下点数大的获胜
                    map.put("u" + i, "您胜出");
                    map.put("au" + i, "对【玩家" + (i + 1) + "】，您输了");
                } else if (computerResult >= userResult && computerResult <= 10.5) {//倘若点数相同，庄家胜，称“食夹棍” //庄家和闲家都10点半闲家要赔
                    map.put("u" + i, "您输了");
                    map.put("au" + i, "对【玩家" + (i + 1) + "】，您胜出");
                } else if (computerResult > userResult && userResult >= 10.5) {//自定义都爆煲情况下点数小的获胜
                    map.put("u" + i, "您胜出");
                    map.put("au" + i, "对【玩家" + (i + 1) + "】，您输了");
                } else if (computerResult < userResult && computerResult >= 10.5) {//自定义都爆煲情况下点数小的获胜
                    map.put("u" + i, "您输了");
                    map.put("au" + i, "对【玩家" + (i + 1) + "】，您胜出");
                } else if (computerResult > 10.5 && userResult <= 10.5) {//倘若庄家爆煲，只需赔给仍未爆的闲家。
                    map.put("u" + i, "您胜出");
                    map.put("au" + i, "对【玩家" + (i + 1) + "】，您输了");
                } else if (computerResult <= 10.5 && userResult > 10.5) {//倘若庄家爆煲，只需赔给仍未爆的闲家。
                    map.put("u" + i, "您输了");
                    map.put("au" + i, "对【玩家" + (i + 1) + "】，您胜出");
                }
                //else {//其他情况，如 庄点数>10.5>玩家点数
                //    map.put("u" + i, "平局");
                //    map.put("au" + i, "对【玩家" + (i + 1) + "】，双方平局");
                //}
            }
            //返回玩家所有牌
            // map.put("c" + i, gameData.getUserCard().get(String.valueOf(i)));
        }
        return map;
    }
}