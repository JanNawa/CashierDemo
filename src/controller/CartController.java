package controller;

import datamodel.Cashier;
import datamodel.OrderHistory;
import datamodel.Product;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import utils.AlertBox;
import utils.AlertMessages;

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

        private AdjustQuantity() {
            quantityLabel.setPadding(new Insets(1));

            hbox.getChildren().addAll(minusButton, quantityLabel, addButton);
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
                        }
                    }
                });

                addButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int newQuantity = item + 1;
                        quantityLabel.setText("" + newQuantity);
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
            showAlertBox(AlertType.ERROR,
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/ProductList.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Product List");
            stage.setScene(new Scene(root, 750, 500));
            stage.show();
        } catch (IOException e) {
            System.out.println("couldn't load product list page");
            e.printStackTrace();
        }
    }

    @FXML
    public void signup() {
        Object[] signupElements = initDialog("Sign Up", "/ui/signup.fxml");
        Dialog<ButtonType> signupDialog = (Dialog<ButtonType>) signupElements[0];
        FXMLLoader fxmlLoader = (FXMLLoader) signupElements[1];

        //wait for user to press button
        Optional<ButtonType> result = signupDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            SignupController controller = fxmlLoader.getController();
            if (controller.isUsernameNullOrEmpty()
                    || controller.isPasswordNullOrEmpty()
                    || controller.isPasswordConfirmNullOrEmpty()) {
                showAlertBox(AlertType.ERROR,
                        AlertMessages.SIGNUP_EMPTY_FIELD_TITLE.getMessage(),
                        AlertMessages.SIGNUP_EMPTY_FIELD_DESC.getMessage());

            } else if (!controller.isValidUsername() && controller.isSamePassword()) {
                goToHistory();
            } else {
                if (controller.isValidUsername()) {
                    showAlertBox(AlertType.ERROR,
                            AlertMessages.SIGNUP_INVALID_USERNAME_TITLE.getMessage(),
                            AlertMessages.SIGNUP_INVALID_USERNAME_DESC.getMessage());
                } else if (!controller.isSamePassword()) {
                    showAlertBox(AlertType.ERROR,
                            AlertMessages.SIGNUP_DIFFERENT_PASSWORD_TITLE.getMessage(),
                            AlertMessages.SIGNUP_DIFFERENT_PASSWORD_DESC.getMessage());
                }
            }
        }
    }

    @FXML
    public void login() {
        Object[] loginElements = initDialog("Login", "/ui/login.fxml");
        Dialog<ButtonType> loginDialog = (Dialog<ButtonType>) loginElements[0];
        FXMLLoader fxmlLoader = (FXMLLoader) loginElements[1];

        //wait for user to press button
        Optional<ButtonType> result = loginDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            LoginController controller = fxmlLoader.getController();
            if (controller.authenticateAccount()) {
                goToHistory();
            } else {
                showAlertBox(AlertType.ERROR,
                        AlertMessages.LOGIN_INVALID_TITLE.getMessage(),
                        AlertMessages.LOGIN_INVALID_DESC.getMessage());
            }
        }
    }

    private Object[] initDialog(String title, String resource) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(cartBorderPane.getScene().getWindow());
        dialog.setTitle(title);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(resource));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("couldn't load " + title + " page");
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        return new Object[]{dialog, fxmlLoader};
    }

    @FXML
    public void goToHistory() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/staff.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Order History");
            stage.setScene(new Scene(root, 750, 500));
            stage.show();
        } catch (IOException e) {
            System.out.println("couldn't load history page");
        }
    }

    @FXML
    public void confirmOrder() throws IOException {
        if (cashier.getOrderSize() > 0) {
            System.out.println("confirm!!!");
            int newId = orderHis.retrieveOrderId() + 1;
            cashier.setOrderId(newId);
            orderHis.writeOrderHistory(cashier);
            showAlertBox(AlertType.CONFIRMATION,
                    AlertMessages.ORDER_STATUS_TITLE.getMessage(),
                    AlertMessages.ORDER_STATUS_SUCCESS_DESC.getMessage());
            reset();
        } else {
            showAlertBox(AlertType.ERROR,
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
        updatePrice();
    }

    private void showAlertBox(AlertType alertType, String title, String content) {
        AlertBox alertBox = new AlertBox();
        alertBox.showAlertBox(alertType, title, content);
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
