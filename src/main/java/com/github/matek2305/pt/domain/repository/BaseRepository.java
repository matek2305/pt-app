package com.github.matek2305.pt.domain.repository;

import com.github.matek2305.pt.domain.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Integer> {

    default Optional<T> findOptional(@NotNull Integer id) {
        return Optional.ofNullable(findOne(id));
    }
}
