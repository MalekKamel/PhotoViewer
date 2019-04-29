package com.sha.frescoimageviewersample;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sha.frescoimageviewersample.data.Data;
import com.sha.frescoimageviewersample.data.Image;
import com.sha.photoviewer.Builder;
import com.sha.photoviewer.PhotoViewer;
import com.sha.photoviewer.listener.OnPhotoSelectedListener;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private PhotoOverlay overlay;

    private List<Image> images = Data.images();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        setSupportActionBar(findViewById(R.id.toolbar));

        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(this, 3));
        PhotosAdapter adapter = new PhotosAdapter(
                images,
                (url, position) -> showViewer(position)
        );
        rv.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.styling_options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        showDialog();
        return super.onOptionsItemSelected(item);
    }

    void showDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMultiChoiceItems(
                        getResources().getStringArray(R.array.options),
                        Option.toArray(),
                        (dialog1, position, isChecked) -> Option.values()[position].value = isChecked
                ).create();
        dialog.show();
    }

    private void showViewer(int startIndex) {
        Builder builder = PhotoViewer.build(this, Data.urls())
                .startAtIndex(startIndex)
                .setOnDismissListener(
                        () -> Toast.makeText(
                                this,
                                R.string.message_on_dismiss,
                                Toast.LENGTH_SHORT
                        ).show()
                );

        builder.showStatusBar(Option.SHOW_STATUS_BAR.value);

        if (Option.PHOTO_MARGIN.value)
            builder.setPhotoMargin(this, R.dimen.image_margin);


        if (Option.CONTAINER_PADDING.value)
            builder.setContainerPadding(this, R.dimen.image_margin);

        builder.setCanSwipeToDismiss(Option.SWIPE_TO_DISMISS.value);

        builder.setZoomable(Option.ZOOMING.value);

        if (Option.SHOW_OVERLAY.value) {
            overlay = new PhotoOverlay(this);
            builder.setOverlayView(overlay);
            builder.setOnPhotoSelectedListener(getImageChangeListener());
        }

        if (Option.RANDOM_BACKGROUND.value)
            builder.setBackgroundColor(getRandomColor());

        builder.show();
    }

    private OnPhotoSelectedListener getImageChangeListener() {
        return (url, position) -> {
            overlay.setShareText(url);
            overlay.setDescription(images.get(position).description);
        };
    }

    private int getRandomColor() {
        Random random = new Random();
        return Color.argb(255, random.nextInt(156), random.nextInt(156), random.nextInt(156));
    }
}
