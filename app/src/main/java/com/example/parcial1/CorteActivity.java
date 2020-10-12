package com.example.parcial1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CorteActivity extends AppCompatActivity {

    ArrayList<Corte> cortes=new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Corte corte;
    int codigo;
    CorteAdapter corteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corte);
        inizializarFirebase();
        Bundle bundle=this.getIntent().getExtras();
        if (bundle!=null){
            codigo=bundle.getInt("codigo");
            String materia=bundle.getString("materia");
            final TextView nombre=findViewById(R.id.c_materia);
            nombre.setText(materia);
            traerCortes(codigo);
        }

        final ListView list=findViewById(R.id.c_lista);
        list.setAdapter(corteAdapter);
        corteAdapter.notifyDataSetChanged();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }
    private void inizializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();
    }

    private void traerCortes(final int codigo){
        databaseReference.child("Corte").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cortes.clear();
                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                    Corte corte=objSnapshot.getValue(Corte.class);
                    if (corte.getIdMateria()==codigo){
                        cortes.add(corte);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        corteAdapter=new CorteAdapter(this,cortes,firebaseDatabase,databaseReference);
    }
}