package derminators.github.com.derminosity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

public class NotificationsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_notifications);
  }

  private void clickNoti() {
    CardView card = findViewById(R.id.card_view);
    card.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        Log.d("please work", "card clicked");
        startActivity(new Intent(NotificationsActivity.this, PhotoDatabaseActivity.class));


      }
    });
  }




}
