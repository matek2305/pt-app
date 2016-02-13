package io.github.matek2305.pt.api;

import io.github.matek2305.pt.api.exception.ResourceNotFoundException;
import io.github.matek2305.pt.api.resource.MatchResource;
import io.github.matek2305.pt.api.resource.PredictionResource;
import io.github.matek2305.pt.api.response.ListResponse;
import io.github.matek2305.pt.domain.entity.Match;
import io.github.matek2305.pt.service.MatchPredictionService;
import io.github.matek2305.pt.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@RestController
@RequestMapping("/matches")
public class MatchesController {

    private final MatchService matchService;
    private final MatchPredictionService matchPredictionService;

    @Autowired
    public MatchesController(
            final MatchService matchService,
            final MatchPredictionService matchPredictionService) {
        this.matchService = matchService;
        this.matchPredictionService = matchPredictionService;
    }


    @RequestMapping(value = "/{matchId}", method = RequestMethod.GET)
    public HttpEntity<MatchResource> getMatch(@PathVariable("matchId") final int matchId) {
        MatchResource matchResource = matchService.getMatch(matchId)
                .map(MatchResource::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("match resource nt found for id=" + matchId));
        return ResponseEntity.ok(matchResource);
    }

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<ListResponse<MatchResource>> getMatchList(@RequestParam("page") final int page, @RequestParam("size") final int size) {
        Page<Match> matchPage = matchService.getMatchPage(page, size);
        List<MatchResource> matchResourceList = matchPage.getContent()
                .stream()
                .map(MatchResource::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ListResponse<>(matchResourceList, matchPage.getTotalElements()));
    }

    @RequestMapping(value = "/{matchId}/predictions", method = RequestMethod.GET)
    public HttpEntity<List<PredictionResource>> getPredictionListForMatch(@PathVariable("matchId") final int matchId) {
        List<PredictionResource> predictionResourceList = matchPredictionService.getPredictionListForMatch(matchId)
                .stream()
                .map(PredictionResource::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(predictionResourceList);
    }
}
