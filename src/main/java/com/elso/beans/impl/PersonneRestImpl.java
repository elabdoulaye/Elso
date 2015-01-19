/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elso.beans.impl;

import com.elso.Utils.IConstantes;
import com.elso.entity.Personne;
import com.elso.filter.PersonneFilter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Elabdoulaye
 */
@Stateless
@TransactionManagement(value = TransactionManagementType.BEAN)
public class PersonneRestImpl {
    @PersistenceContext(unitName = "Matable_PU")
    private EntityManager em;
    @Resource
    private UserTransaction ut;
    
    
    public void addPersonne(Personne personne) {
//        log.info("Début de la transaction ADD");
        //System.out.println("Début de la transaction ADD");
        try {
            ut.begin();
            //em.getTransaction().begin();
            
            this.em.persist(personne);
//            em.setProperty("javax.persistence.cache.storeMode",CacheStoreMode.USE);
            //em.setProperty("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
            // em.getTransaction().commit();
            ut.commit();
            //this.em.flush();
            //log.info("Fin de la transaction ADD");
        } catch (Exception ex) {
            try {
                ut.rollback();
                //em.getTransaction().rollback();
                // log.info("Rollback Transaction ADD");
            } catch (IllegalStateException ex1) {
                Logger.getLogger(PersonneImpl.class.getName()).log(Level.SEVERE, null, ex1);
                //log.info("Rollback IllegalStateException ADD");
            } catch (SystemException ex1) {
                Logger.getLogger(PersonneImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    public Personne updatePersonne(Long id,String prenom,String nom,String statut) {       
         Personne personne = this.em.find(Personne.class, id);
        if (personne == null) {
            throw new IllegalArgumentException("setPersonne id " + id + " not found");
        }
  try {
        ut.begin();
        personne.setNom(nom);
        personne.setPrenom(prenom);
        personne.setStatut(statut);
        this.em.merge(personne);
        ut.commit();

         }
         catch (Exception ex) {
            try {
                ut.rollback();
            } catch (IllegalStateException ex1) {
                Logger.getLogger(PersonneImpl.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (SystemException ex1) {
                Logger.getLogger(PersonneImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
  return personne;
    }
        
    
    

    public Personne getPersonneById(Long id) {
        Personne personne = this.em.find(Personne.class, id);
//       System.out.println("CONTAINS getPersonneById: "+em.getEntityManagerFactory().getCache().contains(Personne.class,id));
        return personne;
    }
   public Personne deletePersonne(Long id) {
        Personne personne = this.em.find(Personne.class, id);      
            try {
                ut.begin();
                personne.setDeleted(IConstantes.DELETED_COLONNE_VALUE);
                this.em.merge(personne);
                ut.commit();
            } catch (Exception ex) {
                try {
                    try {
                        ut.rollback();
                    } catch (SecurityException ex1) {
                        try {
                            ut.rollback();
                        } catch (SecurityException ex2) {
                            Logger.getLogger(PersonneImpl.class.getName()).log(Level.SEVERE, null, ex2);
                        } catch (SystemException ex2) {
                            Logger.getLogger(PersonneImpl.class.getName()).log(Level.SEVERE, null, ex2);
                        }
                        Logger.getLogger(PersonneImpl.class.getName()).log(Level.SEVERE, null, ex1);

                    } catch (SystemException ex1) {
                        Logger.getLogger(PersonneImpl.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } catch (IllegalStateException ex1) {
                    Logger.getLogger(PersonneImpl.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        return personne;
    }
    public List<Personne> findAllPersonne() {

        StringBuffer sbRequest;
        sbRequest = new StringBuffer("Select personne from Personne personne where personne.deleted=0");
        String requete = sbRequest.toString();
        System.out.println("Requete " + requete.toString());
        Query query = this.em.createQuery(requete, Personne.class);
        List<Personne> list = query.getResultList();
        return list;

    }
    
        public List<Personne> AllPersonneByStatut(String statut) {

        StringBuffer sbRequest;
        sbRequest = new StringBuffer("Select personne from Personne personne where personne.deleted=0 and personne.statut=:statut");
        String requete = sbRequest.toString();
        //System.out.println("Requete " + requete.toString());
        Query query = this.em.createQuery(requete, Personne.class);
        query.setParameter("statut", statut);
        List<Personne> list = query.getResultList();
        return list;

    }
    
    public Personne createPersonne(String nom,String prenom,String statut){
        Personne personne=new Personne();
        try {
            
                 ut.begin();
        //personne.setId(id);
        personne.setDeleted(IConstantes.COLONNE_VALUE_ZERO);
        personne.setNom(nom);
        personne.setPrenom(prenom);
        personne.setStatut(statut);
        
        this.em.persist(personne);
        ut.commit();
        }
    catch (Exception ex) {
            try {
                ut.rollback();
            } catch (IllegalStateException ex1) {
                Logger.getLogger(PersonneImpl.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (SystemException ex1) {
                Logger.getLogger(PersonneImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
       return personne;

        
    }
}
