package Controller;

import MainApp.SignUpSignIn;
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
import java.util.ArrayList;

public class LoginPageController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private Label errorLabel1;
    @FXML
    private Label errorLabel11;

    public void newAccount(ActionEvent actionEvent) throws IOException {
        SignUpSignIn.newAccount();
    }

    public void login(ActionEvent actionEvent) throws SQLException, IOException {
        usernameTextField.setText("AmirAli");
        passwordTextField.setText("Artin181818.");

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        boolean isValidUsername = Account.isValid(username, "username") || Account.isValid(username, "email");
        if (isValidUsername){
            if (Account.isValid(password, "password")){
                boolean isUsername = Account.isValid(username, "username");
                String ID = Account.usernameToID(username);

                if(DBTools.exist(username, "users", "username", true) && DBTools.readCell("users", ID, "password").equals(DigestUtils.sha256Hex(password))){
                    Account.login(username, isUsername);
                    SignUpSignIn.goToMainPage();
                }
                else {
                    errorLabel.setText("Username or Password is not Correct!");
                    errorLabel1.setText("");
                    errorLabel11.setText("");
                }
            }
            else {
                errorLabel.setText("");
                errorLabel1.setText("");
                errorLabel11.setText("Invalid");
            }
        }
        else {
            errorLabel.setText("");
            errorLabel1.setText("Invalid");
            errorLabel11.setText("");
        }
    }
}
