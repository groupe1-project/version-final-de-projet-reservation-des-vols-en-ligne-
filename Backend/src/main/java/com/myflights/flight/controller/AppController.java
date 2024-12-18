package com.myflights.flight.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myflights.flight.entity.Bookings;
import com.myflights.flight.entity.Flight;
import com.myflights.flight.response.ObjectResponse;
import com.myflights.flight.service.BookingsService;
import com.myflights.flight.service.FlightService;

@RestController
public class AppController {
  private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private FlightService flightService;
    
    @Autowired
    private BookingsService bookingsService;


    @GetMapping("/find-flights/{origin}/{dest}/{date}") // find-flights/JFK/CLT/12-10-2023        /find-flights?org=JFK&dest=CLT&date=12-10-2023
      public ResponseEntity<ObjectResponse>findFlights(@PathVariable() String origin, @PathVariable() String dest, @PathVariable() String date  ) {
      //public ResponseEntity<ObjectResponse>findFlights(@RequestParam() String origin, @RequestParam() String dest, @RequestParam() String date  ) {

    ObjectResponse resp =  new ObjectResponse();
     try {
       // List<Flight> flights = flightService.findFlightsByDate( origin, dest, date); 
         List<Flight> flights = flightService.findFlights( origin, dest); 
        resp.setResponseData(flights);
        resp.setStatusCode(HttpStatus.OK);
        resp.setMessageType("SUCCESS");
        //return flights;
     } catch (Exception ex){
         resp.setStatusCode(HttpStatus.BAD_REQUEST);
         resp.setMessage("Error occured on the server");
         resp.setMessageType("ERROR");
        // ex.printStackTrace();
        logger.error("An error occurred", ex);
     }
     return new ResponseEntity<>(resp, resp.getStatusCode());
    }
    //ObjectResponse

   @PostMapping("/save-booking") 
      public ResponseEntity<ObjectResponse>findFlights( @RequestBody Bookings booking ) {
      //public ResponseEntity<ObjectResponse>findFlights(@RequestParam() String origin, @RequestParam() String dest, @RequestParam() String date  ) {

    ObjectResponse resp =  new ObjectResponse();
     try {

        //1. Perform vbalidations if needed
       
       //2. save the booking to the DB
        
       Flight flight =  flightService.findFlightById(booking.getFlid());

       if(flight != null){

          booking.setAmount(flight.getCost());
          booking.setDest(flight.getDest());
          booking.setOrigin(flight.getOrigin());
          booking.setTravelDate(flight.getDepartureDate());
        
          bookingsService.saveBooking(booking);
          resp.setStatusCode(HttpStatus.OK);
          resp.setMessageType("SUCCESS");
          resp.setMessage("Booking has been made and you are good to fly!");
       }else{
         resp.setStatusCode(HttpStatus.BAD_REQUEST);
         resp.setMessage("Flight does not exist");
         resp.setMessageType("ERROR");
       }
        //return flights;
     } catch (Exception ex){
         resp.setStatusCode(HttpStatus.BAD_REQUEST);
         resp.setMessage("Error occured on the server");
         resp.setMessageType("ERROR");
         //ex.printStackTrace();
         logger.error("An error occurred", ex);
     }
     return new ResponseEntity<>(resp, resp.getStatusCode());
    }


    
}
