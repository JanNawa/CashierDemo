package controller;

import datamodel.Product;
import datamodel.ProductCategory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jan
 */
public class ProductListController implements Initializable {
    
    Product[] products;
    List<Product> selectedProducts;
    
    ArrayList<ProductCategory> categories;
    ListProperty<ProductCategory> categoryListProperty;
    
    @FXML
    private ListView categoryListView;

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
        products[0] = new Product(101, 111, "Milk", "Low Fat 2.0 litres", 4.99);
        products[1] = new Product(102, 111, "Eggs", "White Large 12 Eggs", 2.49);
        products[2] = new Product(103, 111, "Bread", "100% Whole Wheat Sliced Bread", 2.00);
        products[3] = new Product(104, 111, "Yogurt", "Natural Yogurt 750 g", 2.89);
        products[4] = new Product(105, 111, "Salmon", "Atlantic Salmon Portion 300 g", 11.99);

        product1Button.setText(products[0].getName());
        product2Button.setText(products[1].getName());
        product3Button.setText(products[2].getName());
        product4Button.setText(products[3].getName());
        product5Button.setText(products[4].getName());
        
        categories = new ArrayList<>();
        categories.add(new ProductCategory(1, "Bakery", ""));
        categories.add(new ProductCategory(2, "Dairy & Eggs", ""));
        categories.add(new ProductCategory(3, "Pasta & Rice", ""));
        categories.add(new ProductCategory(4, "Chips & Snacks", ""));
        categories.add(new ProductCategory(5, "Frozen Food", ""));
        categories.add(new ProductCategory(6, "Cereal & Breakfast", ""));
        categories.add(new ProductCategory(7, "Drinks", ""));
        categories.add(new ProductCategory(8, "Fruits & Vegetables", ""));
        categories.add(new ProductCategory(9, "Meat & Seafood", ""));
        
        categoryListProperty = new SimpleListProperty<>();
        categoryListProperty.set(FXCollections.observableArrayList(categories));
        categoryListView.itemsProperty().bind(categoryListProperty);
        categoryListView.setPrefWidth(130);

        selectedProducts = new ArrayList<>();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchKeyword();
        });
    }

    @FXML
    public void addProduct(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String name = btn.getText();
        
        addMatchProduct(name);
    }
    
    public void addProduct(String name) {
        addMatchProduct(name);
    }
    
    private void addMatchProduct(String name){
        switch (name) {
            case "Milk":
                selectedProducts.add(products[0]);
                break;
            case "Eggs":
                selectedProducts.add(products[1]);
                break;
            case "Bread":
                selectedProducts.add(products[2]);
                break;
            case "Yogurt":
                selectedProducts.add(products[3]);
                break;
            case "Salmon":
                selectedProducts.add(products[4]);
                break;
        }
    }
    
    @FXML
    public void goToCart(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/cart.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            
            // fix to dependency injection?
            CartController cartController = fxmlLoader.<CartController>getController();
            cartController.getSelectedProducts(selectedProducts);
            
            Stage stage = new Stage();
            stage.setTitle("Your Cart");
            stage.setScene(new Scene(root, 750, 600));
            stage.show();
        } catch (IOException e) {
            System.out.println("couldn't load your cart page");
            e.printStackTrace();
        }
    }
    
    class SearchProduct extends HBox{
        Label label = new Label();
        Button button = new Button();
        
        SearchProduct(String labelText, String buttonText){
            label.setText(labelText);
            label.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(label, Priority.ALWAYS);
            
            button.setText(buttonText);
            button.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    addProduct(label.getText());
                }
            });
            
            this.getChildren().addAll(label, button);
        }
    }
    
    @FXML
    public void searchKeyword(){
        String searchFieldStr = searchField.getText().trim();
        searchListView.getItems().clear();
        for(Product product : products){
            String productName = product.getName();
            if(containsIgnoreCase(productName, searchFieldStr)){
                searchListView.getItems().add(new SearchProduct(productName, "Add to Cart"));
            }
        }
        matchLabel.setText("Found " + searchListView.getItems().size() + " Match");       
    }
    
    private boolean containsIgnoreCase(String fullStr, String subStr){
        return fullStr.toLowerCase().contains(subStr.toLowerCase());
    }
}
