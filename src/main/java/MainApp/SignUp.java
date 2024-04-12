package MainApp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class SignUp {
    public static void newAccount() throws IOException {
        Scene signupScene = new Scene(FXMLLoader.load(Objects.requireNonNull(SignUp.class.getResource("View/SignUpPage.fxml"))));
        Main.loginStage.setScene(signupScene);
    }
    public static void goBackLogin() throws IOException {
        Scene signupScene = new Scene(FXMLLoader.load(Objects.requireNonNull(SignUp.class.getResource("View/LoginPage.fxml"))));
        Main.loginStage.setScene(signupScene);
    }
}
