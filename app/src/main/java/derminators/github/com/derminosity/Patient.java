package derminators.github.com.derminosity;

import java.util.ArrayList;
import java.util.List;

public class Patient extends User {
    private String uid;
    private String firstName;
    private String lastName;
    private List<String> doctors;
    private List<String> images;

    public Patient() {

    }

    public Patient(String uid, String firstName, String lastName) {
        super(uid, firstName, lastName);
        doctors = new ArrayList<String>();
        images = new ArrayList<String>();
    }

    public List<String> getDoctors() {
        return doctors;
    }

    public List<String> getImages() {
        return images;
    }

    public void addDoctor(String doctor) {
        doctors.add(doctor);
    }

    public void removeDoctor(String doctor) {
        doctors.remove(doctor);
    }

    public void addImage(String image) {
        images.add(image);
    }

    public void removeImage(String image) {
        images.remove(image);
    }
}
