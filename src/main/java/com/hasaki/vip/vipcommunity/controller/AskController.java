package com.hasaki.vip.vipcommunity.controller;

import com.hasaki.vip.vipcommunity.cache.TagCache;
import com.hasaki.vip.vipcommunity.dto.PublishQuestionDTO;
import com.hasaki.vip.vipcommunity.dto.ResponseResultDTO;
import com.hasaki.vip.vipcommunity.dto.UserDTO;
import com.hasaki.vip.vipcommunity.dto.UserLoginDTO;
import com.hasaki.vip.vipcommunity.exception.CustomizeErrorCode;
import com.hasaki.vip.vipcommunity.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * Create by hanzp on 2020-03-17
 */
@Controller
public class AskController {
    @ResponseBody
    @PostMapping("/ask")
    public Object login(@RequestBody PublishQuestionDTO publishQuestionDTO,
                        HttpServletRequest request) {
        if (userLoginDTO.getInputEmail() == null || userLoginDTO.getInputEmail() == "") {
            return ResponseResultDTO.errorOf(CustomizeErrorCode.USER_ACCOUNT_ISNULL);
        }
        if (userLoginDTO.getInputPassword() == null || userLoginDTO.getInputPassword() == "") {
            return ResponseResultDTO.errorOf(CustomizeErrorCode.USER_PASSWORD_ISNULL);
        }
        List<User> users = userService.getUserByAccountPassword(userLoginDTO.getInputEmail(), userLoginDTO.getInputPassword());
        if (users.size() != 1) {
            return ResponseResultDTO.errorOf(CustomizeErrorCode.USER_PASSWORD_INCORRECT);
        }

        User user = users.get(0);
        String userToken = UUID.randomUUID().toString();
        user.setToken(userToken);
        userService.updateUserToken(user);
        UserDTO userDTO = userService.getUserDTOByUser(user);
        request.getSession().setAttribute("user", userDTO);
        return ResponseResultDTO.successOf(userDTO);
    }
}
