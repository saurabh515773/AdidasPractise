package com.demo.app;


import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.text.SimpleDateFormat;  
import java.util.Date;  
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;  

@RestController
public class CityController {
	@RequestMapping(value = "/fetch/{originCity}", method = RequestMethod.GET)
	public @ResponseBody Object getCityName(@PathVariable String originCity) throws ParseException, java.text.ParseException {
		System.out.println("OriginCity-------->"+originCity);
		AllCities Allcities = null;
		
		try {
			File file = new ClassPathResource("city.json").getFile();
			Allcities = new ObjectMapper().readValue(file, AllCities.class);
			List<City> listofcity = Allcities.AllTheCities;
			Itinerary result = null;
			for(City x : listofcity)
			{
				if(x.getOriginCity().equals(originCity))
				{
					List<Itinerary> listOfItineraries = x.getItineraries();
					int minConnection = Integer.MAX_VALUE;
					long totalTime = Integer.MAX_VALUE;
					SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss");
					Date deptTime = datetimeFormatter1.parse(x.getDepartureTime());
					
					for(Itinerary it : listOfItineraries)
					{
						Date arrTime = datetimeFormatter1.parse(it.getArrivalTime());
						int diff = (int) (deptTime.getTime() - arrTime.getTime());
						long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
						/*if(totalTime > minutes) {
							totalTime = minutes;
						}*/
						
						if(minConnection >= Integer.parseInt(it.getConnectionNum()) && totalTime >= minutes) 
						{
							minConnection = Integer.parseInt(it.getConnectionNum());
							totalTime = minutes;
							result = it;
						}
					}
					/*for(Itinerary it : listOfItineraries)
					{
						if(it.getConnectionNum()==minConnection)
					}*/
					
								return result;
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/fetch/{originCity}/{destinationCity}/{connectionNum}", method = RequestMethod.GET)
	public @ResponseBody Object getCityName(@PathVariable String originCity , @PathVariable String destinationCity , @PathVariable String connectionNum) throws ParseException, java.text.ParseException {
		AllCities Allcities = null;
		System.out.println("second service");
		try {
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
						listOfItineraries = x.getItineraries();
				//		System.out.println("before break in second service");
						break;
					}
				}
					
			}		
			
			for(Itinerary it : listOfItineraries)
			{
				if(it.getConnectionNum().equals(connectionNum)) {
					//System.out.println("before return in second service with value");
					return it.getPathId();
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		//System.out.println("before return in second service");
		return null;
	}
		
	
}
