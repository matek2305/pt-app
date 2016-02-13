package io.github.matek2305.pt.service;

import io.github.matek2305.pt.api.resource.MatchResource;
import io.github.matek2305.pt.domain.entity.Match;
import io.github.matek2305.pt.domain.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@TransactionalService
public class MatchService {

    private final MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public Page<Match> getMatchPage(final int page, final int size) {
        return matchRepository.findAll(new PageRequest(page, size));
    }

    public Optional<Match> getMatch(final int matchId) {
        return matchRepository.findOptional(matchId);
    }

    public List<Match> getMatchListFromTournament(final int tournamentId) {
        return matchRepository.findByTournamentId(tournamentId);
    }
}
