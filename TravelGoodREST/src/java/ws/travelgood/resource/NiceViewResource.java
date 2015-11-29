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

import ws.travelgood.data.HotelInfoType;
import ws.travelgood.data.HotelInfoList;
import ws.nv.*;
import ws.travelgood.data.Itiniery;
import ws.travelgood.representation.*;

/**
 * REST Web Service
 *
 * @author Nis
 */
@Path("niceview")
public class NiceViewResource {

    private static Map<String, HotelInfoType> lastSearch = new HashMap();
    
    @Path("reset")
    @PUT
    public void reset() {
        lastSearch = new HashMap();
    }
    
    @Path("hotels")
    @GET
    @Produces(ItinieryResource.MEDIATYPE_TRAVELGOOD)
    public Response hotels(@QueryParam("city") String city, 
            @QueryParam("arr") String arr, @QueryParam("dep") String dep) {
        
        GetRequest input = new GetRequest();
        DatatypeFactory df = null;
        try {
            df = DatatypeFactory.newInstance();
        } catch (Exception e) {}
        
        input.setCity(city);
        
        input.setArr(df.newXMLGregorianCalendar(arr+"T00:00:00"));
        input.setDep(df.newXMLGregorianCalendar(dep+"T00:00:00"));
        
        lastSearch = new HashMap();
        
        List<HotelInfo> result = getHotels(input).getHotelList();
        List<HotelInfoType> output = new ArrayList();
        
        for (HotelInfo hi : result) {
            HotelInfoType hit = new HotelInfoType(hi);
            lastSearch.put(Integer.toString(hi.getBookNum()), hit);
            output.add(hit);
        }
        
        HotelInfoList info = new HotelInfoList(output);
        
        return Response.ok(info).build();
    }
    
    @Path("{iid}/addhotel/{hid}")
    @PUT
    @Produces(ItinieryResource.MEDIATYPE_TRAVELGOOD)
    public StatusRepresentation addHotel(@PathParam("iid") String iid, 
            @PathParam("hid") String hid) {
        StatusRepresentation status = new StatusRepresentation();
        
        Itiniery itin = ItinieryResource.itins.get(iid);
        if (itin == null) {
            Response r = Response.
                    status(Response.Status.NOT_FOUND).
                    entity("Itiniery not found").
                    build();
            throw new NotFoundException(r);
        }
        
        HotelInfoType hotel = lastSearch.get(iid);
        if (hotel == null) {
            Response r = Response.
                    status(Response.Status.NOT_FOUND).
                    entity("No such booking number in last search").
                    build();
            throw new NotFoundException(r);
        }
        
        itin.addHotel(hotel);
        ItinieryResource.itins.put(iid, itin);
        
        status.setStatus(ItinieryResource.STATUS_UPDATED);
        
        return status;
    }

    private static GetResponse getHotels(ws.nv.GetRequest input) {
        ws.nv.NiceViewService service = new ws.nv.NiceViewService();
        ws.nv.NiceViewWSDLPortType port = service.getNiceViewWSDLPortTypeBindingPort();
        return port.getHotels(input);
    }
}