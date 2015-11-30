/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.data;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author Nis
 */
@XmlRootElement
public class Itiniery {
    private String id;
    private HotelInfoList hotels = new HotelInfoList();
    private FlightInfoList flights = new FlightInfoList();
    private String status;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public List<HotelInfoType> getHotels() {
        return hotels.getList();
    }
    
    public void setHotels(List<HotelInfoType> hotels) {
        this.hotels.setList(hotels);
    }
    
    public void addHotel(HotelInfoType hotel) {
        this.hotels.addHotel(hotel);
    }
    
    public List<FlightInfoType> getFlights() {
        return flights.getList();
    }
    
    public void setFlights(List<FlightInfoType> flights) {
        this.flights.setList(flights);
    }
    
    public void addFlight(FlightInfoType flight) {
        flights.addFlight(flight);
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
}
