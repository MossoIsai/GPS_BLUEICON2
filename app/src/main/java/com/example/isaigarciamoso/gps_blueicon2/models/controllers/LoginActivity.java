package com.example.isaigarciamoso.gps_blueicon2.models.controllers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.isaigarciamoso.gps_blueicon2.R;
import com.example.isaigarciamoso.gps_blueicon2.tools.Constantes;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.usuario) EditText usuario;
    @BindView(R.id.pwd) EditText password;
    @BindView(R.id.btnSend) Button btnEnter;
    public static final String PREFERENCIAS = "PREFERENCIAS";
    public static final int MODE_PRIVATE = 0;

    @OnClick(R.id.btnSend) void eventEnter(){

        String user =  usuario.getText().toString();
        String pwd =  password.getText().toString();

        if(!user.equals("blueicon") && !pwd.equals("2468")) {
            Constantes.messageDialog("GPS", "Los datos no son correctos", this);
        }else {
            Intent intent = new Intent(this, ListaNegocios.class);
            startActivity(intent);
            saveDataUser(user,pwd);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = this.getSharedPreferences(PREFERENCIAS, MODE_PRIVATE);
        boolean valor = sharedPreferences.getBoolean("SESSION", false);

        if(valor){
            Intent intent = new Intent(this,ListaNegocios.class);
            startActivity(intent);
            finish();
        }else{

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ButterKnife.bind(this);
        if (!isOnline(LoginActivity.this)) {
            messageDialogWifi(getString(R.string.app_name), "Por favor revisa que cuentes con conexión a Internet.", "Ir a Configuracion");
        }else {

        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
            // hideKeyboard(this);
            /** ::::::::: MANDO A LLAMAR EL METODO QU SE ENCUENTRA DENTRO DE COMTRANTES ::::::::***/
                Constantes.hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }
    //salva los datos del usuario en las preferencias del usuario
    public void saveDataUser(String user,String password) {

        SharedPreferences.Editor editor = getSharedPreferences(PREFERENCIAS,MODE_PRIVATE).edit();
        editor.putString("USER", user);
        editor.putString("PWD",password );
        editor.putBoolean("SESSION",true);
        editor.commit();

    }


    public static boolean isOnline(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info != null && info.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
    public void messageDialogWifi(String titulo, String mensaje, String btnNombre) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton(btnNombre, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

                    }
                });


        AlertDialog alert = builder.create();
        alert.show();
    }
}
