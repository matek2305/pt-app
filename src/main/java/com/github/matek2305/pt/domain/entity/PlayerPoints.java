package com.github.matek2305.pt.domain.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Data
@Embeddable
public class PlayerPoints {

    @Column
    private String username;

    @Column
    private int points;
}
