package com.htec.travel.airports;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

public interface RouteService {

    long parseDataset(InputStream inputStream);

    PathDto findCheapestRoute(BigInteger start, BigInteger destination);
}
