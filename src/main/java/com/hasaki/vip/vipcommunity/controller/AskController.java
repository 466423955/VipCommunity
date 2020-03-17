package com.hasaki.vip.vipcommunity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Create by hanzp on 2020-03-17
 */
@Controller
public class AskController {
    @GetMapping("/ask")
    public ModelAndView ask(){
        return new ModelAndView("ask");
    }
}
