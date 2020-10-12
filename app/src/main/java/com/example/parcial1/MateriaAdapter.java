package com.example.parcial1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MateriaAdapter extends BaseAdapter {
    Activity context;
    ArrayList<Materia> materias;
    ArrayList<Corte> cortes;
    Materia materia;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private static LayoutInflater inflater=null;

    public MateriaAdapter(Activity context, ArrayList<Materia> materias,FirebaseDatabase firebaseDatabase,
                          DatabaseReference databaseReference,ArrayList<Corte> cortes) {
        this.context = context;
        this.cortes=cortes;
        this.materias = materias;
        this.firebaseDatabase=firebaseDatabase;
        this.databaseReference=databaseReference;
        inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        materia=materias.get(position);
        final  View view=inflater.inflate(R.layout.item_materia,null);
        TextView nombre=view.findViewById(R.id.ma_nombre);
        TextView codigo=view.findViewById(R.id.ma_codigo);
        TextView nota=view.findViewById(R.id.ma_nota);
        Button editar=view.findViewById(R.id.ma_editar);
        Button eliminar=view.findViewById(R.id.ma_eliminar);
        Button siguiente=view.findViewById(R.id.ma_siguiente);
        nombre.setText(materia.getNombre());
        codigo.setText(""+materia.getCodigoMateria());
        nota.setText(""+materia.getDefinitiva());
        editar.setTag(position);
        eliminar.setTag(position);
        siguiente.setTag(position);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=Integer.parseInt(v.getTag().toString());
                final Dialog dialog=new Dialog(context);
                dialog.setTitle("Editar materia");
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.registrar_materia);
                dialog.show();
                final EditText nombre=dialog.findViewById(R.id.nombreMateria);
                final EditText codigo=dialog.findViewById(R.id.codigoMateria);
                final Button agregar=dialog.findViewById(R.id.agregarMateria);
                final Button cancelar=dialog.findViewById(R.id.cancelarMateria);
                agregar.setText("Guardar");
                materia=materias.get(pos);
                nombre.setText(materia.getNombre());
                codigo.setText(""+materia.getCodigoMateria());
                agregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Materia nueavMateria=new Materia();
                        nueavMateria.setNombre(nombre.getText().toString());
                        nueavMateria.setCodigoMateria(materia.getCodigoMateria());
                        nueavMateria.setDefinitiva(materia.getDefinitiva());
                        databaseReference.child("Materia").child(nueavMateria.toString()).setValue(nueavMateria);
                        dialog.dismiss();
                    }
                });
                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=Integer.parseInt(v.getTag().toString());
                materia=materias.get(pos);
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
                alertDialog.setMessage("¿Seguron que quiere eliminar la materia?");
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i=0; i<cortes.size();i++){
                            Corte corte=cortes.get(i);
                            if(corte.getIdMateria()==materia.getCodigoMateria()){
                                databaseReference.child("Corte").child(corte.getId()).removeValue();
                            }
                        }
                        databaseReference.child("Materia").child(materia.toString()).removeValue();
                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
        });

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=Integer.parseInt(v.getTag().toString());
                materia=materias.get(pos);
                Intent intent=new Intent(context,CorteActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("codigo",materia.getCodigoMateria());
                bundle.putString("materia",materia.getNombre());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public int getCount() {
        return materias.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
