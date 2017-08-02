package com.justreliefdoctors.fragment;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.justreliefdoctors.R;

import java.io.File;
import java.util.ArrayList;

import simplifii.framework.fragments.BaseFragment;
import simplifii.framework.utility.Util;

/**
 * Created by kartikeya-pc on 6/14/17.
 */

public abstract class AppBaseFragment extends BaseFragment {

    protected void askPermissions() {
        new TedPermission(getActivity())
                .setPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        onPermissionVerify();
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        showToast("Denied");
                    }
                }).check();
    }

    protected void onPermissionVerify() {

    }

    protected void getImagePathFromBitmap(Bitmap bitmap, int tv_id){
        bitmap = Util.getResizeBitmap(bitmap, 1024);

        File imageFile = null;
        if (bitmap != null) {
            try {
                imageFile = Util.getFile(bitmap, "JR");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (imageFile != null) {
            if (imageFile.exists()) {
                String image_name = imageFile.getName();
                setText(tv_id, image_name);
                setTag(tv_id,imageFile);

            }else{
                showToast(R.string.error);
            }
        }else{
            showToast(R.string.error);
        }

    }
    protected void startFragment(Fragment fragmentType){
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentType).commit();
    }
    protected String getFileExtension(File file) {
        String extension = null;
        if (file.exists() && file!=null) {
            String absolutePath = file.getAbsolutePath();
            int i = absolutePath.lastIndexOf(".");
            extension = absolutePath.substring(i + 1);
            return extension;
        }
        return null;
    }
}
