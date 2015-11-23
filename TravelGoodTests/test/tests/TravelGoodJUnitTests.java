/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import dk.dtu.imm.fastmoney.types.CreditCardInfoType;
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
         create.setCustomerName("Thomas");
         create.setItineraryId(4);
         
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
         CreditCardInfoType info = new CreditCardInfoType();
        info.setName("Bech Camilla");
        info.setNumber("50408822");
        CreditCardInfoType.ExpirationDate exDate = new CreditCardInfoType.ExpirationDate();
        exDate.setMonth(7);
        exDate.setYear(9);
        info.setExpirationDate(exDate);
        book.setCreditCardInfo(info);
         
         
         assertTrue(createItinerary(create));
         ListOfFlights list = getFlights(getFlights);
         
         AddFlightRequestType addFlight1 = new AddFlightRequestType();
         addFlight1.setCustomerName(create.getCustomerName());
         addFlight1.setItineraryId(create.getItineraryId());
         addFlight1.setBookingNumber(list.getFlightInfo().get(0).getBookingNumber());         
         //addFlight1.setBookingNumber(20);
         addFlight1.setPrice(list.getFlightInfo().get(0).getPrice());
         
         AddFlightRequestType addFlight2 = new AddFlightRequestType();
         addFlight2.setCustomerName(create.getCustomerName());
         addFlight2.setItineraryId(create.getItineraryId());
         addFlight2.setBookingNumber(list.getFlightInfo().get(1).getBookingNumber());
         //addFlight2.setBookingNumber(20);
         addFlight2.setPrice(list.getFlightInfo().get(1).getPrice());
         
         AddFlightRequestType addFlight3 = new AddFlightRequestType();
         addFlight3.setCustomerName(create.getCustomerName());
         addFlight3.setItineraryId(create.getItineraryId());
         //addFlight3.setBookingNumber(list.getFlightInfo().get(2).getBookingNumber());
         addFlight3.setBookingNumber(20);
         addFlight3.setPrice(list.getFlightInfo().get(2).getPrice());
         
         
         DefaultRequestType getIt = new DefaultRequestType();
         getIt.setCustomerName(create.getCustomerName());
         getIt.setItineraryId(create.getItineraryId());
         
         boolean status1 = addFlight(addFlight1);
         boolean status2 = addFlight(addFlight2);
         boolean status3 = addFlight(addFlight3);
         
         FlightBookingArrayType fbat = getItinerary(getIt);
         
        try {
            assertTrue(bookItinerary(book));
        } catch (BookItineraryFault ex) {
            Logger.getLogger(TravelGoodJUnitTests.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
         
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


    private static FlightBookingArrayType getItinerary(ws.travelgood.DefaultRequestType request) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodBindingPort();
        return port.getItinerary(request);
    }

    private static boolean addFlight(ws.travelgood.AddFlightRequestType request) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodBindingPort();
        return port.addFlight(request);
    }

    private static boolean bookItinerary(ws.travelgood.BookItineraryRequestType bookItineraryRequest) throws BookItineraryFault {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodBindingPort();
        return port.bookItinerary(bookItineraryRequest);
    }
    
}
