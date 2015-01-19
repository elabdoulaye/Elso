/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elso.beans.impl;

import com.elso.Utils.IConstantes;
import com.elso.beans.interfaces.IPersonne;
import com.elso.entity.Personne;
import com.elso.filter.PersonneFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author pcci967
 */
@Stateless
@TransactionManagement(value = TransactionManagementType.BEAN)
public class PersonneImpl implements IPersonne {

    @PersistenceContext(unitName = "Matable_PU")
    private EntityManager em;
    @Resource
    private UserTransaction ut;

    @Override
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

    @Override
    public void updatePersonne(Personne personne) {
        //log.info("Début MAJ");
        try {
            // em.getTransaction().begin();
            ut.begin();
            
            this.em.merge(personne);
            //em.getTransaction().commit();
//            em.setProperty("javax.persistence.cache.storeMode",CacheStoreMode.USE);
            //em.setProperty("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
            ut.commit();
            // this.em.flush();
            //log.info("Fin MAJ");
        } catch (Exception ex) {
            try {
                //em.getTransaction().rollback();
                ut.rollback();
                //log.info("Rollback Transaction MAJ");
            } catch (IllegalStateException ex1) {
                Logger.getLogger(PersonneImpl.class.getName()).log(Level.SEVERE, null, ex1);
                //log.info("Rollback IllegalStateException");
            } catch (SystemException ex1) {
                Logger.getLogger(PersonneImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

    }


    @Override
    public void deletePersonne(Long id) {
//        System.out.println("DeletePersonne:");
//        Map<String, Object> props = new HashMap<String, Object>();
//        props.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
         Personne personne = this.em.find(Personne.class, id);
        //personne = this.em.find(Personne.class, personne.getId(),props);
         System.out.println("CONTAINS deletePersonne: "+em.getEntityManagerFactory().getCache().contains(Personne.class,personne.getId()));
              
        //if (personne != null) {
            try {
                //log.info("Début Delete");
                // em.getTransaction().begin();
                ut.begin();
                personne.setDeleted(IConstantes.DELETED_COLONNE_VALUE);
                this.em.merge(personne);
                //props.put("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH); 
                //em.getTransaction().commit();
                //System.out.println("CONTAINS deletePersonne: "+em.getEntityManagerFactory().getCache().contains(Personne.class,personne.getId()));
//            em.setProperty("javax.persistence.cache.storeMode",CacheStoreMode.USE);
//            em.setProperty("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
                //em.getEntityManagerFactory().getCache().evict(Personne.class, personne.getId());
                ut.commit();

                //this.em.flush();
                //log.info("Fin Delete");
            } catch (Exception ex) {
                try {
                    try {
                        ut.rollback();
                        //log.info("Rollback Delete");
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
                    // log.info("Rollback IllegalStateException");
                }
            }
        //}
    }

    @Override
    public Personne getPersonneById(Long id) {
//        Map<String, Object> props = new HashMap<String, Object>();
//        props.put("javax.persistence.cache.retrieveMode", "USE");
//        Personne personne = this.em.find(Personne.class, id,props);
        Personne personne = this.em.find(Personne.class, id);
       System.out.println("CONTAINS getPersonneById: "+em.getEntityManagerFactory().getCache().contains(Personne.class,id));
        return personne;
    }

    @Override
    public List<Personne> findPersonneListByCriteria(PersonneFilter personneFilter) {

        StringBuffer sbRequest;
        sbRequest = new StringBuffer("Select personne from Personne personne ");
        if (!StringUtils.isEmpty(personneFilter.getNom()) && personneFilter.getNom() != null) {
            sbRequest.append("and personne.nom like '%").append(personneFilter.getNom()).append("%'");
        }
        if (!StringUtils.isEmpty(personneFilter.getPrenom()) && personneFilter.getPrenom() != null) {
            sbRequest.append(" and personne.prenom like '%").append(personneFilter.getPrenom()).append("%'");
        }
        if (!StringUtils.isEmpty(personneFilter.getStatut()) && personneFilter.getStatut() != null) {
            sbRequest.append(" and personne.statut like '%").append(personneFilter.getStatut()).append("%'");
        }
        sbRequest.append(" and personne.deleted=0 ");
        String requete = sbRequest.toString();

        requete = requete.replaceFirst("and", "where");
        System.out.println("Requete " + requete.toString());
        Query query = this.em.createQuery(requete, Personne.class);
        //query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        //query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.BYPASS);
        //query.
        List<Personne> list = query.getResultList();
        return list;

    }

    @Override
    public List<Personne> findPersonneList() {
         StringBuffer sbRequest;
        sbRequest = new StringBuffer("Select personne from Personne personne where personne.deleted=0 ");
        String requete = sbRequest.toString();
        Query query = this.em.createQuery(requete, Personne.class);
        List<Personne> list = query.getResultList();
        return list;

    }

}
