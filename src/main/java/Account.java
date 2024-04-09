import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Pattern;

public class Account {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private int karma;
    private String dispName;
    private String bio; // About you
    private ArrayList<Comment> comments;
    private ArrayList<Post> posts;
    private ArrayList<UUID> subreddits;

    public Account(String username, String email, String password){
        id = UUID.randomUUID();
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public static boolean isValid(String data, String type){
        if (type.equals("username"))
            return Pattern.matches("^[\\w][\\w_.]{5,20}", data); // Minimum 5 and Maximum 20 characters, does not start with a . or _
        else if (type.equals("email"))
            return Pattern.matches("^[a-zA-Z][\\w]+@([\\w-]+\\.)+[\\w-]{2,4}", data);
        else if (type.equals("password"))
            return Pattern.matches("\"^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$\"\n", data); // Minimum eight characters, at least one letter and one number:
        return false;
    }
    public static boolean isUnique(String data, String table, String column) throws SQLException {
        ArrayList<String> dataList = new ArrayList<>();
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/redditDB.db");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + table);

        while (resultSet.next()){
            dataList.add(resultSet.getString(column));
        }

        for (String d : dataList)
            if (d.equals(data)){
                connection.close();
                return false;
            }

        connection.close();
        return true;
    }
    public void signUp() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/redditDB.db");
        String sql = " INSERT INTO users (ID, username, email, password)" + " VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.setString (1, id.toString());
        preparedStmt.setString (2, username);
        preparedStmt.setString (3, email);
        preparedStmt.setString (4, password);
        preparedStmt.execute();

        connection.close();
    }
}
