package com.github.matek2305.pt.dev;

import com.github.matek2305.dataloader.annotations.LoadDataAfter;
import com.github.matek2305.pt.domain.repository.MatchPredictionRepository;
import com.github.matek2305.pt.domain.entity.Match;
import com.github.matek2305.pt.domain.entity.MatchPrediction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Slf4j
@DataLoader
@LoadDataAfter(MatchDataLoader.class)
public class MatchPredictionDataLoader implements com.github.matek2305.dataloader.DataLoader {

    private final SaveAndCountRepository<MatchPrediction> matchPredictionRepository;
    private final MatchDataLoader matchDataLoader;

    @Autowired
    public MatchPredictionDataLoader(
            final MatchPredictionRepository matchPredictionRepository,
            final MatchDataLoader matchDataLoader) {
        this.matchPredictionRepository = new SaveAndCountRepository<>(matchPredictionRepository);
        this.matchDataLoader = matchDataLoader;
    }

    @Override
    public void load() {
        log.info("Loading prediction data ...");
        createAndSavePredicitonOpen(0, 0, MatchDataLoader.MatchDevEntity.POLAND_VS_GERMANY, DevUsernames.MURBANSKI);
        createAndSavePredicitonOpen(1, 1, MatchDataLoader.MatchDevEntity.POLAND_VS_GERMANY, DevUsernames.JKOWALSKI);
        createAndSavePredicitonOpen(0, 2, MatchDataLoader.MatchDevEntity.POLAND_VS_GERMANY, DevUsernames.PRAK);
        createAndSavePredicitonOpen(1, 3, MatchDataLoader.MatchDevEntity.UKRAINE_VS_POLAND, DevUsernames.MURBANSKI);
        createAndSavePredicitonOpen(0, 2, MatchDataLoader.MatchDevEntity.UKRAINE_VS_POLAND, DevUsernames.ZMARTYNIUK);
        log.info("Prediction data ({}) loaded successfully", matchPredictionRepository.getCount());
    }

    private MatchPrediction createAndSavePredicitonOpen(
            int homeTeamScore, int awayTeamScore, MatchDataLoader.MatchDevEntity matchDevEntity, String username) {
        Match match = matchDataLoader.getDevEntity(matchDevEntity);

        MatchPrediction matchPrediction = new MatchPrediction();
        matchPrediction.setHomeTeamScore(homeTeamScore);
        matchPrediction.setAwayTeamScore(awayTeamScore);
        matchPrediction.setEnabledUntil(match.getStartDate().minusMinutes(90));
        matchPrediction.setStatus(MatchPrediction.Status.OPEN);
        matchPrediction.setMatch(match);
        matchPrediction.setPoints(0);
        matchPrediction.setUsername(username);
        return matchPredictionRepository.save(matchPrediction);
    }
}
