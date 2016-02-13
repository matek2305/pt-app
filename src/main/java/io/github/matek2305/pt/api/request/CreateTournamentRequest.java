package io.github.matek2305.pt.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Getter
public class CreateTournamentRequest {

    @NotBlank(message = "Podaj nazwę turnieju")
    private final String name;
    private final String description;

    @JsonCreator
    public CreateTournamentRequest(
            @JsonProperty("name") String name,
            @JsonProperty("description") String description) {
        this.name = name;
        this.description = description;
    }
}
