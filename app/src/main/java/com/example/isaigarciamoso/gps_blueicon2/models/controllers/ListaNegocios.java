package com.example.isaigarciamoso.gps_blueicon2.models.controllers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.isaigarciamoso.gps_blueicon2.R;
import com.example.isaigarciamoso.gps_blueicon2.adapters.AdapterNegocio;
import com.example.isaigarciamoso.gps_blueicon2.http.ClienteRetrofit;
import com.example.isaigarciamoso.gps_blueicon2.http.HttpNegocios;
import com.example.isaigarciamoso.gps_blueicon2.models.response.Negocio;
import com.example.isaigarciamoso.gps_blueicon2.models.response.NegocioMain;
import com.example.isaigarciamoso.gps_blueicon2.models.response.NegocioResponse;
import com.example.isaigarciamoso.gps_blueicon2.tools.Constantes;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by developer on 24/07/17.
 */

public class ListaNegocios extends AppCompatActivity {

    @BindView(R.id.listaNegocios) ListView listView;
    @BindView(R.id.busqueda) EditText editText;
    AdapterNegocio adapterNegocio;
    TextView addNewNegocio;
    private ArrayList<Negocio> negocioList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int arrayColors[] = {R.color.colorPrimary, R.color.naranja, R.color.rojo, R.color.verde};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_negocios_layout);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        negocioList =  new ArrayList<>();

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshCharola);
        swipeRefreshLayout.setColorSchemeResources(arrayColors);
        swipeRefreshLayout.setOnRefreshListener(refreshListener);

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.nav_layout);

        addNewNegocio = (TextView) findViewById(R.id.addNewItem);
        addNewNegocio.setOnClickListener(listener);
        if (!isOnline(ListaNegocios.this)) {
            messageDialogWifi(getString(R.string.app_name), "Por favor revisa que cuentes con conexión a Internet.", "Ir a Configuracion");
        } else {
            obtenerNegocios();
        }

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition =
                        (listView == null || listView.getChildCount() == 0) ?
                                0 : listView.getChildAt(0).getTop();
                swipeRefreshLayout.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }
        });

    }

    //Escuchador para alagregar
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Bundle bundle = new Bundle();
            Intent intent = new Intent(getApplicationContext(), LevantarCoordenadas.class);
            bundle.putInt("ID_NEGOCIO", 0);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };


    public void obtenerNegocios() {

        HttpNegocios httpNegocios = ClienteRetrofit.getSharedInstance().create(HttpNegocios.class);
        Call<NegocioMain> negocioMainClass = httpNegocios.obtenerNegocios();
        negocioMainClass.enqueue(new Callback<NegocioMain>() {

            @Override
            public void onResponse(Call<NegocioMain> call, final Response<NegocioMain> response) {
                if (response.body().isEstatus()) {
                    System.out.println("Mensaje de Textio " + response.body().isEstatus());
                    negocioList = (ArrayList<Negocio>) response.body().getNegocios();
                    adapterNegocio = new AdapterNegocio(getApplicationContext(),R.layout.item_layout ,negocioList);
                    listView.setAdapter(adapterNegocio);
                    swipeRefreshLayout.setRefreshing(false);


                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            System.out.println("ELEJISTE LA POSICION: " + response.body().getNegocios().get(i).getNegocioId());
                            System.out.println("ELEJISTE EL ID: " + response.body().getNegocios().get(i).getId());

                            Bundle bundle = new Bundle();
                            int negocioId = response.body().getNegocios().get(i).getNegocioId();
                            int id = response.body().getNegocios().get(i).getId();

                            Intent intent = new Intent(getApplicationContext(), LevantarCoordenadas.class);
                            bundle.putInt("ID_NEGOCIO", negocioId);
                            bundle.putInt("ID_TEMPORAL", id);
                            System.out.print("ID_TEMPORAL: "+id);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }
                    });
                    //Escuchador  del evento onLongListener
                    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int posicionItem, long l) {
                            System.out.println("POSICION DE A ELIMINARÇ: " + posicionItem);
                            Negocio negocio = negocioList.get(posicionItem);
                            int id = negocio.getId();

                            alertConfirmacionBorrado(id);
                            return false;
                        }
                    }); //fin del metodo


                    editText.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {
                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            adapterNegocio.getFilter().filter(s.toString());
                        }
                    });

                } else {
                    System.out.println("Mensaje de texto 2: " + response.body().getMensaje());
                }
            }

            @Override
            public void onFailure(Call<NegocioMain> call, Throwable t) {
                System.out.println("ERROR: " + t.getMessage() + " MENSAJE  DE ERROR: ");
                Constantes.messageDialog("ERROR DE CONEXIÓN: "+t.getMessage(),"No se pudo establecer la conexion al servidor.",ListaNegocios.this);
            }
        });
    }

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

    //Dialog de confirmacion de eliminacion
    public void alertConfirmacionBorrado(final int posicion) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ELIMINAR")
                .setMessage("¿Deseas eliminar el negocio seleccionado?")
                .setCancelable(false)
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //no haces nada
                    }
                })
                .setPositiveButton("BORRAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            System.out.print("Eliminado: "+" POSICION: "+posicion);

                        HttpNegocios httpNegocios = ClienteRetrofit.getSharedInstance().create(HttpNegocios.class);
                        Call<NegocioResponse> negocioResponseCall =  httpNegocios.eliminarNegocioTemporal(posicion);
                        negocioResponseCall.enqueue(new Callback<NegocioResponse>() {
                            @Override
                            public void onResponse(Call<NegocioResponse> call, Response<NegocioResponse> response) {
                                if(response.body().isEstatus()){
                                    System.out.println("ELIMINADO:  ------>");
                                    adapterNegocio.notifyDataSetChanged();
                                }else{
                                    System.out.print("NO SE REALIZO LA OPERACION");
                                }
                            }

                            @Override
                            public void onFailure(Call<NegocioResponse> call, Throwable t) {

                            }
                        });
                    }
                });


        AlertDialog alertDialog  =  builder.create();
        alertDialog.show();
    }


    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            System.out.println("Sin internet");
            swipeRefreshLayout.setRefreshing(true);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            obtenerNegocios();
            adapterNegocio.notifyDataSetChanged();
        }
    };



    public void obtenerNegociosActualizacion() {

        HttpNegocios httpNegocios = ClienteRetrofit.getSharedInstance().create(HttpNegocios.class);
        Call<NegocioMain> negocioMainClass = httpNegocios.obtenerNegocios();
        negocioMainClass.enqueue(new Callback<NegocioMain>() {

            @Override
            public void onResponse(Call<NegocioMain> call, final Response<NegocioMain> response) {
                if (response.body().isEstatus()) {
                    System.out.println("Mensaje de Textio " + response.body().isEstatus());
                    negocioList = (ArrayList<Negocio>) response.body().getNegocios();
                    adapterNegocio = new AdapterNegocio(getApplicationContext(),R.layout.item_layout ,negocioList);
                    listView.setAdapter(adapterNegocio);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            System.out.println("ELEJISTE LA POSICION: " + response.body().getNegocios().get(i).getNegocioId());
                            System.out.println("ELEJISTE EL ID: " + response.body().getNegocios().get(i).getId());

                            Bundle bundle = new Bundle();
                            int negocioId = response.body().getNegocios().get(i).getNegocioId();
                            int id = response.body().getNegocios().get(i).getId();

                            Intent intent = new Intent(getApplicationContext(), LevantarCoordenadas.class);
                            bundle.putInt("ID_NEGOCIO", negocioId);
                            bundle.putInt("ID_TEMPORAL", id);
                            System.out.print("ID_TEMPORAL: "+id);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }
                    });
                    //Escuchador  del evento onLongListener
                    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int posicionItem, long l) {
                            System.out.println("POSICION DE A ELIMINARÇ: " + posicionItem);
                            Negocio negocio = negocioList.get(posicionItem);
                            int id = negocio.getId();

                            alertConfirmacionBorrado(id);
                            return false;
                        }
                    }); //fin del metodo


                    editText.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {
                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            adapterNegocio.getFilter().filter(s.toString());
                        }
                    });

                } else {
                    System.out.println("Mensaje de texto 2: " + response.body().getMensaje());
                }
            }

            @Override
            public void onFailure(Call<NegocioMain> call, Throwable t) {
                System.out.println("ERROR: " + t.getMessage() + " MENSAJE  DE ERROR: ");
                Constantes.messageDialog("ERROR DE CONEXIÓN: "+t.getMessage(),"No se pudo establecer la conexion al servidor.",ListaNegocios.this);
            }
        });
    }



}
