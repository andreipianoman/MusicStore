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
public class CartClothingItem extends CartItem{
    private String size;
    
    public CartClothingItem(int ID, String name, double price, int stock, String size) {
        super(ID, name, price, stock);
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
    
}
