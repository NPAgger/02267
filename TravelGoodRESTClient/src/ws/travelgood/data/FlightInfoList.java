/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.data;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nis
 */
@XmlRootElement
public class FlightInfoList {
    @XmlElement
    public List<FlightInfoType> values;
    
    public FlightInfoList() {}
    
    public FlightInfoList(List<FlightInfoType> list) {
        values = list;
    }
    
    public List<FlightInfoType> getList() {
        return values;
    }
    
    public void setList(List<FlightInfoType> list) {
        this.values = list;
    }
    
    public void addFlight(FlightInfoType flight) {
        this.values.add(flight);
    }
}