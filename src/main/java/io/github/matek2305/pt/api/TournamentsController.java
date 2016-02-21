package io.github.matek2305.pt.api;

import io.github.matek2305.pt.api.request.AddTournamentMatchRequest;
import io.github.matek2305.pt.api.request.CreateTournamentRequest;
import io.github.matek2305.pt.api.resource.MatchResource;
import io.github.matek2305.pt.api.resource.TournamentResource;
import io.github.matek2305.pt.api.response.PageResponse;
import io.github.matek2305.pt.domain.entity.Match;
import io.github.matek2305.pt.domain.entity.Tournament;
import io.github.matek2305.pt.exception.ResourceNotFoundException;
import io.github.matek2305.pt.service.MatchService;
import io.github.matek2305.pt.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public TournamentResource createTournament(@Valid @NotNull @RequestBody CreateTournamentRequest request) {
        Tournament tournament = tournamentService.createTournament(request.getName(), request.getDescription());
        return TournamentResource.fromEntity(tournament);
    }

    @RequestMapping(value = "/{id}/matches")
    public PageResponse<MatchResource> getMatchListFromTournament(
            @PathVariable("id") final int tournamentId,
            @RequestParam("page") final int page,
            @RequestParam("size") final int size) {

        Page<Match> matchPage = matchService.getMatchPageFromTournament(tournamentId, page, size);
        List<MatchResource> matchResourceList = matchPage.getContent()
                .stream()
                .map(MatchResource::fromEntity)
                .collect(Collectors.toList());

        PageResponse<MatchResource> response = new PageResponse<>(matchResourceList, matchPage.getTotalElements());
        if (matchPage.hasPrevious()) {
            response.addPrevPageLink(methodOn(getClass()).getMatchListFromTournament(tournamentId, page - 1, size));
        }
        if (matchPage.hasNext()) {
            response.addNextPageLink(methodOn(getClass()).getMatchListFromTournament(tournamentId, page + 1, size));
        }

        return response;
    }

    @RequestMapping(value = "/{id}/matches", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public MatchResource addTournamentMatch(
            @PathVariable("id") final int tournamentId,
            @NotNull @Valid @RequestBody AddTournamentMatchRequest request) {
        Match match = tournamentService.addTournamentMatch(tournamentId, request.getHomeTeamName(), request.getAwayTeamName(), request.getStartDate());
        return MatchResource.fromEntity(match);
    }
}
