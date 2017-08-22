package com.example.isaigarciamoso.gps_blueicon2.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import com.example.isaigarciamoso.gps_blueicon2.R;

import com.example.isaigarciamoso.gps_blueicon2.models.response.ObjectListaHorario;
import com.example.isaigarciamoso.gps_blueicon2.models.response.Semana;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by isaigarciamoso on 19/08/17.
 */

public class AdapterHorario extends  RecyclerView.Adapter<AdapterHorario.ViewHolder> {


    private final List<ObjectListaHorario> horarioItems;
    private final OnItem listener;
    private AdapterGridViewMarcado adapterGridViewMarcado;
    private int contador;
    private boolean[] presionado = {false, false, false, false, false, false, false};


    public AdapterHorario(List<ObjectListaHorario> horarioItems,OnItem listener){
        this.horarioItems = horarioItems;
        this.listener = listener;

    }
    public interface  OnItem{
        void  onItemClick(ObjectListaHorario objectListaHorario);
        void onItemLongClick(ObjectListaHorario objectListaHorario);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_horario,null);
        ViewHolder viewHolder = new ViewHolder(vista);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ++contador;
        System.out.println("CONTADOR: "+contador);

        ObjectListaHorario objectListaHorario = horarioItems.get(position);

        Context contextGridView = holder.gridSemana.getContext();
        holder.horaApertura.setText(objectListaHorario.getHoraApertura());
        holder.horacerrado.setText(objectListaHorario.getHoraCerrado());
        for(Semana item: objectListaHorario.getDiasMarcados()){
            System.out.println("ITEM :"+item.getNombreDia()+""+item.isEstatus());
        }
        adapterGridViewMarcado =  new AdapterGridViewMarcado(contextGridView,objectListaHorario.getDiasMarcados());

        System.out.println("dias marcados : "+objectListaHorario.getDiasMarcados());
        holder.gridSemana.setAdapter(adapterGridViewMarcado);
        holder.gridSemana.setOnItemClickListener(listenerSelectedia);
        holder.bind(objectListaHorario,listener);
    }

    @Override
    public int getItemCount() {
        return horarioItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.gridSemana)
        GridView gridSemana;
        @BindView(R.id.horaApertura)
        TextView horaApertura;
        @BindView(R.id.horaCerrado) TextView horacerrado;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void bind(final ObjectListaHorario  objectListaHorario,final OnItem listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(objectListaHorario);

                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClick(objectListaHorario);

                    System.out.println("presiona un Long");
                    return  true;
                }
            });
        }

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
            for (boolean item : presionado) {
                System.out.println("VALOR: "+item);

            }

        }


    };
}
