package derminators.github.com.derminosity;

import java.util.ArrayList;
import java.util.List;

public class Doctor {
  private String uid;
  private String firstName;
  private String lastName;
  private String hospital;
  private List<String> patients;

  public Doctor() {

  }

  public Doctor(String uid, String firstName, String lastName, String hospital, List<String> patients) {
//    super(uid, firstName, lastName);
    this.uid = uid;
    this.firstName = firstName;
    this.lastName = lastName;
    this.hospital = hospital;
    this.patients = patients;
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