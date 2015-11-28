/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.representations;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Link;

/**
 *
 * @author Nis
 */
public class StatusRepresentation extends Representation{
    
    private String status;
    private List<Link> links;
    
    public StatusRepresentation(int id, String status) {
        super(id);
        this.status = status;
        links = new ArrayList();
        
        String base = 
                "http://localhost:8080/TravelGoodREST/webresources/TravelGood/"
                + this.getId();
        
        this.links.add(Link.fromUri(base + "/get/order").build());
        this.links.add(Link.fromUri(base + "/get/status").build());
        this.links.add(Link.fromUri(base + "/cancel").build());
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}
