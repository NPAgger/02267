/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lameduck.ws;
import dk.dtu.imm.fastmoney.types.*;
import java.util.Arrays;
import lameducktypes.*;
import java.util.List;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.ws.WebServiceRef;
import ws.lameduck.BookFlightFault;
import ws.lameduck.CancelFlightFault;

/**
 *
 * @author tmoer
 */
@WebService(serviceName = "lameduckService", portName = "lameduckPortTypeBindingPort", endpointInterface = "ws.lameduck.LameduckPortType", targetNamespace = "http://lameduck.ws", wsdlLocation = "WEB-INF/wsdl/LameDuck/lameduck.wsdl")
public class LameDuck {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/fastmoney.imm.dtu.dk_8080/BankService.wsdl")
    private BankService service_1;
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/fastmoney.imm.dtu.dk_8080/BankService.wsdl")
    private BankService service;
    
    
    private static AccountType account;
    private static DatatypeFactory df;
    static{
        DatatypeFactory d = null;
        try{
            d = DatatypeFactory.newInstance();
        }
        catch(DatatypeConfigurationException e){
        }
        df = d;
        account = new AccountType();
        account.setName("LameDuck");
        account.setNumber("50208812");
    }
    
  FlightInfo flight0 = dataInit(1,500,"Dohop","Reykjavik","Beijing",df.newXMLGregorianCalendar("2015-12-20T00:00:00"),df.newXMLGregorianCalendar("2015-12-24T00:00:00"), "Boeing 747");
    FlightInfo flight1 = dataInit(2,700,"Dohop","Copenhagen","Moscow",df.newXMLGregorianCalendar("2015-12-23T00:00:00"),df.newXMLGregorianCalendar("2015-12-24T00:00:00"), "Boeing 747");
    FlightInfo flight2 = dataInit(3,200,"Dohop","New York","Copenhagen",df.newXMLGregorianCalendar("2015-12-21T00:00:00"),df.newXMLGregorianCalendar("2015-12-25T00:00:00"), "Boeing 747");
    FlightInfo flight3 = dataInit(4,300,"Dohop","Oppdal","Berlin",df.newXMLGregorianCalendar("2015-12-22T00:00:00"),df.newXMLGregorianCalendar("2015-12-26T00:00:00"), "Boeing 747");
    FlightInfo flight4 = dataInit(5,400,"Dohop","Reykjavik","Warsaw",df.newXMLGregorianCalendar("2015-12-23T00:00:00"),df.newXMLGregorianCalendar("2015-12-25T00:00:00"), "Boeing 747");
    FlightInfo flight5 = dataInit(6,800,"Dohop","Copenhagen","Reykjavik",df.newXMLGregorianCalendar("2015-12-23T00:00:00"),df.newXMLGregorianCalendar("2015-12-27T00:00:00"), "Boeing 747");
    FlightInfo flight6 = dataInit(7,400,"Dohop","Reykjavik","Copenhagen",df.newXMLGregorianCalendar("2015-12-19T00:00:00"),df.newXMLGregorianCalendar("2015-12-28T00:00:00"), "Boeing 747");
    FlightInfo flight7 = dataInit(8,500,"Dohop","Copenhagen","New York",df.newXMLGregorianCalendar("2015-12-19T00:00:00"),df.newXMLGregorianCalendar("2015-12-29T00:00:00"), "Boeing 747");
    FlightInfo flight8 = dataInit(9,500,"Dohop","Moscow","Beijing",df.newXMLGregorianCalendar("2015-12-23T00:00:00"),df.newXMLGregorianCalendar("2015-12-24T00:00:00"), "Boeing 747");
    FlightInfo flight9 = dataInit(10,700,"Dohop","Barcelona","Copenhagen",df.newXMLGregorianCalendar("2015-12-23T00:00:00"),df.newXMLGregorianCalendar("2015-12-24T00:00:00"), "Boeing 747");
    FlightInfo flight10 = dataInit(11,600,"Dohop","Moscow","Copenhagen",df.newXMLGregorianCalendar("2015-12-22T00:00:00"),df.newXMLGregorianCalendar("2015-12-25T00:00:00"), "Boeing 747");
    FlightInfo flight11 = dataInit(12,600,"Dohop","New York","Sidney",df.newXMLGregorianCalendar("2015-12-22T00:00:00"),df.newXMLGregorianCalendar("2015-12-26T00:00:00"), "Boeing 747");
  
    
    
