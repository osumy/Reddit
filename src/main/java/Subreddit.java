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
        String membersID = DBTools.readCell("subreddits", subredditID.toString(), "membersID");
        membersID += "," + userID.toString();
        DBTools.updateCell(membersID, "subreddits", subredditID.toString(), "membersID");
    }
}
