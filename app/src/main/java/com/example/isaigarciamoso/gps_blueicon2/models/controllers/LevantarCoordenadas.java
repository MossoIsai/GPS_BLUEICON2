package com.example.isaigarciamoso.gps_blueicon2.models.controllers;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.isaigarciamoso.gps_blueicon2.R;
import com.example.isaigarciamoso.gps_blueicon2.adapters.AdapterGridView;
import com.example.isaigarciamoso.gps_blueicon2.adapters.AdapterHorario;
import com.example.isaigarciamoso.gps_blueicon2.http.ClienteRetrofit;
import com.example.isaigarciamoso.gps_blueicon2.http.HttpNegocios;
import com.example.isaigarciamoso.gps_blueicon2.models.request.NegocioRequest;
import com.example.isaigarciamoso.gps_blueicon2.models.response.DetalleNegocio;
import com.example.isaigarciamoso.gps_blueicon2.models.response.Horario;
import com.example.isaigarciamoso.gps_blueicon2.models.response.HorarioRespuesta;
import com.example.isaigarciamoso.gps_blueicon2.models.response.NegocioResponse;
import com.example.isaigarciamoso.gps_blueicon2.models.response.ObjectListaHorario;
import com.example.isaigarciamoso.gps_blueicon2.models.response.Semana;
import com.example.isaigarciamoso.gps_blueicon2.models.response.SuccesResponse;
import com.example.isaigarciamoso.gps_blueicon2.tools.AlertDialogLoading;
import com.example.isaigarciamoso.gps_blueicon2.tools.Constantes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by developer on 24/07/17.
 */

