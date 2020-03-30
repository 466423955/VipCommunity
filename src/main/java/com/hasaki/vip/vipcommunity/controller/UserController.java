package com.hasaki.vip.vipcommunity.controller;

import com.hasaki.vip.vipcommunity.dto.*;
import com.hasaki.vip.vipcommunity.exception.CustomizeErrorCode;
import com.hasaki.vip.vipcommunity.model.Follow;
import com.hasaki.vip.vipcommunity.model.User;
import com.hasaki.vip.vipcommunity.provider.CommunityProvider;
import com.hasaki.vip.vipcommunity.service.FollowService;
import com.hasaki.vip.vipcommunity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * Create by hanzp on 2020-03-15
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CommunityProvider communityProvider;
    @Autowired
    private FollowService followService;

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        communityProvider.flushCookieExpiryTime(request, response, "token", 0);
        communityProvider.flushCookieExpiryTime(request, response, "tokenMaxAge", 0);
        return new ModelAndView("index");
    }

    @ResponseBody
    @PostMapping("/login")
    public Object login(@RequestBody UserLoginDTO userLoginDTO,
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

    @ResponseBody
    @PostMapping("/follow")
    public Object follow(@RequestBody FollowObjectDTO followObjectDTO,
                         @RequestHeader HttpHeaders headers,
                         HttpServletRequest request) {
        if (followObjectDTO.getObjectType() == null || followObjectDTO.getObjectType() == "") {
            return ResponseResultDTO.errorOf(CustomizeErrorCode.USER_FOLLOW_TYPE_EMPTY);
        }
        if (followObjectDTO.getObjectId() == null) {
            return ResponseResultDTO.errorOf(CustomizeErrorCode.USER_FOLLOW_ID_EMPTY);
        }
        User user = userService.findByToken(headers.getFirst("userToken"));
        if (user == null) {
            return ResponseResultDTO.errorOf(CustomizeErrorCode.USER_TOKEN_EXPIRY);
        }

        Follow follow = new Follow();
        follow.setUserId(user.getId());
        follow.setFollowType(followObjectDTO.getFollowType().getType());
        follow.setFollowId(followObjectDTO.getObjectId());
        follow.setGmtCreate(System.currentTimeMillis());

        Object followObject = followService.isFollowObjectExist(follow);
        if (followObject == null) {
            return ResponseResultDTO.errorOf(CustomizeErrorCode.USER_FOLLOW_OBJECT_NOTEXIST);
        }
        List<Follow> sameFollowLog = followService.getSameFollowLog(follow);
        if (sameFollowLog != null && sameFollowLog.size() > 0) {
            return ResponseResultDTO.errorOf(CustomizeErrorCode.USER_FOLLOW_DUPLICATE);
        }

        followService.executeFollow(follow);
        return ResponseResultDTO.successOf();
    }

    @ResponseBody
    @PostMapping("/unfollow")
    public Object unfollow(@RequestBody FollowObjectDTO followObjectDTO,
                           @RequestHeader HttpHeaders headers,
                           HttpServletRequest request) {
        if (followObjectDTO.getObjectType() == null || followObjectDTO.getObjectType() == "") {
            return ResponseResultDTO.errorOf(CustomizeErrorCode.USER_FOLLOW_TYPE_EMPTY);
        }
        if (followObjectDTO.getObjectId() == null) {
            return ResponseResultDTO.errorOf(CustomizeErrorCode.USER_FOLLOW_ID_EMPTY);
        }
        User user = userService.findByToken(headers.getFirst("userToken"));
        if (user == null) {
            return ResponseResultDTO.errorOf(CustomizeErrorCode.USER_TOKEN_EXPIRY);
        }

        Follow follow = new Follow();
        follow.setUserId(user.getId());
        follow.setFollowType(followObjectDTO.getFollowType().getType());
        follow.setFollowId(followObjectDTO.getObjectId());

        List<Follow> sameFollowLog = followService.getSameFollowLog(follow);
        if (sameFollowLog == null || sameFollowLog.size() == 0) {
            return ResponseResultDTO.errorOf(CustomizeErrorCode.USER_FOLLOW_LOG_NOTEXIST);
        }

        followService.executeUnFollow(sameFollowLog);
        return ResponseResultDTO.successOf();
    }
}
