package io.github.matek2305.pt.api;

import io.github.matek2305.pt.api.exception.ResourceNotFoundException;
import io.github.matek2305.pt.api.resource.PredictionResource;
import io.github.matek2305.pt.service.MatchPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@RestController
@RequestMapping("/predictions")
public class PredictionsController {

    private final MatchPredictionService matchPredictionService;

    @Autowired
    public PredictionsController(MatchPredictionService matchPredictionService) {
        this.matchPredictionService = matchPredictionService;
    }

    @RequestMapping(value = "/{predictionId}", method = RequestMethod.GET)
    public HttpEntity<PredictionResource> getMatchPrediction(@PathVariable("predictionId") final int predictionId) {
        PredictionResource predictionResource = matchPredictionService.getMatchPrediction(predictionId)
                .map(PredictionResource::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("match prediction not found for id="+predictionId));
        return ResponseEntity.ok(predictionResource);
    }
}
