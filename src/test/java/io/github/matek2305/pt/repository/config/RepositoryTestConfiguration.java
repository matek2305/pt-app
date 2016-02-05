package io.github.matek2305.pt.repository.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Configuration
@Import(RepositoryConfiguration.class)
@EnableAutoConfiguration
public class RepositoryTestConfiguration {
}
