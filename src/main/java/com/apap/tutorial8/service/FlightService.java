package com.apap.tutorial8.service;

import com.apap.tutorial8.model.FlightModel;

import java.util.Optional;

/**
 * FlightService
 */
public interface FlightService {
    FlightModel addFlight(FlightModel flight);
    
    void deleteByFlightNumber(String flightNumber);

    Optional<FlightModel> getFlightDetailByFlightNumber(String flightNumber);
}