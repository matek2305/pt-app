package com.github.matek2305.pt.domain.repository;

import com.github.matek2305.pt.domain.entity.Tournament;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Repository
public interface TournamentRepository extends BaseRepository<Tournament> {

    Optional<Tournament> findByName(String name);
}
