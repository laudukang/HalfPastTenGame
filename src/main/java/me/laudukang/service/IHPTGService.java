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
    void resetCard(int userCount);

    boolean hasTenWithJQK(Card[] card);

    float getComputerCurrentPonit();

    float getUserCurrentPonit(int userid);

    Card computerGetOneCard();

    Card userGetOneCard(int userid);

    Map<String, Object> checkBeforeGetCardUser(int userid);

    Map<String, Object> checkBeforeGetCardComputer();

    Map<String, Object> whoWinTheGame(int userCount);

}
