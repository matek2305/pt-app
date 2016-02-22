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

    @Column
    private String admin;

    public static Tournament create(String name, String description, String admin) {
        Tournament tournament = new Tournament();
        tournament.setName(name);
        tournament.setDescription(description);
        tournament.setAdmin(admin);
        return tournament;
    }
}
