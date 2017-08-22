package com.example.isaigarciamoso.gps_blueicon2.models.response;

import java.util.ArrayList;

/**
 * Created by isaigarciamoso on 19/08/17.
 */

public class HorarioItem {

    private ArrayList<String> stringsArrays;
    private String horar;

    public ArrayList<String> getStringsArrays() {
        return stringsArrays;
    }

    public void setStringsArrays(ArrayList<String> stringsArrays) {
        this.stringsArrays = stringsArrays;
    }

    public String getHorar() {
        return horar;
    }

    public void setHorar(String horar) {
        this.horar = horar;
    }

    public HorarioItem(ArrayList<String> stringsArrays, String horar) {
        this.stringsArrays = stringsArrays;
        this.horar = horar;
    }
    public HorarioItem(){

    }
}
