package com.hasaki.vip.vipcommunity.controller;

import com.hasaki.vip.vipcommunity.cache.TagCache;
import com.hasaki.vip.vipcommunity.dto.*;
import com.hasaki.vip.vipcommunity.exception.CustomizeErrorCode;
import com.hasaki.vip.vipcommunity.model.Question;
import com.hasaki.vip.vipcommunity.model.User;
import com.hasaki.vip.vipcommunity.service.QuestionService;
import com.hasaki.vip.vipcommunity.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * Create by hanzp on 2020-03-17
 */
@Controller
public class QuestionController {
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;

    @ResponseBody
    @PostMapping("/ask")
    public Object login(@RequestBody PublishQuestionDTO publishQuestionDTO,
                        @RequestHeader HttpHeaders headers,
                        HttpServletRequest request) {
        if (publishQuestionDTO.getTitle() == null || publishQuestionDTO.getTitle() == "") {
            return ResponseResultDTO.errorOf(CustomizeErrorCode.QUESTION_TITLE_EMPTY);
        }
        if (publishQuestionDTO.getContent() == null || publishQuestionDTO.getContent() == "") {
            return ResponseResultDTO.errorOf(CustomizeErrorCode.QUESTION_CONTENT_EMPTY);
        }
        if (publishQuestionDTO.getTags() == null || publishQuestionDTO.getTags().length == 0) {
            return ResponseResultDTO.errorOf(CustomizeErrorCode.QUESTION_TAG_EMPTY);
        }
        User user = userService.findByToken(headers.getFirst("userToken"));
        if (user == null) {
            return ResponseResultDTO.errorOf(CustomizeErrorCode.USER_TOKEN_EXPIRY);
        }
        Question question = questionService.createOrUpdate(publishQuestionDTO, user);
        UserDTO userDTO = userService.getUserDTOByUser(user);
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestion(question);
        questionDTO.setUser(userDTO);
        return ResponseResultDTO.successOf(questionDTO);
    }

    @ResponseBody
    @GetMapping("/question/{id}")
    public Object getQuestionById(@PathVariable(name = "id") Long id) {
        QuestionDTO questionDTO = questionService.getQuestionDTOById(id);
        return ResponseResultDTO.successOf(questionDTO);
    }
}
