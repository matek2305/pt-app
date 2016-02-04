package io.github.matek2305.pt.repository.config;

import io.github.matek2305.pt.domain.BaseEntity;
import io.github.matek2305.pt.repository.BaseRepository;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackageClasses = BaseEntity.class)
@EnableJpaRepositories(basePackageClasses = BaseRepository.class)
@EnableTransactionManagement
public class RepositoryConfiguration {
}
