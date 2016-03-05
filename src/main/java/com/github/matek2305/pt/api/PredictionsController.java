package com.github.matek2305.pt.api;

import com.github.matek2305.pt.service.MatchPredictionService;
import com.github.matek2305.pt.api.resource.PredictionResource;
import com.github.matek2305.pt.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for "/predictions" resource.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@RestController
@RequestMapping("/predictions")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PredictionsController {

    private final MatchPredictionService matchPredictionService;

    @RequestMapping(value = "/{predictionId}", method = RequestMethod.GET)
    public PredictionResource getMatchPrediction(@PathVariable("predictionId") final int predictionId) {
        return matchPredictionService.getMatchPrediction(predictionId)
                .map(PredictionResource::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("match prediction not found for id=%s", predictionId));
    }
}
