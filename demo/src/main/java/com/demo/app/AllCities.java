package com.demo.app;

import java.util.List;

import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JsonProperty;

public class AllCities {
	@JsonProperty("cities")
	public List<City> AllTheCities;
}
