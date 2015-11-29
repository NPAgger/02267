/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.data;

import java.util.List;
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
    public HotelInfo values;
    
    public HotelInfoType() {}
    
    public HotelInfoType(HotelInfo info) {
        values = info;
    }
    
    public int getBookNum() {
        return values.getBookNum();
    }
}

