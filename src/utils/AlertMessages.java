package utils;

/**
 *
 * @author Jan
 */
public enum AlertMessages {
    ORDER_STATUS_TITLE("Order Status"),
    ORDER_STATUS_SUCCESS_DESC("Your order is successfully confirmed!"),
    ORDER_STATUS_NO_SELECTION_DESC("Please select product to delete product."),
    ORDER_STATUS_EMPTY_DESC("Your order is successfully confirmed!"),
    
    CART_EMPTY_TITLE("Empty the Cart"),
    CART_EMPTY_DESC("Are you sure to empty the cart?"),
    
    SIGNUP_EMPTY_FIELD_TITLE("Sign Up Error"),
    SIGNUP_EMPTY_FIELD_DESC("All fields are required."),
    
    SIGNUP_INVALID_USERNAME_TITLE("Invalid Username"),
    SIGNUP_INVALID_USERNAME_DESC("This username has already used. Try Again!"),
    
    SIGNUP_DIFFERENT_PASSWORD_TITLE("Different Passwords"),
    SIGNUP_DIFFERENT_PASSWORD_DESC("The passwords need to be the same. Try Again!"),
    
    LOGIN_INVALID_TITLE("Invalid Account"),
    LOGIN_INVALID_DESC("The username or password is invalid."),
    
    FILTER_DATE_TITLE("Filter Date Status"),
    FILTER_DATE_DESC("No order on selected date.")
    ;
    
    private final String message;

    private AlertMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
