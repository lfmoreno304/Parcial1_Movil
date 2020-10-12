package com.example.parcial1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Corte corte;
    ListView list;
    ArrayList<Materia> materias=new ArrayList<>();
    ArrayList<Corte> cortes=new ArrayList<>();
    Materia materia;
    MateriaAdapter materiaAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inizializarFirebase();
        traerMaterias();
        Button agregar=findViewById(R.id.agregar);
        list=findViewById(R.id.lista);
        list.setAdapter(materiaAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(MainActivity.this);
                dialog.setTitle("Agregar materia");
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.registrar_materia);
                dialog.show();
                final EditText nombre=dialog.findViewById(R.id.nombreMateria);
                final EditText codigo=dialog.findViewById(R.id.codigoMateria);
                final Button agregar=dialog.findViewById(R.id.agregarMateria);
                final Button cancelar=dialog.findViewById(R.id.cancelarMateria);
                agregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            materia=new Materia(Integer.parseInt(codigo.getText().toString()),nombre.getText().toString(),0.0);
                            databaseReference.child("Materia").child(materia.toString()).setValue(materia);
                            corte=new Corte(materia.getCodigoMateria()+materia.getNombre()+1,"Corte 1",
                                    30,materia.getCodigoMateria());
                            databaseReference.child("Corte").child(corte.getId()).setValue(corte);
                        corte=new Corte(materia.getCodigoMateria()+materia.getNombre()+2,"Corte 2",
                                30,materia.getCodigoMateria());
                        databaseReference.child("Corte").child(corte.getId()).setValue(corte);
                        corte=new Corte(materia.getCodigoMateria()+materia.getNombre()+3,"Corte 3",
                                40,materia.getCodigoMateria());
                        databaseReference.child("Corte").child(corte.getId()).setValue(corte);

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
    }

    private void traerMaterias() {
        databaseReference.child("Materia").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                materias.clear();
                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                    Materia materia= objSnapshot.getValue(Materia.class);
                    materias.add(materia);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("Corte").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cortes.clear();
                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                    Corte corte=objSnapshot.getValue(Corte.class);
                    cortes.add(corte);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        materiaAdapter=new MateriaAdapter(this,materias,firebaseDatabase,databaseReference,cortes);
    }

    private void inizializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();
    }
}