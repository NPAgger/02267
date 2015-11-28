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
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import nv.ws.*;
import rest.representations.*;

/**
 * REST Web Service
 *
 * @author Nis
 */
@Path("/TravelGood")
public class NiceViewResource {

    @Context
    private UriInfo context;
    
    private List<HotelInfo> lastSearch;
    
    @Path("/hotels")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public GetResponse getHotelList(@QueryParam("city") String city, 
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
        
        GetResponse output = getHotels(input);
        
        lastSearch = output.getHotelList();
        
        return output;
    }
    
    @Path("/{id}/addhotel/{book_num}")
    @POST
    public void addHotel(@PathParam("id") int id, 
            @PathParam("book_num") int book_num) {
        
        for (OrderRepresentation or : TravelGoodResource.getOrders()) {
            if (or.getId() == id) {
                for (HotelInfo hi : lastSearch) {
                    if (hi.getBookNum() == book_num) {
                        or.addHotel(hi);
                        break;
                    }
                }
                TravelGoodResource.setOrder(or);
                break;
            }
        }
    }
    
    public static boolean book(BookRequest info) throws BookFault {
        return bookHotel(info).isOutput();
    }
    
    private static GetResponse getHotels(nv.ws.GetRequest input) {
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
    
}
