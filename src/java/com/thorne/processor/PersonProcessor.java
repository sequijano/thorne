/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thorne.processor;

import com.google.gson.Gson;
import com.thorne.dao.Conexion;
import com.thorne.dto.PersonTO;
import com.thorne.dto.ResponseTO;
import com.mysql.jdbc.Connection;
import com.thorne.dao.PersonDAO;
import com.thorne.dao.TokenDAO;
import java.util.Random;

/**
 *
 * @author squijano
 */
public class PersonProcessor {

    //Logger log = Logger.getLogger("ThorneProcessor");
    Connection conn = null;

    public ResponseTO consultPerson(String id, String token) {
        ResponseTO resp = new ResponseTO();
        resp.setRc(-100);
        resp.setMessage("Token inválido");

        try {

            TokenDAO tokendao = new TokenDAO();

            
            Conexion conex = new Conexion();
            conn = conex.obtener();

            if (conn == null) {
                resp.setRc(-4);
                resp.setMessage("Error conexion BD");
            } else {

                boolean tokenVal = false;
                if (token != null) {
                    if (tokendao.validarToken(conn, token)) {
                        tokenVal = true;
                    }
                }
                if (!tokenVal) {
                    return resp;
                }

                resp.setRc(-1);
                resp.setMessage("Error consultando persona");

                PersonDAO dao = new PersonDAO();
                PersonTO persona = dao.consultarPersona(conn, id);

                if (persona != null) {
                    Gson gson = new Gson();
                    resp.setRc(0);
                    resp.setMessage(gson.toJson(persona));
                }
                dao.cerrarConexion(conn);
            }

        } catch (Exception ex) {
            resp.setRc(-5);
            resp.setMessage(ex.toString());
        }

        return resp;
    }

    public ResponseTO modifyPerson(PersonTO person) {
        ResponseTO resp = new ResponseTO();
        resp.setRc(-100);
        resp.setMessage("Token inválido");

        try {
            
            TokenDAO tokendao = new TokenDAO();

            Conexion conex = new Conexion();
            conn = conex.obtener();

            if (conn == null) {
                resp.setRc(-4);
                resp.setMessage("Error conexion BD");
            } else {

                boolean tokenVal = false;
                if (person.getToken() != null) {
                    if (tokendao.validarToken(conn, person.getToken())) {
                        tokenVal = true;
                    }
                }
                if (!tokenVal) {
                    return resp;
                }

                resp.setRc(-1);
                resp.setMessage("Error modificando persona");

                PersonDAO dao = new PersonDAO();
                String msg = dao.modificarPersona(conn, person);

                if (msg.equals("")) {
                    resp.setRc(0);
                    resp.setMessage("Registro actualizado exitosamente");
                } else {
                    resp.setMessage(msg);
                }
                dao.cerrarConexion(conn);
            }

        } catch (Exception ex) {
            resp.setRc(-5);
            resp.setMessage(ex.toString());
        }

        return resp;
    }

    public ResponseTO registerPerson(PersonTO person) {

        ResponseTO resp = new ResponseTO();
        resp.setRc(-100);
        resp.setMessage("Token inválido");

        TokenDAO tokendao = new TokenDAO();

        Conexion conex = new Conexion();

        try {
            conn = conex.obtener();

            if (conn == null) {
                resp.setRc(-4);
                resp.setMessage("Error conexion BD");
                return resp;
            }

            boolean tokenVal = false;
            if (person.getToken() != null) {
                if (tokendao.validarToken(conn, person.getToken())) {
                    tokenVal = true;
                }
            }
            if (!tokenVal) {
                return resp;
            }

            resp.setRc(-1);
            resp.setMessage("Error registrando persona");
            if (parametrosRecibidos(person)) {

                PersonDAO dao = new PersonDAO();
                String msg = dao.insertarPersona(conn, person);

                if (msg.equals("")) {
                    resp.setRc(0);
                    resp.setMessage("Registro exitoso");
                } else {
                    resp.setMessage(msg);
                }

                dao.cerrarConexion(conn);

            } else {
                //log.debug("Parametros inválidos");
                resp.setRc(-2);
                resp.setMessage("Parámetros inválidos");
            }

        } catch (Exception ex) {
            resp.setRc(-5);
            resp.setMessage(ex.toString());

        }

        return resp;
    }

    private boolean parametrosRecibidos(PersonTO person) {

        if (person.getName() == null || person.getName().equals("")
                || person.getLastName() == null || person.getLastName().equals("")
                || person.getDocument() == null || person.getDocument().equals("")) {
            return false;
        }
        return true;
    }
}
