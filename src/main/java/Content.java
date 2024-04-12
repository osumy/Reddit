import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;


public abstract class Content {
    protected UUID id;
    protected UUID subredditID;
    protected UUID ownerID;
    protected String text;
    protected LocalDateTime dateTime;
    protected String tags;
    protected int karma;
    protected ArrayList<UUID> upVotes;
    protected ArrayList<UUID> downVotes;

    protected Content(UUID id, UUID subredditID, UUID ownerID, String text, LocalDateTime dateTime, String tags, int karma, ArrayList<UUID> upVotes, ArrayList<UUID> downVotes){
        this.id = id;
        this.subredditID = subredditID;
        this.ownerID = ownerID;
        this.text = text;
        this.dateTime = dateTime;
        this.tags = tags;
        this.karma = karma;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
    }

}
