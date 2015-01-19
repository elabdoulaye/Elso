/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elso.beans.impl;

import com.elso.beans.interfaces.IUserGroup;
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
public class UserGroupImpl implements IUserGroup{

    @PersistenceContext(unitName = "Matable_PU")
    private EntityManager em;
    @Override
    public String userGroups(String login_name, String group_name) {
        
        try {
            String squery = "SELECT ug.group_name FROM users_groups ug WHERE g.login_name =:loginname and ug.group_name=groupname";
            return em.createQuery(squery,Groups.class)
                    .setParameter("loginname", login_name)
                    .setParameter("groupname", group_name)
                    .getSingleResult().toString();
            
        } catch (NoResultException ex) {
            return null;
        } 

     
    }
    
}
