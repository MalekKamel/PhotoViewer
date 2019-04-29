package com.sha.photoviewer;

import android.app.Dialog;
import android.view.KeyEvent;

class PhotosDialog extends Dialog {

    PhotosDialog(Options options) {
        super(options.context, options.dialogStyle());
        setup(options);
    }

    private void setup(Options options) {
        options.dialog = this;
        PhotosView viewer = new PhotosView(options);

        setContentView(viewer);

        setupKeyListener(viewer);

        if (options.onDismissListener != null)
            setOnDismissListener(dialogInterface -> options.onDismissListener.onDismiss());
    }

    private void setupKeyListener(PhotosView viewer) {
        setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode != KeyEvent.KEYCODE_BACK
                    || event.getAction() != KeyEvent.ACTION_UP
                    || event.isCanceled())
                return false;


            if (viewer.isScaled()) {
                viewer.resetScale();
                return true;
            }

            dialog.cancel();
            return true;
        });
    }


}
