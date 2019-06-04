package controller;

import datamodel.Product;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author Jan
 */
public class ProductListController implements Initializable {
    
    Product product1, product2, product3, product4, product5;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        product1 = new Product(101, "Milk", "Low Fat 2.0 litres", 4.99);
        product2 = new Product(102, "Eggs", "White Large 12 Eggs", 2.49);
        product3 = new Product(103, "Bread", "100% Whole Wheat Sliced Bread", 2.00);
        product4 = new Product(104, "Yogurt", "Natural Yogurt 750 g", 2.89);
        product5 = new Product(105, "Salmon", "Atlantic Salmon Portion 300 g", 11.99);

        product1Button.setText(product1.getName());
        product2Button.setText(product2.getName());
        product3Button.setText(product3.getName());
        product4Button.setText(product4.getName());
        product5Button.setText(product5.getName());
    }
    
    @FXML
    public void addProduct(ActionEvent event) {
        System.out.println("Add Product");
    }
}
