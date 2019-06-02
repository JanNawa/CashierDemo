package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.Validator.PasswordValidator;

/**
 * FXML Controller class
 *
 * @author Jan
 */
public class SignupController implements Initializable {
    
    private final String CORRECT_ICON_LOCATION = "/images/correct.png";
    private final String WRONG_ICON_LOCATION = "/images/wrong.png";

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField passwordConfirm;

    @FXML
    private ImageView lengthIcon;

    @FXML
    private ImageView specialCharIcon;

    @FXML
    private ImageView upperLowercaseIcon;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        password.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Image correctImg = new Image(CORRECT_ICON_LOCATION);
                Image wrongImg = new Image(WRONG_ICON_LOCATION);
                PasswordValidator passwordValidator = new PasswordValidator();
                if (passwordValidator.checkLength(newValue)) {
                    changeIcon(lengthIcon, correctImg, 30, 35);
                } else {
                    changeIcon(lengthIcon, wrongImg, 25, 25);
                }
                if (passwordValidator.checkSpecialCharacter(newValue)) {
                    changeIcon(specialCharIcon, correctImg, 30, 35);
                } else {
                    changeIcon(specialCharIcon, wrongImg, 25, 25);
                }
                if (passwordValidator.checkUpperAndLowercase(newValue)) {
                    changeIcon(upperLowercaseIcon, correctImg, 30, 35);
                } else {
                    changeIcon(upperLowercaseIcon, wrongImg, 25, 25);
                }
            }
        });
    }
    
    private void changeIcon(ImageView imageView, Image image, int width, int height){
        imageView.setImage(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }

    public boolean isValidUsername() {
        String userInput = username.getText();
        return userInput.equals("username");
    }

    public boolean isSamePassword() {
        String passwordInput1 = password.getText();
        String passwordInput2 = passwordConfirm.getText();
        return passwordInput1.equals(passwordInput2);
    }

    public boolean isUsernameNullOrEmpty() {
        String userInput = username.getText().trim();
        return userInput == null || userInput.isEmpty();
    }

    public boolean isPasswordNullOrEmpty() {
        String passwordInput = password.getText().trim();
        return passwordInput == null || passwordInput.isEmpty();
    }

    public boolean isPasswordConfirmNullOrEmpty() {
        String passwordConfirmInput = passwordConfirm.getText().trim();
        return passwordConfirmInput == null || passwordConfirmInput.isEmpty();
    }
}
