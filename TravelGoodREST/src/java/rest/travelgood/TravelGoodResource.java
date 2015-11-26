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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import ld.ws.BookFlightFault_Exception;
import ld.ws.CancelFlightFault_Exception;
import ld.ws.ListOfFlights;

import nv.ws.*;
import ld.ws.*;

/**
 * REST Web Service
 *
 * @author Nis
 */
@Path("/TravelGood")
public class TravelGoodResource {

    List<Itiniery> running;
    List<Itiniery> booked;
    
    @Path("/new")
    @GET
    public int newItin() {
        int id = running.size() + 1;
        
        Itiniery itin = new Itiniery(id);
        
        running.add(itin);
        
        return id;
    }
    
    @Path("/hotels")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public GetResponse getHotels(@QueryParam("city") String city, 
            @QueryParam("arrival") String arrival,
            @QueryParam("departure") String departure) throws Exception{
        GetRequest input = new GetRequest();
        
        DatatypeFactory df = DatatypeFactory.newInstance();
        
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        
        GregorianCalendar temp = new GregorianCalendar();
        
        Date date = format.parse(arrival);
        temp.setTime(date);
        XMLGregorianCalendar arr = df.newXMLGregorianCalendar(temp);
        
        date = format.parse(departure);
        temp.setTime(date);
        XMLGregorianCalendar dep = df.newXMLGregorianCalendar(temp);
        
        input.setCity(city);
        input.setArr(arr);
        input.setDep(dep);
        
        GetResponse output = getHotels_1(input);
        
        return output;
    }
    
    @Path("/addhotel/{id}/{book_num}")
    @POST
    public void addHotel(@PathParam("id") int id, 
            @PathParam("book_num") int book_num) {
        
        for (Itiniery itin : running) {
            if (itin.getId() == id) {
                itin.addHotel(book_num);
                break;
            }
        }
    }
    
    @Path("/addflight/{id}/{book_num}")
    @POST
    public void addFlight(@PathParam("id") int id, 
            @PathParam("book_num") int book_num) {
        
        for (Itiniery itin : running) {
            if (itin.getId() == id) {
                itin.addFlight(book_num);
                break;
            }
        }
    }
    
    @Path("/book/{id}/{name}/{numer}/{month}/{year}")
    @POST
    public void bookItin(@PathParam("id") int id, @PathParam("name") String name, 
            @PathParam("number") String number, @PathParam("month") int month, 
            @PathParam("year") int year) {
                
        BookRequest br = new BookRequest();
        br.getCreditCardInfo().getExpirationDate().setMonth(month);
        br.getCreditCardInfo().getExpirationDate().setMonth(year);
        br.getCreditCardInfo().setName(name);
        br.getCreditCardInfo().setNumber(number);
        
        RequestbookFlight rbf = new RequestbookFlight();
        rbf.getCreditCardInfo().getExpirationDate().setMonth(month);
        rbf.getCreditCardInfo().getExpirationDate().setMonth(year);
        rbf.getCreditCardInfo().setName(name);
        rbf.getCreditCardInfo().setNumber(number);
        
        for (Itiniery itin : running) {
            if (itin.getId() == id) {
                for (int i : itin.getHotels()){
                    br.setBookNum(i);
                    try {
                        bookHotel(br);
                    } catch (Exception e) {
                    }
                }
                for (int i : itin.getFlights()) {
                    rbf.setBookingNumber(i);
                    try {
                        bookFlight(rbf);
                    } catch (Exception e) {
                    }
                }
                running.remove(itin);
                booked.add(itin);
                break;
            }
        }
    }
    
    
    /* 
        Below are Web Service Reference methods to NiceView and LameDuck
    */
    
    private static GetResponse getHotels_1(nv.ws.GetRequest input) {
        nv.ws.NiceViewService service = new nv.ws.NiceViewService();
        nv.ws.NiceViewWSDLPortType port = service.getNiceViewWSDLPortTypeBindingPort();
        return port.getHotels(input);
    }

    private static BookResponse bookHotel(nv.ws.BookRequest input) throws BookFault {
        nv.ws.NiceViewService service = new nv.ws.NiceViewService();
        nv.ws.NiceViewWSDLPortType port = service.getNiceViewWSDLPortTypeBindingPort();
        return port.bookHotel(input);
    }

    private static CancelResponse cancelHotel(nv.ws.CancelRequest input) throws CancelFault_Exception {
        nv.ws.NiceViewService service = new nv.ws.NiceViewService();
        nv.ws.NiceViewWSDLPortType port = service.getNiceViewWSDLPortTypeBindingPort();
        return port.cancelHotel(input);
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