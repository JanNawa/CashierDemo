package datamodel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import utils.TaxRate;

/**
 * This class models the basic attributes for Cashier.
 * 
 * @author Jan, 2019
 */
public class Cashier{

    private int orderId;
    private LocalDateTime dateTime;
    private ArrayList<Product> order;
    private List<Integer> quantity;
    private double subtotal;
    private double tax;
    private double total;    
    
    /**
     * Constructor of Cashier object with the default setting
     * 
     * order id set to 1000
     * date time set to current date and time
     * order set to empty ArrayList
     * subtotal set to 0.0
     * tax of subtotal set to 0.0
     * total set to 0.0
     */
    public Cashier(){
        this.orderId = 1000;
        this.dateTime = LocalDateTime.now();
        this.order  = new ArrayList<>();
        this.quantity = new ArrayList<>();
        this.subtotal = 0.0;
        this.tax = 0.0;
        this.total = 0.0;
    }
    
    /**
     * Add the product to the order list and update the total price
     * 
     * @param product the selected product from user
     */
    public void addProduct(Product product) {
        // empty cart
        if (order.isEmpty()) {
            addNewProduct(product);
            return;
        }
        // add quantity to existing product
        for (int i = 0; i<order.size(); i++){
            if(order.get(i) == product){
                quantity.set(i, quantity.get(i)+1);
                calcTotal();
                return;
            }
        }
        // add new product to non-empty cart
        addNewProduct(product);
    }
    
    private void addNewProduct(Product product){
        order.add(product);
        quantity.add(1);
        calcTotal();
    }
    
    /**
     * Delete the product from the order list and update the total price
     * 
     * @param product the selected product from user
     */
    public void deleteProduct(Product product){
        for(int i=0; i<order.size(); i++){
            if(order.get(i) == product){
                quantity.remove(i);
            }
        }
        order.remove(product);
        calcTotal();
    }

    /**
     * Calculate the subtotal price (product price without tax)
     * 
     * @return subtotal of product price
     */
    private double calcSubtotal() {
        int size = order.size();
        if(size <= 0) {
            return 0.0;
        } else {
            subtotal = 0;
            for(int i = 0; i < size; i++){
                subtotal += ((order.get(i)).getPrice() * quantity.get(i));
            }
            return subtotal;
        }
    }
    
    /**
     * Calculate the subtotal, tax of subtotal and total price
     */
    public void calcTotal() {
        subtotal = calcSubtotal();
        tax = subtotal * TaxRate.TAX_RATE_ONTARIO;    
        total = subtotal + tax;
    }

    /**
     * Set order id
     * @param orderId 
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Retrieve size of the order
     * 
     * @return size of the order
     */
    public int getOrderSize(){
        return order.size();
    }

    /**
     * Retrieve the order list
     * 
     * @return copy of order list
     */
    public ArrayList<Product> getOrder() {
        ArrayList<Product> copyOrder = new ArrayList<>(order);
        return copyOrder;
    }
    
    public List<Integer> getQuantity() {
        List<Integer> copyQuantity = new ArrayList<>(quantity);
        return copyQuantity;
    }
    
    /**
     * Retrieve the subtotal
     * 
     * @return subtotal
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * Retrieve the tax of subtotal
     * 
     * @return tax of subtotal
     */
    public double getTax() {
        return tax;
    }

    /**
     * Retrieve the total price
     * 
     * @return total price
     */
    public double getTotal() {
        return total;
    }

    /**
     * Retrieve the order id, date time, order list, subtotal, tax of subtotal and total price
     * 
     * @return the string of cashier detail
     */
    @Override
    public String toString() {
        return "\norderId=" + orderId
                + "\ndateTime=" + dateTime
                + "\norder=" + order
                + "\nquantity=" + quantity
                + "\nsubtotal=" + subtotal
                + "\ntax=" + tax
                + "\ntotal=" + total;
    }
}
