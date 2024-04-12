import org.sqlite.core.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Subreddit {
    private UUID id;
    private String title;
    private String description;
    private ArrayList<Post> posts;
    private ArrayList<UUID> membersID;
    private UUID mainAdminID;
    private ArrayList<UUID> adminsID;

    public Subreddit(String title, String description, UUID mainAdminID){
        id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        posts = new ArrayList<>();
        membersID = new ArrayList<>();
        membersID.add(mainAdminID);
        this.mainAdminID = mainAdminID;
        adminsID = new ArrayList<>();
    }
    public Subreddit(UUID id, String title, String description, ArrayList<Post> posts, ArrayList<UUID> membersID, UUID mainAdminID, ArrayList<UUID> adminsID){
        this.id = id;
        this.title = title;
        this.description = description;
        this.posts = posts;
        this.membersID = membersID;
        this.mainAdminID = mainAdminID;
        this.adminsID = adminsID;
    }

    public void newSubreddit() throws SQLException { DBTools.insertSubreddit(id, title, description, mainAdminID); }
    public static ArrayList<String> IDtoTitle(List<String> IDList) throws SQLException {
        ArrayList<String> titleList = new ArrayList<>();
        Statement statement = DBTools.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM subreddits");

        while (resultSet.next()){
            if (DBTools.isAmong(resultSet.getString(1), IDList))
                titleList.add(resultSet.getString(2));
        }

        return titleList;
    }
    public static void joinSubreddit(UUID subredditID, UUID userID) throws SQLException {
        DBTools.insertIDtoIDListCell("subreddits", subredditID, "membersID", userID);
        DBTools.insertIDtoIDListCell("users", userID, "subredditID", subredditID);
    }
    public static void addNewAdmin(UUID subredditID, UUID userID) throws SQLException { DBTools.insertIDtoIDListCell("subreddits", subredditID, "adminsID", userID); }
    public static void newDescription(String description, UUID subredditID) throws SQLException { DBTools.updateCell(description, "subreddits", subredditID.toString(), "description"); }
    public static void deletePost(UUID subredditID, UUID postID) throws SQLException {
        ArrayList<String> posts = DBTools.splitID(DBTools.readCell("subreddits", subredditID.toString(), "postsID"));
        for (int i = 0; i < posts.size(); i++){
            if (posts.get(i).equals(postID.toString()))
                posts.remove(i);
        }
        String newPostsID = "";
        for (int i = 0; i < posts.size(); i++){
            if (i > 0)
                newPostsID += ",";
            newPostsID += posts.get(i);
        }
        DBTools.updateCell(newPostsID, "subreddits", subredditID.toString(), "postsID");
        DBTools.deleteRow("posts", postID.toString());
    }
    public static void kickUser(UUID subredditID, UUID userID) throws SQLException {
        ArrayList<String> members = DBTools.splitID(DBTools.readCell("subreddits", subredditID.toString(), "membersID"));
        for (int i = 0; i < members.size(); i++){
            if (members.get(i).equals(userID.toString()))
                members.remove(i);
        }
        String newMembersID = "";
        for (int i = 0; i < members.size(); i++){
            if (i > 0)
                newMembersID += ",";
            newMembersID += members.get(i);
        }
        DBTools.updateCell(newMembersID, "subreddits", subredditID.toString(), "membersID");

        ArrayList<String> userSubreddits = DBTools.splitID(DBTools.readCell("users", userID.toString(), "subredditID"));
        for (int i = 0; i < userSubreddits.size(); i++){
            if (userSubreddits.get(i).equals(userID.toString())) {
                userSubreddits.remove(i);
            }
        }
        String newUserSubreddits = "";
        for (int i = 0; i < userSubreddits.size(); i++){
            if (i > 0) {
                newUserSubreddits += ",";
            }
            newUserSubreddits += userSubreddits.get(i);
        }
        DBTools.updateCell(newUserSubreddits, "users", userID.toString(), "subredditID");
    }
}
