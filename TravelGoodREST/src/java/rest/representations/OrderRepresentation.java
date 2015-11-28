/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.representations;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Link;

import nv.ws.*;
import ld.ws.*;
/**
 *
 * @author Nis
 */

public class OrderRepresentation extends Representation{
    
    private List<HotelInfo> hotels;
    private List<FlightInfo> flights;
    private List<Link> links;
    
    public OrderRepresentation(int id) {
        super(id);
        links = new ArrayList();
        
        String base = 
                "http://localhost:8080/TravelGoodREST/webresources/TravelGood/"
                + this.getId();
        
        this.links.add(Link.fromUri(base + "/get/order").build());
        this.links.add(Link.fromUri(base + "/get/status").build());
        this.links.add(Link.fromUri(base + "/addhotel/{book_num}").build());
        this.links.add(Link.fromUri(base + "/addflight/{book_num}").build());
        this.links.add(Link.fromUri(base + "/cancel").build());
        
        this.hotels = new ArrayList();
        this.flights = new ArrayList();
    }
    
    public List<HotelInfo> getHotels() {
        return this.hotels;
    }
    
    public List<FlightInfo> getFlights() {
        return this.flights;
    }
    
    public void addHotel(HotelInfo hotel) {
        this.hotels.add(hotel);
    }
    
    public void addFlight(FlightInfo flight) {
        this.flights.add(flight);
    }
}