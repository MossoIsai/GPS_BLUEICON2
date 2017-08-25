package com.example.isaigarciamoso.gps_blueicon2.tools;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.inputmethod.InputMethodManager;


import com.example.isaigarciamoso.gps_blueicon2.adapters.AdapterHorario;
import com.example.isaigarciamoso.gps_blueicon2.models.response.ObjectListaHorario;

import java.util.List;

/**
 * Created by developer on 24/07/17.
 */

public class Constantes {

  // public static final String BASE_URL = "http://192.168.2.77:91/api/";
   public static final String BASE_URL = "http://api.distritosonata.com/api/";

    /** :::::: Metodo que lanza un AlertDialog ::::::**/
    public static void messageDialog(String title, String message, Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(null)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public static void messageDialogTwoButton(String title, String message, Context context, final List<ObjectListaHorario> objectListaHorarios, final ObjectListaHorario objectListaHorario, final AdapterHorario adapterHorario) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(null)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      //no hacer nada
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        objectListaHorarios.remove(objectListaHorario);
                        adapterHorario.notifyDataSetChanged();

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }
}
