package com.gechev.discoverbulgaria.data.repositories;

import com.gechev.discoverbulgaria.data.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByAuthority(String authority);
}
