package io.github.matek2305.pt.domain.repository;

import io.github.matek2305.pt.domain.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Integer> {
}
