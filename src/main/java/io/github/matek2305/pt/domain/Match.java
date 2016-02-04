package io.github.matek2305.pt.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Match extends BaseEntity {

    private String homeTeamName;
    private String awayTeamName;
    private int homeTeamScore;
    private int awayTeamScore;
    private LocalDateTime startDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PREDICTION_AVAILABLE,
        PREDICTION_CLOSED,
        STARTED,
        CANCELED,
        FINISHED
    }
}
