package me.laudukang.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/3
 * <p>Time: 23:53
 * <p>Version: 1.0
 */
@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle(Exception ex) {
        return "404";
    }
}
