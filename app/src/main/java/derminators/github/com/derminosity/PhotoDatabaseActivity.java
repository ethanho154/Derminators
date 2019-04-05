package derminators.github.com.derminosity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.module.AppGlideModule;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class PhotoDatabaseActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private AppGlideModule glideModule;

    private ImageView top;
    private ImageView mid;
    private ImageView bot;

    File directory;
    File file = null;

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_database);

        directory = this.getFilesDir();
        Log.d("File path", directory.getAbsolutePath());

        initializeViews();

        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        glideModule = new AppGlideModule() {
        };

        Log.d("on create", "before anonymous signing");
        mAuth.signInAnonymously();
        Log.d("signed in anonymously", "starting all the good shit");
        populateActivity();
    }

    private void populateActivity() {
        Button downloadButton = findViewById(R.id.button2);
        downloadButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("please work", "button clicked");

                Log.d("mStorageRef", "detected!");

                downloadImage(mStorageRef);

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

    private void initializeViews() {
        builder = new AlertDialog.Builder(PhotoDatabaseActivity.this);
        builder.setTitle(R.string.dialog_title)
            .setPositiveButton("Annotate", new DialogInterface.OnClickListener() {
            @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(PhotoDatabaseActivity.this, MarkImageActivity.class);
                    startActivity(i);
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });


        top = findViewById(R.id.topView);
        mid = findViewById(R.id.midView);
        bot = findViewById(R.id.botView);

        top.setOnClickListener(this);
        mid.setOnClickListener(this);
        bot.setOnClickListener(this);
    }

    private void downloadImage(StorageReference mStorageRef) {
        Glide.with(PhotoDatabaseActivity.this)
//            .load(mStorageRef.child("webcam/cam2-2019-03-01-083802.jpg"))
            .load(mStorageRef.child("webcam/cam2.jpg"))
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(top);
        file = new File(directory, "cam2.jpg");
        mStorageRef.child("webcam/cam2.jpg").getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.d("Save", "Success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Save", "Fail");
            }
        });
        for (File f : directory.listFiles()) {
            System.out.println(f.getAbsolutePath());
            System.out.println(f.getName());
        }
        Glide.with(PhotoDatabaseActivity.this)
//            .load(mStorageRef.child("webcam/cam1-2019-03-01-083802.jpg"))
            .load(mStorageRef.child("webcam/cam1.jpg"))
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(mid);
        Glide.with(PhotoDatabaseActivity.this)
//            .load(mStorageRef.child("webcam/cam3-2019-03-01-083802.jpg"))
            .load(mStorageRef.child("webcam/cam3.jpg"))
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(bot);

        Log.d("glide coming thru", "wala");
    }

    @Override
    public void onClick(View v) {
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}