package io.github.matek2305.pt.api.resource;

import io.github.matek2305.pt.api.TournamentsController;
import io.github.matek2305.pt.domain.entity.Tournament;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TournamentResource extends ResourceSupport {

    private final String name;
    private final String description;
    private final String admin;

    public static TournamentResource fromEntity(Tournament tournament) {
        TournamentResource tournamentResource =
                new TournamentResource(tournament.getName(), tournament.getDescription(), tournament.getAdmin());

        tournamentResource.add(linkTo(methodOn(TournamentsController.class).getTournament(tournament.getId())).withSelfRel());
        tournamentResource.add(linkTo(methodOn(TournamentsController.class).getMatchListFromTournament(tournament.getId(), 0, 5)).withRel("matches"));

        return tournamentResource;
    }
}
