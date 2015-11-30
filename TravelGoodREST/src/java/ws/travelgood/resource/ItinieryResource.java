/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ws.travelgood.data.*;
import ws.travelgood.representation.*;

/**
 * REST Web Service
 *
 * @author Nis
 */
@Path("travelgood")
public class ItinieryResource {

    static final String STATUS_RUNNING = "running";
    static final String STATUS_UPDATED = "updated";
    static final String STATUS_BOOKED = "booked";
    static final String STATUS_CANCELLED = "cancelled";
    static final String RELATION_BASE = "http://travelgood.ws/relations/";
    static final String CANCEL_RELATION = RELATION_BASE + "cancel";
    static final String SELF_RELATION = RELATION_BASE + "self";
    static final String STATUS_RELATION = RELATION_BASE + "status";
    static final String BOOK_RELATION = RELATION_BASE + "book";

    static final String MEDIATYPE_TRAVELGOOD = "application/travelgood+xml";
    
    static final String BASE_URI = "http://localhost:8080/travelgood/webresources";

    public static Map<String, Itiniery> itins = new HashMap();
    
    @Path("reset")
    @PUT
    public void reset() {
        itins = new HashMap();
    }
    
    @Path("{id}")
    @GET
    @Produces(MEDIATYPE_TRAVELGOOD)
    public ItinieryRepresentation getItin(@PathParam("id") String id) {
        
        Itiniery itin = itins.get(id);
        if (itin == null) {
            Response r = Response.
                    status(Response.Status.NOT_FOUND).
                    entity("Itiniery not found").
                    build();
            throw new NotFoundException(r);
        }
        
        ItinieryRepresentation itinRep = new ItinieryRepresentation();
        itinRep.setItin(itin);
        
        addSelfLink(id,itinRep);
        addStatusLink(id,itinRep);
        addBookLink(id,itinRep);
        addCancelLink(id,itinRep);
        
        return itinRep;
    }
    
    @Path("new")
    @PUT
    @Produces(MEDIATYPE_TRAVELGOOD)
    public StatusRepresentation createItin() {
        int count = itins.size() + 1;
        
        String id = Integer.toString(count);
        
        StatusRepresentation itinRep = new StatusRepresentation();
        itinRep.setStatus(STATUS_RUNNING);
        
        Itiniery itin = new Itiniery();
        itin.setHotels(new ArrayList());
        itin.setFlights(new ArrayList());
        
        itin.setId(id);
        itin.setStatus(STATUS_RUNNING);
        
        itins.put(id, itin);
        
        addSelfLink(id,itinRep);
        addStatusLink(id,itinRep);
        addCancelLink(id,itinRep);
        
        return itinRep;
    }
    
    @Path("{id}/book")
    @PUT
    @Consumes(MEDIATYPE_TRAVELGOOD)
    @Produces(MEDIATYPE_TRAVELGOOD)
    public StatusRepresentation bookItin(@PathParam("id") String id, 
            CreditCardType cc) {
        
        Itiniery itin = itins.get(id);
        if (itin == null) {
            Response r = Response.
                    status(Response.Status.NOT_FOUND).
                    entity("Itiniery not found").
                    build();
            throw new NotFoundException(r);
        } else if (!(itin.getStatus().equals("running") || 
                itin.getStatus().equals("updated"))) {
            Response r = Response.
                    status(Response.Status.FORBIDDEN).
                    entity("Itiniery is not open").
                    build();
            throw new NotAllowedException(r);
        }
        StatusRepresentation itinRep = new StatusRepresentation();
        
        String result = NiceViewResource.bookList(id, cc);
        
        if (result.equals("complete")) {
            result = LameDuckResource.bookList(id, cc);
            if (result.equals("complete")) {
                itinRep.setStatus(STATUS_BOOKED);
                addSelfLink(id,itinRep);
                addStatusLink(id,itinRep);
            
                itin.setStatus(STATUS_BOOKED);
            } else {
                itinRep.setStatus("updated");
                Response r = Response.
                    status(Response.Status.BAD_REQUEST).
                    entity(result).
                    build();
                throw new BadRequestException(r);
            }
        } else {
            itinRep.setStatus("updated");
            Response r = Response.
                    status(Response.Status.BAD_REQUEST).
                    entity(result).
                    build();
            throw new BadRequestException(r);
        }
        
        addSelfLink(id,itinRep);
        addStatusLink(id,itinRep);
        addCancelLink(id,itinRep);
        
        return itinRep;
    }
    
