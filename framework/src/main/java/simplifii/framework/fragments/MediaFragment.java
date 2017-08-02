package simplifii.framework.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ArrayAdapter;
import java.io.File;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Util;

public class MediaFragment extends Fragment {
    public final int REQUEST_CODE_GALLARY = 50;
    public final int REQUEST_CODE_CAMERA = 51;
    public final int REQUEST_CODE_AUDIO = 52;
    public final int REQUEST_CODE_PICK_VIDEO = 53;
    public final int REQUEST_CODE_GET_PDF = 54;
    public Uri imageUri;
    MediaListener mediaListener;

    public void getDoc(final MediaListener mediaListener, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, new String[]{"Camera", "Gallery", "Pdf"});
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    getImageFromCamera(mediaListener);
                } else if (which == 1) {
                    getImageFromGallery(mediaListener);
                } else if (which == 2){
                    getPdfFromGallery(mediaListener);
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setTitle("Choose a File");
        dialog.show();
    }

    public void getImage(final MediaListener mediaListener, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose a picture");
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, new String[]{"Camera", "Gallery"});
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    getImageFromCamera(mediaListener);
                } else if (which == 1) {
                    getImageFromGallery(mediaListener);
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void getImageFromCamera(MediaListener mediaListener) {
        this.mediaListener = mediaListener;
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (isAdded()) {
            startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
        }

    }

    public void getImageFromCamera(MediaListener mediaListener, String folderName) {
        this.mediaListener = mediaListener;
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageUri = getOutputMediaFileUri(folderName);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
    }

    public void getImageFromGallery(MediaListener mediaListener) {
        this.mediaListener = mediaListener;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_GALLARY);

    }

    public Uri getOutputMediaFileUri(String folderName) {
        File outputMediaFile = getOutputMediaFile(folderName);
        return Uri.fromFile(outputMediaFile);
    }

    private File getOutputMediaFile(String folderName) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), folderName);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".png");

        return mediaFile;
    }


    public void getAudioFromPlayer(MediaListener mediaListener) {
        this.mediaListener = mediaListener;
        Intent intent = new Intent();
        intent.setType("audio/mp3");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select Audio "), REQUEST_CODE_AUDIO);
    }

    public void getVideoFromGallary(MediaListener mediaListener) {
        this.mediaListener = mediaListener;
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_CODE_PICK_VIDEO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode!= Activity.RESULT_OK){
            return;
        }
        Log.i("msg", "onActivity result is called... requestCode=" + requestCode);
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                mediaListener.setUri(imageUri, AppConstants.MEDIA_TYPES.IMAGE);
                mediaListener.setBitmap((Bitmap) data.getExtras().get("data"),AppConstants.MEDIA_TYPES.IMAGE);
                break;
            case REQUEST_CODE_GALLARY:
                try {
                    mediaListener.setUri(data.getData(), AppConstants.MEDIA_TYPES.IMAGE);
                    mediaListener.setBitmap(Util.getBitmapFromUri(getActivity(), data.getData()),AppConstants.MEDIA_TYPES.IMAGE);
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                break;
            case REQUEST_CODE_AUDIO:
                Uri uri = data.getData();
                mediaListener.setUri(uri, AppConstants.MEDIA_TYPES.AUDIO);
                break;
            case REQUEST_CODE_PICK_VIDEO:
                mediaListener.setUri(data.getData(), AppConstants.MEDIA_TYPES.VIDEO);
                break;
            case REQUEST_CODE_GET_PDF:
                Uri uri1 = data.getData();
                String filePath = null;
                try {
                     filePath = Util.getFilePath(getActivity(), uri1);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                mediaListener.setUri(data.getData(),AppConstants.MEDIA_TYPES.DOC,filePath);
                break;
        }
    }

    public interface MediaListener {
        void setUri(Uri uri, String MediaType);
        void setUri(Uri uri, String MediaType,String path);
        void setBitmap(Bitmap bitmap,String MediaType);


    }

    public void getPdfFromGallery(MediaListener mediaListener) {
        this.mediaListener = mediaListener;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivityForResult(Intent.createChooser(intent, "Select a file to upload"), REQUEST_CODE_GET_PDF);
    }

}
