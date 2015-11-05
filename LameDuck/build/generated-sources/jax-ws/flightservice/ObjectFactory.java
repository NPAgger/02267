
package flightservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the flightservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Status_QNAME = new QName("flightservice", "status");
    private final static QName _Destination_QNAME = new QName("flightservice", "destination");
    private final static QName _ListOfFlights_QNAME = new QName("flightservice", "listOfFlights");
    private final static QName _Origin_QNAME = new QName("flightservice", "origin");
    private final static QName _Date_QNAME = new QName("flightservice", "date");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: flightservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Request }
     * 
     */
    public Request createRequest() {
        return new Request();
    }

    /**
     * Create an instance of {@link BookFlightFault }
     * 
     */
    public BookFlightFault createBookFlightFault() {
        return new BookFlightFault();
    }

    /**
     * Create an instance of {@link ListOfFlights }
     * 
     */
    public ListOfFlights createListOfFlights() {
        return new ListOfFlights();
    }

    /**
     * Create an instance of {@link RequestcancelFlight }
     * 
     */
    public RequestcancelFlight createRequestcancelFlight() {
        return new RequestcancelFlight();
    }

    /**
     * Create an instance of {@link RequestbookFlight }
     * 
     */
    public RequestbookFlight createRequestbookFlight() {
        return new RequestbookFlight();
    }

    /**
     * Create an instance of {@link CancelFlightFault }
     * 
     */
    public CancelFlightFault createCancelFlightFault() {
        return new CancelFlightFault();
    }

    /**
     * Create an instance of {@link Flight }
     * 
     */
    public Flight createFlight() {
        return new Flight();
    }

    /**
     * Create an instance of {@link FlightInfo }
     * 
     */
    public FlightInfo createFlightInfo() {
        return new FlightInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "flightservice", name = "status")
    public JAXBElement<Boolean> createStatus(Boolean value) {
        return new JAXBElement<Boolean>(_Status_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "flightservice", name = "destination")
    public JAXBElement<String> createDestination(String value) {
        return new JAXBElement<String>(_Destination_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOfFlights }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "flightservice", name = "listOfFlights")
    public JAXBElement<ListOfFlights> createListOfFlights(ListOfFlights value) {
        return new JAXBElement<ListOfFlights>(_ListOfFlights_QNAME, ListOfFlights.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "flightservice", name = "origin")
    public JAXBElement<String> createOrigin(String value) {
        return new JAXBElement<String>(_Origin_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "flightservice", name = "date")
    public JAXBElement<XMLGregorianCalendar> createDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_Date_QNAME, XMLGregorianCalendar.class, null, value);
    }

}
