package controller;

import datamodel.Cashier;
import datamodel.OrderHistory;
import datamodel.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utils.AlertBox;
import utils.AlertMessages;

/**
 * FXML Controller class
 *
 * @author Jan, 2019
 */
public class UserController implements Initializable {

    Product product1, product2, product3, product4, product5;
    Cashier cashier = new Cashier();
    OrderHistory orderHis = new OrderHistory();
    ListProperty<Product> listProperty;

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

        listProperty = new SimpleListProperty<>();
        listProperty.set(FXCollections.observableArrayList(cashier.getOrder()));
        orderList.itemsProperty().bind(listProperty);

        updatePrice();
    }

    @FXML
    public void addProduct(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String name = btn.getText();

        switch (name) {
            case "Milk":
                cashier.addProduct(product1);
                listProperty.set(FXCollections.observableArrayList(cashier.getOrder()));
                updatePrice();
                break;
            case "Eggs":
                cashier.addProduct(product2);
                listProperty.set(FXCollections.observableArrayList(cashier.getOrder()));
                updatePrice();
                break;
            case "Bread":
                cashier.addProduct(product3);
                listProperty.set(FXCollections.observableArrayList(cashier.getOrder()));
                updatePrice();
                break;
            case "Yogurt":
                cashier.addProduct(product4);
                listProperty.set(FXCollections.observableArrayList(cashier.getOrder()));
                updatePrice();
                break;
            case "Salmon":
                cashier.addProduct(product5);
                listProperty.set(FXCollections.observableArrayList(cashier.getOrder()));
                updatePrice();
                break;
        }
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
                    AlertMessages.ORDER_STATUS_TITLE.getMessage() , 
                    AlertMessages.ORDER_STATUS_NO_SELECTION_DESC.getMessage());
        } else {
            Product item = (Product) orderList.getSelectionModel().getSelectedItem();
            cashier.deleteProduct(item);
            listProperty.set(FXCollections.observableArrayList(cashier.getOrder()));
            updatePrice();
        }
    }
    
    @FXML
    public void signup(){
        Object[] signupElements = initDialog("Sign Up", "/ui/signup.fxml");
        Dialog<ButtonType> signupDialog = (Dialog<ButtonType>) signupElements[0];
        FXMLLoader fxmlLoader = (FXMLLoader) signupElements[1];

        //wait for user to press button
        Optional<ButtonType> result = signupDialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            SignupController controller = fxmlLoader.getController();
            if(!controller.isValidUsername() && controller.isSamePassword()){
                goToHistory();
            } else {
                if(controller.isValidUsername()){
                    showAlertBox(AlertType.ERROR, 
                        AlertMessages.SIGNUP_INVALID_USERNAME_TITLE.getMessage(), 
                        AlertMessages.SIGNUP_INVALID_USERNAME_DESC.getMessage());
                } else {
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
        if(result.isPresent() && result.get() == ButtonType.OK) {
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
    
    private Object[] initDialog(String title, String resource){
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
                    AlertMessages.ORDER_STATUS_TITLE.getMessage() , 
                    AlertMessages.ORDER_STATUS_SUCCESS_DESC.getMessage());
            reset();
        } else {
            showAlertBox(AlertType.ERROR, 
                    AlertMessages.ORDER_STATUS_TITLE.getMessage() , 
                    AlertMessages.ORDER_STATUS_EMPTY_DESC.getMessage());
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

    @FXML
    public void exitCashier() {
        Platform.exit();
    }
}
