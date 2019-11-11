package com.gechev.discoverbulgaria.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "poi")
@Getter
@Setter
@NoArgsConstructor
public class Poi extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column
    private String address;

    @Column(nullable = false, columnDefinition="TEXT")
    private String description;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Type type;

    @OneToOne(targetEntity = Coordinates.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinates_id", referencedColumnName = "id")
    private Coordinates coordinates;

    @ManyToOne(targetEntity = Region.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private Region region;
}
