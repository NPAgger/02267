/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.representation;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mathias
 */
@XmlRootElement()
public class StatusRepresentation extends Representation {

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
