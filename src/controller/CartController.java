package controller;

import datamodel.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.fxml.*;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.util.Callback;
import utils.*;

/**
 * FXML Controller class
 *
 * @author Jan, 2019
 */
public class CartController implements Initializable {

    Product product1, product2, product3, product4, product5;
    Cashier cashier = new Cashier();
    OrderHistory orderHis = new OrderHistory();
    ListProperty<Product> productListProperty;
    ListProperty<Integer> quantityListProperty;

    @FXML
    private BorderPane cartBorderPane;

    @FXML
    private ListView orderList;

    @FXML
    private ListView quantityList;

    @FXML
    private Label subtotal;

    @FXML
    private Label tax;

    @FXML
    private Label total;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        product1 = new Product(101, 111, "Milk", "Low Fat 2.0 litres", 4.99);
        product2 = new Product(102, 111, "Eggs", "White Large 12 Eggs", 2.49);
        product3 = new Product(103, 111, "Bread", "100% Whole Wheat Sliced Bread", 2.00);
        product4 = new Product(104, 111,"Yogurt", "Natural Yogurt 750 g", 2.89);
        product5 = new Product(105, 111,"Salmon", "Atlantic Salmon Portion 300 g", 11.99);

        productListProperty = new SimpleListProperty<>();
        productListProperty.set(FXCollections.observableArrayList(cashier.getOrder()));
        orderList.itemsProperty().bind(productListProperty);

        quantityListProperty = new SimpleListProperty<>();
        quantityListProperty.set(FXCollections.observableArrayList(cashier.getQuantity()));
        quantityList.itemsProperty().bind(quantityListProperty);
        quantityList.setPrefWidth(100);
        quantityList.setCellFactory(new Callback<ListView<Integer>, ListCell<Integer>>() {
            @Override
            public ListCell<Integer> call(ListView<Integer> param) {
                return new AdjustQuantity();
            }
        });

        updatePrice();
    }

    // TODO-add in list view on each product list
    class AdjustQuantity extends ListCell<Integer> {

        HBox hbox = new HBox();
        Button minusButton = new Button("-");
        Label quantityLabel = new Label("");
        Button addButton = new Button("+");
       
        int index;

        private AdjustQuantity() {
            quantityLabel.setPadding(new Insets(1));

            hbox.getChildren().addAll(minusButton, quantityLabel, addButton);
            
            index = this.getIndex();
            System.out.println("Index : " + index);
        }

        @Override
        protected void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                quantityLabel.setText("" + item);
                minusButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (item <= 1) {
                            minusButton.isDisable();
                        } else {
                            int newQuantity = item - 1;
                            quantityLabel.setText("" + newQuantity);
                            
                            cashier.adjustQuantity(index, -1);
                            quantityListProperty.set(FXCollections.observableArrayList(cashier.getQuantity()));
                            updatePrice();
                        }
                    }
                });

                addButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int newQuantity = item + 1;
                        quantityLabel.setText("" + newQuantity);
                        
                        cashier.adjustQuantity(index, 1);
                        quantityListProperty.set(FXCollections.observableArrayList(cashier.getQuantity()));
                        updatePrice();
                    }
                });
                
                setGraphic(hbox);
            } else {
                setGraphic(null);
            }
        }
    }

    public void getSelectedProducts(List<Product> selectedProducts) {
        for (Product product : selectedProducts) {
            addProduct(product.getName());
        }
    }

    private void addProduct(String name) {
        switch (name) {
            case "Milk":
                updateListAndUpdateUI(product1);
                break;
            case "Eggs":
                updateListAndUpdateUI(product2);
                break;
            case "Bread":
                updateListAndUpdateUI(product3);
                break;
            case "Yogurt":
                updateListAndUpdateUI(product4);
                break;
            case "Salmon":
                updateListAndUpdateUI(product5);
                break;
        }
    }

    private void updateListAndUpdateUI(Product product) {
        cashier.addProduct(product);
        productListProperty.set(FXCollections.observableArrayList(cashier.getOrder()));
        quantityListProperty.set(FXCollections.observableArrayList(cashier.getQuantity()));
        updatePrice();
    }

    private void updatePrice() {
        cashier.calcTotal();
        subtotal.setText("Subtotal : " + decimalFormat(cashier.getSubtotal()));
        tax.setText("Tax : " + decimalFormat(cashier.getTax()));
        total.setText("Total : " + decimalFormat(cashier.getTotal()));
    }

    private String decimalFormat(double number) {
        return String.format("%.2f", number);
    }

    @FXML
    public void deleteProduct(ActionEvent event) {
        if (orderList.getSelectionModel().getSelectedItem() == null) {
            AlertBox.showAlertBox(AlertType.ERROR,
                    AlertMessages.ORDER_STATUS_TITLE.getMessage(),
                    AlertMessages.ORDER_STATUS_NO_SELECTION_DESC.getMessage());
        } else {
            Product item = (Product) orderList.getSelectionModel().getSelectedItem();
            cashier.deleteProduct(item);
            productListProperty.set(FXCollections.observableArrayList(cashier.getOrder()));
            quantityListProperty.set(FXCollections.observableArrayList(cashier.getQuantity()));
            updatePrice();
        }
    }

    @FXML
    public void goToProductList() {
        try {
            new UIFactory().createComponent("Product List");
        } catch (IOException e) {
            System.out.println("couldn't load product list page");
        }
    }

    @FXML
    public void confirmOrder() throws IOException {
        if (cashier.getOrderSize() > 0) {
            System.out.println("confirm!!!");
            int newId = orderHis.retrieveOrderId() + 1;
            cashier.setOrderId(newId);
            orderHis.writeOrderHistory(cashier);
            AlertBox.showAlertBox(AlertType.CONFIRMATION,
                    AlertMessages.ORDER_STATUS_TITLE.getMessage(),
                    AlertMessages.ORDER_STATUS_SUCCESS_DESC.getMessage());
            reset();
        } else {
            AlertBox.showAlertBox(AlertType.ERROR,
                    AlertMessages.ORDER_STATUS_TITLE.getMessage(),
                    AlertMessages.ORDER_STATUS_EMPTY_DESC.getMessage());
        }
    }

    public void resetCart() {
        Alert confirmationBox = showConfirmationBox(AlertType.CONFIRMATION,
                AlertMessages.CART_EMPTY_TITLE.getMessage(),
                AlertMessages.CART_EMPTY_DESC.getMessage());
        Optional<ButtonType> result = confirmationBox.showAndWait();
        if (result.get() == ButtonType.OK) {
            reset();
        }
    }

    private void reset() {
        cashier = new Cashier();
        orderList.getItems().clear();
        quantityList.getItems().clear();
        updatePrice();
    }

    private Alert showConfirmationBox(AlertType alertType, String title, String content) {
        Alert confirmationBox = new Alert(alertType);
        confirmationBox.setTitle(title);
        confirmationBox.setContentText(content);
        return confirmationBox;
    }

    @FXML
    public void exitCashier() {
        Platform.exit();
    }
}
