package com.demo.app;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class City {

	private String originCity;
	private String destinationCity;
	private String departureTime;
	public String getOriginCity() {
		return originCity;
	}

	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	@JsonProperty("itineraries")
	private List<Itinerary> itineraries;



	public List<Itinerary> getItineraries() {
		return itineraries;
	}

	public void setItineraries(List<Itinerary> itineraries) {
		this.itineraries = itineraries;
	}
	
}
