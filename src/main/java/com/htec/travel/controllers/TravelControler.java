package com.htec.travel.controllers;

import com.htec.travel.airports.AirportService;
import com.htec.travel.airports.PathDto;
import com.htec.travel.airports.RouteService;
import com.htec.travel.security.HasAnyRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping(path = "travel")
public class TravelControler {

    @Autowired
    private AirportService airportService;

    @Autowired
    private RouteService routeService;

    @HasAnyRole({"ADMIN"})
    @PostMapping(path = "/airports", consumes = MULTIPART_FORM_DATA_VALUE)
    public long uploadAirports(@RequestParam("file") MultipartFile file) throws IOException {
        return airportService.parseDataset(file.getInputStream());
    }

    @HasAnyRole({"ADMIN"})
    @PostMapping(path = "/routes", consumes = MULTIPART_FORM_DATA_VALUE)
    public long uploadRoutes(@RequestParam("file") MultipartFile file) throws IOException {
        return routeService.parseDataset(file.getInputStream());
    }

    @HasAnyRole({"USER", "ADMIN"})
    @GetMapping(path = "/cheapest", produces = APPLICATION_JSON_VALUE, params = {"start", "destination"})
    public PathDto calculateCheapest(@RequestParam("start") BigInteger startAirportId,
                                     @RequestParam("destination") BigInteger destinationAirportId) {
        return routeService.findCheapestRoute(startAirportId, destinationAirportId);
    }
}
