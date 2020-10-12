package com.example.parcial1;

import java.util.ArrayList;
import java.util.List;

public class Corte {
   private String id;
   private String nombre;
   private int porcentaje;
   private double nota;
   private int idMateria;

    public Corte() {
    }

    public Corte(String id, String nombre, int porcentaje, int idMateria) {
        this.id = id;
        this.nombre = nombre;
        this.porcentaje = porcentaje;
        this.idMateria = idMateria;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }
}
