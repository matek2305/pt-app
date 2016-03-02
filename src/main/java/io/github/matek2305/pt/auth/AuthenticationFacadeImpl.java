package io.github.matek2305.pt.auth;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>.
 */
@Component
@Profile("demo")
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    @Override
    public String getLoggedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
