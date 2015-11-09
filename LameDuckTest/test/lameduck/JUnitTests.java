/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lameduck;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import junit.framework.Assert;
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
        XMLGregorianCalendar date;
        date = df.newXMLGregorianCalendar("2015-11-10T00:00:00");
        request.setDate(date);
        
        ListOfFlights list = getFlights(request);
    }
    
    @Test
    public void bookFlightTest(){
        RequestbookFlight request = new RequestbookFlight();
        request.setBookingNumber(1);
        CreditCardInfoType info = new CreditCardInfoType();
        info.setName("Bech Camilla");
        info.setNumber("50408822");
        CreditCardInfoType.ExpirationDate exDate = new CreditCardInfoType.ExpirationDate();
        exDate.setMonth(7);
        exDate.setYear(9);
        info.setExpirationDate(exDate);
        request.setCreditCardInfo(info);
        
        boolean response = false;
        try{
            response = bookFlight(request);
        }
        catch(BookFlightFault_Exception ex){
            Assert.fail(ex.getMessage());
        }
        assertEquals(true, response);
    }
    
    @Test
    public void cancelFlightTest(){
        RequestcancelFlight request = new RequestcancelFlight();
        request.setBookingNumber(1);
        request.setPrice(500);
        CreditCardInfoType info = new CreditCardInfoType();
        info.setName("Bech Camilla");
        info.setNumber("50408822");
        CreditCardInfoType.ExpirationDate exDate = new CreditCardInfoType.ExpirationDate();
        exDate.setMonth(7);
        exDate.setYear(9);
        info.setExpirationDate(exDate);
        request.setCreditCardInfo(info);
        
        boolean response = false;
        try{
            response = cancelFlight(request);
        }
        catch(CancelFlightFault_Exception ex){
            Assert.fail(ex.getMessage());
        }
        assertEquals(true,response);
    }

    private static ListOfFlights getFlights(lameduck.Request input) {
        lameduck.LameduckService service = new lameduck.LameduckService();
        lameduck.LameduckPortType port = service.getLameduckPortTypeBindingPort();
        return port.getFlights(input);
    }

    private static boolean bookFlight(lameduck.RequestbookFlight input) throws BookFlightFault_Exception {
        lameduck.LameduckService service = new lameduck.LameduckService();
        lameduck.LameduckPortType port = service.getLameduckPortTypeBindingPort();
        return port.bookFlight(input);
    }

    private static boolean cancelFlight(lameduck.RequestcancelFlight input) throws CancelFlightFault_Exception {
        lameduck.LameduckService service = new lameduck.LameduckService();
        lameduck.LameduckPortType port = service.getLameduckPortTypeBindingPort();
        return port.cancelFlight(input);
    }
    
    
    
    
}
