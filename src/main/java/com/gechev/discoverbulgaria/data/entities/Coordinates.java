package com.gechev.discoverbulgaria.data.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "coordinates")
@Getter
@Setter
@NoArgsConstructor
public class Coordinates extends BaseEntity {
    @Column
    private Double longitude;
    @Column
    private Double latitude;
}
