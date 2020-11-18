/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thorne.dto;

import java.util.ArrayList;

/**
 *
 * @author squijano
 */
public class PersonTO {
    private String id;
    private String name;
    private String lastName;
    private String document;
    private String documentNew;
    private ArrayList<CardTO> listaTarjetas;
    
    private String token;
    
    private CardTO card;

    public PersonTO() {
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDocumentNew() {
        return documentNew;
    }

    public void setDocumentNew(String documentNew) {
        this.documentNew = documentNew;
    }

    
    
    public CardTO getCard() {
        return card;
    }

    public void setCard(CardTO card) {
        this.card = card;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArrayList<CardTO> getListaTarjetas() {
        return listaTarjetas;
    }

    public void setListaTarjetas(ArrayList<CardTO> listaTarjetas) {
        this.listaTarjetas = listaTarjetas;
    }
           
    
}
