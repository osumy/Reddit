package Controller;

import MainApp.Main;
import MainApp.Profile;
import MainApp.SignUpSignIn;
import Model.Account;
import Model.Search;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainPageController {
    @FXML
    public ComboBox<String> searchComboBox;
    @FXML
    public TextField searchTextField;
    @FXML
    public ListView<String> searchListView;
    @FXML
    public Button logoutButton;


    @FXML
    public void initialize() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("r/");
        strings.add("u/");
        searchComboBox.setItems(FXCollections.observableArrayList(strings));
        searchComboBox.setValue("r/");

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (searchTextField.getText().isEmpty())
                searchListView.setVisible(false);
            else {
                searchListView.setVisible(true);
                Search.search = searchComboBox.getValue() + searchTextField.getText();
                ObservableList<String> items = null;
                try {
                    items = FXCollections.observableArrayList(FXCollections.observableArrayList(Search.search()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                searchListView.setItems(items);
            }
        });
    }

    public void searchComboBoxAction(ActionEvent actionEvent) {
        //searchTextField.setText(searchComboBox.getValue());
    }

    public void searchIt(InputMethodEvent inputMethodEvent) throws SQLException {
        // Didn't work...
    }

    public void PaneMouseClicked(MouseEvent mouseEvent) {
        searchListView.setVisible(false);
    }

    public void logoutClicked(ActionEvent actionEvent) throws IOException {
        Account.logout();
        SignUpSignIn.goFromMainPageToLoginPage();
    }

    public void profileClicked(ActionEvent actionEvent) throws IOException {
        Profile.showProfile(Main.mainStage, Account.myAccount.getID(), false);
    }
}
