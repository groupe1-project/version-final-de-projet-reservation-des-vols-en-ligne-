package com.myflights.flight.controller;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.myflights.flight.entity.Bookings;
import com.myflights.flight.service.BookingsService;
import com.myflights.flight.util.PdfGenerator;

@RestController
public class BookingController {

    private final PdfGenerator pdfGenerator;
    private final BookingsService bookingService;

    // Injection des dépendances (PdfGenerator et BookingService)
    public BookingController(PdfGenerator pdfGenerator, BookingsService bookingService) {
        this.pdfGenerator = pdfGenerator;
        this.bookingService = bookingService;
    }

    // Endpoint pour télécharger le PDF du ticket
    @GetMapping("/booking/{bookingId}/ticket")
    public ResponseEntity<byte[]> getBookingTicket(@PathVariable("bookingId") int bookingId) throws IOException {
        // Récupère la réservation par ID via le service
        Bookings booking = bookingService.findBookingById(bookingId);

        // Générer le PDF à partir de la réservation
        byte[] pdfData = pdfGenerator.generateTicketPdf(booking);

        // Définir les en-têtes pour la réponse HTTP (type PDF et disposition de fichier)
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ticket.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        // Retourner le PDF avec les en-têtes et un statut HTTP 200
        return new ResponseEntity<>(pdfData, headers, HttpStatus.OK);
    }
}
