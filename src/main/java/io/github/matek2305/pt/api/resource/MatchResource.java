package io.github.matek2305.pt.api.resource;

import io.github.matek2305.pt.api.MatchesController;
import io.github.matek2305.pt.api.TournamentsController;
import io.github.matek2305.pt.domain.entity.Match;
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
public class MatchResource extends ResourceSupport {

    private final String homeTeamName;
    private final String awayTeamName;
    private final int homeTeamScore;
    private final int awayTeamScore;
    private final LocalDateTime startDate;
    private final Match.Status status;

    public static MatchResource fromEntity(Match match) {
        MatchResource matchResource = new MatchResource(
                match.getHomeTeamName(),
                match.getAwayTeamName(),
                match.getHomeTeamScore(),
                match.getAwayTeamScore(),
                match.getStartDate(),
                match.getStatus()
        );

        matchResource.add(linkTo(methodOn(MatchesController.class).getMatch(match.getId())).withSelfRel());
        matchResource.add(linkTo(methodOn(MatchesController.class).getPredictionListForMatch(match.getId(), 0, 5)).withRel("predictions"));
        matchResource.add(linkTo(methodOn(TournamentsController.class).getTournament(match.getTournament().getId())).withRel("tournament"));

        return matchResource;
    }
}
