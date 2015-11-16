/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import lameducktypes.*;
import ws.travelgood.*;
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
public class TravelGoodJUnitTests {
    
    public TravelGoodJUnitTests() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void P1() throws DatatypeConfigurationException {
         CreateItineraryRequestType create = new CreateItineraryRequestType();
         create.setCustomerName("Andri");
         create.setItineraryId(1);
         
         GetFlightsRequestType getFlights = new GetFlightsRequestType();
         getFlights.setCustomerName(create.getCustomerName());
         getFlights.setItineraryId(create.getItineraryId());
         
         Request request = new Request();
         request.setOrigin("Iceland");
         request.setDestination("China");
         DatatypeFactory df = DatatypeFactory.newInstance();
         XMLGregorianCalendar date;
         date = df.newXMLGregorianCalendar("2015-11-10T00:00:00");
         request.setDate(date);
         
         getFlights.setRequest(request);
         
         BookItineraryRequestType book = new BookItineraryRequestType();
         book.setCustomerName(create.getCustomerName());
         book.setItineraryId(create.getItineraryId());
         
         assertTrue(createItinerary(create));
         ListOfFlights list = getFlights(getFlights);
        try {
            assertTrue(bookItinerary(book));
        } catch (BookItineraryFault ex) {
            Logger.getLogger(TravelGoodJUnitTests.class.getName()).log(Level.SEVERE, null, ex);
        }
         
     }

    private static boolean bookItinerary(ws.travelgood.BookItineraryRequestType bookItineraryRequest) throws BookItineraryFault {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodBindingPort();
        return port.bookItinerary(bookItineraryRequest);
    }

    private static boolean createItinerary(ws.travelgood.CreateItineraryRequestType createItineraryRequest) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodBindingPort();
        return port.createItinerary(createItineraryRequest);
    }

    private static ListOfFlights getFlights(ws.travelgood.GetFlightsRequestType getFlightsRequest) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodBindingPort();
        return port.getFlights(getFlightsRequest);
    }
}
