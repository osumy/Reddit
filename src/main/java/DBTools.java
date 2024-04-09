import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class DBTools {
    public static void fillList(ArrayList<String> list, String table, String column) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/redditDB.db");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + table);

        while (resultSet.next()){
            list.add(resultSet.getString(column));
        }

        connection.close();
    }
    public static void updateCell(String data, String table, String id, String column) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/redditDB.db");
        Statement statement = connection.createStatement();
        statement.executeUpdate("UPDATE " + table + " SET " + column + "='" + data + "' WHERE ID='" + id + "'");
        connection.close();
    }
    public static void insertUser(UUID id, String username, String email, String password) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/redditDB.db");
        String sql = "INSERT INTO users (ID, username, email, password)" + " VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.setString (1, id.toString());
        preparedStmt.setString (2, username);
        preparedStmt.setString (3, email);
        preparedStmt.setString (4, password);
        preparedStmt.execute();

        connection.close();
    }
    public static void insertSubreddit(UUID id, String username, String email, String password) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/redditDB.db");
        String sql = "INSERT INTO users (ID, username, email, password)" + " VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.setString (1, id.toString());
        preparedStmt.setString (2, username);
        preparedStmt.setString (3, email);
        preparedStmt.setString (4, password);
        preparedStmt.execute();

        connection.close();
    }
    public static void insertPost(UUID id, String username, String email, String password) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/redditDB.db");
        String sql = "INSERT INTO users (ID, username, email, password)" + " VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.setString (1, id.toString());
        preparedStmt.setString (2, username);
        preparedStmt.setString (3, email);
        preparedStmt.setString (4, password);
        preparedStmt.execute();

        connection.close();
    }
    public static void insertComment(UUID id, String username, String email, String password) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/redditDB.db");
        String sql = "INSERT INTO users (ID, username, email, password)" + " VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.setString (1, id.toString());
        preparedStmt.setString (2, username);
        preparedStmt.setString (3, email);
        preparedStmt.setString (4, password);
        preparedStmt.execute();

        connection.close();
    }
}
