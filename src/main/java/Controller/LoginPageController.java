package Controller;

import MainApp.Main;
import MainApp.SignUp;
import Model.Account;
import Model.DBTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.sql.SQLException;

public class LoginPageController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Label errorLabel;

    public void newAccount(ActionEvent actionEvent) throws IOException {
        SignUp.newAccount();
    }

    public void login(ActionEvent actionEvent) throws SQLException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        boolean isValidUsername = Account.isValid(username, "username") || Account.isValid(username, "email");
        if (isValidUsername){
            if (Account.isValid(password, "password")){
                boolean isUsername = Account.isValid(username, "username");

                if(DBTools.exist(username, "users", "username", true) && DBTools.exist(password, "users", "password", true)){
                    Account.login(username, isUsername);
                    System.out.println("SS");
                }
                else {
                    errorLabel.setText("Username or Password is not Correct!");
                }
            }
            else {
                errorLabel.setText("Password is not valid!");
            }
        }
        else {
            errorLabel.setText("Username or Email is not valid!");
        }
    }
}
