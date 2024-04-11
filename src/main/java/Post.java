import org.sqlite.core.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Post extends Content {
    private String title;

    public Post(UUID subredditID, UUID ownerID, String title, String text, LocalDateTime dateTime, String tags, int karma, ArrayList<UUID> upVotes, ArrayList<UUID> downVotes){
        super(UUID.randomUUID(), subredditID, ownerID, text, dateTime, tags, karma, upVotes, downVotes);
        this.title = title;
    }
    public Post(UUID id, UUID subredditID, UUID ownerID, String title, String text, LocalDateTime dateTime, String tags, int karma, ArrayList<UUID> upVotes, ArrayList<UUID> downVotes){
        super(id, subredditID, ownerID, text, dateTime, tags, karma, upVotes, downVotes);
        this.title = title;
    }

    public static ArrayList<Post> IDtoPostList(List<String> IDList) throws SQLException {
        ArrayList<Post> PostList = new ArrayList<>();
        Statement statement = DBTools.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM posts");

        while (resultSet.next()){
            if (DBTools.isAmong(resultSet.getString(1), IDList)){
                UUID id = UUID.fromString(resultSet.getString(1));
                UUID subredditID = UUID.fromString(resultSet.getString(2));
                UUID ownerID = UUID.fromString(resultSet.getString(3));
                String title = resultSet.getString(4);
                String text = resultSet.getString(5);
                LocalDateTime dateTime = LocalDateTime.parse(resultSet.getString(6));
                String tags = resultSet.getString(7);
                int karma = resultSet.getInt(8);
                ArrayList<UUID> upVotes = DBTools.splitID_UUID(resultSet.getString(9));
                ArrayList<UUID> downVotes = DBTools.splitID_UUID(resultSet.getString(10));
                PostList.add(new Post(id, subredditID, ownerID, title, text, dateTime, tags, karma, upVotes, downVotes));
            }
        }

        return PostList;
    }
    public void createPost() throws SQLException {
        DBTools.insertPost(id, subredditID, ownerID, title, text, dateTime.toString(), tags);
        DBTools.insertIDtoIDListCell("users", ownerID , "postID", id);
        DBTools.insertIDtoIDListCell("subreddits", subredditID , "postsID", id);
    }
}
