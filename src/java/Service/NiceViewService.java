/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import javax.jws.WebService;
import java.util.*;

/**
 *
 * @author Nis
 */
@WebService(serviceName = "NiceViewWSDLPortTypeService", portName = "NiceViewWSDLPortTypePort", endpointInterface = "ws.niceview.NiceViewWSDLPortType", targetNamespace = "http://niceview.ws", wsdlLocation = "WEB-INF/wsdl/NiceViewService/NiceViewWSDLWrapper.wsdl")
public class NiceViewService {
    
    DB.DataBase db = new DB.DataBase();
    List<ws.niceview.Hotel> hotels = new ArrayList();

    public ws.niceview.Response getHotels(ws.niceview.Request input) {
        
        ws.niceview.Response output = new ws.niceview.Response();
        
        hotels = db.getHotels(input.getCity(), 
                input.getArr().toGregorianCalendar(), 
                input.getDep().toGregorianCalendar());
        
        for (ws.niceview.Hotel e : hotels) {
            output.getHotelList().add(e);
        }
        
        return output;
    }
    
}
