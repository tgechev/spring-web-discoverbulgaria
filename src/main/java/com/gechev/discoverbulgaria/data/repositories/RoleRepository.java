package com.gechev.discoverbulgaria.data.repositories;

import com.gechev.discoverbulgaria.data.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByAuthority(String authority);
}
