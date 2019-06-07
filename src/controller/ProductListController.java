package controller;

import datamodel.Product;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

/**
 * FXML Controller class
 *
 * @author Jan
 */
public class ProductListController implements Initializable {
    
    Product[] products;

    @FXML
    private Button product1Button;

    @FXML
    private Button product2Button;

    @FXML
    private Button product3Button;

    @FXML
    private Button product4Button;

    @FXML
    private Button product5Button;
    
    @FXML
    private TextField searchField;
    
    @FXML
    private Label matchLabel;
    
    @FXML
    private ListView searchListView;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        products = new Product[5];
        products[0] = new Product(101, "Milk", "Low Fat 2.0 litres", 4.99);
        products[1] = new Product(102, "Eggs", "White Large 12 Eggs", 2.49);
        products[2] = new Product(103, "Bread", "100% Whole Wheat Sliced Bread", 2.00);
        products[3] = new Product(104, "Yogurt", "Natural Yogurt 750 g", 2.89);
        products[4] = new Product(105, "Salmon", "Atlantic Salmon Portion 300 g", 11.99);

        product1Button.setText(products[0].getName());
        product2Button.setText(products[1].getName());
        product3Button.setText(products[2].getName());
        product4Button.setText(products[3].getName());
        product5Button.setText(products[4].getName());
    }
    
    @FXML
    public void addProduct(ActionEvent event) {
        System.out.println("Add Product");
    }
    
    @FXML
    public void searchKeyword(ActionEvent event){
        String searchFieldStr = searchField.getText().trim();
        searchListView.getItems().clear();
        for(Product product : products){
            String productName = product.getName();
            if(containsIgnoreCase(productName, searchFieldStr)){
                searchListView.getItems().add(productName);
            }
        }
        matchLabel.setText("Found " + searchListView.getItems().size() + " Match");       
    }
    
    private boolean containsIgnoreCase(String fullStr, String subStr){
        return fullStr.toLowerCase().contains(subStr.toLowerCase());
    }
}
