import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {






//        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reddit" , "root" , "");
//        Statement statement = connection.createStatement();


//        Delete...
//        statement.executeUpdate("DELETE FROM subreddits WHERE title='abc' AND a=0");

//        Updating...
//        statement.executeUpdate("UPDATE subreddits SET title='abc' WHERE title='aa'"); //WHERE ... AND ...

          // Writing...
//        String sql = " insert into subreddits (title, a, b, c, d)"
//                + " values (?, ?, ?, ?, ?)";
//        PreparedStatement preparedStmt = connection.prepareStatement(sql);
//        preparedStmt.setString (1, "aa");
//        preparedStmt.setInt(2, 1);
//        preparedStmt.setInt(3, 1);
//        preparedStmt.setInt(4, 1);
//        preparedStmt.setInt(5, 1);
//        preparedStmt.execute();
//        connection.close();

          // Reading...
//        ResultSet resultSet = statement.executeQuery("SELECT * FROM subreddits");
//        while (resultSet.next()){
//            System.out.println(resultSet.getString("title"));
//            System.out.println(resultSet.getString("a"));
//        }
    }
}
