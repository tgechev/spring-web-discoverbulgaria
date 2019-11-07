package com.gechev.discoverbulgaria.data.repositories;

import com.gechev.discoverbulgaria.data.models.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, String> {
    Region findByName(String name);
}
