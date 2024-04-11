import org.sqlite.core.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    public static void main(String[] args) throws SQLException {

        //        Scanner sc = new Scanner(System.in);
//        String username = sc.next();


//        String username = sc.next();
//        //String email = sc.next();
//        String password = sc.next();

//        Account account = Account.myAccount;
//
////        if (Account.isValid(username, "username") && Account.isValid(email, "email") && Account.isValid(password, "password"))
////            if (Account.exist(username, "users", "username", true) || Account.exist(email, "users", "email", true))
//                Account.login(username, true);
//        Account account = Account.myAccount;
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
