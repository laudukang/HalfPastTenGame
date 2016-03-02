package me.laudukang.service;

import me.laudukang.model.Card;

import java.util.Map;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/2
 * <p>Time: 16:00
 * <p>Version: 1.0
 */
public interface IHPTGService {
    void initCard();

    boolean hasTenWithJQK(Card[] card);

    float getCurrentPonit(Card[] card);

    void showCard(Card[] card, String msg);

    void shuffle(Card[] card);

    Card computerGetOneCard();

    Card userGetOneCard(int userid);

    void initSpace(int userCount);

    Map<String, String> checkBeforeGetCardUser(int userid);

    Map<String, String> checkBeforeGetCardComputer();

    Map<String, Object> whoWinTheGame(int userCount);


}
