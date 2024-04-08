import java.time.LocalDateTime;
import java.util.UUID;


public abstract class Content {
    private String text;
    private UUID subredditID;
    private UUID ownerID;
    private LocalDateTime dateTime;

    // valid: username / email / password
}
