package derminators.github.com.derminosity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

class AnnotateView extends android.support.v7.widget.AppCompatImageView {
    private final Paint paint = new Paint();
    private float x;
    private float y;

    public AnnotateView(Context context) {
        super(context);
    }

    AnnotateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    AnnotateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x, y, 5, paint);
//        paint.setColor(Color.BLACK);
//        paint.setStyle(Paint.Style.FILL_AND_STROKE);
//        paint.setStrokeWidth(10);
//        canvas.drawRect(leftX, topY, rightX, bottomY, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();
        invalidate();
        return true;
    }
}
