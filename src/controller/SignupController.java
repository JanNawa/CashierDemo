package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Jan
 */
public class SignupController implements Initializable {
    
    @FXML
    private TextField username;
    
    @FXML
    private PasswordField password;
    
    @FXML
    private PasswordField passwordConfirm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public boolean isValidUsername(){
        String userInput = username.getText();
        return userInput.equals("username");
    }

    public boolean isSamePassword(){
        String passwordInput1 = password.getText();
        String passwordInput2 = passwordConfirm.getText();
        return passwordInput1.equals(passwordInput2);
    }   
}