public class LevantarCoordenadas extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
   //pruebas
    private List<String> stringListHorarios = new ArrayList<>();
    private List<String> diasList = new ArrayList<>();


    private GoogleMap googleMap;
    private LocationManager locationManager;
    private List<Horario> horarioList = new ArrayList<>();
    private AdapterGridView adapterGridView;
    private ArrayList<String> diasAgregados = new ArrayList<>();
    private boolean[] presionado = {false, false, false, false, false, false, false};
    private String[] dias = {"D", "L", "M", "Mi", "J", "V", "S",};
    private List<ObjectListaHorario> objectListaHorarios;
    private AdapterHorario adapterHorario;
    private List<Boolean> diasMarcados;
    private List<Semana> semanaList;
    private boolean[] arrayBoolean = {false, false, false, false, false, false, false};
    private double longitud;
    private double latitud;
    private int negocioId;
    private int ID_;
    private Date date;
    private Date fechaFormateada;
    String[] girosArray = {"Moda, Belleza", "Salud, Fitness", "Dónde Comer", "Cafeterías / Postrerías", "Vida Nocturna",
            "Entretenimiento", "Servicios", "Hogar", "Regalos y novedades", "Tecnología", "Tiendas departamentales", "Miscelaneas y Autoservicios", "Market", "Truck Market"};
    String[] estacionamiento = {"De la Plaza", "Frontal", "Techado", "No tiene"};
    private int idPrincipal;
    private int actualizar;


    @BindView(R.id.nameNegocio) EditText nombre;
    @BindView(R.id.addressNegocio) EditText direccion;
    @BindView(R.id.phonePrincipal) EditText principalTelefono;
    @BindView(R.id.phoneSecundario) EditText secundarioTelefono;
    @BindView(R.id.descriptionNegocio) EditText descripcion;
    @BindView(R.id.facebook) EditText facebook;
    @BindView(R.id.twitter) EditText twitter;
    @BindView(R.id.servicioDomicilionegocio) Switch servicioDomicilio;
    @BindView(R.id.mail) EditText mailNegocio;
    @BindView(R.id.textHorario) TextView textViewHorario;
    @BindView(R.id.horarioFormatoServidor) TextView textViewFomartoServidor;
    @BindView(R.id.btnHorario) Button btnAddHorario;
    @BindView(R.id.btnSave) Button btnSaveinfo;
    @BindView(R.id.recyclerHorario) RecyclerView recyclerView;
    @BindView(R.id.spinnerGiro) Spinner spinnerGiro;
    @BindView(R.id.spinnerEstacinamiento) Spinner spinnerEstacionamiento;
    @BindView(R.id.mapa) MapView mapView;
    @BindView(R.id.telefonoContacto) EditText telefonoContacto;
    @BindView(R.id.nombreContacto) EditText nombreContacto;
    @BindView(R.id.mailContacto) EditText mailContacto;
    @BindView(R.id.pendiente) Switch pendiete;
    @BindView(R.id.observaciones) EditText observaciones;
    @BindView(R.id.fechaProximavista) EditText proximaVisita;
    @BindView(R.id.btnAgendarhorario) Button AgendarHorario;
    @BindView(R.id.txtHorarioLetra) TextView horarioLetra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levantar_coordenas_layout);
        init(savedInstanceState);


        proximaVisita.setEnabled(false);
        if (!isOnline(LevantarCoordenadas.this)) {
            messageDialogWifi(getString(R.string.app_name), "Por favor revisa que cuentes con conexión a Internet.", "Ir a Configuracion");
        }
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10 * 1000, 500, this);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        try {
            latitud = location.getLatitude();
            longitud = location.getLongitude();

        } catch (NullPointerException e) {
            System.out.println("Los valores son nullos");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)

    @OnClick(R.id.btnAgendarhorario)
    void agendarHorario() {


        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(LevantarCoordenadas.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                System.out.print("FUCK--->");
                // proximaVisita.setText(i+"-"+i1+"-"+i2);
                int mes = i1 + 1;
                String dia = "";
                String mesStr = "";
                if (i2 <= 9) {
                    dia = "0" + i2;
                } else {
                    dia = "" + i2;
                }
                if (mes <= 9) {
                    mesStr = "0" + mes;
                } else {
                    mesStr = "" + mes;
                }
                final String fecha = dia + "-" + mesStr + "-" + i;
                final String fecha3 = i + i1 + i2 + "";

                TimePickerDialog timePickerDialog = new TimePickerDialog(LevantarCoordenadas.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        proximaVisita.setText(fecha + " " + i + ":" + i1);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                        try {
                            date = formatter.parse(fecha + " " + i + ":" + i1);

                            fechaFormateada = formatter.parse(fecha + " " + i + ":" + i1);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, 20, 30, true);

                timePickerDialog.show();
            }
        }, year, mes, day);
        datePickerDialog.show();

    }

    @OnClick(R.id.btnHorario)
    void addHour() {
        DialogAddHour dialogAddHour = new DialogAddHour();
        dialogAddHour.showDialog(this);

    }

    @OnClick(R.id.btnSave)
    void btnSaveInfoNegocio() {
        btnSaveinfo.setEnabled(false);
        final AlertDialogLoading alertDialogLoading = new AlertDialogLoading(LevantarCoordenadas.this);
        alertDialogLoading.messageDialog("","");

        int diaNumero = 0;
        String nombreNegocio = nombre.getText().toString();
        String dir = direccion.getText().toString();
        String telefonoPNegocio = principalTelefono.getText().toString();
        String telefonoSNegocio = secundarioTelefono.getText().toString();
        String description = descripcion.getText().toString();
        String mail = mailNegocio.getText().toString();
        String face = facebook.getText().toString();
        String twit = twitter.getText().toString();

        //contacto
        String nameContacto = nombreContacto.getText().toString();
        String phoneContacto = telefonoContacto.getText().toString();
        String mailContact = mailContacto.getText().toString();
        String giro = spinnerGiro.getSelectedItem().toString();
        String estacionamiento = spinnerEstacionamiento.getSelectedItem().toString();

        boolean seleccionado = servicioDomicilio.isChecked();
        boolean pendiente = pendiete.isChecked();


        for (ObjectListaHorario objectListaHorario : objectListaHorarios) {
            for (Semana semana : objectListaHorario.getDiasMarcados()) {
                if (semana.isEstatus()) {
                    switch (semana.getNombreDia()) {
                        case "L":
                            diaNumero = 1;
                            break;
                        case "M":
                            diaNumero = 2;
                            break;
                        case "Mi":
                            diaNumero = 3;
                            break;
                        case "J":
                            diaNumero = 4;
                            break;
                        case "V":
                            diaNumero = 5;
                            break;
                        case "S":
                            diaNumero = 6;
                            break;
                        case "D":
                            diaNumero = 0;
                            break;
                    }
                    textViewHorario.append(diaNumero + "@" + objectListaHorario.getHoraApertura() + "-" + objectListaHorario.getHoraCerrado() + "|");
                }
            }
        }
        armarHorario(deleteLastCharacter(textViewHorario.getText().toString()), textViewFomartoServidor);
        HttpNegocios httpNegocios = ClienteRetrofit.getSharedInstance().create(HttpNegocios.class);

        if (idPrincipal == 0 && negocioId == 0 && ID_ == 0) {
            /*System.out.println("REGISTAR ::::::::::::::::->ID_"+ ID_+" NEGOCIOID"+negocioId);*/
            if (proximaVisita.getText().toString().equals("")) {
                fechaFormateada = null;
            }

            Call<SuccesResponse> responseCall = httpNegocios.sendNegocio(new NegocioRequest(ID_, null, nombreNegocio, String.valueOf(latitud), String.valueOf(longitud), dir, telefonoPNegocio, telefonoSNegocio, description, mail,
                    face, twit, giro, estacionamiento, deleteLastCharacter(textViewHorario.getText().toString()), seleccionado, nameContacto, mailContact, phoneContacto, false, observaciones.getText().toString(), fechaFormateada, pendiente));

            responseCall.enqueue(new Callback<SuccesResponse>() {
                @Override
                public void onResponse(Call<SuccesResponse> call, Response<SuccesResponse> response) {
                    if (response.body().isEstatus()) {
                        btnSaveinfo.setEnabled(true);
                        alertDialogLoading.closeMessage();
                        Intent intent = new Intent(getApplicationContext(), ListaNegocios.class);
                        startActivity(intent);
                    } else {
                        System.out.println("OCURRIO UN ERROR AL ENVIAR LOS DATOS: ");
                        Constantes.messageDialog("ERROR DE CONEXION", "No se pudo establecer la conexion al servidor.", LevantarCoordenadas.this);
                    }
                }

                @Override
                public void onFailure(Call<SuccesResponse> call, Throwable t) {
                    System.out.println("ERROR DE CONEXION :" + t.getMessage());
                    Constantes.messageDialog("ERROR DE CONEXIÓN: " + t.getMessage(), "No se pudo establecer la conexion al servidor.", LevantarCoordenadas.this);
                }
            });
        } else {
            //  System.out.println("ACTUALIZAR ::::::::::::::::-> ID_"+ ID_+" NEGOCIOID"+negocioId+" ID_PRINCOPAL: "+idPrincipal);
            // System.out.println();
            //Actualizar
            //Caundo ya hay registro
            if (proximaVisita.getText().toString().equals("")) {
                fechaFormateada = null;
            }
            if (negocioId == 0 && idPrincipal != 0) {
                //  System.out.println("Fue registrado");
                Call<SuccesResponse> responseCall = httpNegocios.sendNegocio(new NegocioRequest(idPrincipal, null, nombreNegocio, String.valueOf(latitud), String.valueOf(longitud), dir, telefonoPNegocio, telefonoSNegocio, description, mail,
                        face, twit, giro, estacionamiento, deleteLastCharacter(textViewHorario.getText().toString()), seleccionado, nameContacto, mailContact, phoneContacto, false, observaciones.getText().toString(), fechaFormateada, pendiente));

                responseCall.enqueue(new Callback<SuccesResponse>() {
                    @Override
                    public void onResponse(Call<SuccesResponse> call, Response<SuccesResponse> response) {
                        if (response.body().isEstatus()) {
                            btnSaveinfo.setEnabled(true);
                            alertDialogLoading.closeMessage();
                            System.out.println("ACTUALIZO :::::::::::::::::::::->");
                            Intent intent = new Intent(getApplicationContext(), ListaNegocios.class);
                            startActivity(intent);
                        } else {
                            System.out.println("OCURRIO UN ERROR AL ENVIAR LOS DATOS: ");
                        }
                    }
                    @Override
                    public void onFailure(Call<SuccesResponse> call, Throwable t) {
                        System.out.println("ERROR DE CONEXION :" + t.getMessage());
                        Constantes.messageDialog("ERROR DE CONEXIÓN: " + t.getMessage(), "No se pudo establecer la conexion al servidor.", LevantarCoordenadas.this);
                    }
                });
            } else if (negocioId == idPrincipal) {
                //   System.out.println("Ya esta registrado: "+nombreNegocio);
                // Cuando ya un registro

                if (proximaVisita.getText().toString().equals("")) {
                    fechaFormateada = null;
                }
                // System.out.print("ID::::::>"+ID_+" NEGOCIOID::::>"+negocioId+"ID_PRINCIPAL: "+idPrincipal);

                Call<SuccesResponse> responseCall = httpNegocios.sendNegocio(new NegocioRequest(actualizar, negocioId, nombreNegocio, String.valueOf(latitud), String.valueOf(longitud), dir, telefonoPNegocio, telefonoSNegocio, description, mail,
                        face, twit, giro, estacionamiento, deleteLastCharacter(textViewHorario.getText().toString()), seleccionado, nameContacto, mailContact, phoneContacto, false, observaciones.getText().toString(), fechaFormateada, pendiente));

                responseCall.enqueue(new Callback<SuccesResponse>() {
                    @Override
                    public void onResponse(Call<SuccesResponse> call, Response<SuccesResponse> response) {
                        if (response.body().isEstatus()) {
                            btnSaveinfo.setEnabled(true);
                            alertDialogLoading.closeMessage();
                            System.out.println("ACTUALIZO :::::::::::::::::::::->");
                            Intent intent = new Intent(getApplicationContext(), ListaNegocios.class);
                            startActivity(intent);
                        } else {
                            System.out.println("OCURRIO UN ERROR AL ENVIAR LOS DATOS: ");
                        }
                    }
                    @Override
                    public void onFailure(Call<SuccesResponse> call, Throwable t) {
                        System.out.println("ERROR DE CONEXION :" + t.getMessage());
                        Constantes.messageDialog("ERROR DE CONEXIÓN: " + t.getMessage(), "No se pudo establecer la conexion al servidor.", LevantarCoordenadas.this);
                    }
                });

            }

        }


    }


    public void init(Bundle savedInstanceState) {


        ButterKnife.bind(this);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        Bundle bundle = getIntent().getExtras();
        btnSaveinfo.setText("GEOLOCALIZANDO...");
        btnSaveinfo.setEnabled(false);
        negocioId = bundle.getInt("ID_NEGOCIO");
        ID_ = bundle.getInt("ID_TEMPORAL");
        actualizar = bundle.getInt("ID_TEMPORAL");

        //if (negocioId != 0) {
        obtenerInformacionNegocio(negocioId); //obteniendo informacion del negocio
        //}

        principalTelefono.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        secundarioTelefono.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        objectListaHorarios = new ArrayList<>();
        diasMarcados = new ArrayList<>();
        semanaList = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.titulo, girosArray);
        spinnerGiro.setAdapter(adapter);

        ArrayAdapter adapterEstacionamiento = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.titulo, estacionamiento);
        spinnerEstacionamiento.setAdapter(adapterEstacionamiento);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        boolean success = this.googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_retro));
        this.googleMap.setPadding(10, 250, 10, 250);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }

        btnSaveinfo.setText("GUARDAR");
        btnSaveinfo.setEnabled(true);

        this.googleMap.setMyLocationEnabled(true);
        //this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        //  this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitud,longitud),16f));

    }

    /*** :::::::::::::: MUESTRA EL DIALOG DE LA VEMTANA ::::::::::::::: **/
    class DialogAddHour {

        public void showDialog(Activity activity) {
            System.out.println("INSTANCIA: ");

            presionado = new boolean[7];
            horarioList.clear();


            final Dialog dialog = new Dialog(activity);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.horario_dialog_layout);
            List<Horario> horarioList = new ArrayList<>();
            final AdapterGridView adapterGridView;

            horarioList.add(new Horario("D"));
            horarioList.add(new Horario("L"));
            horarioList.add(new Horario("M"));
            horarioList.add(new Horario("Mi"));
            horarioList.add(new Horario("J"));
            horarioList.add(new Horario("V"));
            horarioList.add(new Horario("S"));

            GridView gridView = (GridView) dialog.findViewById(R.id.gridHorarioDialog);
            final TimePicker timePickerInicio = (TimePicker) dialog.findViewById(R.id.picker1);
            final TimePicker timePickerCerrado = (TimePicker) dialog.findViewById(R.id.picker2);
            Button btnSaveHours = (Button) dialog.findViewById(R.id.saveHours);
            adapterGridView = new AdapterGridView(activity, horarioList);
            gridView.setAdapter(adapterGridView);
            gridView.setOnItemClickListener(listenerSelectedia);

            timePickerCerrado.setIs24HourView(true);
            timePickerInicio.setIs24HourView(true);

            btnSaveHours.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {

                    String minutosI = String.valueOf(timePickerInicio.getMinute());
                    String minutosF = String.valueOf(timePickerCerrado.getMinute());

                    if (minutosI.equals("0") || minutosF.equals("0")) {
                        minutosI = "00";
                        minutosF = "00";
                    }
                    if (Integer.parseInt(minutosI) == 1 || Integer.parseInt(minutosI) == 2 || Integer.parseInt(minutosI) == 3 || Integer.parseInt(minutosI) == 4 || Integer.parseInt(minutosI) == 5 || Integer.parseInt(minutosI) == 6 || Integer.parseInt(minutosI) == 7 || Integer.parseInt(minutosI) == 8 || Integer.parseInt(minutosI) == 9
                            || Integer.parseInt(minutosF) == 1 || Integer.parseInt(minutosF) == 2 || Integer.parseInt(minutosF) == 3 || Integer.parseInt(minutosF) == 4 || Integer.parseInt(minutosF) == 5 || Integer.parseInt(minutosF) == 6 || Integer.parseInt(minutosF) == 7 || Integer.parseInt(minutosF) == 8 || Integer.parseInt(minutosF) == 9) {
                        minutosI = "0" + minutosI;
                        minutosF = "0" + minutosF;

                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        List<Semana> semanaList2 = new ArrayList<Semana>();
                        for (int i = 0; i < 7; i++) {
                            semanaList2.add(new Semana(dias[i], presionado[i], i));
                            //System.out.println("Dias de la semana: " + semanaList.get(i).isEstatus());
                            System.out.println("Dias de la semana: " + dias[i] + " " + presionado[i]);
                        }
                        for (Semana semana : semanaList2) {
                            System.out.println("SEMANOTA: " + semana.getNombreDia() + " " + semana.isEstatus());
                        }

                        objectListaHorarios.add(new ObjectListaHorario(semanaList2, timePickerInicio.getHour() + ":" + minutosI, timePickerCerrado.getHour() + ":" + minutosF));

                    }
                    adapterHorario = new AdapterHorario(objectListaHorarios, new AdapterHorario.OnItem() {
                        @Override
                        public void onItemClick(ObjectListaHorario objectListaHorario) {

                        }

                        @Override
                        public void onItemLongClick(ObjectListaHorario objectListaHorario) {
                            System.out.println("Evento de borrar: ");
                            Constantes.messageDialogTwoButton("Eliminar", "Deseas Eliminar el item selecionado", LevantarCoordenadas.this, objectListaHorarios, objectListaHorario, adapterHorario);
                            adapterHorario.notifyDataSetChanged();

                        }
                    });

                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapterHorario);
                    dialog.dismiss();

                    for (ObjectListaHorario objectListaHorario : objectListaHorarios) {
                        for (Semana item : objectListaHorario.getDiasMarcados()) {
                            System.out.println("fuck: " + item.getNombreDia() + " " + item.isEstatus());
                        }
                    }
                }
            });


            dialog.show();

        }

        //Lista  selecionada
        AdapterView.OnItemClickListener listenerSelectedia = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TextView textView = (TextView) view.findViewById(R.id.textDiaSemana);
                if (presionado[position]) {
                    presionado[position] = false;
                    for (int i = 0; i < presionado.length; i++) {
                        if (!presionado[i]) {
                            presionado[i] = false;
                            //color gris
                            textView.setTextColor(Color.parseColor("#8b8b8b"));
                        }
                    }

                } else {  // cuando es primera vez true
                    presionado[position] = true;
                    for (int i = 0; i < presionado.length; i++) {
                        if (presionado[i]) {
                            //azul
                            presionado[i] = true;
                            textView.setTextColor(Color.parseColor("#20bbcc"));
                        }
                    }
                }
                /*for (boolean item : presionado) {
                    System.out.println("VALOR: " + item);
                }*/
            }
        };
    }

    public String armarHorario(String cadena, TextView formatoFinal) {
        int contador = 0;
        String[] diaCompleto = cadena.split("\\|");
        for (int i = 0; i < diaCompleto.length; i++) {
            for (int j = 0; j < diaCompleto.length - 1; j++) {
                if (!diaCompleto[i].equals(diaCompleto[j + 1])) {
                    if (diaCompleto[i].charAt(0) == diaCompleto[j + 1].charAt(0)) {
                        contador++;
                        //System.out.println("Somo iguales: " + diaCompleto[i] + " ----->" + diaCompleto[j+1]);
                        String secondHorario = diaCompleto[j + 1].substring(1, diaCompleto[j + 1].length());
                        //System.out.println("listona: "+diaCompleto[i]+secondHorario+"|");
                        formatoFinal.append(diaCompleto[i] + secondHorario + "|");

                    } else {
                        System.out.println("No Somo iguales");
                        break;
                    }
                }

            }
        }
        System.out.println("LISTA FINAL: " + formatoFinal.getText().toString());
        return formatoFinal.getText().toString();
    }

    public String deleteLastCharacter(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == '|') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public void obtenerInformacionNegocio(int idNegocio) {

        if (idNegocio == 0) {
            /** :::::::::::  Obtener información del local :::::: **/
            HttpNegocios httpNegocios = ClienteRetrofit.getSharedInstance().create(HttpNegocios.class);
            Call<NegocioResponse> responseCall = httpNegocios.obtenerDetalleNegocio(0, ID_);
            responseCall.enqueue(new Callback<NegocioResponse>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call<NegocioResponse> call, Response<NegocioResponse> response) {
                    if (response.body().isEstatus()) {
                        System.out.println("obteniendo valores con exito: " + response.body().getNegocioRequest().toString());
                        DetalleNegocio detalleNegocio = response.body().getNegocioRequest();


                        System.out.println("Horario: " + detalleNegocio.getHorario().toString());

                        nombre.setText(detalleNegocio.getNombre());
                        direccion.setText(detalleNegocio.getDireccion());
                        principalTelefono.setText(detalleNegocio.getTelefonoPrincipal());
                        secundarioTelefono.setText(detalleNegocio.getTelefonoSecundario());
                        descripcion.setText(detalleNegocio.getDescripcion());
                        facebook.setText(detalleNegocio.getFacebook());
                        twitter.setText(detalleNegocio.getTwitter());
                        telefonoContacto.setText(detalleNegocio.getTelefonoContacto());
                        mailContacto.setText(detalleNegocio.getMailContacto());
                        nombreContacto.setText(detalleNegocio.getNombreContacto());
                        mailNegocio.setText(detalleNegocio.getMail());
                        ID_ = detalleNegocio.getId();
                        observaciones.setText(detalleNegocio.getObservaciones());
                        proximaVisita.setText(formatterDate(detalleNegocio.getProximaVisita()));

                        String ID_PADRE = String.valueOf(detalleNegocio.getNegocioId());
                        System.out.println("NEGOCIO 1 ------>: " + ID_PADRE);
                        idPrincipal = detalleNegocio.getNegocioId();


                        if (detalleNegocio.isPendiente()) {
                            pendiete.setChecked(true);
                        } else {
                            pendiete.setChecked(false);
                        }
                        if (detalleNegocio.isServicioDomicilio()) {
                            servicioDomicilio.setChecked(true);
                        } else {
                            servicioDomicilio.setChecked(false);
                        }


                        //  System.out.println("HORARIO DEL NEGOCIO: "+detalleNegocio.getHorarioRespuestas().get(0).getHorarioFin());
                        /**List<Semana> semanaList3 = new ArrayList<Semana>();
                         final List<ObjectListaHorario> objectListaHorarios = new ArrayList<ObjectListaHorario>();
                         String horaInicio = "";
                         String horaeioFin = "";*/

                        System.out.println("HORARIO TEXTPLAIN: " + response.body().getNegocioRequest().getHorario());
                        System.out.println("HORARIO TEXTPLAIN: " + response.body().getNegocioRequest().getHorarioRespuestas());


                        String cadenaAllHorarios = response.body().getNegocioRequest().getHorario();
                        //despedazarCadena(cadenaAllHorarios);

                        /*for (HorarioRespuesta respuesta : detalleNegocio.getHorarioRespuestas()) {
                            System.out.println("aqui se va agregar la respuesta: " + respuesta.getDias());
                            switch (respuesta.getDias()) {
                                case 0:
                                    semanaList3.add(new Semana("D", true, 0));
                                    arrayBoolean[0] = true;
                                    break;
                                case 1:
                                    arrayBoolean[1] = true;
                                    semanaList3.add(new Semana("L", true, 1));

                                    break;
                                case 2:
                                    arrayBoolean[2] = true;
                                    semanaList3.add(new Semana("M", true, 2));
                                    break;
                                case 3:
                                    arrayBoolean[3] = true;
                                    semanaList3.add(new Semana("Mi", true, 3));
                                    break;
                                case 4:
                                    arrayBoolean[4] = true;
                                    semanaList3.add(new Semana("J", true, 4));
                                    break;
                                case 5:
                                    arrayBoolean[5] = true;
                                    semanaList3.add(new Semana("V", true, 5));
                                    break;
                                case 6:

                                    arrayBoolean[6] = true;
                                    semanaList3.add(new Semana("S", true, 6));
                                    break;

                            }

                        }*/

                        /** for (HorarioRespuesta item : detalleNegocio.getHorarioRespuestas()) {
                         horaInicio = item.getHorarioInicio();
                         horaeioFin = item.getHorarioFin();
                         }*/

                        /**objectListaHorarios.add(new ObjectListaHorario(semanaList3, horaInicio, horaeioFin));**/

                        /** for (int i = 0; i < girosArray.length; i++) {
                         System.out.println("GIRO:  " + detalleNegocio.getGiro() + " -------> " + girosArray[i]);
                         if (girosArray[i].equals(detalleNegocio.getGiro())) {
                         spinnerGiro.setSelection(getIndex(spinnerGiro, girosArray[i]));
                         }
                         }
                         for (int i = 0; i < estacionamiento.length; i++) {
                         if (estacionamiento[i].equals(detalleNegocio.getEstacionamiento())) {
                         spinnerEstacionamiento.setSelection(i);
                         }
                         }**/

                        //    System.out.println(detalleNegocio.getLalitud() + " " + detalleNegocio.getLongitud() + " " + detalleNegocio.getMailContacto() + " " +
                        //          "" + detalleNegocio.getNombreContacto() + " " + detalleNegocio.isServicioDomicilio());

                        List<HorarioRespuesta> horarioRespuestas = new ArrayList<HorarioRespuesta>();

                        horarioRespuestas = response.body().getNegocioRequest().getHorarioRespuestas();
                        for (HorarioRespuesta horarioRespuesta : horarioRespuestas) {
                            int cadena = horarioRespuesta.getDias();
                            String diaLetra = "";
                            if (cadena == 0) {
                                diaLetra = "Domingo";
                            } else if (cadena == 1) {
                                diaLetra = "Lunes";
                            } else if (cadena == 2) {
                                diaLetra = "Martes";
                            } else if (cadena == 3) {
                                diaLetra = "Miercoles";
                            } else if (cadena == 4) {
                                diaLetra = "Jueves";
                            } else if (cadena == 5) {
                                diaLetra = "Viernes";
                            } else if (cadena == 6) {
                                diaLetra = "Sabado";
                            }
                            System.out.println("DIA HORARIO: " + diaLetra + "-->" + horarioRespuesta.getHorarioInicio() + "-" + horarioRespuesta.getHorarioFin() + " ");
                            horarioLetra.append(diaLetra + " " + horarioRespuesta.getHorarioInicio() + "-" + horarioRespuesta.getHorarioFin()+"\n");
                            stringListHorarios.add(horarioRespuesta.getHorarioInicio()+"-"+horarioRespuesta.getHorarioFin());
                            diasList.add(diaLetra);
                        }

                    } else {
                        System.out.println("DEVULVE FALSE");
                    }
                }

                @Override
                public void onFailure(Call<NegocioResponse> call, Throwable t) {
                    System.out.println("OCURRIO UN ERROR CON LA CONSULTA DEL DETALLE: " + t.getMessage());
                    Constantes.messageDialog("ERROR DE CONEXIÓN: " + t.getMessage(), "No se pudo establecer la conexion al servidor.", LevantarCoordenadas.this);
                }
            });
            /** ::::::: Obtener información con al condicion del else:::::::::::: **/
        } else {

            HttpNegocios httpNegocios = ClienteRetrofit.getSharedInstance().create(HttpNegocios.class);
            Call<NegocioResponse> responseCall = httpNegocios.obtenerDetalleNegocio(idNegocio, 0);
            responseCall.enqueue(new Callback<NegocioResponse>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call<NegocioResponse> call, Response<NegocioResponse> response) {
                    if (response.body().isEstatus()) {
                        System.out.println("obteniendo valores con exito: " + response.body().getNegocioRequest().toString());
                        DetalleNegocio detalleNegocio = response.body().getNegocioRequest();

                        nombre.setText(detalleNegocio.getNombre());
                        direccion.setText(detalleNegocio.getDireccion());
                        principalTelefono.setText(detalleNegocio.getTelefonoPrincipal());
                        secundarioTelefono.setText(detalleNegocio.getTelefonoSecundario());
                        descripcion.setText(detalleNegocio.getDescripcion());
                        facebook.setText(detalleNegocio.getFacebook());
                        twitter.setText(detalleNegocio.getTwitter());
                        telefonoContacto.setText(detalleNegocio.getTelefonoContacto());
                        mailContacto.setText(detalleNegocio.getMailContacto());
                        nombreContacto.setText(detalleNegocio.getNombreContacto());
                        mailNegocio.setText(detalleNegocio.getMail());
                        observaciones.setText(detalleNegocio.getObservaciones());
                        System.out.print("FECHA: ---->" + detalleNegocio.getProximaVisita());

                        // Date date = formatter2.parse(detalleNegocio.getProximaVisita());
                        proximaVisita.setText(formatterDate(detalleNegocio.getProximaVisita()));

                        ID_ = detalleNegocio.getId();
                        String ID_PADRE = String.valueOf(detalleNegocio.getNegocioId());
                        System.out.println("NEGOCIO 2: ---->" + ID_PADRE);
                        idPrincipal = detalleNegocio.getNegocioId();

                        if (detalleNegocio.isPendiente()) {
                            pendiete.setChecked(true);
                        } else {
                            pendiete.setChecked(false);
                        }
                        if (detalleNegocio.isServicioDomicilio()) {
                            servicioDomicilio.setChecked(true);
                        } else {
                            servicioDomicilio.setChecked(false);
                        }

                        System.out.println("HORARIO TEXTPLAIN " + response.body().getNegocioRequest().getHorario());
                        System.out.println("HORARIO TEXTPLAIN " + response.body().getNegocioRequest().getHorarioRespuestas());
                        List<HorarioRespuesta> horarioRespuestas = new ArrayList<HorarioRespuesta>();

                        horarioRespuestas = response.body().getNegocioRequest().getHorarioRespuestas();
                        for (HorarioRespuesta horarioRespuesta : horarioRespuestas) {
                            int cadena = horarioRespuesta.getDias();
                            String diaLetra = "";
                            if (cadena == 0) {
                                diaLetra = "Domingo";
                            } else if (cadena == 1) {
                                diaLetra = "Lunes";
                            } else if (cadena == 2) {
                                diaLetra = "Martes";
                            } else if (cadena == 3) {
                                diaLetra = "Miercoles";
                            } else if (cadena == 4) {
                                diaLetra = "Jueves";
                            } else if (cadena == 5) {
                                diaLetra = "Viernes";
                            } else if (cadena == 6) {
                                diaLetra = "Sabado";
                            }
                            System.out.println("DIA HORARIO: " + diaLetra + "-->" + horarioRespuesta.getHorarioInicio() + "-" + horarioRespuesta.getHorarioFin() + " ");
                            horarioLetra.append(diaLetra + " " + horarioRespuesta.getHorarioInicio() + "-" + horarioRespuesta.getHorarioFin()+"\n");
                            stringListHorarios.add(horarioRespuesta.getHorarioInicio()+"-"+horarioRespuesta.getHorarioFin());
                            diasList.add(diaLetra);
                        }

                        for(int i = 0 ; i< stringListHorarios.size()-1; i++){
                             System.out.println("GRUPO: "+i+"--COMPARO--"+(i+1));
                            if(stringListHorarios.get(i).equals(stringListHorarios.get(i+1))){
                                System.out.println("DIA CON EL MISMO HORARIO "+diasList.get(i));

                            }

                        }
                        //  System.out.println("HORARIO DEL NEGOCIO: "+detalleNegocio.getHorarioRespuestas().get(0).getHorarioFin());
                        List<Semana> semanaList3 = new ArrayList<Semana>();
                        final List<ObjectListaHorario> objectListaHorarios = new ArrayList<ObjectListaHorario>();
                        String horaInicio = "";
                        String horaeioFin = "";

                        System.out.println("HORARIO TEXTPLAIN: " + response.body().getNegocioRequest().getHorario());
                        String cadenaAllHorarios = response.body().getNegocioRequest().getHorario();
                        despedazarCadena(cadenaAllHorarios);

                        for (HorarioRespuesta respuesta : detalleNegocio.getHorarioRespuestas()) {
                            System.out.println("aqui se va agregar la respuesta: " + respuesta.getDias());
                            switch (respuesta.getDias()) {
                                case 0:
                                    semanaList3.add(new Semana("D", true, 0));
                                    arrayBoolean[0] = true;
                                    break;
                                case 1:
                                    arrayBoolean[1] = true;
                                    semanaList3.add(new Semana("L", true, 1));

                                    break;
                                case 2:
                                    arrayBoolean[2] = true;
                                    semanaList3.add(new Semana("M", true, 2));
                                    break;
                                case 3:
                                    arrayBoolean[3] = true;
                                    semanaList3.add(new Semana("Mi", true, 3));
                                    break;
                                case 4:
                                    arrayBoolean[4] = true;
                                    semanaList3.add(new Semana("J", true, 4));
                                    break;
                                case 5:
                                    arrayBoolean[5] = true;
                                    semanaList3.add(new Semana("V", true, 5));
                                    break;
                                case 6:

                                    arrayBoolean[6] = true;
                                    semanaList3.add(new Semana("S", true, 6));
                                    break;

                            }

                        }

                        for (HorarioRespuesta item : detalleNegocio.getHorarioRespuestas()) {
                            horaInicio = item.getHorarioInicio();
                            horaeioFin = item.getHorarioFin();
                        }

                        objectListaHorarios.add(new ObjectListaHorario(semanaList3, horaInicio, horaeioFin));

                        for (int i = 0; i < girosArray.length; i++) {
                            System.out.println("GIRO:  " + detalleNegocio.getGiro() + " -------> " + girosArray[i]);
                            if (girosArray[i].equals(detalleNegocio.getGiro())) {
                                ///  System.out.println("GIRO:  " + detalleNegocio.getGiro() + " ------------------------> " + girosArray[i]);
                                // System.out.println("SELECCIONADO :::::: ->" + spinnerGiro.getSelectedItemPosition());
                                // System.out.println("SELECCIONA DENTRO DEL LA SECCION DE ITEM");
                                //spinnerGiro.setSelection(i);
                                spinnerGiro.setSelection(getIndex(spinnerGiro, girosArray[i]));
                            }
                        }
                        for (int i = 0; i < estacionamiento.length; i++) {
                            //System.out.println("GIRO:  "+detalleNegocio.getGiro());
                            if (estacionamiento[i].equals(detalleNegocio.getEstacionamiento())) {
                                spinnerEstacionamiento.setSelection(i);
                            }
                        }


                        /*System.out.println(detalleNegocio.getLalitud() + " " + detalleNegocio.getLongitud() + " " + detalleNegocio.getMailContacto() + " " +
                                "" + detalleNegocio.getNombreContacto() + " " + detalleNegocio.isServicioDomicilio());*/

                    } else {
                        System.out.println("DEVUELVE FALSE");
                    }
                }

                @Override
                public void onFailure(Call<NegocioResponse> call, Throwable t) {
                    ///  System.out.println("OCURRIO UN ERROR: " + t.getMessage());
                    Constantes.messageDialog("ERROR DE CONEXIÓN: " + t.getMessage(), "No se pudo establecer la conexion al servidor.", LevantarCoordenadas.this);
                }
            });
        }
    }

    //private method of your class
    private int getIndex(Spinner spinner, String myString) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
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
            /** ::::::::: MANDO A LLAMAR EL METODO QU SE ENCUENTRA DENTRO DE COMTRANTES ::::::::***/
                Constantes.hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    /**** :::::EVENTOS DEL GPS PROVIDER ::::::***/
    @Override
    public void onLocationChanged(Location location) {

        latitud = location.getLatitude();
        longitud = location.getLongitude();
        //System.out.println("LATITUD: " + latitud + " LOOGITUD: " + longitud);
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitud, longitud), 16f));
        // Toast.makeText(getApplicationContext(),"LATITUD: "+latitud+" LOOGITUD: "+longitud,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("GPS", "PROVIDER: " + provider + " ESTATUS: " + status + " extras: " + extras.toString());
        // Toast.makeText(getApplicationContext(),"Actulizando: "+latitud+" "+longitud+" ",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("GPS", provider);

    }

    @Override
    public void onProviderDisabled(String provider) {
        // System.out.println("PROVIDER : " + provider);
        //Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        //frfr startActivity(intent);
        /*Toast.makeText(getBaseContext(), "Es n",
             Toast.LENGTH_SHORT).show();*/

    }

    public void despedazarCadena(String cadena) {

        System.out.println("Cadena completa: " + cadena);
        String[] diasconHorario = cadena.split("\\|");
        List<Semana> semanaList4 = new ArrayList<Semana>();
        List<ObjectListaHorario> listHorarioObject = new ArrayList<ObjectListaHorario>();
        String horaInicio = "";
        String horarioFin = "";
        List<Integer> diasAgregados = new ArrayList<>();
        int[] staticDias = {0, 1, 2, 3, 4, 5, 6};
        String onlyHorario = "";

        int sizeHorario = 0;
        List<Character> diasNumero = new ArrayList<>();
        try {
            for (int i = 0; i < diasconHorario.length; i++) {
                System.out.println("ITEM CON HORARIO : " + diasconHorario[i]);
                diasNumero.add(diasconHorario[i].charAt(0));
                System.out.println("OBTENER PRIMER PARAMETRO: " + diasNumero.get(i));
                sizeHorario = diasconHorario[i].length();
                for (int j = 0; j < diasconHorario[i].length(); j++) {
                    System.out.println("CHAR: " + diasconHorario[i].charAt(j));
                    if (diasconHorario[i].charAt(j) == '@') {
                        System.out.println("El dias tiene un arroba en la posicion: " + diasconHorario[i].charAt(j));
                        String horario = diasconHorario[i].substring(j + 1, diasconHorario[i].length());
                        System.out.println("ES DIFERENTE DE @ " + horario);
                        for (int count = 0; count < horario.length(); count++) {
                            if (horario.charAt(count) != '@') {
                                //Logica de un solo horario
                                onlyHorario = horario.substring(0, horario.length());
                                System.out.println("SOLO HORARIO: " + onlyHorario);
                                break;
                            } else {
                                //cadenda con doble horario
                                String primerHorario = horario.substring(0, count);
                                String segundoHorario = horario.substring(count, horario.length());
                                System.out.println("Primero Horario : " + primerHorario);
                                System.out.println("Segundo Horario : " + segundoHorario);
                                break;
                            }
                        }

                    }
                }

            }//Catch
        } catch (IndexOutOfBoundsException e) {
            System.out.println("ERROR DE IndexOutOfBoundsException: " + e.getMessage());

        }
        //Aqui cierrar el for
        for (int i = 0; i < diasNumero.size(); i++) {
            diasAgregados.add(Integer.parseInt(String.valueOf(diasNumero.get(i))));
            System.out.println("DIAS MARCADOS : " + diasAgregados.get(i));

        }
        for (int i = 0; i < diasAgregados.size(); i++) {
            //Domingo
            System.out.println("Marcados::::::>" + diasAgregados.get(i));

            if (diasAgregados.get(i) == 0) {
                System.out.println("Estoy entrando en el true");
                semanaList4.add(new Semana("D", true, 0));

            }
            if (diasAgregados.get(i) == 1) {
                System.out.println("Estoy entrando en el true");
                semanaList4.add(new Semana("L", true, 1));

            }
            if (diasAgregados.get(i) == 2) {
                System.out.println("Estoy entrando en el true");
                semanaList4.add(new Semana("M", true, 2));

            }
            if (diasAgregados.get(i) == 3) {
                System.out.println("Estoy entrando en el true");
                semanaList4.add(new Semana("Mi", true, 3));

            }
            if (diasAgregados.get(i) == 4) {
                System.out.println("Estoy entrando en el true");
                semanaList4.add(new Semana("J", true, 4));

            }
            if (diasAgregados.get(i) == 5) {
                System.out.println("Estoy entrando en el true");
                semanaList4.add(new Semana("V", true, 5));

            }
            if (diasAgregados.get(i) == 6) {
                System.out.println("Estoy entrando en el true");
                semanaList4.add(new Semana("S", true, 6));
            }


        }
        objectListaHorarios.add(new ObjectListaHorario(semanaList4, onlyHorario, horarioFin));
        adapterHorario = new AdapterHorario(objectListaHorarios, new AdapterHorario.OnItem() {
            @Override
            public void onItemClick(ObjectListaHorario objectListaHorario) {

            }

            @Override
            public void onItemLongClick(ObjectListaHorario objectListaHorario) {
                Constantes.messageDialogTwoButton("Eliminar", "Deseas Eliminar el item selecionado", LevantarCoordenadas.this, objectListaHorarios, objectListaHorario, adapterHorario);
                adapterHorario.notifyDataSetChanged();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapterHorario);

    }
    //Alert  dialog builder


    public static boolean isOnline(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {
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

    //2017-08-31T18:30:00
    public String formatterDate(String fecha) {
        if (fecha != null) {
            String[] pedazos = fecha.split("-");
            String anio = pedazos[0];
            String mes = pedazos[1];
            String[] diaArray = pedazos[2].split("T");
            String dia = diaArray[0];
            String horario = diaArray[1];
            String[] horas = horario.split(":");
            String hora = horas[0];
            String minutos = horas[1];
            return dia + "-" + mes + "-" + anio + " " + hora + ":" + minutos;
        } else {
            return "";
        }

    }


}
