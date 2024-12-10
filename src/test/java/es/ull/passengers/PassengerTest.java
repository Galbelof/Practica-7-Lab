package es.ull.passengers;

import es.ull.flights.Flight;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @class PassengerTest
 * @brief Contains JUnit tests for the Passenger class.
 *
 * The PassengerTest class includes various test cases to verify the correct
 * behavior of the Passenger class methods.
 */
public class PassengerTest {

    /**
     * @brief Tests the creation of a Passenger object with valid parameters.
     */
    @Test
    public void testValidPassengerCreation() {
        Passenger passenger = new Passenger("ID001", "Alice", "US");
        assertEquals("ID001", passenger.getIdentifier());
        assertEquals("Alice", passenger.getName());
        assertEquals("US", passenger.getCountryCode());
        assertNull(passenger.getFlight());
    }

    /**
     * @brief Tests the creation of a Passenger object with an invalid country code.
     */
    @Test
    public void testInvalidCountryCode() {
        assertThrows(RuntimeException.class, () -> new Passenger("ID002", "Bob", "XX"));
        assertThrows(RuntimeException.class, () -> new Passenger("ID003", "Charlie", "InvalidCode"));
    }

    /**
     * @brief Tests a passenger joining a flight.
     */
    @Test
    public void testJoinFlight() {
        Flight flight = new Flight("XY123", 3);
        Passenger passenger = new Passenger("ID004", "David", "CA");

        passenger.joinFlight(flight);
        assertEquals(flight, passenger.getFlight());
        assertEquals(1, flight.getNumberOfPassengers());
    }

    /**
     * @brief Tests a passenger leaving a flight by joining a null flight.
     */
    @Test
    public void testLeaveFlight() {
        Flight flight = new Flight("XY123", 3);
        Passenger passenger = new Passenger("ID005", "Eve", "GB");

        passenger.joinFlight(flight);
        assertEquals(flight, passenger.getFlight());

        passenger.joinFlight(null);
        assertNull(passenger.getFlight());
        assertEquals(0, flight.getNumberOfPassengers());
    }

    /**
     * @brief Tests switching flights for a passenger.
     */
    @Test
    public void testSwitchFlights() {
        Flight flight1 = new Flight("XY123", 3);
        Flight flight2 = new Flight("AB456", 3);
        Passenger passenger = new Passenger("ID006", "Frank", "DE");

        passenger.joinFlight(flight1);
        passenger.joinFlight(flight2);

        assertEquals(flight2, passenger.getFlight());
        assertEquals(0, flight1.getNumberOfPassengers());
        assertEquals(1, flight2.getNumberOfPassengers());
    }

    /**
     * @brief Tests adding a passenger to a full flight.
     */
    @Test
    public void testCannotJoinFullFlight() {
        Flight flight = new Flight("XY123", 1);
        Passenger passenger1 = new Passenger("ID007", "Grace", "FR");
        Passenger passenger2 = new Passenger("ID008", "Hank", "ES");

        passenger1.joinFlight(flight);
        assertThrows(RuntimeException.class, () -> passenger2.joinFlight(flight));
    }

    /**
     * @brief Tests the toString method of the Passenger class.
     */
    @Test
    public void testToString() {
        Passenger passenger = new Passenger("ID009", "Ivy", "IT");
        assertEquals("Passenger Ivy with identifier: ID009 from IT", passenger.toString());
    }

    /**
     * @brief Tests a passenger attempting to join a new flight when they cannot leave the previous one.
     */
    @Test
    public void testCannotLeavePreviousFlight() {
        Flight flight1 = new Flight("XY123", 2) {
            @Override
            public boolean removePassenger(Passenger passenger) {
                return false; // Simulate failure to remove passenger
            }
        };

        Passenger passenger = new Passenger("ID010", "Jack", "NL");
        passenger.joinFlight(flight1);

        Flight flight2 = new Flight("AB456", 2);
        assertThrows(RuntimeException.class, () -> passenger.joinFlight(flight2));
    }

    /**
     * @brief Tests a passenger attempting to join a flight that rejects them.
     */
    @Test
    public void testCannotJoinRejectedFlight() {
        Flight flight = new Flight("XY123", 2) {
            @Override
            public boolean addPassenger(Passenger passenger) {
                return false; // Simulate rejection when adding passenger
            }
        };

        Passenger passenger = new Passenger("ID011", "Karen", "SE");
        assertThrows(RuntimeException.class, () -> passenger.joinFlight(flight));
    }

    /**
     * @brief Tests rejoining the same flight multiple times.
     */
    @Test
    public void testRejoinSameFlight() {
        Flight flight = new Flight("XY123", 3);
        Passenger passenger = new Passenger("ID012", "Laura", "FI");

        passenger.joinFlight(flight);
        assertEquals(flight, passenger.getFlight());

        // Rejoining the same flight should not throw any exceptions or add duplicate entries.
        passenger.joinFlight(flight);
        assertEquals(flight, passenger.getFlight());
        assertEquals(1, flight.getNumberOfPassengers());
    }
}
