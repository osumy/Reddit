import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Comment extends Content{
    private UUID postID;
    private boolean isReply;
    private UUID replyOnID;

    public Comment(UUID subredditID, UUID postID, UUID ownerID, String text, LocalDateTime dateTime, String tags, int karma, ArrayList<UUID> upVotes, ArrayList<UUID> downVotes, boolean isReply, UUID replyOnID){
        super(UUID.randomUUID(), subredditID, ownerID, text, dateTime, tags, karma, upVotes, downVotes);
        this.postID = postID;
        this.isReply = isReply;
        this.replyOnID = replyOnID;
    }
    public Comment(UUID id, UUID subredditID, UUID postID, UUID ownerID, String text, LocalDateTime dateTime, String tags, int karma, ArrayList<UUID> upVotes, ArrayList<UUID> downVotes, boolean isReply, UUID replyOnID){
        super(id, subredditID, ownerID, text, dateTime, tags, karma, upVotes, downVotes);
        this.postID = postID;
        this.isReply = isReply;
        this.replyOnID = replyOnID;
    }

    public static ArrayList<Comment> IDtoCommentList(List<String> IDList) throws SQLException {
        ArrayList<Comment> CommentList = new ArrayList<>();
        Statement statement = DBTools.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM comments");

        while (resultSet.next()){
            if (DBTools.isAmong(resultSet.getString(1), IDList)){
                UUID id = UUID.fromString(resultSet.getString(1));
                UUID subredditID = UUID.fromString(resultSet.getString(2));
                UUID postID = UUID.fromString(resultSet.getString(3));
                UUID ownerID = UUID.fromString(resultSet.getString(4));
                String text = resultSet.getString(5);
                LocalDateTime dateTime = LocalDateTime.parse(resultSet.getString(6));
                String tags = resultSet.getString(7);
                int karma = resultSet.getInt(8);
                ArrayList<UUID> upVotes = DBTools.splitID_UUID(resultSet.getString(9));
                ArrayList<UUID> downVotes = DBTools.splitID_UUID(resultSet.getString(10));
                boolean isReply = resultSet.getInt(11) == 1;
                UUID replyOnID = UUID.fromString(resultSet.getString(12));
                CommentList.add(new Comment(id, subredditID, postID, ownerID, text, dateTime, tags, karma, upVotes, downVotes, isReply, replyOnID));
            }
        }

        return CommentList;
    }

}
