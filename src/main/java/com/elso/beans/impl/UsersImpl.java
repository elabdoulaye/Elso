/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elso.beans.impl;

import com.elso.beans.interfaces.IUsers;
import com.elso.entity.Users;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import com.elso.Utils.HashPasswordGenerator;
/**
 *
 * @author pcci967
 */
@Stateless
//@TransactionManagement(value = TransactionManagementType.BEAN)
public class UsersImpl implements IUsers{

    @PersistenceContext(unitName = "Matable_PU")
    private EntityManager em;
    @Resource
    private UserTransaction ut;
    @Override
    public Users findUser(String username, String password) {
        try {
            //String requete = "SELECT u FROM Users u WHERE u.loginName = :login AND u.password =  :password";
            String pass= new HashPasswordGenerator().generateHash(password);
            String requete = "SELECT * FROM users u WHERE u.login_name ='"+username+"' AND u.password ='"+pass+"'";

            //Query query=this.em.createQuery(requete,Users.class);
            Query query=this.em.createNativeQuery(requete,Users.class);
//            String pass= new HashPasswordGenerator().generateHash(password);
//            query.setParameter("login", username);
//            query.setParameter("password",pass );
            //System.out.println("Requete:"+query.toString());
            return  (Users) query.getSingleResult();
            
        } catch (NoResultException ex) {
            return null;
        }
    }
    
}
