/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thorne.dao;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Sergio
 */
public class TokenDAO {

        public boolean validarToken(Connection conn, String token) {

        String query = "SELECT * FROM token WHERE token = '" + token + "' and estatus = 1 ";

        return queryToken(conn, query);
    }
    
    private boolean queryToken(Connection conn, String query) {

        ResultSet rs = null;
        boolean tokenValido = false;
        //PreparedStatement stmt = null;
        try {
            Statement stat = (Statement) conn.createStatement();
            
            //stmt = (PreparedStatement) conn.prepareStatement(query);
            rs = stat.executeQuery(query);
            
            if(rs.next()){
               //user.setLogin(rs.getString("login"));           
                tokenValido = true;
            }
            
            
            stat.close();
            //conn.close();
        } catch (SQLException sqle) {
            System.out.println("Error en la ejecución:"
                    + sqle.getErrorCode() + " " + sqle.getMessage());
        }

        return tokenValido;
    }

}
