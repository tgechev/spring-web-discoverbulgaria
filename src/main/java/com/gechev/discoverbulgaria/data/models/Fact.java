package com.gechev.discoverbulgaria.data.models;

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

    @Column(nullable = false, columnDefinition="TEXT")
    private String description;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Type type;

    @ManyToOne(targetEntity = Region.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private Region region;
}
