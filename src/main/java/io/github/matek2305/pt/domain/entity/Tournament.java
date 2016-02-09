package io.github.matek2305.pt.domain.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Tournament extends BaseEntity {

    @Column
    private String name;

    @Column
    private String description;

    @Setter(AccessLevel.NONE)
    @OneToMany
    @JoinColumn(name = "tournament_id")
    private List<Match> matchList;

    public Tournament addMatch(Match match) {
        if (matchList == null) {
            matchList = new ArrayList<>();
        }

        matchList.add(match);
        return this;
    }
}
