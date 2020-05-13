package com.htec.travel.airports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

import static com.htec.travel.airports.CSVUtil.*;

@Service
public class RouteServiceImpl extends AbstractCSVService implements RouteService {

    @Autowired
    private CachingService cachingService;

    @Autowired
    private RouteRepository routeRepository;

    @Override
    public long parseDataset(InputStream inputStream) {
        return super.parseDataset(inputStream);
    }

    @Override
    public boolean parseAndSaveLine(String[] fields) {
        try {
            if (fields.length != 10) {
                throw new IncorrectColumnsCountException();
            }
            var source = cachingService.findAirportById(toBigInteger(fields[3]));
            if (source.isEmpty()) {
                throw new AirportNotExistException();
            }
            var destination = cachingService.findAirportById(toBigInteger(fields[5]));
            if (destination.isEmpty()) {
                throw new AirportNotExistException();
            }
            var route = Route.builder()
                    .airline(fields[0])
                    .airlineId(toBigInteger(fields[1]))
                    .source(source.get())
                    .destination(destination.get())
                    .codeshare(toBoolean(fields[6]))
                    .stops(toInt(fields[7]))
                    .equipment(fields[8])
                    .price(toDouble(fields[9]))
                    .build();
            routeRepository.save(route);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public PathDto findCheapestRoute(BigInteger start, BigInteger destination) {
        var tree = new RouteTree(routeRepository, start, destination);
        return tree.searchCheapestPath();
    }


}
