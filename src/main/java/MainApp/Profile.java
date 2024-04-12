package MainApp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class Profile {

    public static void showProfile(Stage prevStage, UUID id, boolean editable) throws IOException {
        Main.prevStage = prevStage;
        Main.profileID = id;
        Main.isEditableProfile = editable;
        Main.profileStage = new Stage();
        Scene profileScene = new Scene(FXMLLoader.load(Objects.requireNonNull(Profile.class.getResource("View/ProfilePage.fxml"))));
        Main.profileStage.setScene(profileScene);
        Main.profileStage.centerOnScreen();
        Main.profileStage.setResizable(false);
        Main.profileStage.show();
        Main.prevStage.hide();
    }

    public static void hideProfile(){
        Main.prevStage.show();
        Main.profileStage.close();
    }

}
