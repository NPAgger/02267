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

import ws.travelgood.data.HotelInfoType;
import ws.travelgood.data.HotelInfoList;
import ws.nv.*;
import ws.travelgood.data.CreditCardType;
import ws.travelgood.data.Itiniery;
import ws.travelgood.representation.*;
import static ws.travelgood.resource.ItinieryResource.addBookLink;
import static ws.travelgood.resource.ItinieryResource.addCancelLink;
import static ws.travelgood.resource.ItinieryResource.addSelfLink;
import static ws.travelgood.resource.ItinieryResource.addStatusLink;

/**
 * REST Web Service
 *
 * @author Mathias
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
            hit.setStatus("unconfirmed");
            lastSearch.put(Integer.toString(hi.getBookNum()), hit);
            output.add(hit);
        }
        
        HotelInfoList info = new HotelInfoList(output);
        
        return Response.ok(info).build();
    }
    
    @Path("addhotel/{iid}/{hid}")
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
        } else if (!(itin.getStatus() == "running" || 
                itin.getStatus() == "updated")) {
            Response r = Response.
                    status(Response.Status.BAD_REQUEST).
                    entity("Itiniery is not open").
                    build();
            throw new BadRequestException(r);
        }
        
        HotelInfoType hotel = lastSearch.get(hid);
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
        itin.setStatus(ItinieryResource.STATUS_UPDATED);
        
        addSelfLink(iid,status);
        addStatusLink(iid,status);
        addBookLink(iid,status);
        addCancelLink(iid,status);
        
        return status;
    }
    
    public static String bookList(String id, CreditCardType cc) {
        CreditCardInfoType credit = cc.toNVcc();
        
        Itiniery itin = ItinieryResource.itins.get(id);
        
        int counter;
        BookRequest request = new BookRequest();
        
        for (counter = 0; counter < itin.getHotels().size(); counter++ ) {
            request.setBookNum(itin.getHotels().get(counter).getHotel().getBookNum());
            request.setCreditCardInfo(credit);
            try {
                bookHotel(request);
                itin.getHotels().get(counter).setStatus("confirmed");
            } catch (BookFault bf) {
                CancelRequest cancel = new CancelRequest();
                for (int i = 0; i <= counter; i++) {
                    cancel.setBookNum(itin.getHotels().get(i).getHotel().getBookNum());
                }
                return "booking "+counter+" failed";
            }
        }

        return "complete";
    }
    
    public static String cancelList(String id, CreditCardType cc) {
        CreditCardInfoType credit = cc.toNVcc();
        
        Itiniery itin = ItinieryResource.itins.get(id);
        
        String result = "complete";
        
        int counter;
        CancelRequest cancel = new CancelRequest();
        
        for (counter = 0; counter < itin.getHotels().size(); counter++ ) {
            cancel.setBookNum(itin.getHotels().get(counter).getHotel().getBookNum());
            try {
                cancelHotel(cancel);
                itin.getHotels().get(counter).setStatus("cancelled");
            } catch (CancelFault_Exception cf) {
                BookRequest request = new BookRequest();
                for (int i = 0; i <= counter; i++) {
                    request.setBookNum(itin.getHotels().get(counter).getHotel().getBookNum());
                    request.setCreditCardInfo(credit);
                }
                result = "fail";
            }
        }

        return result;
    }

    private static GetResponse getHotels(ws.nv.GetRequest input) {
        ws.nv.NiceViewService service = new ws.nv.NiceViewService();
        ws.nv.NiceViewWSDLPortType port = service.getNiceViewWSDLPortTypeBindingPort();
        return port.getHotels(input);
    }

    private static boolean bookHotel(ws.nv.BookRequest input) throws BookFault {
        ws.nv.NiceViewService service = new ws.nv.NiceViewService();
        ws.nv.NiceViewWSDLPortType port = service.getNiceViewWSDLPortTypeBindingPort();
        return port.bookHotel(input);
    }

    private static boolean cancelHotel(ws.nv.CancelRequest input) throws CancelFault_Exception {
        ws.nv.NiceViewService service = new ws.nv.NiceViewService();
        ws.nv.NiceViewWSDLPortType port = service.getNiceViewWSDLPortTypeBindingPort();
        return port.cancelHotel(input);
    }
}