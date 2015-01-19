/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elso.rest;

import com.elso.Utils.IConstantes;
import com.elso.beans.impl.PersonneRestImpl;
import com.elso.entity.Personne;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Elabdoulaye
 */
@Path("/personnes")
@Produces({"application/xml","application/json"})
@Consumes({"application/xml","application/json"})
public class PersonneRest {
    
 @PersistenceContext(unitName = "Matable_PU")
 private EntityManager em;
 @Resource
 private UserTransaction ut; 
 @Context
 private UriInfo uriInfo;
 @EJB
 private PersonneRestImpl persRest;

    @GET
    @Path("getPersonne/{id}")
    public Personne getPersonneById(@PathParam("id") Long id) {
     Personne personne=this.persRest.getPersonneById(id);
     return personne;
    }
    
    @GET
    @Path("allPersonneByStatut/{statut}")
    public List<Personne> getallPersonneByStatut(@PathParam("statut") String statut) {
     List<Personne> personne=this.persRest.AllPersonneByStatut(statut);
     return personne;
    }
    @Path("update/{id}")
    @POST
    public Personne updatePersonne(@PathParam("id") Long id,
                                   @QueryParam("prenom") String prenom,
                                   @QueryParam("nom") String nom,
                                   @QueryParam("statut") String statut){
    return this.persRest.updatePersonne(id, prenom, nom, statut);
    }
    
  @GET
  @Path("delete/{id}")
  public Personne deletePersonne(@PathParam("id") Long id){
      return this.persRest.deletePersonne(id);
  }

    @GET
    @Path("All")
    @Produces({"application/xml","application/json"})
    public List<Personne> findAllPersonne(){
    return this.persRest.findAllPersonne();
}
    @POST
    @Asynchronous
    public void createPersonne(@Suspended final AsyncResponse asyncResponse, final JAXBElement<Personne> personneJaxb) {
        asyncResponse.resume(doCreatePersonne(personneJaxb));
    }

    private Response doCreatePersonne(JAXBElement<Personne> personneJaxb) {
        Personne personne=personneJaxb.getValue();
        
        try {
            ut.begin();
            em.persist(personne);
            ut.commit();
            
            
        } catch (Exception ex) {
            try {
                ut.rollback();
            } catch (Exception ex1) {
            }
        }
        URI personneUri=uriInfo.getAbsolutePathBuilder().path(personne.getId().toString()).build();
        return Response.created(personneUri).build();
    }

    public PersonneRestImpl getPersRest() {
        return persRest;
    }

    public void setPersRest(PersonneRestImpl persRest) {
        this.persRest = persRest;
    }
    @POST
    @Path("create")
    @Consumes("application/x-www-form-urlencoded")    
    public Personne createPersonne(@FormParam("nom") String nom,
                                    @FormParam("prenom") String prenom, 
                                    @FormParam("statut") String statut){
        return this.persRest.createPersonne(nom, prenom, statut);
    }
  
    @GET
    @Path("getPersonneRes/{id}")
    //@Produces({"application/xml"})
    public Response getPersonneRes(@PathParam("id") Long id){
        Personne personne=this.persRest.getPersonneById(id);
        return Response.ok(personne, MediaType.APPLICATION_JSON).build();
    }
}
