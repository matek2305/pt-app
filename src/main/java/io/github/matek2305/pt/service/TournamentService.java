package io.github.matek2305.pt.service;

import io.github.matek2305.pt.domain.entity.Tournament;
import io.github.matek2305.pt.domain.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@TransactionalService
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    @Autowired
    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public Optional<Tournament> getTournament(final int id) {
        return tournamentRepository.findOptional(id);
    }
}
