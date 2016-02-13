package io.github.matek2305.pt.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;

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

    public static Tournament create(String name, String description) {
        Tournament tournament = new Tournament();
        tournament.setName(name);
        tournament.setDescription(description);
        return tournament;
    }
}
