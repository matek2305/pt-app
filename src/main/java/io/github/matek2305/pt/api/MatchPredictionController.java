package io.github.matek2305.pt.api;

import io.github.matek2305.pt.domain.entity.MatchPrediction;
import io.github.matek2305.pt.service.MatchPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@RestController
@RequestMapping("/matches/{matchId}/predictions")
public class MatchPredictionController {

    private final MatchPredictionService matchPredictionService;

    @Autowired
    public MatchPredictionController(MatchPredictionService matchPredictionService) {
        this.matchPredictionService = matchPredictionService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<MatchPrediction> getPredictionListForMatch(@PathVariable("matchId") final int matchId) {
        return matchPredictionService.getPredictionListForMatch(matchId);
    }
}
