package derminators.github.com.derminosity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientDiagnosisActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser currentUser;

    List<String> patients = new ArrayList<>();
//    String[] patientDescriptions;
//    int[] listViewImages;

    private List<HashMap<String, String>> aList;
    private int counter;

    private Doctor currDoctor;
    private ListView listView;
    private EditText patientEmail;
    private Button patientButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_diagnosis);

        currentUser = mAuth.getCurrentUser();

        Log.d("User", currentUser.getEmail());

        listView = findViewById(R.id.listView_patientDiagnosis_patients);
        patientEmail = findViewById(R.id.editText_patientDiagnosis_email);
        patientButton = findViewById(R.id.button_patientDiagnosis_submit);

        patientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmail();
            }
        });

        aList = new ArrayList<>();

        populateActivity();
    }

    private void addEmail() {
        final String email = patientEmail.getText().toString();

        DocumentReference docRef = db.collection("doctor").document(currentUser.getEmail());
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Doctor doctor = documentSnapshot.toObject(Doctor.class);
//                doctor.addPatient(email);
//                currDoctor = doctor;
//            }
//        });

        patients.add(email);
        Map<String, Object> map = new HashMap<>();
        map.put("patients", patients);
        docRef.set(map);

        HashMap<String, String> hm = new HashMap<>();
        hm.put("listview_title", email);
        hm.put("listview_description", "New patient!");
        hm.put("listview_image", Integer.toString(R.drawable.pc));
        aList.add(hm);

        String[] from = {"listview_image", "listview_title", "listview_description"};
        int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};
        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.list_patient, from, to);
        listView.setAdapter(simpleAdapter);
    }

    private void populateActivity() {
        DocumentReference docRef = db.collection("doctor").document(currentUser.getEmail());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                currDoctor = documentSnapshot.toObject(Doctor.class);

//                patients = currDoctor.getPatients();
//                patients = (List<String>) documentSnapshot.getData().get("patients");
                patients.add("testUser1@example.com");
                patients.add("testUser2@example.com");
                patients.add("testUser3@example.com");

                if (!patients.isEmpty()) {
                    Object[] patientNames = patients.toArray();

                    for (int i = 0; i < patientNames.length; i++) {
                        HashMap<String, String> hm = new HashMap<>();
                        hm.put("listview_title", (String) patientNames[i]);
                        hm.put("listview_description", "New patient!");
                        hm.put("listview_image", Integer.toString(R.drawable.pc));
                        aList.add(hm);
                    }

                    String[] from = {"listview_image", "listview_title", "listview_description"};
                    int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};
                    SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.list_patient, from, to);
                    listView.setAdapter(simpleAdapter);
                }
            }
        });
    }
}
