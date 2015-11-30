/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
        
        Link link = out.getLinkByRelation("http://travelgood.ws/relations/self");
        assertNotNull(link);
        assertEquals(link.getUri(),
                "http://localhost:8080/travelgood/webresources/travelgood/1");
        
        ItinieryRepresentation itinRep = client.target(link.getUri()).request()
                .accept(MEDIATYPE_TRAVELGOOD)
                .get(ItinieryRepresentation.class);
        
        assertEquals(itinRep.getItin().getId(),"1");
    }
    
    @Test
    public void testP1() {
        StatusRepresentation out = target.path("travelgood").path("new")
                .request().accept(MEDIATYPE_TRAVELGOOD)
                .put(Entity.entity("", MediaType.TEXT_PLAIN),
                        StatusRepresentation.class);
        
        assertEquals(out.getStatus(),"running");
        
        FlightInfoList flightResponse = target.path("lameduck").path("flights")
                .queryParam("orig", "Reykjavik").queryParam("dest", "Copenhagen")
                .queryParam("date", "2015-12-19").request()
                .accept(MEDIATYPE_TRAVELGOOD)
                .get(FlightInfoList.class);
        
        assertEquals(flightResponse.getList().size(),1);        
                
        StatusRepresentation addFlight = target.path("lameduck")
                .path("addflight").path("1").path("7").request().accept(MEDIATYPE_TRAVELGOOD)
                .put(Entity.entity("", MediaType.TEXT_PLAIN),
                        StatusRepresentation.class);
        
        assertEquals(addFlight.getStatus(),"updated");
        
        HotelInfoList hotelResponse = target.path("niceview").path("hotels")
                .queryParam("city", "Copenhagen").queryParam("arr", "2015-12-19")
                .queryParam("dep", "2015-12-20").request()
                .accept(MEDIATYPE_TRAVELGOOD)
                .get(HotelInfoList.class);
        
        StatusRepresentation addHotel = target.path("niceview").path("addhotel")
                .path("1").path("1").request().accept(MEDIATYPE_TRAVELGOOD)
                .put(Entity.entity("", MediaType.TEXT_PLAIN),
                        StatusRepresentation.class);
        
        flightResponse = target.path("lameduck").path("flights")
                .queryParam("orig", "Copenhagen").queryParam("dest", "Moscow")
                .queryParam("date", "2015-12-23").request()
                .accept(MEDIATYPE_TRAVELGOOD)
                .get(FlightInfoList.class);
                
        addFlight = target.path("lameduck")
                .path("addflight").path("1").path("2").request().accept(MEDIATYPE_TRAVELGOOD)
                .put(Entity.entity("", MediaType.TEXT_PLAIN),
                        StatusRepresentation.class);
        
        flightResponse = target.path("lameduck").path("flights")
                .queryParam("orig", "Moscow").queryParam("dest", "Beijing")
                .queryParam("date", "2015-12-23").request()
                .accept(MEDIATYPE_TRAVELGOOD)
                .get(FlightInfoList.class);
                
        addFlight = target.path("lameduck")
                .path("addflight").path("1").path("9").request().accept(MEDIATYPE_TRAVELGOOD)
                .put(Entity.entity("", MediaType.TEXT_PLAIN),
                        StatusRepresentation.class);
        
        hotelResponse = target.path("niceview").path("hotels")
                .queryParam("city", "Beijing").queryParam("arr", "2015-12-23")
                .queryParam("dep", "2015-12-24").request()
                .accept(MEDIATYPE_TRAVELGOOD)
                .get(HotelInfoList.class);
        
        addHotel = target.path("niceview").path("addhotel")
                .path("1").path("3").request().accept(MEDIATYPE_TRAVELGOOD)
                .put(Entity.entity("", MediaType.TEXT_PLAIN),
                        StatusRepresentation.class);
        
        Link link = addHotel.getLinkByRelation("http://travelgood.ws/relations/self");
        ItinieryRepresentation itin = client.target(link.getUri()).request()
                .accept(MEDIATYPE_TRAVELGOOD).get(ItinieryRepresentation.class);
        
        assertEquals(itin.getItin().getStatus(),"updated");
        assertEquals(itin.getItin().getFlights().size(),3);
        assertEquals(itin.getItin().getHotels().size(),2);
        for (HotelInfoType hi : itin.getItin().getHotels()) {
            assertEquals(hi.getStatus(),"unconfirmed");
        }
        for (FlightInfoType hi : itin.getItin().getFlights()) {
            assertEquals(hi.getStatus(),"unconfirmed");
        }
        
        CreditCardType cc = new CreditCardType();
        cc.setName("Anne Strandberg");
        cc.setNumber("50408816");
        
        ExpirationDateType ed = new ExpirationDateType();
        ed.setMonth(5);
        ed.setYear(9);
        cc.setExpDat(ed);
        
        StatusRepresentation status = target.path("travelgood")
                .path(itin.getItin().getId()).path("book").request()
                .accept(MEDIATYPE_TRAVELGOOD)
                .put(Entity.entity(cc, MEDIATYPE_TRAVELGOOD),
                        StatusRepresentation.class);
        
        assertEquals(status.getStatus(),"booked");
        
        link = out.getLinkByRelation("http://travelgood.ws/relations/self");
        assertNotNull(link);
        assertEquals(link.getUri(),
                "http://localhost:8080/travelgood/webresources/travelgood/1");
        
        itin = client.target(link.getUri()).request()
                .accept(MEDIATYPE_TRAVELGOOD)
                .get(ItinieryRepresentation.class);
        
        for (HotelInfoType hi : itin.getItin().getHotels()) {
            assertEquals(hi.getStatus(),"confirmed");
        }
        for (FlightInfoType hi : itin.getItin().getFlights()) {
            assertEquals(hi.getStatus(),"confirmed");
        }
    }
}