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
import javax.ws.rs.BadRequestException;
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
import ws.travelgood.data.CreditCardType;
import ws.travelgood.data.FlightInfoList;
import ws.travelgood.data.FlightInfoType;
import ws.travelgood.data.Itiniery;
import ws.travelgood.representation.StatusRepresentation;
import static ws.travelgood.resource.ItinieryResource.addBookLink;
import static ws.travelgood.resource.ItinieryResource.addCancelLink;
import static ws.travelgood.resource.ItinieryResource.addSelfLink;
import static ws.travelgood.resource.ItinieryResource.addStatusLink;
/**
 * REST Web Service
 *
 * @author Andri
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
            fit.setStatus("unconfirmed");
            lastSearch.put(Integer.toString(fi.getBookingNumber()), fit);
            output.add(fit);
        }
        
        FlightInfoList info = new FlightInfoList(output);
        
        return Response.ok(info).build();
    }
    
    @Path("addflight/{iid}/{fid}")
    @PUT
    @Produces(ItinieryResource.MEDIATYPE_TRAVELGOOD)
    public StatusRepresentation addFlight(@PathParam("iid") String iid, 
            @PathParam("fid") String fid) {
        StatusRepresentation status = new StatusRepresentation();
        
        Itiniery itin = ItinieryResource.itins.get(iid);
        if (itin == null) {
            Response r = Response.
                    status(Response.Status.NOT_FOUND).
                    entity("Itiniery not found").
                    build();
            throw new NotFoundException(r);
        } else if (!(itin.getStatus() == "running" || 
                itin.getStatus() == "updated")) {
            Response r = Response.
                    status(Response.Status.BAD_REQUEST).
                    entity("Itiniery is not open").
                    build();
            throw new BadRequestException(r);
        }
        
        FlightInfoType flight = lastSearch.get(fid);
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
        itin.setStatus(ItinieryResource.STATUS_UPDATED);
        
        addSelfLink(iid,status);
        addStatusLink(iid,status);
        addBookLink(iid,status);
        addCancelLink(iid,status);
        
        return status;
    }
    
    public static String bookList(String id, CreditCardType cc) {
        CreditCardInfoType credit = cc.toLDcc();
        
        Itiniery itin = ItinieryResource.itins.get(id);
        
        int counter;
        RequestbookFlight request = new RequestbookFlight();
        
        for (counter = 0; counter < itin.getFlights().size(); counter++ ) {
            request.setBookingNumber(itin.getFlights().get(counter).getFlight()
                    .getBookingNumber());
            request.setCreditCardInfo(credit);
            try {
                bookFlight(request);
                itin.getFlights().get(counter).setStatus("confirmed");
            } catch (BookFlightFault_Exception bf) {
                RequestcancelFlight cancel = new RequestcancelFlight();
                for (int i = 0; i <= counter; i++) {
                    cancel.setBookingNumber(itin.getFlights().get(i).getFlight()
                            .getBookingNumber());
                    cancel.setCreditCardInfo(credit);
                    cancel.setPrice(itin.getFlights().get(i).getFlight().getPrice());
                }
                return "booking "+counter+" failed";
            }
        }

        return "complete";
    }
    
    public static String cancelList(String id, CreditCardType cc) {
        CreditCardInfoType credit = cc.toLDcc();
        
        Itiniery itin = ItinieryResource.itins.get(id);
        
        String result = "complete";
        
        int counter;
        RequestcancelFlight cancel = new RequestcancelFlight();
        
        for (counter = 0; counter < itin.getFlights().size(); counter++ ) {
            cancel.setBookingNumber(itin.getFlights().get(counter).getFlight()
                    .getBookingNumber());
            cancel.setCreditCardInfo(credit);
            cancel.setPrice(itin.getFlights().get(counter).getFlight().getPrice());
            try {
                cancelFlight(cancel);
                itin.getFlights().get(counter).setStatus("cancelled");
            } catch (CancelFlightFault_Exception cf) {
                RequestbookFlight request = new RequestbookFlight();
                for (int i = 0; i <= counter; i++) {
                    request.setBookingNumber(itin.getFlights().get(i).getFlight()
                            .getBookingNumber());
                    request.setCreditCardInfo(credit);
                }
                result = "fail";
            }
        }

        return result;
    }

    private static ListOfFlights getFlights(ws.ld.Request input) {
        ws.ld.LameduckService service = new ws.ld.LameduckService();
        ws.ld.LameduckPortType port = service.getLameduckPortTypeBindingPort();
        return port.getFlights(input);
    }

    private static boolean bookFlight(ws.ld.RequestbookFlight input) throws BookFlightFault_Exception {
        ws.ld.LameduckService service = new ws.ld.LameduckService();
        ws.ld.LameduckPortType port = service.getLameduckPortTypeBindingPort();
        return port.bookFlight(input);
    }

    private static boolean cancelFlight(ws.ld.RequestcancelFlight input) throws CancelFlightFault_Exception {
        ws.ld.LameduckService service = new ws.ld.LameduckService();
        ws.ld.LameduckPortType port = service.getLameduckPortTypeBindingPort();
        return port.cancelFlight(input);
    }
}
