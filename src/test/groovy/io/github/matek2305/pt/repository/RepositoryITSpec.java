package io.github.matek2305.pt.repository;

import io.github.matek2305.pt.repository.config.RepositoryConfiguration;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.*;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Inherited
@IntegrationTest
@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = RepositoryConfiguration.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepositoryITSpec {
}
