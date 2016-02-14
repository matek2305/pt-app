package io.github.matek2305.pt.api;

import io.github.matek2305.pt.exception.ResourceNotFoundException;
import io.github.matek2305.pt.api.request.CreateTournamentRequest;
import io.github.matek2305.pt.api.resource.MatchResource;
import io.github.matek2305.pt.api.resource.TournamentResource;
import io.github.matek2305.pt.domain.entity.Tournament;
import io.github.matek2305.pt.service.MatchService;
import io.github.matek2305.pt.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for "/tournaments" resource.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@RestController
@RequestMapping("/tournaments")
public class TournamentsController extends BaseExceptionHandler {

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

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public TournamentResource createTournament(@Valid @NotNull @RequestBody CreateTournamentRequest request) {
        Tournament tournament = tournamentService.createTournament(request.getName(), request.getDescription());
        return TournamentResource.fromEntity(tournament);
    }
}
