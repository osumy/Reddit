import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;


public abstract class Content {
    private UUID id;
    private UUID subredditID;
    private UUID ownerID;
    private String text;
    private LocalDateTime dateTime;
    private String tags;
    private int karma;
    private ArrayList<UUID> upVotes;
    private ArrayList<UUID> downVotes;

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
