/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lameduck;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Andri
 */
public class JUnitTests {
    
    public JUnitTests() {
    }
    
    

    
    @Test
    public void getFlightsTest() throws DatatypeConfigurationException {
        Request request = new Request();
        request.setOrigin("Iceland");
        request.setDestination("China");
        DatatypeFactory df = DatatypeFactory.newInstance();
        XMLGregorianCalendar date = df.newXMLGregorianCalendar("2015-11-10");
        request.setDate(date);
        
        ListOfFlights list = getFlights(request);
        
        
        
        
    }

    private static ListOfFlights getFlights(lameduck.Request input) {
        lameduck.LameduckService service = new lameduck.LameduckService();
        lameduck.LameduckPortType port = service.getLameduckPortTypeBindingPort();
        return port.getFlights(input);
    }
    
    
    
}
