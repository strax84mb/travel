package com.htec.travel.airports;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RouteTree {

    private RouteRepository routeRepository;
    private RouteTreeNode root;
    private BigInteger destinationAirportId;
    private Double cheapestPrice = Double.MAX_VALUE;
    private RouteTreeNode cheapestEndpoint = null;

    public RouteTree(RouteRepository routeRepository, BigInteger startingAirportId, BigInteger destinationAirportId) {
        this.routeRepository = routeRepository;
        this.destinationAirportId = destinationAirportId;
        root = new RouteTreeNode(startingAirportId);
    }

    public PathDto searchCheapestPath() {
        searchNode(root);
        if (cheapestEndpoint == null) {
            return PathDto.builder()
                    .flights(List.of())
                    .numberOfFlights(0)
                    .start(null)
                    .sumPrice(0.0)
                    .build();
        }
        var flights = new ArrayList<Route>();
        for (BigInteger routeId : cheapestEndpoint.getAllFlights()) {
            flights.add(routeRepository.findById(routeId).get());
        }
        var dtoBuilder = PathDto.builder()
                .sumPrice(cheapestPrice)
                .numberOfFlights(flights.size())
                .start(toPathRouteDto(flights.get(0).getAirline(), flights.get(0).getAirlineId(), flights.get(0).getSource()));
        List<PathRouteDto> flightDtos = new ArrayList<>();
        for (Route flight : flights) {
            flightDtos.add(toPathRouteDto(flight.getAirline(), flight.getAirlineId(), flight.getDestination()));
        }
        dtoBuilder.flights(flightDtos);
        return dtoBuilder.build();
    }

    private PathRouteDto toPathRouteDto(String airline, BigInteger airlineId, Airport airport) {
        return PathRouteDto.builder()
                .airline(airline)
                .airlineId(airlineId)
                .airport(airport.getName())
                .city(airport.getCity().getName())
                .country(airport.getCity().getCountry())
                .build();
    }

    private void searchNode(RouteTreeNode node) {
        log.info("Destination >>> " + node.getDestinationId());
        List<BigInteger> stops = node.getAllStops();
        List<Route> possibleRoutes = routeRepository.findBySourceIdAndDestinationIdNotIn(node.getDestinationId(), stops);
        for (Route route : possibleRoutes) {
            var newNode = new RouteTreeNode(node, route);
            if (destinationAirportId.equals(route.getDestination().getId())) {
                if (cheapestPrice.compareTo(newNode.getSumPrice()) > 0) {
                    cheapestPrice = newNode.getSumPrice();
                    cheapestEndpoint = newNode;
                }
            } else {
                if (cheapestPrice.compareTo(newNode.getSumPrice()) > 0) {
                    searchNode(newNode);
                }
            }
        }
    }

    public void destroy() {
        root.destroy();
    }
}
