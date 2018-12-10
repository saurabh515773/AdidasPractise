# AdidasPractise
***********************************************************************************************************************************************
This Project is regarding booking of tickets by finding the optimum path to travel from.
The path which is based on number of connection(total number of halts) and also on basis of total time taken to cover the entire Journey.
REST based endpoints are exposed as part of project.
There are majorily two scenario covered :
           INPUT						OUTPUT
1. origin city, destination city		List of Itineraries
2. origin, destination, Connections		Most favourable Itinerary

And then he can apply filter to find his optimum path from which they need to travel.
First Microservice gives us all the Itineraries which connect origin city and destination city.
Second Microservice will give us the best optimum Itinerary, which is been suggested to travel.
