/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import dk.dtu.imm.fastmoney.types.CreditCardInfoType;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import lameducktypes.*;
import niceviewtypes.*;
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
     public void testP1() throws DatatypeConfigurationException {
         //CreateItineraryRequestType create = new CreateItineraryRequestType();
         //create.setCustomerName("Thomas");
         //create.setItineraryId(4);
         
         String itineraryId = createItinerary(new CreateItineraryRequestType());
         
         GetFlightsRequestType getFlights = new GetFlightsRequestType();
         getFlights.setItineraryId(itineraryId);
         
         Request request = new Request();
         request.setOrigin("Iceland");
         request.setDestination("China");
         DatatypeFactory df = DatatypeFactory.newInstance();
         XMLGregorianCalendar date;
         date = df.newXMLGregorianCalendar("2015-11-10T00:00:00");
         request.setDate(date);
         
         getFlights.setRequest(request);
         
         BookItineraryRequestType book = new BookItineraryRequestType();
         book.setItineraryId(itineraryId);
         CreditCardInfoType info = new CreditCardInfoType();
        info.setName("Klinkby Poul");
        info.setNumber("50408817");
        CreditCardInfoType.ExpirationDate exDate = new CreditCardInfoType.ExpirationDate();
        exDate.setMonth(3);
        exDate.setYear(10);
        info.setExpirationDate(exDate);
        book.setCreditCardInfo(info);
         
         //assertTrue(createItinerary(create));
         ListOfFlights list = getFlights(getFlights);
         
         AddFlightRequestType addFlight1 = new AddFlightRequestType();
         addFlight1.setItineraryId(itineraryId);
         addFlight1.setBookingNumber(list.getFlightInfo().get(0).getBookingNumber());         
         //addFlight1.setBookingNumber(20);
         addFlight1.setPrice(list.getFlightInfo().get(0).getPrice());
         
         AddFlightRequestType addFlight2 = new AddFlightRequestType();
         addFlight2.setItineraryId(itineraryId);
         addFlight2.setBookingNumber(list.getFlightInfo().get(1).getBookingNumber());
         //addFlight2.setBookingNumber(20);
         addFlight2.setPrice(list.getFlightInfo().get(1).getPrice());
         
         AddFlightRequestType addFlight3 = new AddFlightRequestType();
         addFlight3.setItineraryId(itineraryId);
         addFlight3.setBookingNumber(list.getFlightInfo().get(2).getBookingNumber());
         //addFlight3.setBookingNumber(20);
         addFlight3.setPrice(list.getFlightInfo().get(2).getPrice());
         
         
         boolean status1 = addFlight(addFlight1);
         boolean status2 = addFlight(addFlight2);
         boolean status3 = addFlight(addFlight3);
         
         //DefaultRequestType cancelIt = new DefaultRequestType();
         //cancelIt.setItineraryId(itineraryId);
         //cancelItinerary(cancelIt);
         
         
         
         GetHotelsRequestType getHotels1 = new GetHotelsRequestType();
         GetRequest getHotelRequest1 = new GetRequest();
         getHotelRequest1.setCity("Copenhagen");
         XMLGregorianCalendar from1;
         from1 = df.newXMLGregorianCalendar("2015-11-10T00:00:00");
         XMLGregorianCalendar to1;
         to1 = df.newXMLGregorianCalendar("2015-11-12T00:00:00");
         getHotelRequest1.setArr(from1);
         getHotelRequest1.setDep(to1);
         getHotels1.setGetRequest(getHotelRequest1);
         getHotels1.setItineraryId(itineraryId);
         
         GetResponse hotels1 = getHotels(getHotels1);
         
         GetHotelsRequestType getHotels2 = new GetHotelsRequestType();
         GetRequest getHotelRequest2 = new GetRequest();
         getHotelRequest2.setCity("Beijing");
         XMLGregorianCalendar from2;
         from2 = df.newXMLGregorianCalendar("2015-11-13T00:00:00");
         XMLGregorianCalendar to2;
         to2 = df.newXMLGregorianCalendar("2015-11-14T00:00:00");
         getHotelRequest2.setArr(from2);
         getHotelRequest2.setDep(to2);
         getHotels2.setGetRequest(getHotelRequest2);
         getHotels2.setItineraryId(itineraryId);
         
         
         AddHotelRequestType addHotel1 = new AddHotelRequestType();
         addHotel1.setItineraryId(itineraryId);
         addHotel1.setBookingNumber(hotels1.getHotelList().get(0).getBookNum());
        
         boolean status4 = addHotel(addHotel1);
                 
         GetResponse hotels2 = getHotels(getHotels2);
         
         AddHotelRequestType addHotel2 = new AddHotelRequestType();
         addHotel2.setItineraryId(itineraryId);
         addHotel2.setBookingNumber(hotels2.getHotelList().get(0).getBookNum());
         //addHotel2.setBookingNumber(1337);

         boolean status5 = addHotel(addHotel2);
         
         DefaultRequestType getIt = new DefaultRequestType();
         getIt.setItineraryId(itineraryId);
         GetItineraryResponseType theIt = getItinerary(getIt);
         
        try {
            assertTrue(bookItinerary(book));
        } catch (BookItineraryFault ex) {
            Logger.getLogger(TravelGoodJUnitTests.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
         
     }

     @Test
     public void testC1() throws DatatypeConfigurationException{
         
         String itineraryId = createItinerary(new CreateItineraryRequestType());
         
         GetFlightsRequestType getFlights = new GetFlightsRequestType();
         getFlights.setItineraryId(itineraryId);
        Request request = new Request();
         request.setOrigin("Iceland");
         request.setDestination("China");
         DatatypeFactory df = DatatypeFactory.newInstance();
         XMLGregorianCalendar date;
         date = df.newXMLGregorianCalendar("2015-11-10T00:00:00");
         request.setDate(date);
         
         getFlights.setRequest(request);
         

         
         //assertTrue(createItinerary(create));
         ListOfFlights list = getFlights(getFlights);
         XMLGregorianCalendar ff1;
         ff1 = df.newXMLGregorianCalendar("2015-11-29T00:00:00");
         AddFlightRequestType addFlight1 = new AddFlightRequestType();
         addFlight1.setItineraryId(itineraryId);
         addFlight1.setBookingNumber(list.getFlightInfo().get(0).getBookingNumber());         
         //addFlight1.setBookingNumber(20);
         addFlight1.setPrice(list.getFlightInfo().get(0).getPrice());
         //addFlight1.setDate(list.getFlightInfo().get(0).getFlight().getDeparture());
         addFlight1.setDate(ff1);
         
         XMLGregorianCalendar ff2;
         ff2 = df.newXMLGregorianCalendar("2015-11-29T00:00:00");
         AddFlightRequestType addFlight2 = new AddFlightRequestType();
         addFlight2.setItineraryId(itineraryId);
         addFlight2.setBookingNumber(list.getFlightInfo().get(1).getBookingNumber());
         //addFlight2.setBookingNumber(20);
         addFlight2.setPrice(list.getFlightInfo().get(1).getPrice());
         //addFlight2.setDate(list.getFlightInfo().get(1).getFlight().getDeparture());
          addFlight2.setDate(ff2);
         
                  
         boolean status1 = addFlight(addFlight1);
         boolean status2 = addFlight(addFlight2);
         
         GetHotelsRequestType getHotels1 = new GetHotelsRequestType();
         GetRequest getHotelRequest1 = new GetRequest();
         getHotelRequest1.setCity("Copenhagen");
         XMLGregorianCalendar from1;
         from1 = df.newXMLGregorianCalendar("2015-11-29T00:00:00");
         XMLGregorianCalendar to1;
         to1 = df.newXMLGregorianCalendar("2015-11-30T00:00:00");
         getHotelRequest1.setArr(from1);
         getHotelRequest1.setDep(to1);
         getHotels1.setGetRequest(getHotelRequest1);
         getHotels1.setItineraryId(itineraryId);
         
         GetResponse hotels1 = getHotels(getHotels1);
         
         AddHotelRequestType addHotel1 = new AddHotelRequestType();
         addHotel1.setItineraryId(itineraryId);
         addHotel1.setBookingNumber(hotels1.getHotelList().get(0).getBookNum());
         addHotel1.setDate(from1);
        
         boolean status4 = addHotel(addHotel1);
         
          BookItineraryRequestType book = new BookItineraryRequestType();
         book.setItineraryId(itineraryId);
         CreditCardInfoType info = new CreditCardInfoType();
        info.setName("Klinkby Poul");
        info.setNumber("50408817");
        CreditCardInfoType.ExpirationDate exDate = new CreditCardInfoType.ExpirationDate();
        exDate.setMonth(3);
        exDate.setYear(10);
        info.setExpirationDate(exDate);
        book.setCreditCardInfo(info);
        
        DefaultRequestType getIt = new DefaultRequestType();
         getIt.setItineraryId(itineraryId);
         GetItineraryResponseType theIt;
         theIt = getItinerary(getIt);
         
        try {
            assertTrue(bookItinerary(book));
        } catch (BookItineraryFault ex) {
            Logger.getLogger(TravelGoodJUnitTests.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        
        GetBookedItineraryRequestType getBooked = new GetBookedItineraryRequestType();
        getBooked.setItineraryId(itineraryId);
        GetBookedItineraryResponseType bookedIt;
         bookedIt = getBookedItinerary(getBooked);
         
         CancelBookedItineraryRequestType cancelIt = new CancelBookedItineraryRequestType();
         cancelIt.setItineraryId(itineraryId);
         cancelIt.setCreditCardInfo(info);
         cancelBookedItinerary(cancelIt);
         
         GetCancelledItineraryRequestType getCancelled = new GetCancelledItineraryRequestType();
         getCancelled.setItineraryId(itineraryId);
        GetCancelledItineraryResponseType cancelledIt;
         cancelledIt = getCancelledItinerary(getCancelled);
        
     }
     
     @Test
     public void testE1() throws DatatypeConfigurationException, InterruptedException{
        String itineraryId = createItinerary(new CreateItineraryRequestType());
         
         GetFlightsRequestType getFlights = new GetFlightsRequestType();
         getFlights.setItineraryId(itineraryId);
        Request request = new Request();
         request.setOrigin("Iceland");
         request.setDestination("China");
         DatatypeFactory df = DatatypeFactory.newInstance();
         XMLGregorianCalendar date;
         date = df.newXMLGregorianCalendar("2015-11-10T00:00:00");
         request.setDate(date);
         
         getFlights.setRequest(request);
         

         
         //assertTrue(createItinerary(create));
         ListOfFlights list = getFlights(getFlights);
         XMLGregorianCalendar ff1;
         ff1 = df.newXMLGregorianCalendar("2015-11-29T00:00:00");
         AddFlightRequestType addFlight1 = new AddFlightRequestType();
         addFlight1.setItineraryId(itineraryId);
         addFlight1.setBookingNumber(list.getFlightInfo().get(0).getBookingNumber());         
         //addFlight1.setBookingNumber(20);
         addFlight1.setPrice(list.getFlightInfo().get(0).getPrice());
         //addFlight1.setDate(list.getFlightInfo().get(0).getFlight().getDeparture());
         addFlight1.setDate(ff1);
         
         XMLGregorianCalendar ff2;
         ff2 = df.newXMLGregorianCalendar("2015-11-26T13:45:30+01:00");
         AddFlightRequestType addFlight2 = new AddFlightRequestType();
         addFlight2.setItineraryId(itineraryId);
         addFlight2.setBookingNumber(list.getFlightInfo().get(1).getBookingNumber());
         //addFlight2.setBookingNumber(20);
         addFlight2.setPrice(list.getFlightInfo().get(1).getPrice());
         //addFlight2.setDate(list.getFlightInfo().get(1).getFlight().getDeparture());
          addFlight2.setDate(ff2);
         
                  
         boolean status1 = addFlight(addFlight1);
         boolean status2 = addFlight(addFlight2);
         
         GetHotelsRequestType getHotels1 = new GetHotelsRequestType();
         GetRequest getHotelRequest1 = new GetRequest();
         getHotelRequest1.setCity("Copenhagen");
         XMLGregorianCalendar from1;
         from1 = df.newXMLGregorianCalendar("2015-11-29T00:00:00");
         XMLGregorianCalendar to1;
         to1 = df.newXMLGregorianCalendar("2015-11-30T00:00:00");
         getHotelRequest1.setArr(from1);
         getHotelRequest1.setDep(to1);
         getHotels1.setGetRequest(getHotelRequest1);
         getHotels1.setItineraryId(itineraryId);
         
         GetResponse hotels1 = getHotels(getHotels1);
         
         AddHotelRequestType addHotel1 = new AddHotelRequestType();
         addHotel1.setItineraryId(itineraryId);
         addHotel1.setBookingNumber(hotels1.getHotelList().get(0).getBookNum());
         addHotel1.setDate(from1);
        
         boolean status4 = addHotel(addHotel1);
         
          BookItineraryRequestType book = new BookItineraryRequestType();
         book.setItineraryId(itineraryId);
         CreditCardInfoType info = new CreditCardInfoType();
        info.setName("Klinkby Poul");
        info.setNumber("50408817");
        CreditCardInfoType.ExpirationDate exDate = new CreditCardInfoType.ExpirationDate();
        exDate.setMonth(3);
        exDate.setYear(10);
        info.setExpirationDate(exDate);
        book.setCreditCardInfo(info);
        
        DefaultRequestType getIt = new DefaultRequestType();
         getIt.setItineraryId(itineraryId);
         GetItineraryResponseType theIt;
         theIt = getItinerary(getIt);
         
        try {
            assertTrue(bookItinerary(book));
        } catch (BookItineraryFault ex) {
            Logger.getLogger(TravelGoodJUnitTests.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        
        TimeUnit.MINUTES.sleep(2);
        
        GetBookedItineraryRequestType getBooked = new GetBookedItineraryRequestType();
        getBooked.setItineraryId(itineraryId);
        GetBookedItineraryResponseType bookedIt;
         bookedIt = getBookedItinerary(getBooked);
         
         CancelBookedItineraryRequestType cancelIt = new CancelBookedItineraryRequestType();
         cancelIt.setItineraryId(itineraryId);
         cancelIt.setCreditCardInfo(info);
         cancelBookedItinerary(cancelIt);
         
         GetCancelledItineraryRequestType getCancelled = new GetCancelledItineraryRequestType();
         getCancelled.setItineraryId(itineraryId);
        GetCancelledItineraryResponseType cancelledIt;
         cancelledIt = getCancelledItinerary(getCancelled);
     }
     
    private static ListOfFlights getFlights(ws.travelgood.GetFlightsRequestType getFlightsRequest) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodBindingPort();
        return port.getFlights(getFlightsRequest);
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

    private static String createItinerary(ws.travelgood.CreateItineraryRequestType createItineraryRequest) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodBindingPort();
        return port.createItinerary(createItineraryRequest);
    }

    private static GetResponse getHotels(ws.travelgood.GetHotelsRequestType getHotelsRequest) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodBindingPort();
        return port.getHotels(getHotelsRequest);
    }

    private static boolean addHotel(ws.travelgood.AddHotelRequestType addHotelRequest) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodBindingPort();
        return port.addHotel(addHotelRequest);
    }

    private static String cancelItinerary(ws.travelgood.DefaultRequestType cancelItineraryRequest) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodBindingPort();
        return port.cancelItinerary(cancelItineraryRequest);
    }

    private static GetItineraryResponseType getItinerary(ws.travelgood.DefaultRequestType getItineraryRequest) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodBindingPort();
        return port.getItinerary(getItineraryRequest);
    }

    private static String cancelBookedItinerary(ws.travelgood.CancelBookedItineraryRequestType cancelBookedItineraryRequest) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodBindingPort();
        return port.cancelBookedItinerary(cancelBookedItineraryRequest);
    }

    private static GetBookedItineraryResponseType getBookedItinerary(ws.travelgood.GetBookedItineraryRequestType getBookedItineraryRequest) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodBindingPort();
        return port.getBookedItinerary(getBookedItineraryRequest);
    }

    private static GetCancelledItineraryResponseType getCancelledItinerary(ws.travelgood.GetCancelledItineraryRequestType getCancelledItineraryRequest) {
        ws.travelgood.TravelGoodService service = new ws.travelgood.TravelGoodService();
        ws.travelgood.TravelGoodPortType port = service.getTravelGoodBindingPort();
        return port.getCancelledItinerary(getCancelledItineraryRequest);
    } 


    
}
