package io.github.matek2305.pt.api;

import io.github.matek2305.pt.api.config.ApiConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Inherited
@WebIntegrationTest
@ContextConfiguration(
        loader = SpringApplicationContextLoader.class,
        classes = { ApiConfiguration.class, ServiceMockConfiguration.class }
)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerITSpec {
}
