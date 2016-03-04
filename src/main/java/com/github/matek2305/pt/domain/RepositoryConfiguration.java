package com.github.matek2305.pt.domain;

import com.github.matek2305.pt.domain.entity.BaseEntity;
import com.github.matek2305.pt.domain.repository.BaseRepository;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Configuration
@EntityScan(basePackageClasses = BaseEntity.class)
@EnableJpaRepositories(basePackageClasses = BaseRepository.class)
@EnableTransactionManagement
@EnableJpaAuditing
public class RepositoryConfiguration {
}
