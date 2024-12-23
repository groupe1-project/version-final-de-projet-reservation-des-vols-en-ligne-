package com.myflights.flight.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.myflights.flight.entity.Bookings;

@Service
public class PdfGenerator {

    private static final Logger logger = LoggerFactory.getLogger(PdfGenerator.class);

    public byte[] generateTicketPdf(Bookings booking) throws IOException {
        // Vérification préalable des données de la réservation
        if (booking == null) {
            throw new IllegalArgumentException("Booking object cannot be null");
        }

        if (booking.getBkid() == null || booking.getOrigin() == null || booking.getDest() == null 
            || booking.getTravelDate() == null || booking.getAmount() == null) {
            throw new IllegalArgumentException("Booking data contains null fields. Please verify the input.");
        }

        // Création du document PDF
        Document document = new Document();

        // Utilisation de ByteArrayOutputStream pour générer le PDF en mémoire
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // Création du PdfWriter
            PdfWriter.getInstance(document, outputStream);

            // Ouvrir le document
            document.open();

            // Définir la police
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12);

            // Ajouter le contenu du ticket dans le document PDF
            document.add(new Paragraph("Ticket for Booking ID: " + booking.getBkid(), font));
            document.add(new Paragraph("Flight from " + booking.getOrigin() + " to " + booking.getDest(), font));
            document.add(new Paragraph("Date: " + booking.getTravelDate(), font));
            document.add(new Paragraph("Amount: " + booking.getAmount() + " USD", font));

            // Fermer le document pour finaliser le PDF
            document.close();

            // Retourner le PDF sous forme de tableau d'octets
            return outputStream.toByteArray();
        } catch (Exception e) {
            logger.error("Error generating PDF", e);
            throw new IOException("Error generating PDF", e);
        }
    }
}
