package com.myflights.flight.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.myflights.flight.entity.Bookings;

@Service
public class PdfGenerator {

    public byte[] generateTicketPdf(Bookings booking) throws IOException {
        // Création du document PDF
        Document document = new Document();
        
        // Utilisation de ByteArrayOutputStream pour générer le PDF en mémoire
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
           // logger.info("Generated PDF for booking ID: " + booking.getBkid());
            // Création du PdfWriter qui écrit dans le ByteArrayOutputStream
            PdfWriter.getInstance(document, outputStream);
            
            // Ouvrir le document pour commencer à écrire dedans
            document.open();
            
            // Définir la police
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12);
            
            // Ajouter le contenu du ticket dans le document PDF
            document.add(new Paragraph("Ticket for Booking ID: " + booking.getBkid(), font));
            document.add(new Paragraph("Flight from " + booking.getOrigin() + " to " + booking.getDest(), font));
            document.add(new Paragraph("Date: " + booking.getTravelDate(), font));
            document.add(new Paragraph("Amount: " + booking.getAmount(), font));
            
            // Fermer le document pour finaliser le PDF
            document.close();
            
            // Retourner le PDF sous forme de tableau d'octets
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new IOException("Error generating PDF", e);
        }
    }
}
