package com.example.parcial1;

public class Actividad {
   private String idActividad;
    private String  idCorte;
   private String nombre;
    private double nota;
   private int porcentaje;



    public Actividad() {
    }

    public Actividad(String idActividad, String nombre, int porcentaje, double nota, String idCorte) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.porcentaje = porcentaje;
        this.nota = nota;
        this.idCorte = idCorte;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getIdCorte() {
        return idCorte;
    }

    public void setIdCorte(String idCorte) {
        this.idCorte = idCorte;
    }

    public String getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(String idActividad) {
        this.idActividad = idActividad;
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
