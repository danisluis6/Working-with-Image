package tutorial.lorence.template.view.activities.crop;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.View;

public class ImageViewUtil {

    /**
     * Gets the rectangular position of a Bitmap if it were placed inside a View
     * with scale type set to ImageView# ScaleType #CENTER_INSIDE.
     *
     * @param bitmap the Bitmap
     * @param view   the parent View of the Bitmap
     * @return the rectangular position of the Bitmap
     */
    public static Rect getBitmapRectCenterInside(Bitmap bitmap, View view) {

        final int bitmapWidth = bitmap.getWidth();
        final int bitmapHeight = bitmap.getHeight();
        final int viewWidth = view.getWidth();
        final int viewHeight = view.getHeight();

        return getBitmapRectCenterInsideHelper(bitmapWidth, bitmapHeight, viewWidth, viewHeight);
    }

    /**
     * Helper that does the work of the above functions. Gets the rectangular
     * position of a Bitmap if it were placed inside a View with scale type set
     * to ImageView# ScaleType #CENTER_INSIDE.
     *
     * @param bitmapWidth  the Bitmap's width
     * @param bitmapHeight the Bitmap's height
     * @param viewWidth    the parent View's width
     * @param viewHeight   the parent View's height
     * @return the rectangular position of the Bitmap
     */
    private static Rect getBitmapRectCenterInsideHelper(int bitmapWidth,
                                                        int bitmapHeight,
                                                        int viewWidth,
                                                        int viewHeight) {
        double resultWidth;
        double resultHeight;
        int resultX;
        int resultY;

        double viewToBitmapWidthRatio = Double.POSITIVE_INFINITY;
        double viewToBitmapHeightRatio = Double.POSITIVE_INFINITY;

        // Checks if either width or height needs to be fixed
        if (viewWidth < bitmapWidth) {
            viewToBitmapWidthRatio = (double) viewWidth / (double) bitmapWidth;
        }
        if (viewHeight < bitmapHeight) {
            viewToBitmapHeightRatio = (double) viewHeight / (double) bitmapHeight;
        }

        // If either needs to be fixed, choose smallest ratio and calculate from
        // there
        if (viewToBitmapWidthRatio != Double.POSITIVE_INFINITY || viewToBitmapHeightRatio != Double.POSITIVE_INFINITY) {
            if (viewToBitmapWidthRatio <= viewToBitmapHeightRatio) {
                resultWidth = viewWidth;
                resultHeight = (bitmapHeight * resultWidth / bitmapWidth);
            } else {
                resultHeight = viewHeight;
                resultWidth = (bitmapWidth * resultHeight / bitmapHeight);
            }
        }
        // Otherwise, the picture is within frame layout bounds. Desired width
        // is simply picture size
        else {
            resultHeight = bitmapHeight;
            resultWidth = bitmapWidth;
        }

        // Calculate the position of the bitmap inside the ImageView.
        if (resultWidth == viewWidth) {
            resultX = 0;
            resultY = (int) Math.round((viewHeight - resultHeight) / 2);
        } else if (resultHeight == viewHeight) {
            resultX = (int) Math.round((viewWidth - resultWidth) / 2);
            resultY = 0;
        } else {
            resultX = (int) Math.round((viewWidth - resultWidth) / 2);
            resultY = (int) Math.round((viewHeight - resultHeight) / 2);
        }

        return new Rect(resultX,
                resultY,
                resultX + (int) Math.ceil(resultWidth),
                resultY + (int) Math.ceil(resultHeight));
    }

    /*
     * Get width of screen on real device
     */
    public static int getPosWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    /**
     * Get Height of screen on real device
     */
    public static int getPosHeight(Activity activity) {
        // Status bar height
        int statusBarHeight = 0;
        int resourceIdx = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceIdx > 0) {
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceIdx);
        }

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y - statusBarHeight;
    }

}
