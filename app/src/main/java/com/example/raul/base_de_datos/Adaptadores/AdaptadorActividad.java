package com.example.raul.base_de_datos.Adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.raul.base_de_datos.R;
import com.example.raul.base_de_datos.entidades.Actividad;

import java.util.ArrayList;

public class AdaptadorActividad extends RecyclerView.Adapter<AdaptadorActividad.ViewHolderActividad> {

    ArrayList<Actividad> listaActividad;

    public AdaptadorActividad(ArrayList<Actividad> listaActividad) {
        this.listaActividad = listaActividad;
    }

    @NonNull
    @Override
    public AdaptadorActividad.ViewHolderActividad onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_actividad, null,false);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_actividad,null,false);
        return new ViewHolderActividad(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorActividad.ViewHolderActividad holder, int position) {
/*        if (position % 5 == 0) {
            holder.edtDato.setText((listaActividad.get(position).getMesAño()));
        } else if (position % 5 == 1) {
            holder.edtDato.setText(listaActividad.get(position).getHoras().toString());
        } else if (position % 5 == 2) {
            holder.edtDato.setText(listaActividad.get(position).getRevisitas().toString());
        } else if (position % 5 == 3) {
            holder.edtDato.setText(listaActividad.get(position).getEstudios().toString());
        } else if (position % 5 == 4) {
            holder.edtDato.setText(listaActividad.get(position).getObservaciones());
        }*/
        holder.edtFecha.setText(listaActividad.get(position).getMesAño());
        holder.edtHoras.setText(listaActividad.get(position).getHoras().toString());
        holder.edtRevisitas.setText(listaActividad.get(position).getRevisitas().toString());
        holder.edtEstudios.setText(listaActividad.get(position).getEstudios().toString());
        holder.edtObservaciones.setText(listaActividad.get(position).getObservaciones());
    }

    @Override
    public int getItemCount() {
        return listaActividad.size();
    }

    public class ViewHolderActividad extends RecyclerView.ViewHolder {
        TextView edtFecha, edtHoras, edtRevisitas, edtEstudios, edtObservaciones;
//        CheckBox chkPrecursorAuxiliar;
//        EditText edtDato;

        public ViewHolderActividad(View itemView) {
            super(itemView);
            edtFecha = (TextView) itemView.findViewById(R.id.edtFecha);
            edtHoras = (TextView) itemView.findViewById(R.id.edtHoras);
            edtRevisitas = (TextView) itemView.findViewById(R.id.edtRevisitas);
            edtEstudios = (TextView) itemView.findViewById(R.id.edtEstudios);
            edtObservaciones = (TextView) itemView.findViewById(R.id.edtObservaciones);
//            chkPrecursorAuxiliar = (CheckBox) itemView.findViewById(R.id.chkPrecursorAuxiliar);
//            edtDato = (EditText) itemView.findViewById(R.id.edtDato);
        }
    }
}
