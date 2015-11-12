/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.util.*;
import ws.niceview.*;
/**
 *
 * @author Nis
 */
public class DataBase {
    
    List<Hotel> hotels;
    
    public DataBase() {
        
        hotels = new ArrayList();
        
        hotels.add(new Hotel("D'Angleterre", "Copenhagen", "Kgs Nytorv 34",
                123456, 5500, 0, true, "DK Hotels"));
        hotels.add(new Hotel("Toren", "Amsterdam", "Geile Strasse 69",
                123457, 700, 0, false, "Tripadvisor"));
        
    }
    
    public List<ws.niceview.Hotel> getHotels(String city, GregorianCalendar arr,
            GregorianCalendar dep) {
        
        List<ws.niceview.Hotel> result = new ArrayList();
        ws.niceview.Hotel tmp = new ws.niceview.Hotel();
        
        for (Hotel e : hotels) {
            if (e.getCity().equals(city)) {
                tmp.setName(e.getName());
                tmp.setAddress(e.getAddress());
                tmp.setBookNum(e.getBookNum());
                tmp.setPricePrN(e.getPricePrN());
                tmp.setCredit(e.getCredit());
                tmp.setService(e.getService());
                tmp.setPrice(getDays(arr,dep)*e.getPricePrN());
                
                result.add(tmp);
            }
        }
        
        return result;
    }
    
    public int getDays(GregorianCalendar arr, GregorianCalendar dep) {
        
        int days = 0;
        
        while(dep.compareTo(arr) > 0) {
            arr.add(Calendar.DATE, 1);
            days++;
        }
        
        return days;
    }
}