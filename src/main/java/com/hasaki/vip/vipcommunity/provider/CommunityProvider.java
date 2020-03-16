package com.hasaki.vip.vipcommunity.provider;

import org.springframework.stereotype.Component;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Create by hanzp on 2020-03-16
 */
@Component
public class CommunityProvider {

    public String getCookieByKey(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return null;
        }
        for(Cookie cookie:cookies){
            if(cookie.getName().equals(key)){
                return cookie.getValue();
            }
        }
        return null;
    }

    public void flushCookieExpiryTime(HttpServletRequest request, HttpServletResponse response, String key, double hours) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    cookie.setMaxAge((int)(hours*60*60));
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    return;
                }
            }
        }
    }
}
