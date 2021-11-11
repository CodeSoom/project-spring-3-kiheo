package project.spring.kiheo.application;

import com.github.dozermapper.core.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.spring.kiheo.domain.Airport;
import project.spring.kiheo.domain.AirportRepository;
import project.spring.kiheo.domain.User;
import project.spring.kiheo.domain.UserRepository;
import project.spring.kiheo.dto.UserRegistrationData;
import project.spring.kiheo.errors.UserEmailDuplicationException;
import project.spring.kiheo.errors.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
/*@Transactional*/
public class AirportService {
    private final Mapper mapper;
    private final AirportRepository airportRepository;

    public AirportService(Mapper dozerMapper, AirportRepository airportRepository) {
        this.mapper = dozerMapper;
        this.airportRepository = airportRepository;
    }

    public void registerAirports() {
        List<Airport> airports = new ArrayList<>();
        airports.add(Airport.builder().airportId("ttt").airportNm("ttt1").build());
        airports.add(Airport.builder().airportId("aaa").airportNm("aaa1").build());
        airportRepository.saveAll(airports);
    }
}