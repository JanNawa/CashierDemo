package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.*;

/**
 * FXML Controller class
 *
 * @author Jan
 */
public class LoginController implements Initializable {
    
    @FXML
    private TextField username;
    
    @FXML
    private PasswordField password;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // do sth
    }
    
    public boolean authenticateAccount(){
        String userInput = username.getText().trim();
        String passwordInput = password.getText().trim();
        return authenticateUser(userInput) && authenticatePassword(passwordInput);
    }
    
    private boolean authenticateUser(String username){
        return "username".equals(username);
    }
    
    private boolean authenticatePassword(String password){
        return "password".equals(password);
    }   
}
