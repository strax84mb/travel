package com.htec.travel.cities;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, BigInteger> {

    List<City> findByNameContainingIgnoreCase(String searchString);

    Optional<City> findByNameAndCountryIgnoreCase(String name, String country);
}
