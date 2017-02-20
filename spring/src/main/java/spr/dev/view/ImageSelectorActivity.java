package spr.dev.view;

import android.support.v4.app.FragmentActivity;

import java.io.File;

import spr.dev.view.framelayout.ImageSelectorFragment;

/**
 * Created by hanki on 2017/2/20.
 */

public class ImageSelectorActivity extends FragmentActivity implements
        ImageSelectorFragment.Callback {
    @Override
    public void onSingleImageSelected(String path) {

    }

    @Override
    public void onImageSelected(String path) {

    }

    @Override
    public void onImageUnselected(String path) {

    }

    @Override
    public void onCameraShot(File imageFile) {

    }
}
