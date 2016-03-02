package me.laudukang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @RequestMapping("hi")
    @ResponseBody
    public Map<String, String> hi() {
        System.out.println("hi laudukang");
        Map<String, String> map = new HashMap<String, String>();
        map.put("msg", "hi lau");
        return map;
    }

    @RequestMapping("/")
    public String home() {
        return "index";
    }
}
