package com.htec.travel.airports;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, BigInteger> {

    Optional<Airport> findByAirportId(BigInteger airportId);
}
