package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;

public class FareCalculatorServiceTest {

	private static FareCalculatorService fareCalculatorService;
	private Ticket ticket;

	@BeforeAll
	private static void setUp() {
		fareCalculatorService = new FareCalculatorService();
	}

	@BeforeEach
	private void setUpPerTest() {
		ticket = new Ticket();
	}

	@Test
	public void calculateFareCar() {
		LocalDateTime inTime = LocalDateTime.now().minusHours(1);
		// inTime.setTime( System.currentTimeMillis() - ( 60 * 60 * 1000) );
		LocalDateTime outTime = LocalDateTime.now();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket);
		//assertEquals(ticket.getPrice(), Fare.CAR_RATE_PER_HOUR);
		assertEquals(Fare.CAR_RATE_PER_HOUR, ticket.getPrice());
	}

	@Test
	public void calculateFareBike() {
		LocalDateTime inTime = LocalDateTime.now().minusHours(1);
		// inTime.setTime( System.currentTimeMillis() - ( 60 * 60 * 1000) );
		LocalDateTime outTime = LocalDateTime.now();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket);
		//assertEquals(ticket.getPrice(), Fare.BIKE_RATE_PER_HOUR);
		assertEquals(Fare.BIKE_RATE_PER_HOUR, ticket.getPrice());
	}

	@Test
	public void calculateFare_UnkownType() {
		LocalDateTime inTime = LocalDateTime.now().minusHours(1);
		// inTime.setTime( System.currentTimeMillis() - ( 60 * 60 * 1000) );
		LocalDateTime outTime = LocalDateTime.now();
		ParkingSpot parkingSpot = new ParkingSpot(1, null, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket));
	}

	@Test
	public void calculateFareCar_WithFutureInTime() {
		LocalDateTime inTime = LocalDateTime.now().plusHours(1);
		// inTime.setTime( System.currentTimeMillis() + ( 60 * 60 * 1000) );
		LocalDateTime outTime = LocalDateTime.now();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
	}

	@Test
	public void calculateFareBike_WithFutureInTime() {
		LocalDateTime inTime = LocalDateTime.now().plusHours(1);
		// inTime.setTime( System.currentTimeMillis() + ( 60 * 60 * 1000) );
		LocalDateTime outTime = LocalDateTime.now();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
	}

	@Test
	public void calculateFareCar_WithLessThanOneHourParkingTime() {
		LocalDateTime inTime = LocalDateTime.now().minusMinutes(45);// 45 minutes parking time should give 3/4th parking
																	// fare
		// inTime.setTime( System.currentTimeMillis() - ( 45 * 60 * 1000) );//45 minutes
		// parking time should give 3/4th parking fare
		LocalDateTime outTime = LocalDateTime.now();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket);

		assertEquals((0.75 * Fare.CAR_RATE_PER_HOUR), ticket.getPrice());
	}

	@Test
	public void calculateFareBike_WithLessThanOneHourParkingTime() {
		LocalDateTime inTime = LocalDateTime.now().minusMinutes(45);// 45 minutes parking time should give 3/4th parking
																	// fare
		// inTime.setTime( System.currentTimeMillis() - ( 45 * 60 * 1000) );//45 minutes
		// parking time should give 3/4th parking fare
		LocalDateTime outTime = LocalDateTime.now();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket);
		assertEquals((0.75 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice());
	}

	@Test
	public void calculateFareCar_WithMoreThanADayParkingTime() {
		LocalDateTime inTime = LocalDateTime.now().minusDays(1);// 1 day (= 24 hours) parking time should give 24 *
																// parking fare per hour
		// inTime.setTime( System.currentTimeMillis() - ( 24 * 60 * 60 * 1000) );//24
		// hours parking time should give 24 * parking fare per hour
		LocalDateTime outTime = LocalDateTime.now();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket);
		assertEquals((24 * Fare.CAR_RATE_PER_HOUR), ticket.getPrice());
	}

	@Test
	public void calculateFareBike_WithMoreThanADayParkingTime() {
		LocalDateTime inTime = LocalDateTime.now().minusDays(1);// 1 day (= 24 hours) parking time should give 24 *
																// parking fare per hour
		// inTime.setTime( System.currentTimeMillis() - ( 24 * 60 * 60 * 1000) );//24
		// hours parking time should give 24 * parking fare per hour
		LocalDateTime outTime = LocalDateTime.now();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket);
		assertEquals((24 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice());
	}

	@Test
	public void calculateFareCar_WithNullOutTime() {
		LocalDateTime inTime = LocalDateTime.now().minusDays(1);// 1 day (= 24 hours) parking time should give 24 *
																// parking fare per hour
		// inTime.setTime( System.currentTimeMillis() - ( 24 * 60 * 60 * 1000) );//24
		// hours parking time should give 24 * parking fare per hour
		LocalDateTime outTime = null;
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		// fareCalculatorService.calculateFare(ticket);
		// assertThrows(IllegalArgumentException.class, () ->
		// fareCalculatorService.calculateFare(ticket));
		assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket));
	}

	@Test
	public void calculateFareBike_WithNullOutTime() {
		LocalDateTime inTime = LocalDateTime.now().minusDays(1);// 1 day (= 24 hours) parking time should give 24 *
																// parking fare per hour
		// inTime.setTime( System.currentTimeMillis() - ( 24 * 60 * 60 * 1000) );//24
		// hours parking time should give 24 * parking fare per hour
		LocalDateTime outTime = null;
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		// fareCalculatorService.calculateFare(ticket);
		// assertThrows(IllegalArgumentException.class, () ->
		// fareCalculatorService.calculateFare(ticket));
		assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket));
	}
}
