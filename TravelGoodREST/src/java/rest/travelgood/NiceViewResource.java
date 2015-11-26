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
import nv.ws.*;

/**
 *
 * @author Nis
 */

@Path("/TravelGood")
public class NiceViewResource {
    
    @Path("/hotels")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public GetResponse getHotels(@QueryParam("city") String city, 
            @QueryParam("arrival") String arrival,
            @QueryParam("departure") String departure) throws Exception{
        GetRequest input = new GetRequest();
        
        DatatypeFactory df = DatatypeFactory.newInstance();
        
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        
        GregorianCalendar temp = new GregorianCalendar();
        
        Date date = format.parse(arrival);
        temp.setTime(date);
        XMLGregorianCalendar arr = df.newXMLGregorianCalendar(temp);
        
        date = format.parse(departure);
        temp.setTime(date);
        XMLGregorianCalendar dep = df.newXMLGregorianCalendar(temp);
        
        input.setCity(city);
        input.setArr(arr);
        input.setDep(dep);
        
        GetResponse output = getHotels_1(input);
        
        return output;
    }

    private static GetResponse getHotels_1(nv.ws.GetRequest input) {
        nv.ws.NiceViewService service = new nv.ws.NiceViewService();
        nv.ws.NiceViewWSDLPortType port = service.getNiceViewWSDLPortTypeBindingPort();
        return port.getHotels(input);
    }
    
    
}
