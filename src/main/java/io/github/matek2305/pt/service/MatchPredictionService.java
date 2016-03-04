package io.github.matek2305.pt.service;

import io.github.matek2305.pt.domain.entity.MatchPrediction;
import io.github.matek2305.pt.domain.repository.MatchPredictionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MatchPredictionService {

    private final MatchPredictionRepository matchPredictionRepository;

    public Optional<MatchPrediction> getMatchPrediction(final int predictionId) {
        return matchPredictionRepository.findOptional(predictionId);
    }

    public Page<MatchPrediction> getPredictionPageForMatch(final int matchId, final int page, final int size) {
        return matchPredictionRepository.findByMatchId(matchId, new PageRequest(page, size));
    }
}
