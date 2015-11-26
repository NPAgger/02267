/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.travelgood;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nis
 */
public class Itiniery {
    
    private List<Integer> hotels;
    private List<Integer> flights;
    private int id;
    
    public Itiniery(int id) {
        this.id = id;
        
        hotels = new ArrayList();
        flights = new ArrayList();
    }
    
    public int getId() {
        return this.id;
    }
    
    public List<Integer> getHotels() {
        return this.hotels;
    }
    
    public List<Integer> getFlights() {
        return this.flights;
    }
    
    public void addHotel(int book_num) {
        this.hotels.add(book_num);
    }
    
    public void addFlight(int book_num) {
        this.flights.add(book_num);
    }
    
}
