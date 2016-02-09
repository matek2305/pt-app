package io.github.matek2305.pt.dev;

import com.github.matek2305.dataloader.DataLoader;
import com.github.matek2305.dataloader.annotations.LoadDataAfter;
import io.github.matek2305.pt.domain.entity.Match;
import io.github.matek2305.pt.domain.entity.Tournament;
import io.github.matek2305.pt.domain.repository.TournamentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Slf4j
@DevComponent
@LoadDataAfter(MatchDataLoader.class)
public class TournamentDataLoader implements DataLoader {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private MatchDataLoader matchDataLoader;

    private int count = 0;

    @Override
    public void load() {
        log.info("Loading tournament data ...");
        createTournament("Euro 2012", "UEFA Euro 2012 Poland Ukraine", matchDataLoader.polandVsGermany);
        createTournament("Premier League 2015/16", "Barclays Premier League 2015/16", matchDataLoader.chelseaVsManUtd);
        log.info("Tournament data ({}) loaded successfully", count);
    }

    private Tournament createTournament(String name, String desc, Match... matches) {
        count++;
        Tournament tournament = new Tournament();
        tournament.setName(name);
        tournament.setDescription(desc);
        Stream.of(matches).forEach(tournament::addMatch);
        return tournamentRepository.save(tournament);
    }
}
