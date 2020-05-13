package com.htec.travel.airports;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface RouteRepository extends JpaRepository<Route, BigInteger> {

    List<Route> findBySourceIdAndDestinationIdNotIn(BigInteger sourceId, List<BigInteger> destintionIds);
}
