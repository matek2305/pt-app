package io.github.matek2305.pt.domain.repository;

import io.github.matek2305.pt.domain.entity.Match;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Repository
public interface MatchRepository extends BaseRepository<Match> {

    List<Match> findByTournamentId(int tournamentId);
}
