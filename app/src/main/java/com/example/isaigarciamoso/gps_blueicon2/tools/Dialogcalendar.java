package com.example.isaigarciamoso.gps_blueicon2.tools;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * Created by developer on 18/08/17.
 */

public class Dialogcalendar extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private EditText editText;

    public  Dialogcalendar(View  view){
        editText = (EditText)view;


    }
    public Dialog oncreateDialog(Bundle bundleInstance){

        int year = 0;
        int mes = 0 ;
        int day = 0 ;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            final Calendar calendar = Calendar.getInstance();
             year = calendar.get(Calendar.YEAR);
             mes = calendar.get(Calendar.MONTH);
             day = calendar.get( Calendar.DAY_OF_MONTH);

        }

        return new DatePickerDialog(getActivity(),this,year,mes,day);

    }
    @Override
    public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
      String fecha = anio+"-"+mes+"-"+dia;
        editText.setText(fecha);
    }
}
