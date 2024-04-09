import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Pattern;
import org.apache.commons.codec.digest.DigestUtils;

public class Account {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private int karma;
    private String dispName;
    private String bio; // About you
    //private ArrayList<Comment> comments;
    //private ArrayList<Post> posts;
    //private ArrayList<UUID> subreddits;
    static Account account;

    public Account(String username, String email, String password){
        id = UUID.randomUUID();
        this.username = username;
        this.email = email;
        this.password = DigestUtils.sha256Hex(password);
    }
    public Account(){}

    private static void setId(UUID id){ account.id = id; }
    private static void setUsername(String username){ account.username = username; }
    private static void setEmail(String email){ account.email = email; }
    private static void setKarma(int karma){ account.karma = karma; }
    private static void setDispName(String dispName){ account.dispName = dispName; }
    private static void setBio(String bio){ account.bio = bio; }

    public static boolean isValid(String data, String type){
        if (type.equals("username"))
            return Pattern.matches("^[\\w][\\w_.]{5,20}", data); // Minimum 5 and Maximum 20 characters, does not start with a . or _
        else if (type.equals("email"))
            return Pattern.matches("^[a-zA-Z][\\w]+@([\\w-]+\\.)+[\\w-]{2,4}", data);
        else if (type.equals("password"))
            return Pattern.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-.]).{8,}", data); //Minimum eight characters, at least one upper case English letter, one lower case English letter, one number and one special character

        return false;
    }
    private static void fillList(ArrayList<String> list, String table, String column) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/redditDB.db");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + table);

        while (resultSet.next()){
            list.add(resultSet.getString(column));
        }

        connection.close();
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
    public void signUp() throws SQLException {
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
    public static void login(String user, boolean isUsername) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/redditDB.db");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

        while (resultSet.next()){
            boolean isOK = false;
            if (isUsername)
                if (resultSet.getString(2).equals(user))
                    isOK = true;
            else
                if (resultSet.getString(3).equals(user))
                    isOK = true;
            if (isOK){
                account = new Account();
                setId(UUID.fromString(resultSet.getString(1)));
                setUsername(resultSet.getString(2));
                setEmail(resultSet.getString(3));
                //setPassword(resultSet.getString(4)); not necessary!
                setKarma(resultSet.getInt(5));
                setDispName(resultSet.getString(6));
                setBio(resultSet.getString(7));
            }
        }

        connection.close();
    }
}
