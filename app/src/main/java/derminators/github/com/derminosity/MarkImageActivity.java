package derminators.github.com.derminosity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MarkImageActivity extends AppCompatActivity {
    private CropView cropView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_image);
        setCropView();
    }

    private void populateActivity() {

    }

    private void setCropView() {
    cropView = findViewById(R.id.cropView);
    Glide.with(MarkImageActivity.this)
        .load(this.getFilesDir().getAbsolutePath() + "/cam2.jpg")
        .into(cropView);
    }
}
