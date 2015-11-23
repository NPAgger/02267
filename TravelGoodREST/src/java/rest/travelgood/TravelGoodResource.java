/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.travelgood;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceRef;
import ws.nv.*;

/**
 *
 * @author Nis
 */

@Path("TravelGood")
public class TravelGoodResource {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/NiceView/NiceViewService.wsdl")
    private NiceViewService nvs;
    
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_XML)
    public GetResponse getHotels(String in) throws Exception{
        GetRequest input = new GetRequest();
        
        //String in = "Copenhagen-01 01 2015-02 01 2015";
        
        String[] list = in.split("-");
        
        DateFormat df = new SimpleDateFormat("dd MM yyyy");
        Date arr = df.parse(list[1]);
        Date dep = df.parse(list[2]);
        
        GregorianCalendar arrival = new GregorianCalendar();
        arrival.setTime(arr);
        GregorianCalendar departure = new GregorianCalendar();
        arrival.setTime(dep);
        
        XMLGregorianCalendar finalArr = DatatypeFactory.newInstance().newXMLGregorianCalendar(arrival);
        XMLGregorianCalendar finalDep = DatatypeFactory.newInstance().newXMLGregorianCalendar(departure);
        
        input.setCity(list[0]);
        input.setArr(finalArr);
        input.setDep(finalDep);
        
        GetResponse output = getHotels_1(input);
        
        return output;
    }

    private static GetResponse getHotels_1(ws.nv.GetRequest input) {
        ws.nv.NiceViewService service = new ws.nv.NiceViewService();
        ws.nv.NiceViewWSDLPortType port = service.getNiceViewWSDLPortTypeBindingPort();
        return port.getHotels(input);
    }
    
    
}
