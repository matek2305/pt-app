package com.github.matek2305.pt.api;

import com.github.matek2305.pt.auth.AuthenticationFacade;
import java.time.Duration;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoggingInterceptor implements HandlerInterceptor {

    private final AuthenticationFacade authenticationFacade;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("pt-request-received", LocalDateTime.now());
        log.info("received {} request for '{}' from user = {}", request.getMethod(), request.getRequestURI(), authenticationFacade.getLoggedUsername());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LocalDateTime receivedTime = (LocalDateTime) request.getAttribute("pt-request-received");
        long timeInMillis = Duration.between(receivedTime, LocalDateTime.now()).toMillis();
        log.info("{} request for '{}' from user = {} completed in {} ms with status {}", request.getMethod(), request.getRequestURI(),
                authenticationFacade.getLoggedUsername(), timeInMillis, response.getStatus());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
