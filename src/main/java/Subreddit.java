import java.util.ArrayList;
import java.util.UUID;

public class Subreddit {
    private String title;
    private String description;
    private ArrayList<Post> posts;
    private ArrayList<UUID> membersID;
    private UUID mainAdminID;
    private ArrayList<UUID> adminsID;

}
