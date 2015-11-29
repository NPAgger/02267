/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.resource;

import javax.persistence.criteria.Order;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import ws.travelgood.representation.StatusRepresentation;
import ws.travelgood.representation.ItinieryRepresentation;
import ws.travelgood.data.Itiniery;
/**
 *
 * @author Nis
 */
@Path("itinieries/{id}/status")
public class ItinieryStatusResource {
    
    @GET
    @Produces(ItinieryResource.MEDIATYPE_TRAVELGOOD)
    public StatusRepresentation getStatus(@PathParam("id") String id) {
        
        StatusRepresentation status = new StatusRepresentation();
        Itiniery itin = ItinieryResource.itins.get(id);
        if (itin == null) {
            Response r = Response.
                    status(Response.Status.NOT_FOUND).
                    entity("Itiniery not found").
                    build();
            throw new NotFoundException(r);
        }
        status.setStatus(itin.getStatus());
        
        ItinieryResource.addSelfLink(id, status);

        return status;
    }
    
    @PUT
    @Consumes("text/plain")
    @Produces(ItinieryResource.MEDIATYPE_TRAVELGOOD)
    public StatusRepresentation cancel(@PathParam("id") String id,
            String newStatus) {

        StatusRepresentation status = new StatusRepresentation();

        return status;
    }
}
