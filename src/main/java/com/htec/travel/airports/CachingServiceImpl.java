package com.htec.travel.airports;

import com.htec.travel.cities.City;
import com.htec.travel.cities.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class CachingServiceImpl implements CachingService {

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public Optional<Airport> findAirportById(BigInteger id) {
        return airportRepository.findByAirportId(id);
    }

    @Override
    public Optional<Route> findRouteById(BigInteger id) {
        return routeRepository.findById(id);
    }

    @Override
    public Optional<City> findCityByNameAndCountryIgnoreCase(String name, String country) {
        return cityRepository.findByNameAndCountryIgnoreCase(name, country);
    }
}
