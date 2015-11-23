/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niceview.ws;

import javax.jws.WebService;
import javax.xml.ws.WebServiceRef;

import dk.dtu.imm.fastmoney.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ws.niceview.*;

/**
 *
 * @author Nis
 */
@WebService(serviceName = "NiceViewWSDLPortTypeService", portName = "NiceViewWSDLPortTypePort", endpointInterface = "ws.niceview.NiceViewWSDLPortType", targetNamespace = "http://niceview.ws", wsdlLocation = "WEB-INF/wsdl/NiceViewService/NiceViewWSDLWrapper.wsdl")
public class NiceViewService {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/fastmoney.imm.dtu.dk_8080/BankService.wsdl")
    private BankService service;
    
    private HotelInfo dataInt(int book_num, boolean credit, String service, 
            String name, String city, String address, int price_pr_n) {
        
        HotelInfo info = new HotelInfo();
        info.setBookNum(book_num);
        info.setCredit(credit);
        info.setService(service);
        info.setPrice(0);
        
        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setAddress(address);
        hotel.setCity(city);
        hotel.setPricePrN(price_pr_n);
        
        info.setHotel(hotel);
        
        return info;
    }
    
    HotelInfo hotel1 = dataInt(1,true,"NiceView","D'Angleterre","Copenhagen",
            "Kngs. Nytorv 17", 5000);
    HotelInfo hotel2 = dataInt(2,false,"NiceView","Reykjavik Hotel","Reykjavik",
            "Kaffi Hljómalind", 1000);
    HotelInfo hotel3 = dataInt(3,false,"NiceView","Raffles Beijing Hotel", 
            "Beijing", "33 E Chang'an Ave", 1500);
    HotelInfo hotel4 = dataInt(4,true,"NiceView","The St. Regis Beijing", 
            "Beijing", "21 Jianguomen Outer St", 3500);
    
    private List<HotelInfo> staticHotelData = Arrays.asList(hotel1,hotel2,
            hotel3,hotel4);
    
    private List<Integer> bookedHotels = new ArrayList<Integer>();
    
    public GetResponse getHotels(GetRequest input) {
        GetResponse output = new GetResponse();
        
        for (HotelInfo info : staticHotelData) {
            if (info.getHotel().getCity().equals(input.getCity())) {
                int time = (int) (input.getDep().toGregorianCalendar()
                        .getTimeInMillis() - input.getArr().toGregorianCalendar()
                        .getTimeInMillis());
                
                info.setPrice(time/(1000*60*60*24) * info.getHotel().getPricePrN());
                output.getHotelList().add(info);
            }
        }
        
        return output;
    }
    
    public boolean bookHotel(BookRequest input) throws BookFault {
        
        boolean output = false;
        int price = 0;
        
        for (HotelInfo info : staticHotelData) {
            if (info.getBookNum() == input.getBookNum()) {
                output = true;
                price = info.getPrice();
            }
        }
        
        if (output) {
            try {
                validateCreditCard(13,input.getCreditCardInfo(),price);
                
                bookedHotels.add(input.getBookNum());
            } catch (CreditCardFaultMessage fault) {
                String message = fault.getMessage();
                BookHotelFault bhf = new BookHotelFault();
                bhf.setMessage(fault.getFaultInfo().getMessage());
                BookFault ex = new BookFault(message,bhf);
                throw ex;
            }
        }
        else {
            String message = "Hotel not found";
            BookHotelFault bhf = new BookHotelFault();
            bhf.setMessage(message);
            BookFault ex = new BookFault(message,bhf);
            throw ex;
        }
        return output;
    }
    
    public boolean cancelHotel(CancelRequest input) throws CancelFault_Exception {
        boolean output = false;
        
        if (bookedHotels.contains(input.getBookNum())) {
            bookedHotels.remove(input.getBookNum());
            output = true;
        } else {
            String message = "";
            CancelFault cf = new CancelFault();
            cf.setMessage(message);
            CancelFault_Exception ex = new CancelFault_Exception(message, cf);
            throw ex;
        }
        
        return output;
    }
        
    private boolean validateCreditCard(int group, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCardInfo, int amount) throws CreditCardFaultMessage {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        BankPortType port = service.getBankPort();
        return port.validateCreditCard(group, creditCardInfo, amount);
    }
    
}