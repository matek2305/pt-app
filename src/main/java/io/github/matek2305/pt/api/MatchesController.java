package io.github.matek2305.pt.api;

import io.github.matek2305.pt.api.resource.MatchResource;
import io.github.matek2305.pt.api.resource.PredictionResource;
import io.github.matek2305.pt.api.response.PageResponse;
import io.github.matek2305.pt.domain.entity.MatchPrediction;
import io.github.matek2305.pt.exception.ResourceNotFoundException;
import io.github.matek2305.pt.service.MatchPredictionService;
import io.github.matek2305.pt.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Controller for "/matches" resource.
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
    public MatchResource getMatch(@PathVariable("matchId") final int matchId) {
        return matchService.getMatch(matchId)
                .map(MatchResource::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("match resource nt found for id=" + matchId));
    }

    @RequestMapping(value = "/{matchId}/predictions", method = RequestMethod.GET)
    public PageResponse<PredictionResource> getPredictionListForMatch(
            @PathVariable("matchId") final int matchId,
            @RequestParam("page") final int page,
            @RequestParam("size") final int size) {

        Page<MatchPrediction> matchPredictionPage = matchPredictionService.getPredictionPageForMatch(matchId, page, size);
        List<PredictionResource> predictionResourceList = matchPredictionPage.getContent()
                .stream()
                .map(PredictionResource::fromEntity)
                .collect(Collectors.toList());

        PageResponse<PredictionResource> response = new PageResponse<>(predictionResourceList, matchPredictionPage.getTotalElements());
        if (matchPredictionPage.hasPrevious()) {
            response.addPrevPageLink(methodOn(getClass()).getPredictionListForMatch(matchId, page - 1, size));
        }
        if (matchPredictionPage.hasNext()) {
            response.addNextPageLink(methodOn(getClass()).getPredictionListForMatch(matchId, page + 1, size));
        }

        return response;
    }
}
