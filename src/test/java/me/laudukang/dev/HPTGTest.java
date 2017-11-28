package me.laudukang.dev;

import me.laudukang.service.IHPTGService;
import me.laudukang.service.impl.HPTGService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/2
 * <p>Time: 16:57
 * <p>Version: 1.0
 */
public class HPTGTest {

    @Before
    public void init(){

    }

    @Test
    public void test() {

        IHPTGService ihptgService = new HPTGService();
        ihptgService.resetCard(1);

        float userPoint = 0, computerPoint = 0;

        userPoint += ihptgService.userGetOneCard(0).getPoint();

        userPoint += ihptgService.userGetOneCard(0).getPoint();
        Assert.assertTrue("", userPoint > 10.5);

        userPoint += ihptgService.userGetOneCard(0).getPoint();
        Assert.assertTrue("", userPoint > 10.5);


        computerPoint += ihptgService.computerGetOneCard().getPoint();
        computerPoint += ihptgService.computerGetOneCard().getPoint();
        Assert.assertTrue("", computerPoint > 10.5);


    }
}
