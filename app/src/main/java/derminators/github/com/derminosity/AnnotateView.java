package derminators.github.com.derminosity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

class AnnotateView extends android.support.v7.widget.AppCompatImageView {
    private float xPos;
    private float yPos;

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
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                xPos = getX() - event.getRawX();
                yPos = getY() - event.getRawY();
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                animate()
                        .x(event.getRawX() + xPos)
                        .y(event.getRawY() + yPos)
                        .setDuration(0).start();
                break;
            }

            case MotionEvent.ACTION_UP: {
                break;
            }
        }
        return true;
    }

}
