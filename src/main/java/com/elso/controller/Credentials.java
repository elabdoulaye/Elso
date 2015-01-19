/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elso.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;


/**
 *
 * @author yazid
 */
@Named("credentials")
@RequestScoped
public class Credentials {
    // ======================================
    // =             Attributes             =
    // ======================================

    private String login;
    private String password;

    // ======================================
    // =         Getters & setters          =
    // ======================================

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
