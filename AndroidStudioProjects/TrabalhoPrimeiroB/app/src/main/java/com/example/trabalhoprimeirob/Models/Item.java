package com.example.trabalhoprimeirob.Models;

public class Item {

    private String nome;
    private int qtn;
    private Double vl_unitario;

    private Double total;

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Item(String nome, int qtn, Double vl_unitario) {
        this.nome = nome;
        this.qtn = qtn;
        this.vl_unitario = vl_unitario;
        total = vl_unitario * qtn;
    }

    public Item() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQtn() {
        return qtn;
    }

    public void setQtn(int qtn) {
        this.qtn = qtn;
    }

    public Double getVl_unitario() {
        return vl_unitario;
    }

    public void setVl_unitario(Double vl_unitario) {
        this.vl_unitario = vl_unitario;
    }

}

