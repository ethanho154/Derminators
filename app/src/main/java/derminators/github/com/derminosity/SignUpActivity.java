package derminators.github.com.derminosity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser currentUser;

    private View loginFormView;
    private View progressView;
    private EditText firstNameView;
    private EditText lastNameView;
    private EditText emailView;
    private Spinner userType;
    private EditText hospitalView;
    private EditText passwordView;
    private Button createAccountButton;
    private TextView loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initializeViews();
    }

    private void initializeViews() {
        loginFormView = findViewById(R.id.scrollview_signup_form);
        progressView = findViewById(R.id.progressbar_signup_status);

        firstNameView = findViewById(R.id.autocompletetextview_signup_firstname);
        lastNameView = findViewById(R.id.autocompletetextview_signup_lastname);
        emailView = findViewById(R.id.autocompletetextview_signup_email);
        userType = findViewById(R.id.spinner_signup_usertype);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.user_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userType.setAdapter(adapter);
        hospitalView = findViewById(R.id.edittext_signup_hospital);
        passwordView = findViewById(R.id.edittext_signup_password);

        createAccountButton = findViewById(R.id.button_signup_createaccount);
        createAccountButton.setOnClickListener(this);

        loginText = findViewById(R.id.textview_signup_accountalreadyexists);
        loginText.setOnClickListener(this);
    }

    private boolean validateFields(String firstName, String lastName, String email, String password, String hospital) {
        // Reset errors.
        firstNameView.setError(null);
        lastNameView.setError(null);
        emailView.setError(null);
        passwordView.setError(null);
        hospitalView.setError(null);

        // Store values at the time of the login attempt.
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(firstName)) {
            firstNameView.setError(getString(R.string.error_field_required));
            focusView = firstNameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(lastName)) {
            lastNameView.setError(getString(R.string.error_field_required));
            focusView = lastNameView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailView.setError(getString(R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(hospital)) {
            hospitalView.setError(getString(R.string.error_field_required));
            focusView = hospitalView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }

        return !cancel;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private void createAccount() {
        final String firstName = firstNameView.getText().toString();
        final String lastName = lastNameView.getText().toString();
        final String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        final String hospital = hospitalView.getText().toString();

        if (validateFields(firstName, lastName, email, password, hospital)) {
            showProgress(true);
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Signup attempt", "createUserWithEmail:success");
                        currentUser = mAuth.getCurrentUser();
                        String uid = currentUser.getUid();
                        Log.d("Signup attempt", "saving data model to firebase");
                        saveData(uid, email, firstName, lastName, hospital);
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // If sign in fails, display a message to the user.
                        Log.w("Signup attempt", "createUserWithEmail:failure", e);
                        Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            showProgress(false);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            loginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void saveData(String uid, String email, String firstName, String lastName, String hospital) {
        Doctor doctor = new Doctor(uid, firstName, lastName, hospital);
        db.collection("doctors").document(email)
                .set(doctor)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("saveData", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("saveData", "Error writing document", e);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_signup_createaccount) {
            createAccount();
            startActivity(new Intent(SignUpActivity.this, MenuActivity.class));
        }
        if (i == R.id.textview_signup_accountalreadyexists) {
//            createAccount();
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        }
    }
}
