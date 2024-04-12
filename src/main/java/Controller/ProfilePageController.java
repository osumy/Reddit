package Controller;

import MainApp.Main;
import MainApp.Profile;
import Model.Account;
import Model.DBTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.ArrayList;

public class ProfilePageController {
    @FXML
    private Label dispalynameLabel;
    @FXML
    private Button displaynameEditButton;
    @FXML
    private TextField displaynameUpdateTextField;
    @FXML
    private Button displaynameUpdateButton;
    @FXML
    private Label emailLabel;
    @FXML
    private Button emailEditButton;
    @FXML
    private TextField emailUpdateTextField;
    @FXML
    private Button emailUpdateButton;
    @FXML
    private Button passwordEditButton;
    @FXML
    private TextField passwordUpdateTextField;
    @FXML
    private Button passwordUpdateButton;
    @FXML
    private Button bioEditButton;
    @FXML
    private TextArea bioTextArea;
    @FXML
    private Label karmaLabel;
    @FXML
    private Label usernameLabel;



    public void goBackClicked(ActionEvent actionEvent) {
        Profile.hideProfile();
    }
    @FXML
    public void initialize() throws SQLException {
        if (!Main.isEditableProfile){
            displaynameEditButton.setVisible(false);
            emailEditButton.setVisible(false);
            passwordEditButton.setVisible(false);
            bioEditButton.setVisible(false);
            bioTextArea.setEditable(false);
        }

        String bio = Model.DBTools.readCell("users", Main.profileID.toString(), "bio");
        if ( bio != null) {
            bioTextArea.setText(bio);
        }

        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/redditDB.db");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
        String karma = "";
        while (resultSet.next()){
            if (resultSet.getString(1).equals(Main.profileID.toString())){
                karma += Integer.toString(resultSet.getInt("karma"));
                connection.close();
                break;
            }
        }
        karmaLabel.setText("Karma: " + karma);

        usernameLabel.setText("u/" + DBTools.readCell("users", Main.profileID.toString(), "username"));
        dispalynameLabel.setText("Display Name: " + DBTools.readCell("users", Main.profileID.toString(), "dispName"));
        emailLabel.setText("Email: " + DBTools.readCell("users", Main.profileID.toString(), "email"));


    }

    public void displaynameEditClick(ActionEvent actionEvent) throws SQLException {
        displaynameUpdateButton.setVisible(true);
        displaynameUpdateTextField.setVisible(true);
    }

    public void displaynameUpdateClick(ActionEvent actionEvent) throws SQLException {
        Account.updateDispName(displaynameUpdateTextField.getText());
    }

    public void editBioClicked(ActionEvent actionEvent) throws SQLException {
        try {
            Account.updateBio(bioTextArea.getText());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void editPasswordClicked(ActionEvent actionEvent) {
        passwordUpdateTextField.setVisible(true);
        passwordUpdateButton.setVisible(true);
    }

    public void updatePasswordClicked(ActionEvent actionEvent) throws SQLException {
        Account.updatePassword(DigestUtils.sha256Hex(passwordUpdateTextField.getText()));
    }

    public void emailEditClicked(ActionEvent actionEvent) {
        emailUpdateTextField.setVisible(true);
        emailUpdateButton.setVisible(true);
    }

    public void emailUpdateClicked(ActionEvent actionEvent) throws SQLException {
        Account.updateEmail(emailUpdateTextField.getText());
    }
}
