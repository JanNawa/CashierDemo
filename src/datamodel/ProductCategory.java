package datamodel;

/**
 *
 * @author Jan
 */

public class ProductCategory {
    private int id;
    private String name;
    private String description;

    public ProductCategory(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }   
}
