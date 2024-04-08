import java.util.ArrayList;
import java.util.UUID;

public class Account {
    private UUID id;
    private String username;
    private String dispName;
    private String bio; // About you
    private String email;
    private String password;
    private int karma;
    private ArrayList<Comment> comments;
    private ArrayList<Post> posts;
    private ArrayList<UUID> subredditIDs;
}
