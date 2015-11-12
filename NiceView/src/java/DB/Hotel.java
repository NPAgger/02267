/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

/**
 *
 * @author Nis
 */
public class Hotel {
    private String name;
    private String city;
    private String address;
    private int book_num;
    private int price_pr_n;
    private int price;
    private boolean credit;
    private String service;
    
    public Hotel(String name, String city, String address, int book_num, 
            int price_pr_n, int price, boolean credit, String service) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.book_num = book_num;
        this.price_pr_n = price_pr_n;
        this.price = price;
        this.credit = credit;
        this.service = service;
    }
    
    public String getName() {
        return name;
    }
    
    public String getCity() {
        return city;
    }
    
    public String getAddress() {
        return address;
    }
    
    public int getBookNum() {
        return book_num;
    }
    
    public int getPricePrN() {
        return price_pr_n;
    }
    
    public boolean getCredit() {
        return credit;
    }
    
    public String getService() {
        return service;
    }

}
