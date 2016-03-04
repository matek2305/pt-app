package io.github.matek2305.pt.auth;

import io.github.matek2305.pt.dev.DevUsernames;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>.
 */
@Profile("dev")
@Component
public class DevAuthenticationFacadeImpl implements AuthenticationFacade {

    @Override
    public String getLoggedUsername() {
        return DevUsernames.JKOWALSKI;
    }
}
