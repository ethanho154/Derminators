package derminators.github.com.derminosity;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends User {
  private String uid;
  private String firstName;
  private String lastName;
  private String hospital;
  private List<String> patients;

  public Doctor() {

  }

  public Doctor(String uid, String firstName, String lastName, String hospital) {
    super(uid, firstName, lastName);
    this.hospital = hospital;
    patients = new ArrayList<String>();
  }

  public String getHospital() {
    return hospital;
  }

  public List<String> getPatients() {
    return patients;
  }

  public void addPatient(String patient) {
    patients.add(patient);
  }

  public void removePatient(String patient) {
    patients.remove(patient);
  }
}