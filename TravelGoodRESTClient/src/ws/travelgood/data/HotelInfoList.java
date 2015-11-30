/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.data;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Thomas
 */
@XmlRootElement
public class HotelInfoList {
    @XmlElement
    public List<HotelInfoType> values = new ArrayList();
    
    public HotelInfoList() {}
    
    public HotelInfoList(List<HotelInfoType> list) {
        values = list;
    }
    
    public List<HotelInfoType> getList() {
        return values;
    }
    
    public void setList(List<HotelInfoType> list) {
        this.values = list;
    }
    
    public void addHotel(HotelInfoType hotel) {
        this.values.add(hotel);
    }
}