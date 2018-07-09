package tutorial.lorence.template.view.activities.crop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.TypedValue;

public class PaintUtil {

    private static final String SEMI_TRANSPARENT = "#AAFFFFFF";
    private static final String DEFAULT_BORDER_COLOR = "#FFFFFFFF";
    private static final String DEFAULT_BACKGROUND_COLOR_ID = "#B029303F";
    private static final float DEFAULT_LINE_THICKNESS_DP = 2;
    private static final float DEFAULT_GUIDELINE_THICKNESS_PX = 2;


    /**
     * Creates the Paint object for drawing the crop window border.
     *
     * @param context the Context
     * @return new Paint object
     */
    public static Paint newBorderPaint(Context context) {

        // Set the line thickness for the crop window border.
        final float lineThicknessPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_LINE_THICKNESS_DP, context.getResources().getDisplayMetrics());

        final Paint borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setColor(Color.parseColor(DEFAULT_BORDER_COLOR));
        borderPaint.setStrokeWidth(lineThicknessPx);
        borderPaint.setStyle(Paint.Style.STROKE);

        return borderPaint;
    }

    /**
     * Creates the Paint object for drawing the crop window guidelines.
     *
     * @return the new Paint object
     */
    public static Paint newGuidelinePaint() {

        final Paint paint = new Paint();
        paint.setColor(Color.parseColor(SEMI_TRANSPARENT));
        paint.setStrokeWidth(DEFAULT_GUIDELINE_THICKNESS_PX);

        return paint;
    }

    /**
     * Creates the Paint object for drawing the translucent overlay outside the
     * crop window.
     *
     * @return the new Paint object
     */
    public static Paint newBackgroundPaint() {

        final Paint paint = new Paint();
        paint.setColor(Color.parseColor(DEFAULT_BACKGROUND_COLOR_ID));

        return paint;
    }
}