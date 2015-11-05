/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lameduck.ws;

import java.util.Arrays;
import flightservice.*;
import java.util.List;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Andri
 */
@WebService(serviceName = "lameduckService", portName = "lameduckPortTypeBindingPort", endpointInterface = "flightservice.LameduckPortType", targetNamespace = "flightservice", wsdlLocation = "WEB-INF/wsdl/LameDuck/lameduck.wsdl")
public class LameDuck {
    
    private static DatatypeFactory df;
    static{
        DatatypeFactory d = null;
        try{
            d = DatatypeFactory.newInstance();
        }
        catch(DatatypeConfigurationException e){
        }
        df = d;
    }
    
    FlightInfo flight0 = dataInit(1,500,"Dohop","Iceland","China",df.newXMLGregorianCalendar("2015-11-10T00:00:00"),df.newXMLGregorianCalendar("2015-11-20T00:00:00"), "Boeing 747");
    FlightInfo flight1 = dataInit(1,500,"Dohop","Iceland","China",df.newXMLGregorianCalendar("2015-11-10T00:00:00"),df.newXMLGregorianCalendar("2015-11-20T00:00:00"), "Boeing 747");
    FlightInfo flight2 = dataInit(1,500,"Dohop","Iceland","China",df.newXMLGregorianCalendar("2015-11-10T00:00:00"),df.newXMLGregorianCalendar("2015-11-20T00:00:00"), "Boeing 747");
    FlightInfo flight3 = dataInit(1,500,"Dohop","Iceland","China",df.newXMLGregorianCalendar("2015-11-10T00:00:00"),df.newXMLGregorianCalendar("2015-11-20T00:00:00"), "Boeing 747");
    FlightInfo flight4 = dataInit(1,500,"Dohop","Iceland","China",df.newXMLGregorianCalendar("2015-11-10T00:00:00"),df.newXMLGregorianCalendar("2015-11-20T00:00:00"), "Boeing 747");
    FlightInfo flight5 = dataInit(1,500,"Dohop","Iceland","China",df.newXMLGregorianCalendar("2015-11-10T00:00:00"),df.newXMLGregorianCalendar("2015-11-20T00:00:00"), "Boeing 747");
    FlightInfo flight6 = dataInit(1,500,"Dohop","Iceland","China",df.newXMLGregorianCalendar("2015-11-10T00:00:00"),df.newXMLGregorianCalendar("2015-11-20T00:00:00"), "Boeing 747");
    FlightInfo flight7 = dataInit(1,500,"Dohop","Iceland","China",df.newXMLGregorianCalendar("2015-11-10T00:00:00"),df.newXMLGregorianCalendar("2015-11-20T00:00:00"), "Boeing 747");
    FlightInfo flight8 = dataInit(1,500,"Dohop","Iceland","China",df.newXMLGregorianCalendar("2015-11-10T00:00:00"),df.newXMLGregorianCalendar("2015-11-20T00:00:00"), "Boeing 747");
    FlightInfo flight9 = dataInit(1,500,"Dohop","Iceland","China",df.newXMLGregorianCalendar("2015-11-10T00:00:00"),df.newXMLGregorianCalendar("2015-11-20T00:00:00"), "Boeing 747");
    FlightInfo flight10 = dataInit(1,500,"Dohop","Iceland","China",df.newXMLGregorianCalendar("2015-11-10T00:00:00"),df.newXMLGregorianCalendar("2015-11-20T00:00:00"), "Boeing 747");
    FlightInfo flight11 = dataInit(1,500,"Dohop","Iceland","China",df.newXMLGregorianCalendar("2015-11-10T00:00:00"),df.newXMLGregorianCalendar("2015-11-20T00:00:00"), "Boeing 747");
    
    
    
    public List<FlightInfo> staticFlightData = Arrays.asList(flight0,flight1,flight2,flight3,flight4,flight5,flight6,flight7,flight8,flight9,flight10);
    
    
    public FlightInfo dataInit(int bookNmr, float price, String airlineRes, String ori, String des, XMLGregorianCalendar dep, XMLGregorianCalendar arr, String carr){
        FlightInfo info = new FlightInfo();
        info.setAirlineReservationService(airlineRes);
        info.setBookingNumber(bookNmr);
        info.setPrice(price);
        Flight flight = new Flight();
        flight.setOrigin(ori);
        flight.setDestination(des);
        flight.setDeparture(dep);
        flight.setArrival(arr);
        flight.setCarrier(carr);
        info.setFlight(flight);
        
        return info;
    }
    

    public flightservice.ListOfFlights getFlights(flightservice.Request input) {
        flightservice.ListOfFlights list = new flightservice.ListOfFlights();
        
        for(int i = 0; i < staticFlightData.size(); i++){
            if(input.getOrigin().equals(staticFlightData.get(i).getFlight().getOrigin()) && input.getDestination().equals(staticFlightData.get(i).getFlight().getDestination())){
                if(input.getDate().equals(staticFlightData.get(i).getFlight().getDeparture())){
                    flightservice.FlightInfo info = new flightservice.FlightInfo();
                    info.setBookingNumber(staticFlightData.get(i).getBookingNumber());
                    info.setPrice(staticFlightData.get(i).getPrice());
                    info.setAirlineReservationService(staticFlightData.get(i).getAirlineReservationService());
                    flightservice.Flight flight = new flightservice.Flight();
                    flight.setOrigin(staticFlightData.get(i).getFlight().getOrigin());
                    flight.setDestination(staticFlightData.get(i).getFlight().getDestination());
                    flight.setDeparture(staticFlightData.get(i).getFlight().getDeparture());
                    flight.setArrival(staticFlightData.get(i).getFlight().getArrival());
                    flight.setCarrier(staticFlightData.get(i).getFlight().getCarrier());
                    info.setFlight(flight);
                
                    list.getFlightInfo().add(info);
                }
            }
        }
        return list;
    }

    public boolean bookFlight(flightservice.RequestbookFlight input) throws flightservice.BookFlightFault_Exception {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public boolean cancelFlight(flightservice.RequestcancelFlight input) throws flightservice.CancelFlightFault_Exception {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}
