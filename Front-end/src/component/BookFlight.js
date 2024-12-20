import React, { useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

const BookFlight = () => {
  const { bkid } = useParams();  // Utilisation de useParams pour récupérer l'ID
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState('');

  const generateTicket = async () => {
    console.log('Function generateTicket called with bookingId:', bkid);
    setLoading(true);

    try {
      // Envoi de la requête pour générer le PDF
      const response = await axios.get(`http://localhost:8080/generate-ticket/${bkid}`, {
        responseType: 'arraybuffer'  // Attente d'un fichier binaire (PDF)
      });
      console.log('Received response:', response);
      
      // Création du fichier Blob à partir de la réponse
      const blob = new Blob([response.data], { type: 'application/pdf' });
      const link = document.createElement('a');
      link.href = URL.createObjectURL(blob);
      link.download = `flight-ticket-${bkid}.pdf`;  // Nom du fichier PDF
      
      // Ajouter le lien au DOM et simuler un clic pour démarrer le téléchargement
      document.body.appendChild(link);
      
      // Attendre un petit délai avant de simuler le clic (parfois nécessaire)
      setTimeout(() => {
        link.click();  // Déclenche le téléchargement
        document.body.removeChild(link);  // Retirer le lien après utilisation
      }, 100);

      setMessage('Ticket PDF generated successfully!');
    } catch (error) {
      setMessage(`Error generating ticket PDF: ${error.message}`);
      console.error('Error generating PDF:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h1>Flight Booking</h1>
      <button onClick={generateTicket} disabled={loading}>Book this flight</button>
      {loading && <p>Loading...</p>}
      {message && <p>{message}</p>}
    </div>
  );
};

export default BookFlight;
