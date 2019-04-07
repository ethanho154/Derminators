package derminators.github.com.derminosity;

public abstract class User {
    private String uid;

    public User() {

    }

    public User(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }
}
