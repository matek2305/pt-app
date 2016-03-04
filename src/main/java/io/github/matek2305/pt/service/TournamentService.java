package io.github.matek2305.pt.service;

import io.github.matek2305.pt.domain.entity.Match;
import io.github.matek2305.pt.domain.entity.Tournament;
import io.github.matek2305.pt.domain.repository.MatchRepository;
import io.github.matek2305.pt.domain.repository.TournamentRepository;
import io.github.matek2305.pt.exception.ResourceNotFoundException;
import io.github.matek2305.pt.exception.ValidationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static pl.wavesoftware.eid.utils.EidPreconditions.checkArgument;
import static pl.wavesoftware.eid.utils.EidPreconditions.checkNotNull;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final MatchRepository matchRepository;

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

    public Match addTournamentMatch(final int tournamentId, String homeTeamName, String awayTeamName, LocalDateTime startDate) {
        checkArgument(isNotBlank(homeTeamName), "20160217:212204");
        checkArgument(isNotBlank(awayTeamName), "20160217:212208");
        checkNotNull(startDate, "20160217:212210");

        Tournament tournament = tournamentRepository.findOptional(tournamentId)
                .orElseThrow(() -> new ResourceNotFoundException("tournament resource not found for id=" + tournamentId));

        if (startDate.isBefore(LocalDateTime.now())) {
            throw new ValidationFailedException("Nieprawidłowe dane meczu, data rozpoczęcia nie może być wcześniejsza niż aktualna");
        } else if (startDate.isBefore(LocalDateTime.now().plusMinutes(90))) {
            throw new ValidationFailedException("Nieprawidłowe dane meczu, data rozpoczęcia nie może być poźniejsza niż " + LocalDateTime.now().minusMinutes(90));
        }

        Match match = Match.createPredictionAvailable(homeTeamName, awayTeamName, startDate, tournament);
        return matchRepository.save(match);
    }
}
