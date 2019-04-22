package datamodel;

/**
 * This class models the basic attributes for Product.
 * 
 * @author Nawaphan Chayopathum(Jan)
 */
public class Product {

    private int id;
    private String name;
    private String description;
    private double price;

    /**
     * Constructor of Product with given id, given name, given description and given price.
     * 
     * @param id the id of product
     * @param name the name of product
     * @param description the description of product
     * @param price the price of product
     */
    public Product(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    /**
     * Retrieve the name of the product
     * 
     * @return the name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieve the price of the product
     * 
     * @return the price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Retrieve the id, name, description and price of product
     * 
     * @return the string of product detail
     */
    @Override
    public String toString() {
        return "id = " + id
                + "\tname = " + name
                + "\tdescription = " + description
                + "\tprice = " + price;
    }
}