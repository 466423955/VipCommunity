package com.hasaki.vip.vipcommunity.interceptor;

import com.hasaki.vip.vipcommunity.dto.UserDTO;
import com.hasaki.vip.vipcommunity.model.User;
import com.hasaki.vip.vipcommunity.provider.CommunityProvider;
import com.hasaki.vip.vipcommunity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Create by hanzp on 2020-02-29
 */
@Service
public class LoginStatusInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Autowired
    private CommunityProvider communityProvider;

    //检测是否登录，如果已经登录，取用户信息发到前台，并刷新cookie
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = communityProvider.getCookieByKey(request, "token");
        if (token == null) {
            response.sendRedirect("/login");
            return false;
        }
        User user = userService.findByToken(token);
        if (user != null) {
            return true;
        }
        response.sendRedirect("/login");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
