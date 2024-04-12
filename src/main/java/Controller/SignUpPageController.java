package Controller;

import MainApp.SignUpSignIn;
import Model.Account;
import Model.DBTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpPageController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private Label errorLabel;
    @FXML
    private Label errorLabel1;

    public void newAccount(ActionEvent actionEvent) throws SQLException, IOException {
        String username = usernameTextField.getText();
        String email = emailTextField.getText();
        String password = passwordPasswordField.getText();

        if (Account.isValid(username, "username")){
            if (Account.isValid(email, "email")){
                if (Account.isValid(password, "password")){
                    if (DBTools.exist(username, "users", "username", false) && DBTools.exist(email, "users", "email", false)) {
                        Account account = new Account(username, email, password);
                        account.signUp();
                        SignUpSignIn.goBackLogin();
                    }
                    else {
                        errorLabel.setText("Username or Email is already taken!");
                    }
                }
                else {
                    errorLabel.setText("Password is not valid!");
                    errorLabel1.setText("Minimum eight characters, at least one upper case and one lower case, one number and one special character");
                }
            }
            else {
                errorLabel.setText("Email is not valid!");
            }
        }
        else {
            errorLabel.setText("Username is not valid!");
            errorLabel1.setText("Minimum 5 and Maximum 20 characters, does not start with a . or _");
        }
    }

    public void goBackLogin(ActionEvent actionEvent) throws IOException {
        SignUpSignIn.goBackLogin();
    }
}
