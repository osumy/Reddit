import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;


public abstract class Content {
    private UUID id;
    private UUID subredditID;
    private UUID ownerID;
    private String text;
    private LocalDateTime dateTime;
    private int karma = 0;
    private ArrayList<UUID> upVotes = new ArrayList<>();
    private ArrayList<UUID> downVotes = new ArrayList<>();

}
