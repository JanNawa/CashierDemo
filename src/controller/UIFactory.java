package controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

/**
 *
 * @author Jan
 */
public class UIFactory {
    public void createComponent(String title) throws IOException{
        if(title.equals("Product List")){
            loadComponent(title, "/ui/ProductList.fxml");
        } else if(title.equals("Order History")){
            loadComponent(title, "/ui/staff.fxml");
        }
    }
    
    private void loadComponent(String title, String sourcePath) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(sourcePath));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 750, 500));
        stage.show();
    }
}
