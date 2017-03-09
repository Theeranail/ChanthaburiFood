package com.example.theeranaiasipong.chanthaburifood.model;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.theeranaiasipong.chanthaburifood.R;

/**
 * Created by TheeranaiAsipong on 6/6/2559.
 */
public class CreateFuction {

    Context context;

    public CreateFuction(Context context) {
        this.context = context;
    }

    public void AlertDialogOK(String msg) {

        AlertDialog.Builder dbuilder = new AlertDialog.Builder(context);
        dbuilder.setIcon(R.drawable.ic_error_outline_black_24dp);
        dbuilder.setMessage(msg);
        dbuilder.setPositiveButton("ตกลง", null);
        dbuilder.show();

    }
}
