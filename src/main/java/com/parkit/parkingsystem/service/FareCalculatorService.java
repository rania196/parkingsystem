package com.parkit.parkingsystem.service;

import java.util.concurrent.TimeUnit;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

	public void calculateFare(Ticket ticket) {
		if ((ticket.getOutTime() == null)
				|| (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:"
					+ ticket.getOutTime().toString());
		}

		long inHour = ticket.getInTime().getTime();
		long outHour = ticket.getOutTime().getTime();

		// TODO: Some tests are failing here. Need to check if this logic is
		// correct
		long duration = outHour - inHour;
		TimeUnit timeUnitM = TimeUnit.MINUTES;

		float durationInHours = (float) timeUnitM.convert(duration,
				TimeUnit.MILLISECONDS) / (float) 60;
		if (durationInHours <= 0.5) {
			ticket.setPrice(0);
		} else {
			switch (ticket.getParkingSpot().getParkingType()) {
				case CAR : {
					ticket.setPrice(durationInHours * Fare.CAR_RATE_PER_HOUR);
					break;
				}
				case BIKE : {
					ticket.setPrice(durationInHours * Fare.BIKE_RATE_PER_HOUR);
					break;
				}
				default :
					throw new IllegalArgumentException("Unkown Parking Type");
			}
		}
	}
}