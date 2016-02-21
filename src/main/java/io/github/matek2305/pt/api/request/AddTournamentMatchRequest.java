package io.github.matek2305.pt.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>.
 */
@Getter
public class AddTournamentMatchRequest {

    @NotBlank(message = "Podaj nazwę drużyny gospodarzy")
    private final String homeTeamName;

    @NotBlank(message = "Podaj nazwę drużyny gości")
    private final String awayTeamName;

    @NotNull(message = "Podaj termin rozpoczęcia meczu")
    private final LocalDateTime startDate;

    @JsonCreator
    public AddTournamentMatchRequest(
            @JsonProperty("homeTeamName") String homeTeamName,
            @JsonProperty("awayTeamName") String awayTeamName,
            @JsonProperty("startDate") LocalDateTime startDate) {
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.startDate = startDate;
    }
}
