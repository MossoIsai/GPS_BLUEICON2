package com.example.isaigarciamoso.gps_blueicon2.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.isaigarciamoso.gps_blueicon2.R;
import com.example.isaigarciamoso.gps_blueicon2.models.response.Semana;

import java.util.List;

/**
 * Created by isaigarciamoso on 19/08/17.
 */

public class AdapterGridViewMarcado extends ArrayAdapter<Semana> {
    private List<Semana> semanaList;

    public AdapterGridViewMarcado(Context context, List<Semana> semanaList) {
        super(context,0, semanaList);
        this.semanaList = semanaList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Semana semana = semanaList.get(position);
        System.out.println("SEMANA--->: "+semana.getNombreDia()+" "+semana.isEstatus());



        View view = convertView;
        if(convertView == null){
            view = inflater.inflate(R.layout.item_horario,viewGroup,false);
        }
        TextView texhorario = (TextView) view.findViewById(R.id.textDiaSemana);
        texhorario.setText(semana.getNombreDia());
        if(semana.isEstatus()){
            texhorario.setTextColor(Color.parseColor("#20bbcc"));
        }else{
            texhorario.setTextColor(Color.parseColor("#8b8b8b"));

        }

        return view;
    }

    @Override
    public int getCount() {
        return semanaList.size();
    }
}
