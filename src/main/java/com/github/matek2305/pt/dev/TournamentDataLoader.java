package com.github.matek2305.pt.dev;

import com.github.matek2305.pt.domain.repository.TournamentRepository;
import com.github.matek2305.pt.domain.entity.Tournament;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.EnumMap;
import java.util.Map;

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
        createTournament(TournamentDevEntity.EURO_2016, "Euro 2016", "UEFA Euro 2016 France", DevUsernames.JKOWALSKI);
        createTournament(TournamentDevEntity.PL_2015_16, "Premier League 2015/16", "Barclays Premier League 2015/16", DevUsernames.ZMARTYNIUK);
        log.info("Tournament data ({}) loaded successfully", tournamentRepository.getCount());
    }

    private void createTournament(TournamentDevEntity key, String name, String desc, String admin) {
        Tournament tournament = new Tournament();
        tournament.setName(name);
        tournament.setDescription(desc);
        tournament.setAdmin(admin);
        entityMap.put(key, tournamentRepository.save(tournament));
    }
}
