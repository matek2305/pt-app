package io.github.matek2305.pt.service;

import io.github.matek2305.pt.domain.entity.Tournament;
import io.github.matek2305.pt.domain.repository.TournamentRepository;
import io.github.matek2305.pt.service.exception.ValidationFailedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static pl.wavesoftware.eid.utils.EidPreconditions.checkArgument;
import static pl.wavesoftware.eid.utils.EidPreconditions.checkNotNull;

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

    public Tournament createTournament(String name, String description) {
        checkArgument(isNotBlank(name), "20160213:213725");

        if (tournamentRepository.findByName(name).isPresent()) {
            throw new ValidationFailedException("Nazwa " + name + " jest zajęta, wybierz inną.");
        }

        Tournament tournament = Tournament.create(name, description);
        return tournamentRepository.save(tournament);
    }
}
