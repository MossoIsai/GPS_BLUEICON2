package com.example.isaigarciamoso.gps_blueicon2.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.isaigarciamoso.gps_blueicon2.R;
import com.example.isaigarciamoso.gps_blueicon2.models.response.Negocio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 26/07/17.
 */

public class AdapterNegocio extends ArrayAdapter<Negocio> {
    private List<Negocio> negociosList;
    private ArrayList<Negocio> originalList;
    private ArrayList<Negocio> countryList;
    private CountryFilter filter;

    public AdapterNegocio(Context context, int resLayout,ArrayList<Negocio> countryList) {
        super(context, 0, countryList);
        //this.negociosList =  negociosList;
        this.countryList = new ArrayList<Negocio>();
        this.countryList.addAll(countryList);
        this.originalList = new ArrayList<Negocio>();
        this.originalList.addAll(countryList);
    }
    @Override
    public Filter getFilter() {
        if (filter == null){
            filter  = new CountryFilter();
        }
        return filter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listViewInflate = convertView;

        if (convertView == null) {
            listViewInflate = inflater.inflate(R.layout.item_layout, viewGroup, false);
        }
        TextView textView = (TextView) listViewInflate.findViewById(R.id.nombreNegocio);
        ImageView imageView = (ImageView) listViewInflate.findViewById(R.id.flecha);

        final Negocio negocio = getItem(position);
        textView.setText(negocio.getNombre());
        imageView.setImageResource(R.drawable.flecha);

        if(negocio.isEliminado()){
            listViewInflate.setBackgroundColor(Color.parseColor("#f94f4b"));
            textView.setTextColor(Color.parseColor("#FFFFFF"));
        }else{
            listViewInflate.setBackgroundColor(Color.parseColor("#FFFFFF"));
            textView.setTextColor(Color.parseColor("#000000"));


        }


        return listViewInflate;
    }
    private class CountryFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if(constraint != null && constraint.toString().length() > 0)
            {
                ArrayList<Negocio> filteredItems = new ArrayList<Negocio>();

                for(int i = 0, l = originalList.size(); i < l; i++)
                {
                    Negocio negocio = originalList.get(i);
                    if(negocio.toString().toLowerCase().contains(constraint))
                        filteredItems.add(negocio);
                }
                result.count = filteredItems.size();
                result.values = filteredItems;
            }
            else
            {
                synchronized(this)
                {
                    result.values = originalList;
                    result.count = originalList.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            countryList = (ArrayList<Negocio>)results.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = countryList.size(); i < l; i++)
                add(countryList.get(i));
            notifyDataSetInvalidated();
        }
    }

}
