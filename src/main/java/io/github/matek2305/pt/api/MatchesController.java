package io.github.matek2305.pt.api;

import io.github.matek2305.pt.api.exception.ResourceNotFoundException;
import io.github.matek2305.pt.api.response.ListResponse;
import io.github.matek2305.pt.domain.entity.Match;
import io.github.matek2305.pt.domain.entity.MatchPrediction;
import io.github.matek2305.pt.service.MatchPredictionService;
import io.github.matek2305.pt.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Match getMatch(@PathVariable("matchId") final int matchId) {
        return matchService.getMatch(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("match not found for id=" + matchId));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ListResponse<Match> getMatchList(@RequestParam("page") final int page, @RequestParam("size") final int size) {
        Page<Match> matchPage = matchService.getMatchPage(page, size);
        return new ListResponse<>(matchPage.getContent(), matchPage.getTotalElements());
    }

    @RequestMapping(value = "/{matchId}/predictions", method = RequestMethod.GET)
    public List<MatchPrediction> getPredictionListForMatch(@PathVariable("matchId") final int matchId) {
        return matchPredictionService.getPredictionListForMatch(matchId);
    }
}
