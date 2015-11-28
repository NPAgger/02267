/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.travelgood;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response;

import nv.ws.*;
import ld.ws.*;
import rest.representations.*;

/**
 * REST Web Service
 *
 * @author Nis
 */
@Path("/TravelGood")
public class TravelGoodResource {

    @Context
    private UriInfo context;
    
    private static List<OrderRepresentation> orders;
    
    private static List<StatusRepresentation> statuses;
    
    @Path("/new")
    @POST
    public int newItin() {
        int id = statuses.size() + 1;
        
        OrderRepresentation order = new OrderRepresentation(id);
        StatusRepresentation status = new StatusRepresentation(id, "Open");
        
        orders.add(order);
        statuses.add(status);
        
        return id;
    }
    
    @Path("/{id}/get/status")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getStatus(@PathParam("id") int id) {
        
        for (StatusRepresentation srep : statuses) {
            if (srep.getId() == id) {
                return Response.ok(srep).build();
            }
        }
        
        return Response.ok().build();
    }
    
    @Path("/{id}/get/order")
    @GET
    public Response getOrder(@PathParam("id") int id) {
        for (OrderRepresentation or : orders) {
            if (or.getId() == id) {
                return Response.ok(or).build();
            }
        }
        
        return Response.ok().build();
    }
    
    @Path("/book/{id}/{name}/{numer}/{month}/{year}")
    @POST
    public void bookItin(@PathParam("id") int id, @PathParam("name") String name, 
            @PathParam("number") String number, @PathParam("month") int month, 
            @PathParam("year") int year) throws Exception{
                
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
        
        for (OrderRepresentation order : orders) {
            for (HotelInfo hi : order.getHotels()) {
                br.setBookNum(hi.getBookNum());
                NiceViewResource.book(br);
            }
            for (FlightInfo fi : order.getFlights()) {
                rbf.setBookingNumber(fi.getBookingNumber());
                LameDuckResource.book(rbf);
            }
        }
        
        for (StatusRepresentation srep : statuses) {
            if (srep.getId() == id) {
                srep.setStatus("Booked");
                break;
            }
        }
    }
    
    
    public static List<OrderRepresentation> getOrders() {
        return orders;
    }
    
    public static void setOrder(OrderRepresentation order) {
        for (OrderRepresentation o : orders) {
            if (o.getId() == o.getId()) {
                o = order;
                break;
            }
        }
    }
}