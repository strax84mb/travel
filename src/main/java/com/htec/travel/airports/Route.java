package com.htec.travel.airports;

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
@Table(name = "route", indexes = {
        @Index(name = "route_source_index", columnList = "source_id"),
        @Index(name = "route_destination_index", columnList = "destination_id"),
        @Index(name = "route_path_index", columnList = "source_id,destination_id"),
        @Index(name = "route_unique_index", columnList = "source_id,destination_id,airline_id", unique = true)
})
public class Route extends BaseEntity {

    @Column(nullable = false, length = 3)
    private String airline;

    @Column(name = "airline_id", nullable = false)
    private BigInteger airlineId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id")
    private Airport source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id")
    private Airport destination;

    @Column(nullable = false)
    private Boolean codeshare;

    @Column(nullable = false)
    private Integer stops;

    @Column(nullable = false, length = 3)
    private String equipment;

    @Column(nullable = false)
    private Double price;
}
