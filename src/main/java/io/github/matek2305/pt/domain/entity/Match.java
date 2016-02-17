package io.github.matek2305.pt.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Match extends BaseEntity {

    @Column
    private String homeTeamName;

    @Column
    private String awayTeamName;

    @Column
    private int homeTeamScore;

    @Column
    private int awayTeamScore;

    @Column
    private LocalDateTime startDate;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    public enum Status {
        PREDICTION_AVAILABLE,
        PREDICTION_CLOSED,
        STARTED,
        CANCELED,
        FINISHED
    }
}
