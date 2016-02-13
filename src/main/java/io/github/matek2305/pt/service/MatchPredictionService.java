package io.github.matek2305.pt.service;

import io.github.matek2305.pt.domain.entity.MatchPrediction;
import io.github.matek2305.pt.domain.repository.MatchPredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@TransactionalService
public class MatchPredictionService {

    private final MatchPredictionRepository matchPredictionRepository;

    @Autowired
    public MatchPredictionService(MatchPredictionRepository matchPredictionRepository) {
        this.matchPredictionRepository = matchPredictionRepository;
    }

    public List<MatchPrediction> getPredictionListForMatch(final int matchId) {
        return matchPredictionRepository.findByMatchId(matchId);
    }

    public Optional<MatchPrediction> getMatchPrediction(final int predictionId) {
        return matchPredictionRepository.findOptional(predictionId);
    }
}
