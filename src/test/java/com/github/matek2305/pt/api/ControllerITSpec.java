package com.github.matek2305.pt.api;

import com.github.matek2305.pt.api.config.ApiConfiguration;
import com.github.matek2305.pt.auth.DevAuthenticationFacadeImpl;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Inherited
@ActiveProfiles("dev")
@WebIntegrationTest
@ContextConfiguration(
        loader = SpringApplicationContextLoader.class,
        classes = { ApiConfiguration.class, ServiceMockConfiguration.class, DevAuthenticationFacadeImpl.class }
)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerITSpec {
}
