import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        /*Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/redditDB.db");
        Statement statement = connection.createStatement();

        connection.close();*/

//        Reading...
//        ResultSet resultSet = statement.executeQuery("SELECT * FROM subreddits");
//        while (resultSet.next()){
//            System.out.println(resultSet.getString(1));
//            System.out.println(resultSet.getString(2));
//        }

//        Inserting...
//        String sql = " insert into subreddits (title)"
//                + " values (?)";
//        PreparedStatement preparedStmt = connection.prepareStatement(sql);
//        preparedStmt.setString (1, "aa");
//        preparedStmt.execute();


//        Delete...
//        statement.executeUpdate("DELETE FROM subreddits WHERE title='abc' AND a=0");

//        Updating...
//        statement.executeUpdate("UPDATE subreddits SET title='abc' WHERE title='aa'"); //WHERE ... AND ...


    }
}
