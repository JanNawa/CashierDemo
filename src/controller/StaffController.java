package controller;

import datamodel.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.util.Callback;
import utils.*;

/**
 * FXML Controller class
 *
 * @author Jan, 2019
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
            AlertBox.showAlertBox(AlertType.INFORMATION, 
                    AlertMessages.FILTER_DATE_TITLE.getMessage(), 
                    AlertMessages.FILTER_DATE_DESC.getMessage());
        }
    }
}
