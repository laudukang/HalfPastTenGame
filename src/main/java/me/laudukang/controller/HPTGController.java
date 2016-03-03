package me.laudukang.controller;

import me.laudukang.service.IHPTGService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/2
 * <p>Time: 15:47
 * <p>Version: 1.0
 */
@Controller
public class HPTGController {
    final static int MAX_USER = 2;
    @Autowired
    private IHPTGService hptgService;
    private int currentUserCount;

    @RequestMapping("hi")
    @ResponseBody
    public Map<String, String> hi() {
        System.out.println("hi laudukang");
        Map<String, String> map = new HashMap<String, String>();
        map.put("msg", "hi lau");
        return map;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage() {
        return "index";
    }

    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public String gamePage(HttpServletRequest request, Model model) {
        int userCount = 0;
        if (null != request.getParameter("number")) {
            try {
                userCount = Integer.valueOf(request.getParameter("number"));
                if (userCount > MAX_USER || userCount < 1) {
                    throw new Exception("用户数过多");
                }
                model.addAttribute("userCount", userCount + 1);
                currentUserCount = userCount;
                hptgService.resetCard(userCount);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                model.addAttribute("msg", "参数非法：用户数无效");
            }
        } else {
            model.addAttribute("msg", "参数非法：请输入一个整数");
        }
        return "game";
    }

    @RequestMapping(value = "/userGetCard")
    @ResponseBody
    public Map<String, Object> userGetCard(HttpServletRequest request) {
        Map<String, Object> map = null;
        if (null != request.getParameter("userid")) {
            try {
                int userid = Integer.valueOf(request.getParameter("userid"));
                if (currentUserCount < userid) throw new Exception("数组越界");
                map = hptgService.checkBeforeGetCardUser(userid);
                if (Boolean.valueOf(String.valueOf(map.get("isGet")))) {
                    map.put("card", hptgService.userGetOneCard(userid));
                    map.put("totalPoint", hptgService.getUserCurrentPonit(userid));
                }
            } catch (Exception ex) {
                map = new HashMap<>(2);
                map.put("msg", "参数非法");
                map.put("isGet", false);
            }
        } else {
            map = new HashMap<>(2);
            map.put("msg", "参数非法");
            map.put("isGet", false);
        }
        return map;
    }

    @RequestMapping(value = "/computerGetCard")
    @ResponseBody
    public Map<String, Object> computerGetCard(HttpServletRequest request) {
        Map<String, Object> map = null;
        map = hptgService.checkBeforeGetCardComputer();
        if ("true".equals(String.valueOf(map.get("isGet")))) {
            map.put("card", hptgService.computerGetOneCard());
            map.put("totalPoint", hptgService.getComputerCurrentPonit());
        }
        return map;
    }

    @RequestMapping(value = "/winner", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> whoWinTheGame() {
        return hptgService.whoWinTheGame(currentUserCount);
    }

}
