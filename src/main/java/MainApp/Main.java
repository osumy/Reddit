package MainApp;

import Model.DBTools;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.*;
import java.util.Objects;

public class Main extends Application {
    public static Stage loginStage;
    @Override
    public void start(Stage stage) throws Exception{
        loginStage = new Stage(StageStyle.UTILITY);
        Scene loginScene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("View/LoginPage.fxml"))));
        loginStage.setScene(loginScene);
        loginStage.centerOnScreen();
        loginStage.show();
    }


    public static void main(String[] args) throws SQLException {
        launch(args);


        //        Scanner sc = new Scanner(System.in);
//        String username = sc.next();


//        String username = sc.next();
//        //String email = sc.next();
//        String password = sc.next();

//        Model.Account account = Model.Account.myAccount;
//
////        if (Application.Account.isValid(username, "username") && Model.Account.isValid(email, "email") && Application.Account.isValid(password, "password"))
////            if (Model.Account.exist(username, "users", "username", true) || Model.Account.exist(email, "users", "email", true))
//                Model.Account.login(username, true);
//        Application.Account account = Application.Account.myAccount;
//        int a = 3;

//        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/redditDB.db");
//        Statement statement = connection.createStatement();
//
//        connection.close();

//        Reading...
//        ResultSet resultSet = statement.executeQuery("SELECT * FROM subreddits");
//        while (resultSet.next()){
//            System.out.println(resultSet.getString(1));
//            System.out.println(resultSet.getString(2));
//        }

//        Inserting...
//        String sql = "insert into subreddits (title)" + " values (?)";
//        PreparedStatement preparedStmt = connection.prepareStatement(sql);
//        preparedStmt.setString(1, "aa");
//        preparedStmt.execute();


//        Delete...
//        statement.executeUpdate("DELETE FROM subreddits WHERE title='abc' AND a=0");

//        Updating...
//        statement.executeUpdate("UPDATE subreddits SET title='abc' WHERE title='aa'"); //WHERE ... AND ...

        DBTools.connection.close();
    }
}
