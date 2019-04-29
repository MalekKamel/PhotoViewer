package com.sha.frescoimageviewersample;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class StylingOptions {

    public void showDialog(Context context) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setMultiChoiceItems(
                        context.getResources().getStringArray(R.array.options),
                        Option.toArray(),
                        (dialog1, position, isChecked) -> Option.values()[position].value = isChecked
                ).create();
        dialog.show();
    }
}
