package es.ull.passengers;

import es.ull.flights.Flight;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PassengerTest {

    @Test
    void testValidPassengerCreation() {
        Passenger passenger = new Passenger("P001", "John Doe", "US");

        assertEquals("P001", passenger.getIdentifier());
        assertEquals("John Doe", passenger.getName());
        assertEquals("US", passenger.getCountryCode());
    }

    @Test
    void testInvalidCountryCode() {
        assertThrows(RuntimeException.class, () -> new Passenger("P001", "John Doe", "XX"));
    }

    @Test
    void testPassengerJoinFlight() {
        Flight flight = new Flight("AB123", 2);
        Passenger passenger = new Passenger("P001", "John Doe", "US");

        passenger.joinFlight(flight);
        assertEquals(flight, passenger.getFlight());
        assertEquals(1, flight.getNumberOfPassengers());
    }

    @Test
    void testPassengerSwitchFlight() {
        Flight flight1 = new Flight("AB123", 2);
        Flight flight2 = new Flight("CD456", 2);
        Passenger passenger = new Passenger("P001", "John Doe", "US");

        passenger.joinFlight(flight1);
        passenger.joinFlight(flight2);

        assertEquals(flight2, passenger.getFlight());
        assertEquals(0, flight1.getNumberOfPassengers());
        assertEquals(1, flight2.getNumberOfPassengers());
    }

    @Test
    void testRemovePassengerFromFlight() {
        Flight flight = new Flight("AB123", 2);
        Passenger passenger = new Passenger("P001", "John Doe", "US");

        passenger.joinFlight(flight);
        passenger.setFlight(null);
        assertNull(passenger.getFlight());
        assertEquals(0, flight.getNumberOfPassengers());
    }
}
