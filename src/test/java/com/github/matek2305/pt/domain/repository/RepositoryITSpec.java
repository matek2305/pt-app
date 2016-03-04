package com.github.matek2305.pt.domain.repository;

import com.github.matek2305.pt.domain.config.RepositoryTestConfiguration;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Inherited
@IntegrationTest
@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = RepositoryTestConfiguration.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepositoryITSpec {
}
