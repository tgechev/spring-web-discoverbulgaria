package com.gechev.discoverbulgaria.data.repositories;

import com.gechev.discoverbulgaria.data.models.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinatesRepository extends JpaRepository<Coordinates, String> {

}
