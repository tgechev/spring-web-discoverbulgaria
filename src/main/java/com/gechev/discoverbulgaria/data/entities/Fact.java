package com.gechev.discoverbulgaria.data.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "facts")
@Getter
@Setter
@NoArgsConstructor
public class Fact extends BaseEntity {
    @Column
    private String description;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Type type;

    @ManyToOne(targetEntity = Region.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private Region region;
}
