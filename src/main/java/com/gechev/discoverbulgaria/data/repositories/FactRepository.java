package com.gechev.discoverbulgaria.data.repositories;

import com.gechev.discoverbulgaria.data.models.Fact;
import com.gechev.discoverbulgaria.data.models.Region;
import com.gechev.discoverbulgaria.data.models.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FactRepository extends JpaRepository<Fact, String> {
    Set<Fact> findAllByRegion(Region region);
    Set<Fact> findAllByRegionAndType(Region region, Type type);
    Set<Fact> findAllByType(Type type);
}
