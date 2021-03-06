/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ws.ld.FlightInfo;
/**
 *
 * @author Andri
 */
@XmlRootElement
public class FlightInfoType {
    @XmlElement
    private FlightInfo values;
    private String status;
    
    public FlightInfoType() {}
    
    public FlightInfoType(FlightInfo info) {
        this.values = info;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return status;
    }
    
    public int getBookNum() {
        return values.getBookingNumber();
    }
    
    public FlightInfo getFlight() {
        return values;
    }
}