/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.representations;

import java.util.List;
import javax.ws.rs.core.Link;

/**
 *
 * @author Nis
 */
public abstract class Representation {
    
    private List<Link> links;
    private int id;
    
    public Representation(int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    public List<Link> getLinks() {
        return this.links;
    }
}