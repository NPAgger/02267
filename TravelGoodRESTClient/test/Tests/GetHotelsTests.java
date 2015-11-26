/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import ws.nv.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Nis
 */
public class GetHotelsTests {
    
    @Test
    public void getHotelsTest_denmark() throws Exception{
        Client client = ClientBuilder.newClient();
        WebTarget wt = client.target("http://localhost:8080/TravelGoodREST/webresources/TravelGood");
        
        GetResponse result = wt.path("/hotels").queryParam("city", "Copenhagen")
                .queryParam("arrival", "01-01-2015").queryParam("departure", "02-01-2015")
                .request().get(GetResponse.class);
        
        assertEquals(result.getHotelList().get(0).getBookNum(),1);
    }
    
    @Test
    public void getHotelsTest_iceland() throws Exception{
        Client client = ClientBuilder.newClient();
        WebTarget wt = 
                client.target("http://localhost:8080/TravelGoodREST/webresources/TravelGood");
        
        GetResponse result = wt.path("/hotels").queryParam("city", "Reykjavik")
                .queryParam("arrival", "01-01-2015").queryParam("departure", "05-01-2015")
                .request().get(GetResponse.class);
        
        assertEquals(result.getHotelList().get(0).getHotel().getAddress(),
                "Kaffi Hljómalind");
    }
    
    @Test
    public void getHotelsTest_china() throws Exception{
        Client client = ClientBuilder.newClient();
        WebTarget wt = 
                client.target("http://localhost:8080/TravelGoodREST/webresources/TravelGood");
        
        GetResponse result = wt.path("/hotels").queryParam("city", "Beijing")
                .queryParam("arrival", "01-01-2015").queryParam("departure", "05-01-2015")
                .request().get(GetResponse.class);
        
        assertEquals(result.getHotelList().size(),2);
    }
    
    @Test
    public void getHotelsTest_empty() throws Exception{
        Client client = ClientBuilder.newClient();
        WebTarget wt = 
                client.target("http://localhost:8080/TravelGoodREST/webresources/TravelGood");
        
        GetResponse result = wt.path("/hotels").queryParam("city", "Berlin")
                .queryParam("arrival", "01-01-2015").queryParam("departure", "02-01-2015")
                .request().get(GetResponse.class);
        
        assertEquals(result.getHotelList().size(),0);
    }
    
    
}
