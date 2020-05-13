package com.htec.travel.airports;

import com.htec.travel.cities.City;
import org.springframework.cache.annotation.Cacheable;

import java.math.BigInteger;
import java.util.Optional;

public interface CachingService {

    @Cacheable(key = "#id", value = "airport_cache")
    Optional<Airport> findAirportById(BigInteger id);

    @Cacheable(key = "#id", value = "route_cache")
    Optional<Route> findRouteById(BigInteger id);

    @Cacheable(key = "{#name, #country}", value = "city_cache")
    Optional<City> findCityByNameAndCountryIgnoreCase(String name, String country);
}
