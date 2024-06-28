package com.vuelosglobales.bookings.domain.models;

public class Payment {
    private int id;
    private int idTbd;
    private int idMetodo;
    private Integer numero;

    public Payment(int id, int idTbd, int idMetodo, int numero) {
        this.id = id;
        this.idTbd = idTbd;
        this.idMetodo = idMetodo;
        if (numero == -1) {
            this.numero = null;
        } else { this.numero = numero; }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTbd() {
        return idTbd;
    }

    public void setIdTbd(int idTbd) {
        this.idTbd = idTbd;
    }

    public int getIdMetodo() {
        return idMetodo;
    }

    public void setIdMetodo(int idMetodo) {
        this.idMetodo = idMetodo;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "[id="+ id + ", idTbd=" + idTbd + ", idMetodo=" + idMetodo + ", numero=" + numero + "]";
    }

}
