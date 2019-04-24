package derminators.github.com.derminosity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.module.AppGlideModule;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhotoDatabaseActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private AppGlideModule glideModule;

    private ListView listView;
    private List<String> images;

    private Map<View, Integer> indices;
    private int index;

//    private ImageView top;
//    private ImageView mid;
//    private ImageView bot;

    File directory;
    File file = null;

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_database);

        directory = this.getFilesDir();
//        Log.d("File path", directory.getAbsolutePath());

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
            mStorageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
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
                file = new File(directory, "savedImage.jpg");
                Log.d("Save", "Statement reached");
                mStorageRef.child("webcam/cam" + index + ".jpg").getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
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

                Intent i = new Intent(PhotoDatabaseActivity.this, BlobDetectionActivity.class);
                startActivity(i);
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

        listView = findViewById(R.id.listView_photoDB_images);

        indices = new HashMap<>();

    }

    private void downloadImage(StorageReference mStorageRef) {
        images = new ArrayList<>();
        for (int i = 1; i <= 16; i++) {
//            images.add("home/pi/webcam/cam" + i + ".jpg");
            images.add("webcam/cam" + i + ".jpg");
        }

        listView.setAdapter(new ImageListAdapter(PhotoDatabaseActivity.this, R.layout.list_item, images));

        Log.d("glide coming thru", "wala");
    }

    @Override
    public void onClick(View v) {
        index = indices.get(v);
        System.out.println(index);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public class ImageListAdapter extends ArrayAdapter<String> {
        List<String> items;

        public ImageListAdapter(Context context, int resource, List<String> items) {
            super(context, resource, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.list_item, null);
            }

            Glide.with(getContext())
                    .load(mStorageRef.child(items.get(position)))
                    .into((ImageView) convertView);

            indices.put(convertView, position);

            return convertView;
        }
    }
}