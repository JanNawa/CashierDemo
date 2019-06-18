package datamodel;

/**
 * This class models the basic attributes for Product.
 * 
 * @author Jan, 2019
 */
public class Product {

    private int id;
    private int categoryId;
    private String name;
    private String description;
    private double price;

    /**
     * Constructor of Product with given id, given name, given description and given price.
     * 
     * @param id the id of product
     * @param categoryId the id of product category
     * @param name the name of product
     * @param description the description of product
     * @param price the price of product
     */
    public Product(int id, int categoryId, String name, String description, double price) {
        this.id = id;
        this.categoryId = categoryId;
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
