import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class Comment {
    private UUID postID;
    private boolean isReply = false;
    private UUID replyOnID;

    public Comment(UUID id, UUID subredditID, UUID postID, UUID ownerID, String text, LocalDateTime dateTime, String tags, int karma, ArrayList<UUID> upVotes, ArrayList<UUID> downVotes, boolean isReply, UUID replyOnID){

    }

}
