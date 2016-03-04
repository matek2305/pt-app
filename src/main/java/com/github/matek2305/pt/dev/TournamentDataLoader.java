package com.github.matek2305.pt.dev;

import com.github.matek2305.pt.domain.entity.PlayerPoints;
import com.github.matek2305.pt.domain.entity.Tournament;
import com.github.matek2305.pt.domain.repository.TournamentRepository;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Slf4j
@DataLoader
public class TournamentDataLoader implements KeyDataLoader<Tournament, TournamentDataLoader.TournamentDevEntity> {

    enum TournamentDevEntity {
        EURO_2016,
        PL_2015_16
    }

    private final Map<TournamentDevEntity, Tournament> entityMap = new EnumMap<>(TournamentDevEntity.class);
    private final SaveAndCountRepository<Tournament> tournamentRepository;

    @Autowired
    public TournamentDataLoader(TournamentRepository tournamentRepository) {
        this.tournamentRepository = new SaveAndCountRepository<>(tournamentRepository);
    }

    @Override
    public Tournament getDevEntity(TournamentDevEntity key) {
        return entityMap.get(key);
    }

    @Override
    public void load() {
        log.info("Loading tournament data ...");

        createTournament("Euro 2016", "UEFA Euro 2016 France", DevUsernames.JKOWALSKI)
                .addPlayerPoints(DevUsernames.JKOWALSKI, 12)
                .addPlayerPoints(DevUsernames.MURBANSKI, 10)
                .addPlayerPoints(DevUsernames.PRAK, 10)
                .saveAs(TournamentDevEntity.EURO_2016);

        createTournament("Premier League 2015/16", "Barclays Premier League 2015/16", DevUsernames.ZMARTYNIUK)
                .addPlayerPoints(DevUsernames.JKOWALSKI, 22)
                .addPlayerPoints(DevUsernames.MURBANSKI, 32)
                .saveAs(TournamentDevEntity.PL_2015_16);

        log.info("Tournament data ({}) loaded successfully", tournamentRepository.getCount());
    }

    private TournamentBuilder createTournament(String name, String desc, String admin) {
        Tournament tournament = new Tournament();
        tournament.setName(name);
        tournament.setDescription(desc);
        tournament.setAdmin(admin);
        return new TournamentBuilder(tournament);
    }

    @RequiredArgsConstructor
    private class TournamentBuilder {

        private final Tournament tournament;

        TournamentBuilder addPlayerPoints(String username, int points) {
            if (tournament.getPlayerPointsList() == null) {
                tournament.setPlayerPointsList(new ArrayList<>());
            }

            PlayerPoints playerPoints = new PlayerPoints();
            playerPoints.setUsername(username);
            playerPoints.setPoints(points);
            tournament.getPlayerPointsList().add(playerPoints);
            return this;
        }

        void saveAs(TournamentDevEntity key) {
            entityMap.put(key, tournamentRepository.save(tournament));
        }

    }
}
