import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;

public class Account {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private int karma;
    private String dispName;
    private String bio;
    private ArrayList<String> mySubreddits;
    private ArrayList<Post> myPosts;
    private ArrayList<Comment> myComments;

    public static Account myAccount;

    public Account(String username, String email, String password){
        id = UUID.randomUUID();
        this.username = username;
        this.email = email;
        this.password = DigestUtils.sha256Hex(password);
        mySubreddits = new ArrayList<>();
        myComments = new ArrayList<>();
        myPosts =  new ArrayList<>();
    }
    public Account(){
        mySubreddits = new ArrayList<>();
        myComments = new ArrayList<>();
        myPosts =  new ArrayList<>();
    }
    public Account(UUID id, String username, String email, int karma, String dispName, String bio, ArrayList<String> mySubreddits, ArrayList<Post> myPosts, ArrayList<Comment> myComments){
        this.id = id;
        this.username = username;
        this.email = email;
        this.karma = karma;
        this.dispName = dispName;
        this.bio = bio;
        this.mySubreddits = mySubreddits;
        this.myPosts = myPosts;
        this.myComments = myComments;
    }

    private static void setId(UUID id){ myAccount.id = id; }
    private static void setUsername(String username){ myAccount.username = username; }
    private static void setEmail(String email){ myAccount.email = email; }
    private static void setKarma(int karma){ myAccount.karma = karma; }
    private static void setDispName(String dispName){ myAccount.dispName = dispName; }
    private static void setBio(String bio){ myAccount.bio = bio; }
    private static void setMySubreddits(ArrayList<String> subreddits){ myAccount.mySubreddits = subreddits; }
    private static void setMyPosts(ArrayList<Post> posts){ myAccount.myPosts = posts; }
    private static void setMyComments(ArrayList<Comment> comments){ myAccount.myComments = comments; }

    // ========== Account Management ==========
    public static boolean isValid(String data, String type){
        if (type.equals("username"))
            return Pattern.matches("^[\\w][\\w_.]{5,20}", data); // Minimum 5 and Maximum 20 characters, does not start with a . or _
        else if (type.equals("email"))
            return Pattern.matches("^[a-zA-Z][\\w]+@([\\w-]+\\.)+[\\w-]{2,4}", data);
        else if (type.equals("password"))
            return Pattern.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-.]).{8,}", data); //Minimum eight characters, at least one upper case English letter, one lower case English letter, one number and one special character

        return false;
    }
    public void signUp() throws SQLException { DBTools.insertUser(id, username, email, password); }
    public static void login(String user, boolean isUsername) throws SQLException {
        Statement statement = DBTools.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

        while (resultSet.next()){
            boolean isOK = false;
            if (isUsername)
                if (resultSet.getString(2).equals(user))
                    isOK = true;
            else
                if (resultSet.getString(3).equals(user))
                    isOK = true;
            if (isOK){
                myAccount = new Account();
                setId(UUID.fromString(resultSet.getString(1)));
                setUsername(resultSet.getString(2));
                setEmail(resultSet.getString(3));
                //setPassword(resultSet.getString(4)); not necessary!
                setKarma(resultSet.getInt(5));
                setDispName(resultSet.getString(6));
                setBio(resultSet.getString(7));
                setMySubreddits(Subreddit.IDtoTitle(DBTools.splitID(resultSet.getString(8))));
                setMyPosts(Post.IDtoPostList(Pattern.compile(",").splitAsStream(resultSet.getString(9)).toList()));
                setMyComments(Comment.IDtoCommentList(Pattern.compile(",").splitAsStream(resultSet.getString(10)).toList()));
            }
        }
    }
    public static void logout(){ myAccount = null; }

    // ========== Edit Profile ==========
    public void updateEmail(String email) throws SQLException {
        DBTools.updateCell(email, "users", myAccount.id.toString(), "email");
        setUsername(email);
    }
    public void updatePassword(String password) throws SQLException {
        DBTools.updateCell(password, "users", myAccount.id.toString(), "password");
        setUsername(password);
    }
    public void updateDispName(String dispName) throws SQLException {
        DBTools.updateCell(dispName, "users", myAccount.id.toString(), "dispName");
        setUsername(dispName);
    }
    public void updateBio(String bio) throws SQLException {
        DBTools.updateCell(bio, "users", myAccount.id.toString(), "bio");
        setUsername(bio);
    }


    public static ArrayList<String> IDtoUsernameList(ArrayList<String> IDList) throws SQLException {
        ArrayList<String> usernames = new ArrayList<>();
        Statement statement = DBTools.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

        while (resultSet.next()){
            if (DBTools.isAmong(resultSet.getString(1), IDList)){
                usernames.add(resultSet.getString(2));
            }
        }

        return usernames;
    }
}
