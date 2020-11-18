/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thorne.dao;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.thorne.dto.CardTO;
import com.thorne.dto.PersonTO;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Sergio
 */
public class CardDAO {

    public String insertarTarjeta(Connection conn, CardTO card) {

        String query = "SELECT id from person where documento ='" + card.getDocument() + "'";

        PersonTO persona = queryPersona(conn, query);

        if (persona.getId().equals("")) {
            return "Persona no encontrada";
        }

        query = "INSERT INTO Card(fk_persona,nroTarjeta,fecExp,nombreEnTarjeta) "
                + "VALUES('" + persona.getId() + "','" + card.getNumber() + "','" + card.getDateExp() + "','" + card.getNameOnCard() + "')";

        return insertUpdate(conn, query);
    }

    public void cerrarConexion(Connection conn) {
        try {
            conn.close();
        } catch (Exception ex) {
        }
    }
    
    public String modificarTarjeta(Connection conn, CardTO card) {
        String query = "UPDATE card set ";
        boolean sw = false;
        if(card.getDateExp() != null){
           query += " fecExp = '" + card.getDateExp() + "'";
           sw = true;
        }
        
        if(card.getNameOnCard() != null){
           if(sw){
               query = query + ",";
           }
           query += " nombreEnTarjeta = '" + card.getNameOnCard() + "'";
           sw = true;
        }
        
        if(card.getNumberNew() != null){
           if(sw){
               query = query + ",";
           }
           query += " nroTarjeta = '" + card.getNumberNew() + "'";
         }
        
        if(card.getEstatus() != null){
           if(sw){
               query = query + ",";
           }
           query += " estatus = '" + card.getEstatus() + "'";
         }
        
        query = query + " where nroTarjeta = '" + card.getNumber() + "'";
        
        return insertUpdate(conn, query);        
    }


    private String insertUpdate(Connection conn, String query) {

        //log.debug("Ejecutar insert: " + query);
        String msgError = "";

        try {
            PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.execute(query);

            stmt.close();
            //conn.close();

        } catch (SQLException sqle) {
            msgError = sqle.toString();
            System.out.println("Error en la ejecución:"
                    + sqle.getErrorCode() + " " + sqle.getMessage());
        }

        return msgError;
    }

    private PersonTO queryPersona(Connection conn, String query) {

        ResultSet rs = null;
        PersonTO person = new PersonTO();
        person.setId("");
        //PreparedStatement stmt = null;
        try {
            Statement stat = (Statement) conn.createStatement();

            //stmt = (PreparedStatement) conn.prepareStatement(query);
            rs = stat.executeQuery(query);

            if (rs.next()) {
                person.setId(rs.getString("id"));

            }

            stat.close();
            //conn.close();
        } catch (SQLException sqle) {
            person.setName(sqle.toString());
            System.out.println("Error en la ejecución:"
                    + sqle.getErrorCode() + " " + sqle.getMessage());
        }

        return person;
    }

}
