package tutorial.lorence.template.view.activities.crop;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * This class is used for convert dimension.
 *
 * @author fairly
 */
public final class Dimension {
    /**
     * Constructor.
     */
    private Dimension() {
    }

    /**
     * DPI standard.
     */
    private static final int DPI_STANDARD = 160;
    /**
     * Nexus dimension.
     */
    private static final int NEXUS_DIMENSION = 480;
    /**
     * displayMetrics.
     */
    private static DisplayMetrics displayMetrics;

    /**
     * Convert PX to DP.
     *
     * @param d       number of PX.
     * @param context application context.
     * @return number of DP
     */
    public static int convertDptoPx(final double d, final Context context) {
        if (displayMetrics == null) {
            displayMetrics = context.getResources().getDisplayMetrics();
        }
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) (d * DPI_STANDARD / NEXUS_DIMENSION), displayMetrics);
    }
}
