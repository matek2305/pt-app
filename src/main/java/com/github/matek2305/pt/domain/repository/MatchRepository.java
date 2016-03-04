package com.github.matek2305.pt.domain.repository;

import com.github.matek2305.pt.domain.entity.Match;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Repository
public interface MatchRepository extends BaseRepository<Match> {

    Page<Match> findByTournamentId(int tournamentId, Pageable pageable);

    List<Match> findTop10ByTournamentPlayerPointsListUsernameAndStatusOrderByStartDateAsc(String username, Match.Status status);
}
