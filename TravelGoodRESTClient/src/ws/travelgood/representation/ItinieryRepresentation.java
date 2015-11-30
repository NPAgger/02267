/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.representation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import ws.travelgood.data.Itiniery;

/**
 *
 * @author Nis
 */
@XmlRootElement()
public class ItinieryRepresentation extends Representation {

    private Itiniery itin;

    public Itiniery getItin() {
        return itin;
    }
    
    public void setItin(Itiniery itin) {
        this.itin = itin;
    }
}
