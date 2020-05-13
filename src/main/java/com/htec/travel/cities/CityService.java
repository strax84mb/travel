package com.htec.travel.cities;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

public interface CityService {

    CityDto createCity(CreateCityDto input);

    List<CityDto> getAll(Integer maxComments);

    CityDto getById(BigInteger id, Integer maxComments);

    List<CityDto> findByName(String searchString, Integer maxComments);

    long parseDataset(InputStream inputStream);
}
