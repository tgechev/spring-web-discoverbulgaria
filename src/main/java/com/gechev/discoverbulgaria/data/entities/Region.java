package com.gechev.discoverbulgaria.data.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "regions")
public class Region extends BaseEntity {
    @Column
    private String name;
    @Column
    private Integer population;
    @Column
    private Double area;
    private Set<Poi> poi;
    private Set<Fact> facts;
}
