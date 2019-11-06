package com.gechev.discoverbulgaria.data.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "poi")
@Getter
@Setter
@NoArgsConstructor
public class Poi extends BaseEntity {
    @Column
    private String name;
    @Column
    private String address;
    @Column
    private Coordinates coordinates;
    @Column
    private PoiType type;
    private Region region;
}
