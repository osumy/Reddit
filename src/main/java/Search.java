import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Pattern;

public class Search {
    public static Subreddit subreddit;
    public static Account account;
    public static String search;

    public static ArrayList<String> search() throws SQLException {
        ArrayList<String> searchResult = new ArrayList<>();
        boolean isUser = search.charAt(0) == 'r';
        String searchText = search.substring(2);
        Statement statement = DBTools.connection.createStatement();
        ResultSet resultSet;

        if (isUser){
            resultSet =  statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()){
                if (Pattern.matches("^" + searchText + ".*", resultSet.getString(2)))
                    searchResult.add(resultSet.getString(2));
            }
        }
        else {
            resultSet =  statement.executeQuery("SELECT * FROM subreddits");
            while (resultSet.next()){
                if (Pattern.matches("^" + searchText + ".*", resultSet.getString(2)))
                    searchResult.add(resultSet.getString(2));
            }
        }

        return searchResult;
    }

    public static void userSearch(String username) throws SQLException {
        Statement statement = DBTools.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

        while (resultSet.next()) {
            if (resultSet.getString(2).equals(username)){
                UUID id = UUID.fromString(resultSet.getString(1));
                String email = resultSet.getString(3);
                int karma = resultSet.getInt(5);
                String dispName = resultSet.getString(6);
                String bio = resultSet.getString(7);
                ArrayList<String> subreddits = Subreddit.IDtoTitle(DBTools.splitID(resultSet.getString(8)));
                ArrayList<Post> posts = Post.IDtoPostList(DBTools.splitID(resultSet.getString(9)));
                ArrayList<Comment> comments = Comment.IDtoCommentList(DBTools.splitID(resultSet.getString(10)));
                account = new Account(id, username, email, karma, dispName, bio, subreddits, posts, comments);
                break;
            }
        }
    }
    public static void subredditSearch(String title) throws SQLException {
        Statement statement = DBTools.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM subreddits");

        while (resultSet.next()) {
            if (resultSet.getString(2).equals(title)){
                UUID id = UUID.fromString(resultSet.getString(1));
                String description = resultSet.getString(3);
                ArrayList<Post> posts = Post.IDtoPostList(DBTools.splitID(resultSet.getString(4)));
                ArrayList<UUID> membersID = DBTools.splitID_UUID(resultSet.getString(5));
                UUID mainAdminID = UUID.fromString(resultSet.getString(6));
                ArrayList<UUID> adminsID = DBTools.splitID_UUID(resultSet.getString(7));
                subreddit = new Subreddit(id, title, description, posts, membersID, mainAdminID, adminsID);
                break;
            }
        }
    }
}
