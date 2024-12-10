package es.ull.flights;

import es.ull.passengers.Passenger;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @class FlightTest
 * @brief Contains JUnit tests for the Flight class.
 *
 * The FlightTest class includes various test cases to verify the correct
 * behavior of the Flight class methods, such as adding and removing passengers,
 * handling invalid flight numbers, exceeding capacity, and more.
 */
public class FlightTest {

    /**
     * @brief Tests the creation of a flight with a valid flight number.
     */
    @Test
    public void testValidFlightNumber() {
        Flight flight = new Flight("AB123", 100);
        assertEquals("AB123", flight.getFlightNumber());
        assertEquals(0, flight.getNumberOfPassengers());
    }

    /**
     * @brief Tests the creation of a flight with an invalid flight number.
     */
    @Test
    public void testInvalidFlightNumber() {
        assertThrows(RuntimeException.class, () -> new Flight("1234", 50));
        assertThrows(RuntimeException.class, () -> new Flight("A1", 50));
        assertThrows(RuntimeException.class, () -> new Flight("AB12345", 50));
    }

    /**
     * @brief Tests adding a passenger to the flight.
     */
    @Test
    public void testAddPassenger() {
        Flight flight = new Flight("AB123", 2);
        Passenger passenger = new Passenger("ID123", "John Doe", "US");

        assertTrue(flight.addPassenger(passenger));
        assertEquals(1, flight.getNumberOfPassengers());
    }

    /**
     * @brief Tests removing a passenger from the flight.
     */
    @Test
    public void testRemovePassenger() {
        Flight flight = new Flight("AB123", 2);
        Passenger passenger = new Passenger("ID123", "John Doe", "US");

        flight.addPassenger(passenger);
        assertTrue(flight.removePassenger(passenger));
        assertEquals(0, flight.getNumberOfPassengers());
    }

    /**
     * @brief Tests adding passengers beyond the flight's capacity.
     */
    @Test
    public void testExceedingCapacity() {
        Flight flight = new Flight("AB123", 1);
        Passenger passenger1 = new Passenger("ID123", "John Doe", "US");
        Passenger passenger2 = new Passenger("ID456", "Jane Doe", "CA");

        flight.addPassenger(passenger1);
        assertThrows(RuntimeException.class, () -> flight.addPassenger(passenger2));
    }

    /**
     * @brief Tests removing a passenger not in the flight.
     */
    @Test
    public void testRemoveNonexistentPassenger() {
        Flight flight = new Flight("AB123", 2);
        Passenger passenger = new Passenger("ID123", "John Doe", "US");

        assertTrue(!flight.removePassenger(passenger));
        assertEquals(0, flight.getNumberOfPassengers());
    }

    /**
     * @brief Tests adding a passenger who is already in the flight.
     */
    @Test
    public void testAddDuplicatePassenger() {
        Flight flight = new Flight("AB123", 2);
        Passenger passenger = new Passenger("ID123", "John Doe", "US");

        flight.addPassenger(passenger);
        assertTrue(!flight.addPassenger(passenger));
        assertEquals(1, flight.getNumberOfPassengers());
    }

    /**
     * @brief Tests flight behavior with no passengers.
     */
    @Test
    public void testFlightWithNoPassengers() {
        Flight flight = new Flight("AB123", 2);
        assertEquals(0, flight.getNumberOfPassengers());
    }

    /**
     * @brief Tests adding passengers to fill the flight exactly to its capacity.
     */
    @Test
    public void testFillFlightToCapacity() {
        Flight flight = new Flight("AB123", 2);
        Passenger passenger1 = new Passenger("ID123", "John Doe", "US");
        Passenger passenger2 = new Passenger("ID456", "Jane Doe", "CA");

        flight.addPassenger(passenger1);
        flight.addPassenger(passenger2);

        assertEquals(2, flight.getNumberOfPassengers());
        assertThrows(RuntimeException.class, () -> flight.addPassenger(new Passenger("ID789", "Alice", "FR")));
    }

    /**
     * @brief Tests flight behavior when the same passenger is added and removed repeatedly.
     */
    @Test
    public void testAddAndRemovePassengerMultipleTimes() {
        Flight flight = new Flight("AB123", 2);
        Passenger passenger = new Passenger("ID123", "John Doe", "US");

        flight.addPassenger(passenger);
        assertEquals(1, flight.getNumberOfPassengers());

        flight.removePassenger(passenger);
        assertEquals(0, flight.getNumberOfPassengers());

        flight.addPassenger(passenger);
        assertEquals(1, flight.getNumberOfPassengers());
    }

    /**
     * @brief Tests removing a passenger when the flight is empty.
     */
    @Test
    public void testRemovePassengerFromEmptyFlight() {
        Flight flight = new Flight("AB123", 2);
        Passenger passenger = new Passenger("ID123", "John Doe", "US");

        assertTrue(!flight.removePassenger(passenger));
    }
}
