package com.htec.travel.airports;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class RouteTreeNode {

    private RouteTreeNode parent;
    private Set<RouteTreeNode> children = new HashSet<>();
    private BigInteger routeId;
    private BigInteger startId;
    private BigInteger destinationId;
    private Double sumPrice;

    public RouteTreeNode(BigInteger startingPoint) {
        parent = null;
        routeId = null;
        startId = null;
        destinationId = startingPoint;
        sumPrice = 0.0;
    }

    public RouteTreeNode(RouteTreeNode parent, Route route) {
        this.parent = parent;
        routeId = route.getId();
        startId = route.getSource().getId();
        destinationId = route.getDestination().getId();
        sumPrice = route.getPrice() + parent.getSumPrice();
    }

    public List<BigInteger> getAllStops() {
        RouteTreeNode temp = this;
        List<BigInteger> result = new ArrayList<>();
        while(temp != null) {
            result.add(temp.getDestinationId());
            temp = temp.getParent();
        }
        return result;
    }

    public List<BigInteger> getAllFlights() {
        RouteTreeNode temp = this;
        List<BigInteger> result = new ArrayList<>();
        while(temp != null && temp.getRouteId() != null) {
            result.add(0, temp.getRouteId());
            temp = temp.getParent();
        }
        return result;
    }

    public void destroy() {
        for (RouteTreeNode child : children) {
            child.destroy();
        }
        children.clear();
        parent = null;
    }
}
