/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thorne.dto;

/**
 *
 * @author squijano
 */
public class CardTO {
    private String document;
    private String number;
    private String dateExp;
    private String nameOnCard;
    private String token;
    private String numberNew;
    private String estatus;
    
    public CardTO() {
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    
    public String getDocument() {
        return document;
    }

    public void setDocument(String documento) {
        this.document = documento;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDateExp() {
        return dateExp;
    }

    public void setDateExp(String dateExp) {
        this.dateExp = dateExp;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getNumberNew() {
        return numberNew;
    }

    public void setNumberNew(String numberNew) {
        this.numberNew = numberNew;
    }
    
    
    
}
