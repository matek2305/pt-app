package io.github.matek2305.pt.service;

import io.github.matek2305.pt.domain.entity.Match;
import io.github.matek2305.pt.domain.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MatchService {

    private final MatchRepository matchRepository;

    public Optional<Match> getMatch(final int matchId) {
        return matchRepository.findOptional(matchId);
    }

    public Page<Match> getMatchPageFromTournament(final int tournamentId, final int page, final int size) {
        return matchRepository.findByTournamentId(tournamentId, new PageRequest(page, size));
    }
}
