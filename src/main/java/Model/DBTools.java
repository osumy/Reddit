package Model;

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
    public static void insertIDtoIDListCell(String table, UUID rowID, String column, UUID id) throws SQLException {
        String newIDList = "";
        String DBIDList = DBTools.readCell(table, rowID.toString(), column);
        if (DBIDList == null || DBIDList.isEmpty())
            newIDList = id.toString();
        else
            newIDList += DBIDList + "," + id.toString();
        DBTools.updateCell(newIDList, table, rowID.toString(), column);
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
        String sql = "INSERT INTO users (ID, username, email, password, karma) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.setString (1, id.toString());
        preparedStmt.setString (2, username);
        preparedStmt.setString (3, email);
        preparedStmt.setString (4, password);
        preparedStmt.setInt (5, 0);
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
    public static void insertPost(UUID id, UUID subredditID, UUID ownerID, String title, String text, String dateTime, String tags) throws SQLException {
        String sql = "INSERT INTO posts (ID, subredditID, ownerID, title, text, dateTime, tags, karma)" + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.setString (1, id.toString());
        preparedStmt.setString (2, subredditID.toString());
        preparedStmt.setString (3, ownerID.toString());
        preparedStmt.setString (4, title);
        preparedStmt.setString (5, text);
        preparedStmt.setString (6, dateTime);
        preparedStmt.setString (7, tags);
        preparedStmt.setInt (8, 0);
        preparedStmt.execute();
    }
    public static void insertComment(UUID id, UUID subredditID, UUID postID, UUID ownerID, String text, String dateTime, String tags, boolean isReply, UUID replyOnID) throws SQLException {
        if (!isReply) {
            String sql = "INSERT INTO comments (ID, subredditID, postID, ownerID, text, dateTime, tags, karma, isReply)" + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString(1, id.toString());
            preparedStmt.setString(2, subredditID.toString());
            preparedStmt.setString(3, postID.toString());
            preparedStmt.setString(4, ownerID.toString());
            preparedStmt.setString(5, text);
            preparedStmt.setString(6, dateTime);
            preparedStmt.setString(7, tags);
            preparedStmt.setInt(8, 0);
            preparedStmt.setInt(9, 0);
            preparedStmt.execute();
        }
        else {
            String sql = "INSERT INTO comments (ID, subredditID, postID, ownerID, text, dateTime, tags, karma, isReply, replyOnID)" + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString(1, id.toString());
            preparedStmt.setString(2, subredditID.toString());
            preparedStmt.setString(3, postID.toString());
            preparedStmt.setString(4, ownerID.toString());
            preparedStmt.setString(5, text);
            preparedStmt.setString(6, dateTime);
            preparedStmt.setString(7, tags);
            preparedStmt.setInt(8, 0);
            preparedStmt.setInt(9, 1);
            preparedStmt.setString(7, replyOnID.toString());
            preparedStmt.execute();
        }
    }
    public static void deleteRow(String table, String ID) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM '" + table + "' WHERE ID='" + ID + "'");
    }
}
