/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thorne.dao;

import com.thorne.dto.PersonTO;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.thorne.dto.UserTO;
import java.sql.ResultSet;
import java.sql.SQLException;
//import org.apache.log4j.Logger;

/**
 *
 * @author squijano
 */
public class PersonDAO {

    //private Connection conn = null;
    //Logger log = Logger.getLogger("ThorneDAO");

    /*
     public boolean abrirConexion() {

     String sURL = "jdbc:mysql://localhost:3307/ThornePersonas";

     try {
     Class.forName("com.mysql.jdbc.Driver");
     conn = DriverManager.getConnection(sURL, "root", "1234567");
     log.debug("Conexion exitosa");
     return true;
     } catch (Exception ex) {

     log.debug(ex.toString());
     }

     return false;
     }
     */
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
        //PreparedStatement stmt = null;
        try {
            Statement stat = (Statement) conn.createStatement();
            
            //stmt = (PreparedStatement) conn.prepareStatement(query);
            rs = stat.executeQuery(query);
            
            if(rs.next()){
               person.setName(rs.getString("nombre"));
               person.setLastName(rs.getString("apellido"));
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

    public String insertarPersona(Connection conn, PersonTO person) {

        String query = "INSERT INTO Person(documento,nombre,apellido) "
                + "VALUES('" + person.getDocument() + "','" + person.getName() + "','" + person.getLastName()+ "')";

        return insertUpdate(conn, query);
    }

    public PersonTO consultarPersona(Connection conn, String id) {
        String query = "SELECT nombre, apellido from person where documento = '" + id + "'";

        return queryPersona(conn, query);        
    }

    public String modificarPersona(Connection conn, PersonTO person) {
        String query = "UPDATE person set ";
        boolean sw = false;
        if(person.getName() != null){
           query += " nombre = '" + person.getName() + "'";
           sw = true;
        }
        
        if(person.getLastName() != null){
           if(sw){
               query = query + ",";
           }
           query += " apellido = '" + person.getLastName()+ "'";
           sw = true;
        }
        
        if(person.getDocumentNew()!= null){
           if(sw){
               query = query + ",";
           }
           query += " documento = '" + person.getDocumentNew()+ "'";
         }
        
        query = query + " where documento = '" + person.getDocument() + "'";
        
        return insertUpdate(conn, query);        
    }
    
     public void cerrarConexion(Connection conn) {
        try {
            conn.close();
        } catch (Exception ex) {
        }
    }   
}
