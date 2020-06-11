package com.przeskocz.AdvertEduPortal.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AppLogoutSuccessHandler extends
        SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {
    private final static Logger logger = LoggerFactory.getLogger(AppLogoutSuccessHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse
            response, Authentication authentication)
            throws IOException {

        if (authentication != null)
            logger.info("The " + authentication.getName() + "logout.");

        response.setStatus(HttpStatus.OK.value());
        response.sendRedirect("/successlogout");
    }
}