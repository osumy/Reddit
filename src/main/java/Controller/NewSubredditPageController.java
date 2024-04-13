package Controller;

import MainApp.Main;
import Model.Account;
import Model.Subreddit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.sql.SQLException;

public class NewSubredditPageController {
    @FXML
    private Pane pane;

    public void newSubreddit(ActionEvent actionEvent) throws SQLException {
        Subreddit subreddit = new Subreddit(((TextField)pane.getChildren().get(1)).getText(), ((TextArea)pane.getChildren().get(2)).getText(), Account.myAccount.getID());
        subreddit.newSubreddit();
        Main.newSubredditStage.close();
    }
}
