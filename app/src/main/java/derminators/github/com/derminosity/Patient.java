package derminators.github.com.derminosity;

import java.util.ArrayList;
import java.util.List;

public class Patient extends User {
    private String uid;
    private List<String> doctors;
    private List<String> images;

    public Patient() {

    }

    public Patient(String uid) {
        this.uid = uid;
        doctors = new ArrayList<String>();
        images = new ArrayList<String>();
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
