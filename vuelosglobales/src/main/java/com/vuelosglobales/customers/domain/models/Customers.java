package com.vuelosglobales.customers.domain.models;

public class Customers {
    private String id;
    private String name;
    private int age;
    private int idDocument;

    public Customers() {
    }

    // getters and setters

    public Customers(String id, String name, int age, int idDocument) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.idDocument = idDocument;
    }

    public String getId() {
        return id;
    }
 
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(int idDocument) {
        this.idDocument = idDocument;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", name=" + name + ", age=" + age + ", idDocument=" + idDocument + "]";
    }
}