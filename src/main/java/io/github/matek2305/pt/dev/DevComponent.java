package io.github.matek2305.pt.dev;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Component
@Profile("dev")
public @interface DevComponent {
}
