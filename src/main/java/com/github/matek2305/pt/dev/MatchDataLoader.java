package com.github.matek2305.pt.dev;

import com.github.matek2305.dataloader.annotations.LoadDataAfter;
import com.github.matek2305.pt.domain.repository.MatchRepository;
import com.github.matek2305.pt.domain.entity.Match;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

import static com.github.matek2305.pt.dev.TournamentDataLoader.TournamentDevEntity;
import static com.github.matek2305.pt.dev.TournamentDataLoader.TournamentDevEntity.EURO_2016;
import static com.github.matek2305.pt.dev.TournamentDataLoader.TournamentDevEntity.PL_2015_16;
import static java.time.LocalDateTime.now;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Slf4j
@DataLoader
@LoadDataAfter(TournamentDataLoader.class)
public class MatchDataLoader implements KeyDataLoader<Match, MatchDataLoader.MatchDevEntity> {

    enum MatchDevEntity {
        POLAND_VS_GERMANY,
        CHELSEA_VS_MANUTD,
        UKRAINE_VS_POLAND,
        LIV_VS_MCI,
        TOT_VS_ARS,
    }

    private final Map<MatchDevEntity, Match> entityMap = new EnumMap<>(MatchDevEntity.class);
    private final SaveAndCountRepository<Match> matchRepository;
    private final TournamentDataLoader tournamentDataLoader;

    @Autowired
    public MatchDataLoader(MatchRepository matchRepository, TournamentDataLoader tournamentDataLoader) {
        this.matchRepository = new SaveAndCountRepository<>(matchRepository);
        this.tournamentDataLoader = tournamentDataLoader;
    }

    @Override
    public void load() {
        log.info("Loading match data ...");

        create("Poland", "Germany", now().plusDays(2).withHour(20).withMinute(45), EURO_2016)
                .status(Match.Status.PREDICTION_AVAILABLE)
                .saveAs(MatchDevEntity.POLAND_VS_GERMANY);

        create("Ukraine", "Poland", now().plusDays(5).withHour(20).withMinute(45), EURO_2016)
                .status(Match.Status.PREDICTION_AVAILABLE)
                .saveAs(MatchDevEntity.UKRAINE_VS_POLAND);

        create("Chelsea FC", "Manchester United FC", now().plusMinutes(30), PL_2015_16)
                .status(Match.Status.PREDICTION_CLOSED)
                .saveAs(MatchDevEntity.CHELSEA_VS_MANUTD);

        create("Liverpool FC", "Manchester City FC", now().minusDays(2), PL_2015_16)
                .result(3, 0)
                .status(Match.Status.FINISHED)
                .saveAs(MatchDevEntity.LIV_VS_MCI);

        create("Spurs", "Arsenal", now().plusDays(2).withHour(16).withMinute(0), PL_2015_16)
                .status(Match.Status.PREDICTION_AVAILABLE)
                .saveAs(MatchDevEntity.TOT_VS_ARS);

        log.info("Match data ({}) loaded successfully", matchRepository.getCount());
    }

    @Override
    public Match getDevEntity(MatchDevEntity key) {
        return entityMap.get(key);
    }

    private MatchBuilder create(String homeTeamName, String awayTeamName, LocalDateTime startDate, TournamentDevEntity tournamentKey) {
        Match match = new Match();
        match.setHomeTeamName(homeTeamName);
        match.setAwayTeamName(awayTeamName);
        match.setStartDate(startDate);
        match.setTournament(tournamentDataLoader.getDevEntity(tournamentKey));
        return new MatchBuilder(match);
    }

    @RequiredArgsConstructor
    private class MatchBuilder {

        private final Match match;

        MatchBuilder result(int homeTeamScore, int awayTeamScore) {
            match.setHomeTeamScore(homeTeamScore);
            match.setAwayTeamScore(awayTeamScore);
            return this;
        }

        MatchBuilder status(Match.Status status) {
            match.setStatus(status);
            return this;
        }

        void saveAs(MatchDevEntity kay) {
            entityMap.put(kay, matchRepository.save(match));
        }
    }
}
