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

    @Column(name = "region_id", nullable = false)
    private String regionId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer population;

    @Column(nullable = false)
    private Double area;

    @OneToMany(mappedBy = "region")
    private Set<Poi> poi;

    @OneToMany(mappedBy = "region")
    private Set<Fact> facts;
}
