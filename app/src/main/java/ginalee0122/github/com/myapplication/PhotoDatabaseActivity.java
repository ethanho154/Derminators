package ginalee0122.github.com.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.module.AppGlideModule;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class PhotoDatabaseActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private AppGlideModule glideModule;

    private ImageView top;
    private ImageView mid;
    private ImageView bot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_databse);

        top = findViewById(R.id.imageView1);
        mid = findViewById(R.id.imageView2);
        bot = findViewById(R.id.imageView3);

        mAuth = FirebaseAuth.getInstance();
        glideModule = new AppGlideModule() {
        };
        Log.d("on create", "before anonymous signing");
        mAuth.signInAnonymously();
        Log.d("signed in anonymously", "starting all the good shit");
        populateActivity();
    }

    private void populateActivity() {
        Button mybutton = findViewById(R.id.button2);
        mybutton.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Log.d("please work", "button clicked");
            final StorageReference mStorageRef;
            mStorageRef = FirebaseStorage.getInstance().getReference();
            Log.d("mStorageRef", "detected!");

            loadImage(mStorageRef);

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

    private void loadImage(StorageReference mStorageRef) {
        Glide.with(PhotoDatabaseActivity.this)
              .load(mStorageRef.child("webcam/cam2-2019-03-01-083802.jpg"))
              .into(top);
        Glide.with(PhotoDatabaseActivity.this)
              .load(mStorageRef.child("webcam/cam1-2019-03-01-083802.jpg"))
              .into(mid);
        Glide.with(PhotoDatabaseActivity.this)
              .load(mStorageRef.child("webcam/cam3-2019-03-01-083802.jpg"))
              .into(bot);

        Log.d("glide coming thru", "wala");

    }

//  private File storeImage(StorageReference mStorageRef) {
//          StorageReference topRef = mStorageRef.child("webcam/cam2-2019-03-01-083802.jpg");
//
//          final long ONE_MEGABYTE = 1024*1024;
//          topRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//              @Override
//              public void onSuccess(byte[] bytes) {
//                  byteArray = bytes;
//              }
//          }).addOnFailureListener(new OnFailureListener() {
//              @Override
//              public void onFailure(@NonNull Exception exception) {
//              }
//          });
//
//          return byteArray;
//  }
}