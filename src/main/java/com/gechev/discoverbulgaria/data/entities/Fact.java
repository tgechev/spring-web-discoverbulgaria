package com.gechev.discoverbulgaria.data.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "facts")
@Getter
@Setter
@NoArgsConstructor
public class Fact extends BaseEntity {
    @Column
    private String description;
    @Column
    private Region region;
}
