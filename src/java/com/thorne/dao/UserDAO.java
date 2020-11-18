/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thorne.dao;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.thorne.dto.PersonTO;
import com.thorne.dto.UserTO;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Sergio
 */
public class UserDAO {

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

    private String generarSha256(String pass) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            digest.update(pass.getBytes("utf-8"));

            return String.format("%064x", new BigInteger(1, digest.digest()));
        } catch (Exception ex) {
        }

        return "";
    }

    public String insertarUsuario(Connection conn, UserTO user) {

        String query = "";
        try {

            query = "INSERT INTO usuario(login,password) "
                    + "VALUES('" + user.getLogin() + "','" + generarSha256(user.getPassword()) + "')";

        } catch (Exception ex) {
        }

        return insertUpdate(conn, query);
    }

    private boolean queryUsuario(Connection conn, String query) {

        ResultSet rs = null;
        boolean existe = false;
        //PreparedStatement stmt = null;
        try {
            Statement stat = (Statement) conn.createStatement();

            //stmt = (PreparedStatement) conn.prepareStatement(query);
            rs = stat.executeQuery(query);

            if (rs.next()) {
                existe = true;
            }

            stat.close();
            //conn.close();
        } catch (SQLException sqle) {
            System.out.println("Error en la ejecución:"
                    + sqle.getErrorCode() + " " + sqle.getMessage());
        }

        return existe;
    }

    public boolean validarUsuario(Connection conn, UserTO user) {

        String query = "SELECT * FROM usuario WHERE login = '" + user.getLogin() + "' and password ='" + generarSha256(user.getPassword()) + "' ";

        return queryUsuario(conn, query);
    }

    public String insertarToken(Connection conn, UserTO user, String token) {
        String query = "INSERT INTO token (login, token) VALUES('" + user.getLogin() + "','" + token + "')";

        return insertUpdate(conn, query);
    }

    public String desactivarTokenUsuario(Connection conn, UserTO user) {
        String query = "UPDATE token set estatus = 0 where login = '" + user.getLogin() + "' ";

        return insertUpdate(conn, query);
    }

    public void cerrarConexion(Connection conn) {
        try {
            conn.close();
        } catch (Exception ex) {
        }
    }
}
