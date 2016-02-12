package io.github.matek2305.pt.dev;

import com.github.matek2305.dataloader.DataLoader;
import com.github.matek2305.dataloader.annotations.LoadDataAfter;
import io.github.matek2305.pt.domain.entity.Match;
import io.github.matek2305.pt.domain.entity.MatchPrediction;
import io.github.matek2305.pt.domain.repository.MatchPredictionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Slf4j
@DevComponent
@LoadDataAfter(MatchDataLoader.class)
public class MatchPredictionDataLoader implements DataLoader {

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
        createAndSavePredicitonOpen(0, 0, matchDataLoader.getDevEntity(MatchDataLoader.MatchDevEntity.POLAND_VS_GERMANY));
        createAndSavePredicitonOpen(1, 1, matchDataLoader.getDevEntity(MatchDataLoader.MatchDevEntity.POLAND_VS_GERMANY));
        createAndSavePredicitonOpen(0, 2, matchDataLoader.getDevEntity(MatchDataLoader.MatchDevEntity.POLAND_VS_GERMANY));
        createAndSavePredicitonOpen(1, 3, matchDataLoader.getDevEntity(MatchDataLoader.MatchDevEntity.UKRAINE_VS_POLAND));
        createAndSavePredicitonOpen(0, 2, matchDataLoader.getDevEntity(MatchDataLoader.MatchDevEntity.UKRAINE_VS_POLAND));
        log.info("Prediction data ({}) loaded successfully", matchPredictionRepository.getCount());
    }

    private MatchPrediction createAndSavePredicitonOpen(int homeTeamScore, int awayTeamScore, Match match) {
        MatchPrediction matchPrediction = new MatchPrediction();
        matchPrediction.setHomeTeamScore(homeTeamScore);
        matchPrediction.setAwayTeamScore(awayTeamScore);
        matchPrediction.setEnabledUntil(match.getStartDate().minusMinutes(90));
        matchPrediction.setStatus(MatchPrediction.Status.OPEN);
        matchPrediction.setMatch(match);
        matchPrediction.setPoints(0);
        return matchPredictionRepository.save(matchPrediction);
    }
}
