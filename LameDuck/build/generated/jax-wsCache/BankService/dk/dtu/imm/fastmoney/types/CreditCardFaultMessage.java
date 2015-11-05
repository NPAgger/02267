
package dk.dtu.imm.fastmoney.types;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.10-b140803.1500
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "CreditCardFault", targetNamespace = "http://fastmoney.imm.dtu.dk")
public class CreditCardFaultMessage
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private CreditCardFaultType faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public CreditCardFaultMessage(String message, CreditCardFaultType faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public CreditCardFaultMessage(String message, CreditCardFaultType faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: dk.dtu.imm.fastmoney.types.CreditCardFaultType
     */
    public CreditCardFaultType getFaultInfo() {
        return faultInfo;
    }

}