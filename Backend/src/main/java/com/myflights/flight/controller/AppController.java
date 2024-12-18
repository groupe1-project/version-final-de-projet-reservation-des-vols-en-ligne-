package com.myflights.flight.controller;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.myflights.flight.util.PdfGenerator;

@RestController
public class AppController {
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private FlightService flightService;

    @Autowired
    private BookingsService bookingsService;

    @Autowired
    private PdfGenerator pdfGenerator;

    @GetMapping("/find-flights/{origin}/{dest}/{date}")
    public ResponseEntity<ObjectResponse> findFlights(@PathVariable String origin, @PathVariable String dest, @PathVariable String date) {
        ObjectResponse resp = new ObjectResponse();
        try {
            // Recherche des vols
            List<Flight> flights = flightService.findFlights(origin, dest);
            resp.setResponseData(flights);
            resp.setStatusCode(HttpStatus.OK);
            resp.setMessageType("SUCCESS");
        } catch (Exception ex) {
            resp.setStatusCode(HttpStatus.BAD_REQUEST);
            resp.setMessage("Error occurred on the server");
            resp.setMessageType("ERROR");
            logger.error("An error occurred", ex);
        }
        return new ResponseEntity<>(resp, resp.getStatusCode());
    }

    @PostMapping("/save-booking")
    public ResponseEntity<ObjectResponse> saveBooking(@RequestBody Bookings booking) {
        ObjectResponse resp = new ObjectResponse();
        try {
            // Validation et sauvegarde de la réservation
            Flight flight = flightService.findFlightById(booking.getFlid());

            if (flight != null) {
                booking.setAmount(flight.getCost());
                booking.setDest(flight.getDest());
                booking.setOrigin(flight.getOrigin());
                booking.setTravelDate(flight.getDepartureDate());

                bookingsService.saveBooking(booking);
                resp.setStatusCode(HttpStatus.OK);
                resp.setMessageType("SUCCESS");
                resp.setMessage("Booking has been made and you are good to fly!");
            } else {
                resp.setStatusCode(HttpStatus.BAD_REQUEST);
                resp.setMessage("Flight does not exist");
                resp.setMessageType("ERROR");
            }
        } catch (Exception ex) {
            resp.setStatusCode(HttpStatus.BAD_REQUEST);
            resp.setMessage("Error occurred on the server");
            resp.setMessageType("ERROR");
            logger.error("An error occurred", ex);
        }
        return new ResponseEntity<>(resp, resp.getStatusCode());
    }

    @GetMapping("/generate-ticket/{bkid}")
    public ResponseEntity<byte[]> generateTicket(@PathVariable Integer bkid) {  // Changez Long en Integer ici
        try {
            // Trouver la réservation par son ID
            Bookings booking = bookingsService.findBookingById(bkid);
            if (booking == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Si la réservation n'existe pas
            }

            // Générer le PDF
            byte[] pdfBytes = pdfGenerator.generateTicketPdf(booking);

            // Définir les headers pour le téléchargement du PDF
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=flight-ticket-" + bkid + ".pdf");
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

            // Retourner la réponse avec le PDF en byte array
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (IOException e) {
            logger.error("Error generating ticket PDF", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // En cas d'erreur
        }

        
    }
    
}
