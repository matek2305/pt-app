package io.github.matek2305.pt.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class MatchPrediction extends BaseEntity {

    @Column
    private int homeTeamScore;

    @Column
    private int awayTeamScore;

    @Column
    private LocalDateTime enabledUntil;

    @Column
    private int points;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    public enum Status {
        OPEN,
        CLOSED,
        POINTS_AVAILABLE
    }
}
