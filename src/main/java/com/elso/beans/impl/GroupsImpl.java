/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elso.beans.impl;

import com.elso.beans.interfaces.IGroups;
import com.elso.entity.Groups;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author pcci967
 */
@Stateless
@TransactionManagement(value = TransactionManagementType.BEAN)
public class GroupsImpl implements IGroups{

    @PersistenceContext(unitName = "Matable_PU")
    private EntityManager em;
    public Groups findGroup(String groupname) {
        
        try {
            String squery = "SELECT g FROM Groups g WHERE g.name =:name";
            return em.createQuery(squery,Groups.class)
                    .setParameter("login", groupname).getSingleResult();
            
        } catch (NoResultException ex) {
            return null;
        }
    }
    
}
