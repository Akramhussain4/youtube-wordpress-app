package com.tbv.akramhussain.thebaiganvines.Misc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by Akram Hussain on 1/29/2017.
 */

public class AlertDialogFragment  extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("OOPS!..Sorry!")
                .setMessage("There was an error.Please try again!")
                .setPositiveButton("OK",null);

        AlertDialog dialog = builder.create();
        return  dialog;
    }
}
