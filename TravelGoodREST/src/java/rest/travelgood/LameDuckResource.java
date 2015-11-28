/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.travelgood;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import ld.ws.*;
import nv.ws.HotelInfo;
import rest.representations.OrderRepresentation;

/**
 * REST Web Service
 *
 * @author Nis
 */
@Path("/TravelGood")
public class LameDuckResource {
    
    @Context
    private UriInfo context;
    
    private List<FlightInfo> lastSearch;

    @Path("/flights")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ListOfFlights getFlightsList(@QueryParam("origin") String origin, 
            @QueryParam("destination") String destination,
            @QueryParam("date") String date) throws Exception{
        
        Request input = new Request();
        
        DatatypeFactory df = DatatypeFactory.newInstance();
        
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        
        GregorianCalendar temp = new GregorianCalendar();
        
        Date d = format.parse(date);
        temp.setTime(d);
        XMLGregorianCalendar greg_date = df.newXMLGregorianCalendar(temp);
        
        input.setOrigin(origin);
        input.setDestination(destination);
        input.setDate(greg_date);
        
        ListOfFlights output = getFlights(input);
        
        lastSearch = output.getFlightInfo();
        
        return output;
    }
    
    @Path("/addflight/{id}/{book_num}")
    @POST
    public void addFlight(@PathParam("id") int id, 
            @PathParam("book_num") int book_num) {
        
        for (OrderRepresentation or : TravelGoodResource.getOrders()) {
            if (or.getId() == id) {
                for (FlightInfo fi : lastSearch) {
                    if (fi.getBookingNumber() == book_num) {
                        or.addFlight(fi);
                        break;
                    }
                }
                TravelGoodResource.setOrder(or);
                break;
            }
        }
    }
    
    public static boolean book(RequestbookFlight input) throws BookFlightFault_Exception{
        return bookFlight(input);
    }
    
    private static ListOfFlights getFlights(ld.ws.Request input) {
        ld.ws.LameduckService service = new ld.ws.LameduckService();
        ld.ws.LameduckPortType port = service.getLameduckPortTypeBindingPort();
        return port.getFlights(input);
    }

    private static boolean bookFlight(ld.ws.RequestbookFlight input) throws BookFlightFault_Exception {
        ld.ws.LameduckService service = new ld.ws.LameduckService();
        ld.ws.LameduckPortType port = service.getLameduckPortTypeBindingPort();
        return port.bookFlight(input);
    }

    private static boolean cancelFlight(ld.ws.RequestcancelFlight input) throws CancelFlightFault_Exception {
        ld.ws.LameduckService service = new ld.ws.LameduckService();
        ld.ws.LameduckPortType port = service.getLameduckPortTypeBindingPort();
        return port.cancelFlight(input);
    }
    
    
}
