/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ws.nv.HotelInfo;
/**
 *
 * @author Nis
 */
@XmlRootElement
public class HotelInfoType {
    @XmlElement
    private HotelInfo values;
    private String status;
    
    public HotelInfoType() {}
    
    public HotelInfoType(HotelInfo info) {
        this.values = info;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return status;
    }
    
    public int getBookNum() {
        return values.getBookNum();
    }
    
    public HotelInfo getHotel() {
        return values;
    }
}