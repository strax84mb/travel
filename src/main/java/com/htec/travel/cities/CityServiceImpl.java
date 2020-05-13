package com.htec.travel.cities;

import com.htec.travel.airports.AbstractCSVService;
import com.htec.travel.airports.IncorrectColumnsCountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl extends AbstractCSVService implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ConverterUtil converterUtil;

    @Override
    public CityDto createCity(CreateCityDto input) {
        var city = City.builder()
                .name(input.getName())
                .country(input.getCountry())
                .description(input.getDescription())
                .build();
        city = cityRepository.save(city);
        return converterUtil.convertCityEntity(city, Integer.MAX_VALUE);
    }

    @Override
    public List<CityDto> getAll(Integer maxComments) {
        return cityRepository.findAll().stream()
                .map( city -> converterUtil.convertCityEntity(city, maxComments))
                .collect(Collectors.toList());
    }

    @Override
    public CityDto getById(BigInteger id, Integer maxComments) {
        return converterUtil.convertCityEntity(
                cityRepository.findById(id).get(), maxComments);
    }

    @Override
    public List<CityDto> findByName(String searchString, Integer maxComments) {
        return cityRepository.findByNameContainingIgnoreCase(searchString).stream()
                .map(city -> converterUtil.convertCityEntity(city, maxComments))
                .collect(Collectors.toList());
    }

    @Override
    public boolean parseAndSaveLine(String[] fields) {
        try {
            if (fields.length != 3) {
                throw new IncorrectColumnsCountException();
            }
            var city = City.builder()
                    .name(fields[0])
                    .country(fields[1])
                    .description(fields[2])
                    .build();
            cityRepository.save(city);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
