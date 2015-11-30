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
 * @author Nis
 */
@XmlRootElement
public class FlightInfoType {
    @XmlElement
    public FlightInfo values;
    
    public FlightInfoType() {}
    
    public FlightInfoType(FlightInfo info) {
        values = info;
    }
    
    public int getBookNum() {
        return values.getBookingNumber();
    }
    
    public FlightInfo getFlight() {
        return values;
    }
}
