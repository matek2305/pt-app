package io.github.matek2305.pt.dev;

import io.github.matek2305.pt.domain.entity.Match;
import io.github.matek2305.pt.domain.repository.MatchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

import static java.time.LocalDateTime.now;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Slf4j
@DevComponent
public class MatchDataLoader implements KeyDataLoader<Match, MatchDataLoader.MatchDevEntity> {

    enum MatchDevEntity {
        POLAND_VS_GERMANY,
        CHELSEA_VS_MANUTD,
        UKRAINE_VS_POLAND
    }

    private final Map<MatchDevEntity, Match> entityMap = new EnumMap<>(MatchDevEntity.class);
    private final SaveAndCountRepository<Match> matchRepository;

    @Autowired
    public MatchDataLoader(MatchRepository matchRepository) {
        this.matchRepository = new SaveAndCountRepository<>(matchRepository);
    }

    @Override
    public void load() {
        log.info("Loading match data ...");
        createAndSavePredictionAvailable(MatchDevEntity.POLAND_VS_GERMANY, "Poland", "Germany", now().plusDays(2).withHour(20).withMinute(45));
        createAndSavePredictionAvailable(MatchDevEntity.UKRAINE_VS_POLAND, "Ukraine", "Poland", now().plusDays(5).withHour(20).withMinute(45));
        createAndSavePredictionAvailable(MatchDevEntity.CHELSEA_VS_MANUTD, "Chelsea FC", "Manchester United FC", now().plusDays(1).withHour(17).withMinute(0));
        log.info("Match data ({}) loaded successfully", matchRepository.getCount());
    }

    @Override
    public Match getDevEntity(MatchDevEntity key) {
        return entityMap.get(key);
    }

    private void createAndSavePredictionAvailable(MatchDevEntity key, String homeTeamName, String awayTeamName, LocalDateTime startDate) {
        Match match = new Match();
        match.setHomeTeamName(homeTeamName);
        match.setAwayTeamName(awayTeamName);
        match.setStartDate(startDate);
        match.setStatus(Match.Status.PREDICTION_AVAILABLE);
        entityMap.put(key, matchRepository.save(match));
    }
}
