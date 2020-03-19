package com.hasaki.vip.vipcommunity.controller;

import com.hasaki.vip.vipcommunity.cache.TagCache;
import com.hasaki.vip.vipcommunity.dto.ResponseResultDTO;
import com.hasaki.vip.vipcommunity.dto.TagsDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Create by hanzp on 2020-03-19
 */
@Controller
public class TagController {

    @ResponseBody
    @GetMapping("/tag")
    public Object getTags(){
        TagsDTO askDTO = new TagsDTO();
        askDTO.setTagDTOs(TagCache.get());
        return ResponseResultDTO.successOf(askDTO);
    }
}
