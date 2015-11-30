/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import org.junit.Test;

import ws.travelgood.data.*;
import ws.travelgood.representation.*;

import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Nis
 */
public class RESTTest {
    
    public static final String MEDIATYPE_TRAVELGOOD = "application/travelgood+xml";
    Client client;
    String baseURI;
    WebTarget target;

    public RESTTest() {
        client = ClientBuilder.newClient();
        baseURI = "http://localhost:8080/travelgood/webresources";
        target = client.target(baseURI);
    }
    
    @Before()
    public void reset() {
        target.path("travelgood").path("reset")
                .request()
                .put(Entity.entity("", MediaType.TEXT_PLAIN));
        
        target.path("niceview").path("reset")
                .request()
                .put(Entity.entity("", MediaType.TEXT_PLAIN));
        
        target.path("lameduck").path("reset")
                .request()
                .put(Entity.entity("", MediaType.TEXT_PLAIN));
    }
    
    @Test
    public void newItiniery() {
        StatusRepresentation out = target.path("travelgood").path("new")
                .request().accept(MEDIATYPE_TRAVELGOOD)
                .put(Entity.entity("", MediaType.TEXT_PLAIN),
                        StatusRepresentation.class);
        
        assertEquals("running",out.getStatus());
    }
    
    @Test
    public void getItiniery() {
        StatusRepresentation out = target.path("travelgood").path("new")
                .request().accept(MEDIATYPE_TRAVELGOOD)
                .put(Entity.entity("", MediaType.TEXT_PLAIN),
                        StatusRepresentation.class);
        
        assertEquals("running",out.getStatus());
        
        ItinieryRepresentation itinRep = target.path("travelgood").path("1")
                .request().accept(MEDIATYPE_TRAVELGOOD)
                .get(ItinieryRepresentation.class);
        
        assertEquals(itinRep.getItin().getId(),"1");
        
        Link link = out.getLinkByRelation("http://travelgood.ws/relations/self");
        assertNotNull(link);
        assertEquals(link.getUri(),
                "http://localhost:8080/travelgood/webresources/travelgood/1");
        
        ItinieryRepresentation linkRep = client.target(link.getUri())
                .request().accept(MEDIATYPE_TRAVELGOOD)
                .get(ItinieryRepresentation.class);
        
        assertEquals(linkRep.getItin().getId(),itinRep.getItin().getId());
    }
    
    @Test
    public void getStatus() {
        StatusRepresentation out = target.path("travelgood").path("new")
                .request().accept(MEDIATYPE_TRAVELGOOD)
                .put(Entity.entity("", MediaType.TEXT_PLAIN),
                        StatusRepresentation.class);
        
        assertEquals("running",out.getStatus());
        
        StatusRepresentation status = target.path("itinieries").path("1")
                .path("status").request().accept(MEDIATYPE_TRAVELGOOD)
                .get(StatusRepresentation.class);
        
        assertEquals(out.getStatus(),status.getStatus());
    }
    
    @Test
    public void getHotels() {
        HotelInfoList response = target.path("niceview").path("hotels")
                .queryParam("city", "Copenhagen").queryParam("arr", "2015-01-01")
                .queryParam("dep", "2015-01-02").request()
                .accept(MEDIATYPE_TRAVELGOOD)
                .get(HotelInfoList.class);
        
        assertEquals(response.getList().size(),1);
        assertEquals(response.getList().get(0).getBookNum(),1);
    }
    
    @Test
    public void getFlights() {
        FlightInfoList response = target.path("lameduck").path("flights")
                .queryParam("orig", "Iceland").queryParam("dest", "China")
                .queryParam("date", "2015-11-10").request()
                .accept(MEDIATYPE_TRAVELGOOD)
                .get(FlightInfoList.class);
        
        assertEquals(response.getList().size(),11);
    }
    
