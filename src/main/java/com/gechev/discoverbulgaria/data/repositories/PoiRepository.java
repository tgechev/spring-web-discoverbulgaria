package com.gechev.discoverbulgaria.data.repositories;

import com.gechev.discoverbulgaria.data.models.Poi;
import com.gechev.discoverbulgaria.data.models.Region;
import com.gechev.discoverbulgaria.data.models.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PoiRepository extends JpaRepository<Poi, String> {
    Set<Poi> findAllByType(Type type);
    Set<Poi> findAllByRegion(Region region);
    Set<Poi> findAllByRegionAndType(Region region, Type type);
    Optional<Poi> findByTitle(String name);
}
