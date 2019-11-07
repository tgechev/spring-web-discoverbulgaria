package com.gechev.discoverbulgaria.data.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority {

    @Column
    private String authority;

    public Role() {
    }

    public Role(String authority){
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}

