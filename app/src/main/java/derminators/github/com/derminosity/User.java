package derminators.github.com.derminosity;

public abstract class User {
    private String uid;
    private String firstName;
    private String lastName;

    public User() {

    }

    public User(String uid, String firstName, String lastName) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUid() {
        return uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
