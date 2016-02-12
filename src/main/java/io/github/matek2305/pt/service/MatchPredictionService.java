package io.github.matek2305.pt.service;

import io.github.matek2305.pt.domain.entity.MatchPrediction;
import io.github.matek2305.pt.domain.repository.MatchPredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Service
@Transactional
public class MatchPredictionService {

    private final MatchPredictionRepository matchPredictionRepository;

    @Autowired
    public MatchPredictionService(MatchPredictionRepository matchPredictionRepository) {
        this.matchPredictionRepository = matchPredictionRepository;
    }

    public List<MatchPrediction> getPredictionListForMatch(final int matchId) {
        return matchPredictionRepository.findByMatchId(matchId);
    }
}
