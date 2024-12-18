import React, { useState, useEffect } from 'react';
import axios from 'axios';

const BookFlight = ({ match }) => {
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState('');
  const bookingId = match.params.bkid;  // Vous récupérez l'ID de réservation depuis l'URL

  useEffect(() => {
    // Cette fonction se déclenche lors du rendu du composant
    generateTicket(bookingId);
  }, [bookingId]);

  const generateTicket = async (bookingId) => {
    setLoading(true);
    try {
      const response = await axios.get(`http://localhost:8080/generate-ticket/${bookingId}`, {
        responseType: 'arraybuffer'  // Indique que nous attendons un fichier binaire (PDF)
      });

      // Créer un lien pour télécharger le PDF
      const blob = new Blob([response.data], { type: 'application/pdf' });
      const link = document.createElement('a');
      link.href = URL.createObjectURL(blob);
      link.download = `flight-ticket-${bookingId}.pdf`;  // Le nom du fichier PDF
      link.click();  // Simule le clic pour télécharger le fichier
      setMessage('Ticket PDF generated successfully!');
    } catch (error) {
      setMessage('Error generating ticket PDF.');
      console.error('Error generating PDF:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h1>Flight Booking</h1>
      {loading && <p>Loading...</p>}
      {message && <p>{message}</p>}
    </div>
  );
};

export default BookFlight;