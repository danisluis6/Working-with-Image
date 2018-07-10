package tutorial.lorence.template.view.activities.crop;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import tutorial.lorence.template.R;
import tutorial.lorence.template.other.Constants;
import tutorial.lorence.template.other.Utils;

public class CropImageActivity extends Activity implements View.OnClickListener {
    private ImageViewZoom zoomView;
    private String mImagePath;
    private Button btnCancle, btnCrop;
    private ContentResolver mContentResolver;

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    // Called when the activity is first created.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get screen dimensions in pixels
        ViewGroup viewGroup = findViewById(R.id.activity_image_cropper);
        View parent = getLayoutInflater().inflate(R.layout.activity_image_cropper, viewGroup);
        initViews(parent);
        initAttributes();
        initListeners();

        /* Convert image to small */
        Uri mImageUri = Uri.fromFile(new File(mImagePath));
        zoomView.setImageBitmap(getBitmap(mImageUri));
        setContentView(parent);
    }

    private void initListeners() {
        btnCrop.setOnClickListener(this);
        btnCancle.setOnClickListener(this);
    }

    private void initAttributes() {
        mContentResolver = getContentResolver();
        mImagePath = getIntent().getStringExtra("Path");
    }

    private void initViews(View parent) {
        parent.findViewById(R.id.crop_layout_view);
        btnCancle = parent.findViewById(R.id.btnCancel);
        btnCrop = parent.findViewById(R.id.btnCrop);
        zoomView = parent.findViewById(R.id.iv_zoom);
    }

    private Bitmap getBitmap(Uri uri) {
        int image_size_allow = 1024;
        InputStream in;
        Bitmap returnedBitmap;
        try {
            in = mContentResolver.openInputStream(uri);
            if (in != null) {
                // Decode image size
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(in, null, options);
                in.close();

                int scale = calculateInSampleSize(options, image_size_allow, image_size_allow);
                BitmapFactory.Options o2 = new BitmapFactory.Options();
                o2.inSampleSize = scale;
                in = mContentResolver.openInputStream(uri);
                if (in != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(in, null, o2);
                    in.close();
                    returnedBitmap = Utils.fixOrientationBugOfProcessedBitmap(this, bitmap, mImagePath);
                    return returnedBitmap;
                }
            }
        } catch (FileNotFoundException e) {
            Log.d("TAG", "FileNotFoundException");
        } catch (IOException e) {
            Log.d("TAG", "IOException");
        }
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                finish();
                break;
            case R.id.btnCrop:
                getImageAfterCropSuccess();
                break;
        }
    }

    private void getImageAfterCropSuccess() {
        Bitmap bitmap = saveOutput();
        if (bitmap != null) {
            Intent intent = new Intent();
            intent.putExtra("Path_Cropper", bitmap);
            setResult(Constants.REQUEST_IMAGE_CROP, intent);
            finish();
        } else {
            Toast.makeText(this, "Unable to save Image into your device.", Toast.LENGTH_LONG).show();
        }
    }

    private Bitmap saveOutput() {
        return Bitmap.createScaledBitmap(getCroppedImage(), Dimension.convertDptoPx(150, this),
                Dimension.convertDptoPx(150, this), true);
    }

    private Bitmap getCroppedImage() {
        Bitmap mCurrentDisplayedBitmap = getCurrentDisplayedImage();
        Rect displayedImageRect = ImageViewUtil.getBitmapRectCenterInside(mCurrentDisplayedBitmap, zoomView);

        /*
         * Get the scale factor between the actual Bitmap dimensions and the
         * Displayed dimensions for width.
         */
        float actualImageWidth = mCurrentDisplayedBitmap.getWidth();
        float displayedImageWidth = displayedImageRect.width();
        float scaleFactorWidth = actualImageWidth / displayedImageWidth;

        /*
         * Get the scale factor between the actual Bitmap dimensions and the
         * Displayed dimensions for height.
         */
        float actualImageHeight = mCurrentDisplayedBitmap.getHeight();
        float displayedImageHeight = displayedImageRect.height();
        float scaleFactorHeight = actualImageHeight / displayedImageHeight;
        /* Get crop window position relative to the displayed image. */
        float cropWindowX = Edge.LEFT.getCoordinate() - displayedImageRect.left;
        float cropWindowY = Edge.TOP.getCoordinate() - displayedImageRect.top;
        float cropWindowWidth = Edge.getWidth();
        float cropWindowHeight = Edge.getHeight();
        /* Scale the crop window position to the actual size of the Bitmap. */
        float actualCropX = cropWindowX * scaleFactorWidth;
        float actualCropY = cropWindowY * scaleFactorHeight;
        float actualCropWidth = cropWindowWidth * scaleFactorWidth;
        float actualCropHeight = cropWindowHeight * scaleFactorHeight;
        /* Crop the subset from the original Bitmap. */
        return Bitmap.createBitmap(mCurrentDisplayedBitmap, (int) actualCropX, (int) actualCropY, (int) actualCropWidth, (int) actualCropHeight);
    }

    private Bitmap getCurrentDisplayedImage() {
        Bitmap result = Bitmap.createBitmap(zoomView.getWidth(), zoomView.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(result);
        zoomView.draw(c);
        return result;
    }
}