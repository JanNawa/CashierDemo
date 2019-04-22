package controller;

import datamodel.OrderHistory;
import datamodel.Product;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Nawaphan Chayopathum(Jan)
 */
public class StaffController implements Initializable {
    Product product1, product2, product3, product4, product5;
    OrderHistory orderHis = new OrderHistory();

    @FXML
    private ListView historyList; 
    
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
    private DatePicker datePicker;

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
        
        Callback<DatePicker, DateCell> dayCellFactory= this.getDayCellFactory();
        datePicker.setDayCellFactory(dayCellFactory);
    }

    //Invalidate the date in the future
    private Callback<DatePicker, DateCell> getDayCellFactory() {
 
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
 
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
 
                        // Disable date in the future
                        if (item.isAfter(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #F5F5F5;");
                        }
                    }
                };
            }
        };
        return dayCellFactory;
    }
    
    @FXML
    public void filterProduct(ActionEvent event) throws IOException{
        historyList.getItems().clear();
        Button btn = (Button) event.getSource();
        String name = btn.getText();
        String[] seperateOrder = orderHis.readOrderHistory();
        for(String str : seperateOrder){
            if(str.contains(name)){
                historyList.getItems().add(str);
            }     
        }
    }
    
    @FXML
    public void viewAllHistory() throws IOException{
        historyList.getItems().clear();
        String[] seperateOrder = orderHis.readOrderHistory();
        for(String str : seperateOrder){
            historyList.getItems().add(str);
        }
    }
    
    @FXML
    public void filterDateOrder() throws IOException{
        historyList.getItems().clear();
        String date = datePicker.getValue().toString();
        String[] seperateOrder = orderHis.readOrderHistory();
        int count = 0;
        for (String str : seperateOrder) {
            if(str.contains(date)){
                historyList.getItems().add(str);
                count++;
            }
        }
        if(count == 0){
            showAlertBox(AlertType.INFORMATION, "Filter Date Status", "No order on selected date");
        }
    }
    
    private void showAlertBox(Alert.AlertType alertType, String title, String content){
        Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
    }
}