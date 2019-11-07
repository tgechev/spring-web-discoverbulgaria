package com.gechev.discoverbulgaria.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "regions")
@Getter
@Setter
@NoArgsConstructor
public class Region extends BaseEntity {
    @Column
    private String name;
    @Column
    private Integer population;
    @Column
    private Double area;

    @OneToMany(mappedBy = "region")
    private Set<Poi> poi;

    @OneToMany(mappedBy = "region")
    private Set<Fact> facts;
}
