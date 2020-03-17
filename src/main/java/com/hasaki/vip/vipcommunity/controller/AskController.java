package com.hasaki.vip.vipcommunity.controller;

import com.hasaki.vip.vipcommunity.cache.TagCache;
import com.hasaki.vip.vipcommunity.dto.AskDTO;
import com.hasaki.vip.vipcommunity.dto.ResponseResultDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Create by hanzp on 2020-03-17
 */
@Controller
public class AskController {

    @ResponseBody
    @GetMapping("/ask")
    public Object ask(){
        AskDTO askDTO = new AskDTO();
        askDTO.setTagDTOs(TagCache.get());
        return ResponseResultDTO.successOf(askDTO);
    }
}
