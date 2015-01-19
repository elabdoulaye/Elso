/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elso.beans.interfaces;

import javax.ejb.Local;


/**
 *
 * @author pcci967
 */
@Local
public interface IUserGroup {
    public String userGroups(String login_name,String group_name); 
}
