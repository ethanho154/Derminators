package ginalee0122.github.com.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;



public class PhotoDatabseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_photo_databse);
      FirebaseAuth mAuth = FirebaseAuth.getInstance();
      mAuth.signInAnonymously();
      Log.d("signed in anonymously", "starting all the good shit");
      allTheGoodShit();
  }


  private void allTheGoodShit() {
    Button mybutton = (Button) findViewById(R.id.button2);
    mybutton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.d("please work", "button clicked");
        StorageReference mStorageRef;
        mStorageRef = FirebaseStorage.getInstance().getReference();
          Log.d("mStorageRef", "detected??");
        //FromUrl("gs://bme590project.appspot.com/webcam/cam1-2019-03-01-015451.jpg");
        //System.out.println("accessed Firebase Storage");

        try {
          File localFile = File.createTempFile("images", "jpg");
          mStorageRef.getFile(localFile)
            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {

              public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                System.out.println("successfully download the byte blob!");
                // Successfully downloaded data to local file
                // ...
              }
            }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
              System.out.println("F A I L :P");
              // Handle failed download
              // ...
            }
          });
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }






}
