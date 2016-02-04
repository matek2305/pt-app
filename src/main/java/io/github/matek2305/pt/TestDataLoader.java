package io.github.matek2305.pt;

import io.github.matek2305.pt.domain.Match;
import io.github.matek2305.pt.repository.MatchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Slf4j
@Profile("dev")
@Component
public class TestDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private MatchRepository matchRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Loading test data ...");
        Match testMatch = new Match();
        testMatch.setHomeTeamName("Poland");
        testMatch.setAwayTeamName("Germany");
        testMatch.setStartDate(LocalDateTime.now().plusDays(2));
        testMatch.setStatus(Match.Status.PREDICTION_AVAILABLE);
        matchRepository.save(testMatch);
        log.info("Test data loaded successfully");
    }
}
