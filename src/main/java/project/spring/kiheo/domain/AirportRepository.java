package project.spring.kiheo.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, String> {
    //Airport saveAll(Airport Airport);
    List<Airport> findAll();
}
