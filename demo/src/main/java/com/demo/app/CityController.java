package com.demo.app;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class CityController {
	

	@RequestMapping(value = "/fetch/{originCity}/{destinationCity}", method = RequestMethod.GET)
	public @ResponseBody Object getAllItineraries(@PathVariable String originCity , @PathVariable String destinationCity) throws ParseException, java.text.ParseException {
		System.out.println("OriginCity-------->"+originCity);
		AllCities Allcities = null;
		
		try {
			File file = new ClassPathResource("city.json").getFile();
			Allcities = new ObjectMapper().readValue(file, AllCities.class);
			List<City> listofcity = Allcities.AllTheCities;
			for(City x : listofcity)
			{
				if(x.getOriginCity().equals(originCity))
				{
					if(x.getDestinationCity().equals(destinationCity)) {
						return x.getItineraries();
					}
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	@RequestMapping(value = "/fetch/{originCity}/{destinationCity}/fastest", method = RequestMethod.GET)
	public @ResponseBody Object getFastestRoute(@PathVariable String originCity , @PathVariable String destinationCity) throws ParseException, java.text.ParseException {
		
		AllCities Allcities = null;
		try {
			Date departureDate = null;
			Date arrivalDate = null;
			long totalTimeTravel = Integer.MAX_VALUE;
			int noOfHalts = Integer.MAX_VALUE;
			String routeName = null;
			
			File file = new ClassPathResource("city.json").getFile();
			Allcities = new ObjectMapper().readValue(file, AllCities.class);
			List<City> listofcity = Allcities.AllTheCities;
			List<Itinerary> listOfItineraries = null;
			//System.out.println("inside try in second service");
			for(City x : listofcity)
			{
				if(x.getOriginCity().equals(originCity))
				{
					if(x.getDestinationCity().equals(destinationCity)) {
						//retreiving all the Itineraries in respect to origin and destination.
						listOfItineraries = x.getItineraries();
						
						//converting the departure time to date format.
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
						departureDate = sdf.parse(x.getDepartureTime());
						
				//		System.out.println("before break in second service");
						break;
					}
				}
				
			}		
			
			for(Itinerary i : listOfItineraries)
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
				arrivalDate = sdf.parse(i.getArrivalTime());
				long diff = arrivalDate.getTime() - departureDate.getTime();
				//System.out.println(diff);
				if(diff<=totalTimeTravel && Integer.parseInt(i.getConnectionNum()) <= noOfHalts) {
					//System.out.println("before return in second service with value");
					totalTimeTravel = diff;
					noOfHalts = Integer.parseInt(i.getConnectionNum());
					routeName = i.getPathId();
					
				}
			}
			return routeName;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		//System.out.println("before return in second service");
		return null;
	}
		
	
}
