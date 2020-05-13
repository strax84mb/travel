package com.htec.travel.airports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

import static com.htec.travel.airports.CSVUtil.*;

@Service
public class AirportServiceImpl extends AbstractCSVService implements AirportService {

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private CachingService cachingService;

    @Override
    public long parseDataset(InputStream inputStream) {
        return super.parseDataset(inputStream);
    }

    @Override
    public boolean parseAndSaveLine(String[] fields) {
        try {
            if (fields.length != 14) {
                throw new IncorrectColumnsCountException();
            }
            var city = cachingService.findCityByNameAndCountryIgnoreCase(fields[2], fields[3]);
            if (city.isEmpty()) {
                return false;
            }
            var airport = Airport.builder()
                    .airportId(toBigInteger(fields[0]))
                    .name(fields[1])
                    .city(city.get())
                    .iata(textOrNull(fields[4]))
                    .icao(textOrNull(fields[5]))
                    .latitude(toDouble(fields[6]))
                    .longitude(toDouble(fields[7]))
                    .altitude(toInt(fields[8]))
                    .timezone(toInt(fields[9]))
                    .dst(toDST(fields[10]))
                    .dbTimeZone(textOrNull(fields[11]))
                    .type(fields[12])
                    .source(fields[13])
                    .build();
            airport = airportRepository.save(airport);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
