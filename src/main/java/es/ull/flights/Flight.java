package es.ull.flights;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.ull.passengers.Passenger;

public class Flight {

    private String flightNumber; ///< The flight number.
    private int seats; ///< The total number of seats in the flight.
    private Set<Passenger> passengers = new HashSet<>(); ///< Set of passengers on the flight.

    private static String flightNumberRegex = "^[A-Z]{2}\\d{3,4}$"; ///< Regular expression for validating flight numbers.
    private static Pattern pattern = Pattern.compile(flightNumberRegex); ///< Pattern object for flight number validation.

    public Flight(String flightNumber, int seats) {
        Matcher matcher = pattern.matcher(flightNumber);
        if (!matcher.matches()) {
            throw new RuntimeException("Invalid flight number");
        }
        this.flightNumber = flightNumber;
        this.seats = seats;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public int getNumberOfPassengers() {
        return passengers.size();
    }

    public boolean addPassenger(Passenger passenger) {
        if (getNumberOfPassengers() >= seats) {
            throw new RuntimeException("Not enough seats for flight " + getFlightNumber());
        }
        passenger.setFlight(this);
        return passengers.add(passenger);
    }

    public boolean removePassenger(Passenger passenger) {
        passenger.setFlight(null);
        return passengers.remove(passenger);
    }
}