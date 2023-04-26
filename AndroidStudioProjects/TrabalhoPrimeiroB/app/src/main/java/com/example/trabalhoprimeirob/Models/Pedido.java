package com.example.trabalhoprimeirob.Models;

import java.util.ArrayList;

public class Pedido {
    private int id;
    private Cliente cliente;
    private ArrayList<Item> listItens = new ArrayList<Item>();
    private Double vlTotalPagto;
    private boolean vista;

    public Pedido(int id, Cliente cliente, ArrayList<Item> listItens, Double vlTotalPagto, boolean vista) {
        this.id = id;
        this.cliente = cliente;
        this.listItens = listItens;
        this.vlTotalPagto = vlTotalPagto;
        this.vista = vista;
    }

    public Pedido() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Item> getListItens() {
        return listItens;
    }

    public void setListItens(ArrayList<Item> listItens) {
        this.listItens = listItens;
    }

    public Double getVlTotalPagto() {
        return vlTotalPagto;
    }

    public void setVlTotalPagto(Double vlTotalPagto) {
        this.vlTotalPagto = vlTotalPagto;
    }

    public boolean isVista() {
        return vista;
    }

    public void setVista(boolean vista) {
        this.vista = vista;
    }
}

