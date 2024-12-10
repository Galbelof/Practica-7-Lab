    package es.ull.flights;

    import es.ull.passengers.Passenger;
    import org.junit.jupiter.api.Test;

    import static org.junit.jupiter.api.Assertions.*;

    class FlightTest {

        @Test
        void testValidFlightNumber() {
            Flight flight = new Flight("AB123", 10);
            assertEquals("AB123", flight.getFlightNumber());
        }

        @Test
        void testInvalidFlightNumber() {
            assertThrows(RuntimeException.class, () -> new Flight("1234", 10));
        }

        @Test
        void testAddPassenger() {
            Flight flight = new Flight("AB123", 1);
            Passenger passenger = new Passenger("P001", "John Doe", "US");

            assertTrue(flight.addPassenger(passenger));
            assertEquals(1, flight.getNumberOfPassengers());
        }

        @Test
        void testAddPassengerExceedingCapacity() {
            Flight flight = new Flight("AB123", 1);
            Passenger passenger1 = new Passenger("P001", "John Doe", "US");
            Passenger passenger2 = new Passenger("P002", "Jane Doe", "US");

            flight.addPassenger(passenger1);
            assertThrows(RuntimeException.class, () -> flight.addPassenger(passenger2));
        }

        @Test
        void testRemovePassenger() {
            Flight flight = new Flight("AB123", 2);
            Passenger passenger = new Passenger("P001", "John Doe", "US");

            flight.addPassenger(passenger);
            assertTrue(flight.removePassenger(passenger));
            assertEquals(0, flight.getNumberOfPassengers());
        }
    }
