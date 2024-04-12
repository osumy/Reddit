package MainApp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SignUpSignIn {
    public static void newAccount() throws IOException {
        Scene signupScene = new Scene(FXMLLoader.load(Objects.requireNonNull(SignUpSignIn.class.getResource("View/SignUpPage.fxml"))));
        Main.loginStage.setScene(signupScene);
    }
    public static void goBackLogin() throws IOException {
        Scene signupScene = new Scene(FXMLLoader.load(Objects.requireNonNull(SignUpSignIn.class.getResource("View/LoginPage.fxml"))));
        Main.loginStage.setScene(signupScene);
    }
    public static void goToMainPage() throws IOException {
        Main.mainStage = new Stage();
        Scene mainScene = new Scene(FXMLLoader.load(Objects.requireNonNull(SignUpSignIn.class.getResource("View/MainPage.fxml"))));
        Main.mainStage.setScene(mainScene);
        Main.loginStage.close();
        Main.mainStage.centerOnScreen();
        Main.mainStage.setResizable(false);
        Main.mainStage.show();
    }
}
