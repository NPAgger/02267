/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import ws.nv.*;
import ws.nv.*;

/**
 *
 * @author Nis
 */
@XmlRootElement()
public class CreditCardType {
    @XmlElement
    private ExpirationDateType exp;
    private String name;
    private String number;
    
    public ExpirationDateType getExpDate() {
        return exp;
    }
    
    public void setExpDat(ExpirationDateType exp) {
        this.exp = exp;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getNumber() {
        return number;
    }
    
    public void setNumber(String number) {
        this.number = number;
    }
    
    public ws.nv.CreditCardInfoType toNVcc() {
        ws.nv.CreditCardInfoType cc = new ws.nv.CreditCardInfoType();
        cc.setName(name);
        cc.setNumber(number);
        
        ws.nv.CreditCardInfoType.ExpirationDate expdate = exp.toNVexp();
        cc.setExpirationDate(expdate);
        
        return cc;
    }
    
    public ws.ld.CreditCardInfoType toLDcc() {
        ws.ld.CreditCardInfoType cc = new ws.ld.CreditCardInfoType();
        cc.setName(name);
        cc.setNumber(number);
        
        ws.ld.CreditCardInfoType.ExpirationDate expdate = exp.toLDexp();
        cc.setExpirationDate(expdate);
        
        return cc;
    }
}