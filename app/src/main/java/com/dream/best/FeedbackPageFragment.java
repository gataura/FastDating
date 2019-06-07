package com.dream.best;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;

public class FeedbackPageFragment extends DialogFragment {
    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setMessage(R.string.feedback_text)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }
}