    @Test
    public void addHotelAndFlight() {
        StatusRepresentation out = target.path("travelgood").path("new")
                .request().accept(MEDIATYPE_TRAVELGOOD)
                .put(Entity.entity("", MediaType.TEXT_PLAIN),
                        StatusRepresentation.class);
        
        assertEquals("running",out.getStatus());
        
        HotelInfoList hotelResponse = target.path("niceview").path("hotels")
                .queryParam("city", "Copenhagen").queryParam("arr", "2015-01-01")
                .queryParam("dep", "2015-01-02").request()
                .accept(MEDIATYPE_TRAVELGOOD)
                .get(HotelInfoList.class);
        
        assertEquals(hotelResponse.getList().size(),1);
        assertEquals(hotelResponse.getList().get(0).getBookNum(),1);
        
        FlightInfoList flightResponse = target.path("lameduck").path("flights")
                .queryParam("orig", "Iceland").queryParam("dest", "China")
                .queryParam("date", "2015-11-10").request()
                .accept(MEDIATYPE_TRAVELGOOD)
                .get(FlightInfoList.class);
        
        assertEquals(flightResponse.getList().size(),11);
        
        HotelInfoType info = target.path("niceview").path("check").request()
                .accept(MEDIATYPE_TRAVELGOOD).get(HotelInfoType.class);
        assertEquals(info.getBookNum(),1);
        
        
        StatusRepresentation addHotel = target.path("niceview").path("addhotel")
                .path("1").path("1").request().accept(MEDIATYPE_TRAVELGOOD)
                .put(Entity.entity("", MediaType.TEXT_PLAIN),
                        StatusRepresentation.class);
        
        assertEquals(addHotel.getStatus(),"updated");
        
        StatusRepresentation addFlight = target.path("lameduck")
                .path("addflight").path("1").path("1").request().accept(MEDIATYPE_TRAVELGOOD)
                .put(Entity.entity("", MediaType.TEXT_PLAIN),
                        StatusRepresentation.class);
        
        addFlight = target.path("lameduck")
                .path("addflight").path("1").path("2").request().accept(MEDIATYPE_TRAVELGOOD)
                .put(Entity.entity("", MediaType.TEXT_PLAIN),
                        StatusRepresentation.class);
        
        assertEquals(addFlight.getStatus(),"updated");
        
        ItinieryRepresentation itinRep = target.path("travelgood").path("1")
                .request().accept(MEDIATYPE_TRAVELGOOD)
                .get(ItinieryRepresentation.class);
        
        assertEquals(itinRep.getItin().getId(),"1");
        assertEquals(itinRep.getItin().getHotels().size(),1);
        assertEquals(itinRep.getItin().getFlights().size(),2);
    }
    
    @Test
    public void bookHotels() {
        StatusRepresentation out = target.path("travelgood").path("new")
                .request().accept(MEDIATYPE_TRAVELGOOD)
                .put(Entity.entity("", MediaType.TEXT_PLAIN),
                        StatusRepresentation.class);
        
        assertEquals("running",out.getStatus());
        
        HotelInfoList response = target.path("niceview").path("hotels")
                .queryParam("city", "Beijing").queryParam("arr", "2015-01-01")
                .queryParam("dep", "2015-01-02").request()
                .accept(MEDIATYPE_TRAVELGOOD)
                .get(HotelInfoList.class);
        
        assertEquals(response.getList().size(),2);
        
        CreditCardType cc = new CreditCardType();
        cc.setName("Tick Joachim");
        cc.setNumber("50408824");
        
        ExpirationDateType ed = new ExpirationDateType();
        ed.setMonth(2);
        ed.setYear(11);
        cc.setExpDat(ed);
        
        out = target.path("travelgood").path("1").path("book")
                .request().accept(MEDIATYPE_TRAVELGOOD)
                .put(Entity.entity(cc, MEDIATYPE_TRAVELGOOD),
                        StatusRepresentation.class);
        
        assertEquals(out.getStatus(),"booked");
    }
}