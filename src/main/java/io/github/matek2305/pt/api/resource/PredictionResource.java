package io.github.matek2305.pt.api.resource;

import io.github.matek2305.pt.api.MatchesController;
import io.github.matek2305.pt.api.PredictionsController;
import io.github.matek2305.pt.domain.entity.MatchPrediction;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PredictionResource extends ResourceSupport {

    private final int homeTeamScore;
    private final int awayTeamScore;
    private final LocalDateTime enabledUntil;
    private final int points;
    private final MatchPrediction.Status status;

    public static PredictionResource fromEntity(MatchPrediction matchPrediction) {
        PredictionResource resource = new PredictionResource(
                matchPrediction.getHomeTeamScore(),
                matchPrediction.getAwayTeamScore(),
                matchPrediction.getEnabledUntil(),
                matchPrediction.getPoints(),
                matchPrediction.getStatus());

        resource.add(linkTo(methodOn(PredictionsController.class).getMatchPrediction(matchPrediction.getId())).withSelfRel());
        resource.add(linkTo(methodOn(MatchesController.class).getMatch(matchPrediction.getMatch().getId())).withRel("match"));

        return resource;
    }
}
