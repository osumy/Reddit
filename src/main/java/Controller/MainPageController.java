package Controller;

import MainApp.Main;
import MainApp.Profile;
import MainApp.SignUpSignIn;
import Model.Account;
import Model.Search;
import Model.Subreddit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

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
    public Button createSubredditButton;
    @FXML
    public ImageView logo;


    @FXML
    public void initialize() throws FileNotFoundException {
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

        Path path = Paths.get("src/main/resources/logo.png");
        String filePath = path.toAbsolutePath().toString();
        logo.setImage(new Image(new FileInputStream(filePath)));

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
        Profile.showProfile(Main.mainStage, Account.myAccount.getID(), true);
    }

    public void createSubreddit(ActionEvent actionEvent) throws IOException, InterruptedException {
        Stage newSubredditStage = new Stage();
        Main.newSubredditStage = newSubredditStage;
        newSubredditStage.setResizable(false);
        newSubredditStage.centerOnScreen();
        newSubredditStage.initOwner(Main.mainStage);
        newSubredditStage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(Profile.class.getResource("View/NewSubredditPage.fxml")))));
        newSubredditStage.setAlwaysOnTop(true);
        newSubredditStage.show();
    }

}
