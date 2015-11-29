/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.xml.datatype.DatatypeFactory;

import ws.ld.*;
import ws.travelgood.data.FlightInfoList;
import ws.travelgood.data.FlightInfoType;
import ws.travelgood.data.HotelInfoType;
import ws.travelgood.data.Itiniery;
import ws.travelgood.representation.StatusRepresentation;
/**
 * REST Web Service
 *
 * @author Nis
 */
@Path("lameduck")
public class LameDuckResource {

    private static Map<String, FlightInfoType> lastSearch = new HashMap();
    
    @Path("reset")
    @PUT
    public void reset() {
        lastSearch = new HashMap();
    }
    
    @Path("flights")
    @GET
    @Produces(ItinieryResource.MEDIATYPE_TRAVELGOOD)
    public Response flights(@QueryParam("orig") String orig, 
            @QueryParam("dest") String dest, @QueryParam("date") String date) {
        Request input = new Request();
        DatatypeFactory df = null;
        try {
            df = DatatypeFactory.newInstance();
        } catch (Exception e) {}
        
        input.setOrigin(orig);
        input.setDestination(dest);
        input.setDate(df.newXMLGregorianCalendar(date+"T00:00:00"));
        
        lastSearch = new HashMap();
        
        List<FlightInfo> result = getFlights(input).getFlightInfo();
        List<FlightInfoType> output = new ArrayList();
        
        for (FlightInfo fi : result) {
            FlightInfoType fit = new FlightInfoType(fi);
            lastSearch.put(Integer.toString(fi.getBookingNumber()), fit);
            output.add(fit);
        }
        
        FlightInfoList info = new FlightInfoList(output);
        
        return Response.ok(info).build();
    }
    
    @Path("{iid}/addflight/{hid}")
    @PUT
    @Produces(ItinieryResource.MEDIATYPE_TRAVELGOOD)
    public StatusRepresentation addFlight(@PathParam("iid") String iid, 
            @PathParam("hid") String fid) {
        StatusRepresentation status = new StatusRepresentation();
        
        Itiniery itin = ItinieryResource.itins.get(iid);
        if (itin == null) {
            Response r = Response.
                    status(Response.Status.NOT_FOUND).
                    entity("Itiniery not found").
                    build();
            throw new NotFoundException(r);
        }
        
        FlightInfoType flight = lastSearch.get(iid);
        if (flight == null) {
            Response r = Response.
                    status(Response.Status.NOT_FOUND).
                    entity("No such booking number in last search").
                    build();
            throw new NotFoundException(r);
        }
        
        itin.addFlight(flight);
        ItinieryResource.itins.put(iid, itin);
        
        status.setStatus(ItinieryResource.STATUS_UPDATED);
        
        return status;
    }

    private static ListOfFlights getFlights(ws.ld.Request input) {
        ws.ld.LameduckService service = new ws.ld.LameduckService();
        ws.ld.LameduckPortType port = service.getLameduckPortTypeBindingPort();
        return port.getFlights(input);
    }
}