    @Path("{id}/cancel")
    @PUT
    @Consumes(MEDIATYPE_TRAVELGOOD)
    @Produces(MEDIATYPE_TRAVELGOOD)
    public StatusRepresentation cancelItin(@PathParam("id") String id, 
            CreditCardType cc) {
        
        Itiniery itin = itins.get(id);
        if (itin == null) {
            Response r = Response.
                    status(Response.Status.NOT_FOUND).
                    entity("Itiniery not found").
                    build();
            throw new NotFoundException(r);
        }
        if (itin.getStatus().equals("cancelled")) {
            Response r = Response.
                    status(Response.Status.BAD_REQUEST).
                    entity("Itiniery already cancelled").
                    build();
            throw new BadRequestException(r);
        }
        
        StatusRepresentation rep = new StatusRepresentation();
        if (itin.getStatus().equals("running") || itin.getStatus().equals("updated")) {
            itin = new Itiniery();
            itin.setFlights(new ArrayList());
            itin.setHotels(new ArrayList());
            itin.setStatus(STATUS_CANCELLED);
            
            rep.setStatus(STATUS_CANCELLED);
            
            itins.put(id, itin);
            
            itin.setStatus(STATUS_CANCELLED);
        }
        else if(itin.getStatus().equals("booked")) {
            String result = NiceViewResource.cancelList(id, cc);
            
            if (result.equals("complete")) {
                result = LameDuckResource.cancelList(id, cc);
                
                if (result.equals("complete")) {
                    rep.setStatus(STATUS_CANCELLED);
                    addSelfLink(id,rep);
                    addStatusLink(id,rep);
            
                    itin.setStatus(STATUS_CANCELLED);
                } else {
                    rep.setStatus("updated");
                    Response r = Response.
                    status(Response.Status.BAD_REQUEST).
                    entity(result).
                    build();
                    throw new BadRequestException(r);
                }
            } else {
                rep.setStatus("updated");
                Response r = Response.
                status(Response.Status.BAD_REQUEST).
                entity(result).
                build();
                throw new BadRequestException(r);
            }
        }
        
        addSelfLink(id,rep);
        addStatusLink(id,rep);
        
        return rep;
    }
    
    public static void addSelfLink(String id, Representation response) {
        Link link = new Link();
        link.setUri(String.format("%s/%s/%s", BASE_URI, "travelgood",id));
        link.setRel(SELF_RELATION);
        response.getLinks().add(link);
    }
    
    public static void addStatusLink(String id, Representation response) {
        Link link = new Link();
        link.setUri(String.format("%s/itinieries/%s/status", BASE_URI, id));
        link.setRel(STATUS_RELATION);
        response.getLinks().add(link);
    }
    
    public static void addBookLink(String id, Representation response) {
        Link link = new Link();
        link.setUri(String.format("%s/%s/%s/%s", BASE_URI, "travelgood",id,"book"));
        link.setRel(BOOK_RELATION);
        response.getLinks().add(link);
    }
    
    public static void addCancelLink(String id, Representation response) {
        Link link = new Link();
        link.setUri(String.format("%s/%s/%s/%s", BASE_URI, "travelgood",id,"cancel"));
        link.setRel(CANCEL_RELATION);
        response.getLinks().add(link);
    }
}
