package com.htec.travel.airports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PathDto {

    private Double sumPrice;
    private Integer numberOfFlights;
    private PathRouteDto start;
    private List<PathRouteDto> flights;
}
