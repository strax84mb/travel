package com.htec.travel.airports;

import com.htec.travel.cities.City;
import com.htec.travel.data.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "airport")
public class Airport extends BaseEntity {

    @Column(name = "airport_id", nullable = false)
    private BigInteger airportId;

    @Column(nullable = false, length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @Column(length = 3)
    private String iata;

    @Column(length = 4)
    private String icao;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Integer altitude;

    @Column
    private Integer timezone;

    @Column
    @Enumerated(EnumType.STRING)
    private AirportDst dst;

    @Column(name = "db_time_zone", length = 40)
    private String dbTimeZone;

    @Column(nullable = false, length = 30)
    private String type;

    @Column(nullable = false, length = 30)
    private String source;
}
