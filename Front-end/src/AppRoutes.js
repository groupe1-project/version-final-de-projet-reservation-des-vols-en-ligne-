
import React from 'react';
import {BrowserRouter, Routes, Route} from 'react-router-dom';
import LandingView from './pages/LandingView';
import FlightInfo from './pages/FlightInfo';
import BookingInfo from './pages/BookingInfo';
import BookFlight from './component/BookFlight'; 


export default function AppRoutes(props) {




    return(
     <BrowserRouter>
         <Routes> 
            <Route path='/' element = {<LandingView/>} />
            <Route path='/view-flight-info' element = {<FlightInfo/>} />
            <Route path='/book-flight/:flid' element = {<BookingInfo/>} />
            <Route path='/book-flight/:bkid' element={<BookFlight />} /> 
         </Routes>
         
     </BrowserRouter>

    )
}


