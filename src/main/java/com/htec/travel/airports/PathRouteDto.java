package com.htec.travel.airports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PathRouteDto {

    private String airline;
    private BigInteger airlineId;
    private String airport;
    private String city;
    private String country;
}
