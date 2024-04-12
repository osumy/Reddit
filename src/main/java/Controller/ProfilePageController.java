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

import java.sql.*;
import java.util.ArrayList;

public class ProfilePageController {
    @FXML
    private Button displaynameEditButton;
    @FXML
    private Button emailEditButton;
    @FXML
    private Button passwordEditButton;
    @FXML
    private Button bioEditButton;
    @FXML
    private TextArea bioTextArea;
    @FXML
    private Label karmaLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label dispalynameLabel;
    @FXML
    private Label emailLabel;



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

    }

    public void displaynameUpdateClick(ActionEvent actionEvent) {
    }

    public void editBioClicked(ActionEvent actionEvent) throws SQLException {
        try {

            DBTools.updateCell(bioTextArea.getText(), "users", "a17893b4-42c0-40bf-9e78-6076614d5b82", "bio");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //Account.updateBio(bioTextArea.getText());
        //DBTools.updateCell(bioTextArea.getText(), "users", Main.profileID.toString(), "bio");
    }
}
