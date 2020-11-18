/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thorne.service;

import com.google.gson.Gson;
import com.thorne.dto.CardTO;
import com.thorne.dto.PersonTO;
import com.thorne.dto.ResponseTO;
import com.thorne.dto.UserTO;
import com.thorne.processor.CardProcessor;
import com.thorne.processor.PersonProcessor;
import com.thorne.processor.UserProcessor;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;

/**
 * REST Web Service
 *
 * @author squijano
 */
@Path("thorne")
public class GenericResource {

    //Logger log = Logger.getLogger("ThorneService");

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.thorne.services.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/consultPerson/{id}/{token}")
    @Consumes("text/plain")
    @Produces("application/json")
    public String getPerson(@PathParam("id") String id, @PathParam("token") String token) {
        
        Gson gson = new Gson();       
                
        PersonProcessor processor = new PersonProcessor();
        
        ResponseTO resp = processor.consultPerson(id, token);
       
        return gson.toJson(resp);
    }

    @POST
    @Path("/login")
    @Consumes("application/json")
    @Produces("text/plain")
    public String getToken(String content) {
        
        Gson gson = new Gson();       
                
        UserProcessor processor = new UserProcessor();
        
        ResponseTO resp = processor.generateToken(gson.fromJson(content, UserTO.class));
       
        return gson.toJson(resp);
    }
    
    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("/modifyPerson")
    @Consumes("application/json")
    @Produces("application/json")
    public String modifyPerson(String content) {
        Gson gson = new Gson();       
        ResponseTO resp;
        //log.debug("Objeto recibido: " + content);
        
        PersonProcessor processor = new PersonProcessor();
        
        resp = processor.modifyPerson(gson.fromJson(content, PersonTO.class));
        
        return gson.toJson(resp);
    }

    @PUT
    @Path("/modifyCard")
    @Consumes("application/json")
    @Produces("application/json")
    public String modifyCard(String content) {
        Gson gson = new Gson();       
        ResponseTO resp;
        //log.debug("Objeto recibido: " + content);
        
        CardProcessor processor = new CardProcessor();
        
        resp = processor.modifyCard(gson.fromJson(content, CardTO.class));
        
        return gson.toJson(resp);
    }
    
    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Path("/registerPerson")
    @Consumes("application/json")
    @Produces("application/json")
    public String registerPerson(String content) {
        
        Gson gson = new Gson();       
        ResponseTO resp;
        //log.debug("Objeto recibido: " + content);
        
        PersonProcessor processor = new PersonProcessor();
        
        resp = processor.registerPerson(gson.fromJson(content, PersonTO.class));
        
        return gson.toJson(resp);        
    }

    @POST
    @Path("/registerCard")
    @Consumes("application/json")
    @Produces("application/json")
    public String registerCard(String content) {
        Gson gson = new Gson();       
        ResponseTO resp;
        //log.debug("Objeto recibido: " + content);
        
        CardProcessor processor = new CardProcessor();
        
        resp = processor.registerCard(gson.fromJson(content, CardTO.class));
        
        return gson.toJson(resp);
    }

    
    @POST
    @Path("/registerUser")
    @Consumes("application/json")
    @Produces("application/json")
    public String registerUser(String content) {
        
        Gson gson = new Gson();       
        ResponseTO resp;
        //log.debug("Objeto recibido: " + content);
        
        UserProcessor processor = new UserProcessor();
        
        resp = processor.registerUser(gson.fromJson(content, UserTO.class));
        
        return gson.toJson(resp);        
    }
}
