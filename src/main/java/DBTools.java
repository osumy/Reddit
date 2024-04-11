import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class DBTools {
    public static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/redditDB.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DBTools() throws SQLException {}

    public static void fillList(ArrayList<String> list, String table, String column) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + table);

        while (resultSet.next()){
            list.add(resultSet.getString(column));
        }
    }
    public static boolean isAmong(String data, List<String> list){
        for (String s : list)
            if (s.equals(data))
                return true;
        return false;
    }
    public static ArrayList<String> splitID(String ID){
        List<String> IDList;
        if (ID != null)
            IDList = Pattern.compile(",").splitAsStream(ID).toList();
        else
            IDList = new ArrayList<>();
        return new ArrayList<>(IDList);
    }
    public static ArrayList<UUID> splitID_UUID(String ID){
        List<String> IDList;
        if (ID != null)
            IDList = Pattern.compile(",").splitAsStream(ID).toList();
        else
            IDList = new ArrayList<>();
        ArrayList<UUID> IDArrayList = new ArrayList<>();
        for (String s : IDList)
            IDArrayList.add(UUID.fromString(s));
        return IDArrayList;
    }
    // false to check isUnique
    public static boolean exist(String data, String table, String column, boolean isExist) throws SQLException {
        ArrayList<String> dataList = new ArrayList<>();
        fillList(dataList, table, column);

        if (column.equals("password"))
            data = DigestUtils.sha256Hex(data);

        for (String d : dataList)
            if (d.equals(data))
                return isExist;

        return !isExist;
    }
    public static void updateCell(String data, String table, String id, String column) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("UPDATE " + table + " SET " + column + "='" + data + "' WHERE ID='" + id + "'");
    }
    public static String readCell(String table, String id, String column) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + table);
        while (resultSet.next()){
            if (resultSet.getString(1).equals(id))
                return resultSet.getString(column);
        }
        return null;
    }
    public static void insertUser(UUID id, String username, String email, String password) throws SQLException {
        String sql = "INSERT INTO users (ID, username, email, password) VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.setString (1, id.toString());
        preparedStmt.setString (2, username);
        preparedStmt.setString (3, email);
        preparedStmt.setString (4, password);
        preparedStmt.execute();
    }
    public static void insertSubreddit(UUID id, String title, String description, UUID mainAdminID) throws SQLException {
        String sql = "INSERT INTO subreddits (ID, title, description, membersID, mainAdminID, adminsID)" + " VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.setString (1, id.toString());
        preparedStmt.setString (2, title);
        preparedStmt.setString (3, description);
        preparedStmt.setString (4, mainAdminID.toString());
        preparedStmt.setString (5, mainAdminID.toString());
        preparedStmt.setString (6, mainAdminID.toString());
        preparedStmt.execute();
    }
    public static void insertPost(UUID id, String username, String email, String password) throws SQLException {
        String sql = "INSERT INTO users (ID, username, email, password)" + " VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.setString (1, id.toString());
        preparedStmt.setString (2, username);
        preparedStmt.setString (3, email);
        preparedStmt.setString (4, password);
        preparedStmt.execute();
    }
    public static void insertComment(UUID id, String username, String email, String password) throws SQLException {
        String sql = "INSERT INTO users (ID, username, email, password)" + " VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.setString (1, id.toString());
        preparedStmt.setString (2, username);
        preparedStmt.setString (3, email);
        preparedStmt.setString (4, password);
        preparedStmt.execute();
    }
    public static void deleteRow(String table, String ID) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM '" + table + "' WHERE ID='" + ID + "'");
    }
}
