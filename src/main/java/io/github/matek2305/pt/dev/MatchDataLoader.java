package io.github.matek2305.pt.dev;

import io.github.matek2305.pt.domain.Match;
import io.github.matek2305.pt.repository.MatchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import pl.murbanski.spring.dataloader.DataLoader;

import java.time.LocalDateTime;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Slf4j
public class MatchDataLoader implements DataLoader {

    @Autowired
    private MatchRepository matchRepository;

    @Override
    public void load() {
        log.info("Loading match data ...");
        Match testMatch = new Match();
        testMatch.setHomeTeamName("Poland");
        testMatch.setAwayTeamName("Germany");
        testMatch.setStartDate(LocalDateTime.now().plusDays(2));
        testMatch.setStatus(Match.Status.PREDICTION_AVAILABLE);
        matchRepository.save(testMatch);
        log.info("Match data loaded successfully");
    }
}
