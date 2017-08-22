package com.example.isaigarciamoso.gps_blueicon2.http;



import com.example.isaigarciamoso.gps_blueicon2.models.request.NegocioRequest;
import com.example.isaigarciamoso.gps_blueicon2.models.response.NegocioMain;
import com.example.isaigarciamoso.gps_blueicon2.models.response.NegocioResponse;
import com.example.isaigarciamoso.gps_blueicon2.models.response.SuccesResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by developer on 25/07/17.
 */

public interface HttpNegocios {

    @POST("NegocioTemporal/Listado")
    @Headers("Accept: application/json")
    Call<NegocioMain> obtenerNegocios();

    //Enviar el servidor para el registro del negocio
    @POST("NegocioTemporal/Agregar")
    @Headers("Accept: application/json")
    Call<SuccesResponse> sendNegocio(@Body NegocioRequest negocioRequest);

    //Detalle del negocio
    @GET("NegocioTemporal/Detalle")
    @Headers("Accept: application/json")
    Call<NegocioResponse> obtenerDetalleNegocio(@Query("NegocioId") int negocioId, @Query("Id") int id);


    //Eliminar la seccion del api temporal

    @GET("NegocioTemporal/Eliminar")
    @Headers("Accept: application/json")
    Call<NegocioResponse> eliminarNegocioTemporal(@Query("Id") int idEliminar);








}
