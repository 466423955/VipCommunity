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
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Autowired
    private CommunityProvider communityProvider;

    //检测是否登录，如果已经登录，取用户信息发到前台，并刷新cookie
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = communityProvider.getCookieByKey(request, "token");
        if (token == null) {
//            response.sendRedirect("/login");
            return true;
        }
        User user = userService.findByToken(token);
        if (user != null) {
            UserDTO userDTO = userService.getUserDTOByUser(user);
            request.getSession().setAttribute("user", userDTO);
            String tokenMaxAge = communityProvider.getCookieByKey(request, "tokenMaxAge");
            if("720".equals(tokenMaxAge)){
                communityProvider.flushCookieExpiryTime(request, response,"token", 720);
                communityProvider.flushCookieExpiryTime(request, response,"tokenMaxAge", 720);
            } else {
                communityProvider.flushCookieExpiryTime(request, response,"token", 0.5);
                communityProvider.flushCookieExpiryTime(request, response,"tokenMaxAge", 0.5);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
