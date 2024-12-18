package com.myflights.flight.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.myflights.flight.entity.Bookings;

@Service
public class PdfGenerator {

    public byte[] generateTicketPdf(Bookings booking) throws IOException {
        // Utilisation de try-with-resources pour garantir que ByteArrayOutputStream est bien fermé
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            // Créez le contenu du PDF sous forme de chaîne de caractères
            String pdfContent = "Ticket for Booking ID: " + booking.getBkid() + "\n" +
                    "Flight from " + booking.getOrigin() + " to " + booking.getDest() +
                    "\nDate: " + booking.getTravelDate() + "\nAmount: " + booking.getAmount();

            // Écrire dans le ByteArrayOutputStream
            outputStream.write(pdfContent.getBytes());

            // Retourner le contenu du PDF sous forme de tableau d'octets
            return outputStream.toByteArray();
        }
        // Pas besoin de fermer explicitement le ByteArrayOutputStream car try-with-resources le fait automatiquement
    }
}
