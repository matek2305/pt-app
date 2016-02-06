package io.github.matek2305.pt.dev.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.murbanski.spring.dataloader.annotations.EnableDataLoader;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Profile("dev")
@Configuration
@EnableDataLoader
public class DevDataLoaderConfiguration {
}
