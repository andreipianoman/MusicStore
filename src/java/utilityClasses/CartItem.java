/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilityClasses;

/**
 *
 * @author Turbotwins
 */
public class CartItem {
    private int ID;
    private String name;
    private double price;
    private int quantity;
    private String size;
    private String image;
    
    public CartItem() {}
    
    public CartItem(int ID, String name, double price, int stock, String size, String image) {
        this.ID=ID;
        this.name = name;
        this.price = price;
        this.quantity = stock;
        this.size = size;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int stock) {
        this.quantity = stock;
    }
}
