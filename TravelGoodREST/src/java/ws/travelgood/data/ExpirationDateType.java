/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nis
 */
@XmlRootElement()
public class ExpirationDateType {
    private int month;
    private int year;
    
    public int getMonth() {
        return month;
    }
    
    public void setMonth(int month) {
        this.month = month;
    }
    
    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public ws.ld.CreditCardInfoType.ExpirationDate toLDexp() {
        ws.ld.CreditCardInfoType.ExpirationDate exp = 
                new ws.ld.CreditCardInfoType.ExpirationDate();
        exp.setMonth(month);
        exp.setYear(year);
        
        return exp;
    }
    
    public ws.nv.CreditCardInfoType.ExpirationDate toNVexp() {
        ws.nv.CreditCardInfoType.ExpirationDate exp = 
                new ws.nv.CreditCardInfoType.ExpirationDate();
        exp.setMonth(month);
        exp.setYear(year);
        
        return exp;
    }
}
