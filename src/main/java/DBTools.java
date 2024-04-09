import java.sql.*;
import java.util.ArrayList;

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
}
