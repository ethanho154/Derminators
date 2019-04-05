package derminators.github.com.derminosity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MarkImageActivity extends AppCompatActivity {
    private AnnotateView annotateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_image);
        setAnnotateView();
    }

    private void populateActivity() {

    }

    private void setAnnotateView() {
//        annotateView = new AnnotateView();
    annotateView = findViewById(R.id.annotateView);
    Glide.with(MarkImageActivity.this)
        .load(this.getFilesDir().getAbsolutePath() + "/cam2.jpg")
        .into(annotateView);
    }
}
