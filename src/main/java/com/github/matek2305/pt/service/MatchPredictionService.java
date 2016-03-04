package com.github.matek2305.pt.service;

import com.github.matek2305.pt.domain.repository.MatchRepository;
import com.github.matek2305.pt.domain.entity.Match;
import com.github.matek2305.pt.domain.entity.MatchPrediction;
import com.github.matek2305.pt.domain.repository.MatchPredictionRepository;
import com.github.matek2305.pt.exception.ForbiddenResourceException;
import com.github.matek2305.pt.exception.ResourceNotFoundException;
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
    private final MatchRepository matchRepository;

    public Optional<MatchPrediction> getMatchPrediction(final int predictionId) {
        return matchPredictionRepository.findOptional(predictionId);
    }

    public Page<MatchPrediction> getPredictionPageForMatch(final int matchId, final int page, final int size) {
        Match match = matchRepository.findOptional(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono meczu dla id=", matchId));
        if (match.getStatus() == Match.Status.PREDICTION_AVAILABLE) {
            throw new ForbiddenResourceException("Lista typów jest zablokowana");
        }

        return matchPredictionRepository.findByMatchId(matchId, new PageRequest(page, size));
    }
}
