package io.github.matek2305.pt.service;

import io.github.matek2305.pt.domain.entity.MatchPrediction;
import io.github.matek2305.pt.domain.repository.MatchPredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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

    public Optional<MatchPrediction> getMatchPrediction(final int predictionId) {
        return matchPredictionRepository.findOptional(predictionId);
    }

    public Page<MatchPrediction> getPredictionPageForMatch(final int matchId, final int page, final int size) {
        return matchPredictionRepository.findByMatchId(matchId, new PageRequest(page, size));
    }
}
