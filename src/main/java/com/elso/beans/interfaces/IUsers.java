/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elso.beans.interfaces;

import com.elso.entity.Users;
import javax.ejb.Local;

/**
 *
 * @author pcci967
 */
@Local
public interface IUsers {
    
    public Users findUser(String username,String password);
   
}
