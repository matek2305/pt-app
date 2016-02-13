package io.github.matek2305.pt.api;

import io.github.matek2305.pt.api.exception.ResourceNotFoundException;
import io.github.matek2305.pt.api.resource.MatchResource;
import io.github.matek2305.pt.api.resource.TournamentResource;
import io.github.matek2305.pt.service.MatchService;
import io.github.matek2305.pt.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for "/tournaments" resource.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@RestController
@RequestMapping("/tournaments")
public class TournamentsController {

    private final TournamentService tournamentService;
    private final MatchService matchService;

    @Autowired
    public TournamentsController(TournamentService tournamentService, MatchService matchService) {
        this.tournamentService = tournamentService;
        this.matchService = matchService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TournamentResource getTournament(@PathVariable("id") final int tournamentId) {
        return tournamentService.getTournament(tournamentId)
                .map(TournamentResource::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("tournament resource not found for id=" + tournamentId));
    }

    @RequestMapping(value = "/{id}/matches")
    public List<MatchResource> getMatchListFromTournament(@PathVariable("id") final int tournamentId) {
        return matchService.getMatchListFromTournament(tournamentId)
                .stream()
                .map(MatchResource::fromEntity)
                .collect(Collectors.toList());
    }
}
