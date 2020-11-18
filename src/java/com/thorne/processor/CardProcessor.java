/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thorne.processor;

import com.mysql.jdbc.Connection;
import com.thorne.dao.CardDAO;
import com.thorne.dao.Conexion;
import com.thorne.dao.PersonDAO;
import com.thorne.dao.TokenDAO;
import com.thorne.dto.CardTO;
import com.thorne.dto.PersonTO;
import com.thorne.dto.ResponseTO;

/**
 *
 * @author Sergio
 */
public class CardProcessor {
    Connection conn = null;
    
    public ResponseTO registerCard(CardTO card) {

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
            if (card.getToken() != null) {
                if (tokendao.validarToken(conn, card.getToken())) {
                    tokenVal = true;
                }
            }
            if (!tokenVal) {
                return resp;
            }

            resp.setRc(-1);
            resp.setMessage("Error registrando tarjeta");
            if (parametrosRecibidos(card)) {
                CardDAO dao = new CardDAO();
                String msg = dao.insertarTarjeta(conn, card);

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

    public ResponseTO modifyCard(CardTO card) {
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
                if (card.getToken() != null) {
                    if (tokendao.validarToken(conn, card.getToken())) {
                        tokenVal = true;
                    }
                }
                if (!tokenVal) {
                    return resp;
                }

                resp.setRc(-1);
                resp.setMessage("Error modificando tarjeta");

                CardDAO dao = new CardDAO();
                String msg = dao.modificarTarjeta(conn, card);

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

    
    private boolean parametrosRecibidos(CardTO card) {

        if (card.getDocument() == null || card.getDocument().equals("")
                || card.getNumber() == null || card.getNumber().equals("")
                || card.getDateExp() == null || card.getDateExp().equals("")
                || card.getNameOnCard() == null || card.getNameOnCard().equals("")) {
            return false;
        }
        return true;
    }
}
