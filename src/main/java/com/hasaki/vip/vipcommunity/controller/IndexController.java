package com.hasaki.vip.vipcommunity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Create by hanzp on 2020-03-13
 */
@Controller
public class IndexController {

    @GetMapping({"/", "/index"})
    public ModelAndView index() {
        return new ModelAndView("index");
    }
}