    public List<FlightInfo> staticFlightData = Arrays.asList(flight0,flight1,flight2,flight3,flight4,flight5,flight6,flight7,flight8,flight9,flight10);
    
    
    public FlightInfo dataInit(int bookNmr, int price, String airlineRes, String ori, String des, XMLGregorianCalendar dep, XMLGregorianCalendar arr, String carr){
        FlightInfo info = new FlightInfo();
        info.setAirlineReservationService(airlineRes);
        info.setBookingNumber(bookNmr);
        info.setPrice(price);
        Flight flight = new Flight();
        flight.setOrigin(ori);
        flight.setDestination(des);
        flight.setDeparture(dep);
        flight.setArrival(arr);
        flight.setCarrier(carr);
        info.setFlight(flight);
        
        return info;
    }
    

    public lameducktypes.ListOfFlights getFlights(lameducktypes.Request input) {
        lameducktypes.ListOfFlights list = new lameducktypes.ListOfFlights();
        
        for(int i = 0; i < staticFlightData.size(); i++){
            if(input.getOrigin().equals(staticFlightData.get(i).getFlight().getOrigin()) && input.getDestination().equals(staticFlightData.get(i).getFlight().getDestination())){
                if(input.getDate().equals((staticFlightData.get(i).getFlight().getDeparture()))){
                    lameducktypes.FlightInfo info = new lameducktypes.FlightInfo();
                    info.setBookingNumber(staticFlightData.get(i).getBookingNumber());
                    info.setPrice(staticFlightData.get(i).getPrice());
                    info.setAirlineReservationService(staticFlightData.get(i).getAirlineReservationService());
                    lameducktypes.Flight flight = new lameducktypes.Flight();
                    flight.setOrigin(staticFlightData.get(i).getFlight().getOrigin());
                    flight.setDestination(staticFlightData.get(i).getFlight().getDestination());
                    flight.setDeparture(staticFlightData.get(i).getFlight().getDeparture());
                    flight.setArrival(staticFlightData.get(i).getFlight().getArrival());
                    flight.setCarrier(staticFlightData.get(i).getFlight().getCarrier());
                    info.setFlight(flight);
                
                    list.getFlightInfo().add(info);
                }
            }
        }
        return list;
    }

    public boolean bookFlight(lameducktypes.RequestbookFlight input) throws BookFlightFault {
        boolean status = false;
        int price = 0;
        for (FlightInfo staticFlightData1 : staticFlightData) {
            if (staticFlightData1.getBookingNumber() == input.getBookingNumber()) {
                status = true;
                price = staticFlightData1.getPrice();
            }
        }
        if (status){
            try{
                boolean ccValid = validateCreditCard(13,input.getCreditCardInfo(),price);
                
                if (ccValid){
                    chargeCreditCard(13, input.getCreditCardInfo(), price, account);
                }
            }
            catch (CreditCardFaultMessage fault){
                String message = fault.getMessage();
                lameducktypes.BookFlightFault bff = new lameducktypes.BookFlightFault();
                bff.setMessage(fault.getFaultInfo().getMessage());
                BookFlightFault ex = new BookFlightFault(message, bff);
                throw ex;
            }
            
        }
        else {
            String message = "Flight not found";
            lameducktypes.BookFlightFault bff = new lameducktypes.BookFlightFault();
            bff.setMessage(message);
            BookFlightFault ex = new BookFlightFault(message, bff);
            throw ex;
        }
        return status;
    }

    public boolean cancelFlight(lameducktypes.RequestcancelFlight input) throws CancelFlightFault {
        boolean status;
        int halfPrice = (int) (input.getPrice()*0.5);
        try{
            refundCreditCard(13, input.getCreditCardInfo(), halfPrice, account);
            status = true;
        }
        catch(CreditCardFaultMessage fault){
            String message = fault.getMessage();
            lameducktypes.CancelFlightFault cff = new lameducktypes.CancelFlightFault();
            cff.setMessage(fault.getFaultInfo().getMessage());
            CancelFlightFault ex = new CancelFlightFault(message, cff);
            throw ex;
        }
        return status;
    }

    private boolean chargeCreditCard(int group, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCardInfo, int amount, dk.dtu.imm.fastmoney.types.AccountType account) throws CreditCardFaultMessage {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        dk.dtu.imm.fastmoney.types.BankPortType port = service_1.getBankPort();
        return port.chargeCreditCard(group, creditCardInfo, amount, account);
    }

    private boolean refundCreditCard(int group, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCardInfo, int amount, dk.dtu.imm.fastmoney.types.AccountType account) throws CreditCardFaultMessage {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        dk.dtu.imm.fastmoney.types.BankPortType port = service_1.getBankPort();
        return port.refundCreditCard(group, creditCardInfo, amount, account);
    }

    private boolean validateCreditCard(int group, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCardInfo, int amount) throws CreditCardFaultMessage {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        dk.dtu.imm.fastmoney.types.BankPortType port = service_1.getBankPort();
        return port.validateCreditCard(group, creditCardInfo, amount);
    }
    
}
