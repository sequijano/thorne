/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thorne.processor;

import com.mysql.jdbc.Connection;
import com.thorne.dao.Conexion;
import com.thorne.dao.PersonDAO;
import com.thorne.dao.UserDAO;
import com.thorne.dto.PersonTO;
import com.thorne.dto.ResponseTO;
import com.thorne.dto.UserTO;
import java.util.Random;

/**
 *
 * @author Sergio
 */
public class UserProcessor {

    Connection conn = null;

    public ResponseTO registerUser(UserTO user) {

        ResponseTO resp = new ResponseTO();
        resp.setRc(-1);
        resp.setMessage("Error registrando usuario");

        if (parametrosRecibidos(user)) {

            try {

                Conexion conex = new Conexion();
                conn = conex.obtener();

                if (conn == null) {
                    resp.setRc(-4);
                    resp.setMessage("Error conexion BD");
                } else {

                    UserDAO dao = new UserDAO();

                    String msg = dao.insertarUsuario(conn, user);

                    if (msg.equals("")) {
                        resp.setRc(0);
                        resp.setMessage("Registro exitoso");
                    } else {
                        resp.setMessage(msg);
                    }

                    dao.cerrarConexion(conn);
                    
                }

            } catch (Exception ex) {
                resp.setRc(-5);
                resp.setMessage(ex.toString());
            }

        } else {
            //log.debug("Parametros inválidos");
            resp.setRc(-2);
            resp.setMessage("Parámetros inválidos");
        }

        return resp;
    }

    public ResponseTO generateToken(UserTO user) {
        ResponseTO resp = new ResponseTO();

        try {
            Conexion conex = new Conexion();
            conn = conex.obtener();

            if (conn == null) {
                resp.setRc(-4);
                resp.setMessage("Error conexion BD");
            } else {

                UserDAO dao = new UserDAO();

                boolean existe = dao.validarUsuario(conn, user);

                if (existe) {

                    String msgError = dao.desactivarTokenUsuario(conn, user);

                    if (msgError.equals("")) {
                        String token = randomText();
                        msgError = dao.insertarToken(conn, user, token);
                        if (msgError.equals("")) {
                            resp.setRc(0);
                            resp.setMessage(token);
                        } else {
                            resp.setRc(-8);
                            resp.setMessage(msgError);
                        }
                    } else {
                        resp.setRc(-6);
                        resp.setMessage(msgError);
                    }
                } else {
                    resp.setRc(-10);
                    resp.setMessage("Usuario inválido");
                }

                dao.cerrarConexion(conn);                
            }
        } catch (Exception ex) {

        }

        return resp;
    }

    private String randomText() {
        char[] chars = "zxcvbnmasdfghjklqwertuiop1234567890".toCharArray();
        StringBuilder token = new StringBuilder(20);
        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            char c = chars[rand.nextInt(chars.length)];
            token.append(c);
        }

        return token.toString();
    }

    private boolean parametrosRecibidos(UserTO user) {

        if (user.getLogin() == null || user.getLogin().equals("")
                || user.getPassword() == null || user.getPassword().equals("")) {
            return false;
        }
        return true;
    }
}
