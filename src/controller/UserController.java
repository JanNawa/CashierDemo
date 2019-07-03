package controller;

import datamodel.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.fxml.*;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import utils.*;

/**
 * FXML Controller class
 *
 * @author Jan, 2019
 */
public class UserController implements Initializable {

    Product product1, product2, product3, product4, product5;
    Cashier cashier = new Cashier();
    OrderHistory orderHis = new OrderHistory();
    ListProperty<Product> productListProperty;
    ListProperty<Integer> quantityListProperty;

    @FXML
    private BorderPane userBorderPane;

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
        product4 = new Product(104, 111, "Yogurt", "Natural Yogurt 750 g", 2.89);
        product5 = new Product(105, 111, "Salmon", "Atlantic Salmon Portion 300 g", 11.99);

        product1Button.setText(product1.getName());
        product2Button.setText(product2.getName());
        product3Button.setText(product3.getName());
        product4Button.setText(product4.getName());
        product5Button.setText(product5.getName());

        productListProperty = new SimpleListProperty<>();
        productListProperty.set(FXCollections.observableArrayList(cashier.getOrder()));
        orderList.itemsProperty().bind(productListProperty);
        
        quantityListProperty = new SimpleListProperty<>();
        quantityListProperty.set(FXCollections.observableArrayList(cashier.getQuantity()));
        quantityList.itemsProperty().bind(quantityListProperty);
        quantityList.setPrefWidth(75);

        updatePrice();
    }

    @FXML
    public void addProduct(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String name = btn.getText();

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
                AlertBox.showAlertBox(AlertType.ERROR,
                        AlertMessages.SIGNUP_EMPTY_FIELD_TITLE.getMessage(),
                        AlertMessages.SIGNUP_EMPTY_FIELD_DESC.getMessage());

            } else if (!controller.isValidUsername() && controller.isSamePassword()) {
                goToHistory();
            } else {
                if (controller.isValidUsername()) {
                    AlertBox.showAlertBox(AlertType.ERROR,
                            AlertMessages.SIGNUP_INVALID_USERNAME_TITLE.getMessage(),
                            AlertMessages.SIGNUP_INVALID_USERNAME_DESC.getMessage());
                } else if (!controller.isSamePassword()) {
                    AlertBox.showAlertBox(AlertType.ERROR,
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
                AlertBox.showAlertBox(AlertType.ERROR,
                        AlertMessages.LOGIN_INVALID_TITLE.getMessage(),
                        AlertMessages.LOGIN_INVALID_DESC.getMessage());
            }
        }
    }

    private Object[] initDialog(String title, String resource) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(userBorderPane.getScene().getWindow());
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
            new UIFactory().createComponent("Order History");
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

    private void reset() {
        cashier = new Cashier();
        orderList.getItems().clear();
        updatePrice();
    }

    @FXML
    public void exitCashier() {
        Platform.exit();
    }
}
