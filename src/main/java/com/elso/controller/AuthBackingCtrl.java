/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elso.controller;

import com.elso.Utils.HashPasswordGenerator;
import com.elso.beans.interfaces.IGroups;
import com.elso.beans.interfaces.IUserGroup;
import com.elso.beans.interfaces.IUsers;
import com.elso.entity.Users;
import java.io.Serializable;
import java.security.AccessControlContext;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.Principal;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pcci967
 */
@Named("authBackingCtrl")
@SessionScoped
public class AuthBackingCtrl implements Serializable {

    // private static Logger log = Logger.getLogger(AuthBackingCtrl.class.getName());
    private String userName;
    private String password;
    @Inject
    private IUsers user;
    @Inject
    private IGroups group;
    @Inject
    private IUserGroup usergroup;
    @Inject
    private Credentials credential;
    //@Resource 
//   @Inject 
//   @SessionScoped 
//    @Resource
//    private transient LoginContext loginContext;
//    @Resource
//    SessionContext ctx;
    //Pour loginContext utilisé @Resource Au lieu de @Inject depuis JEE7 http://docs.oracle.com/javaee/7/tutorial/doc/cdi-adv004.htm#CJGHGDBA
//    public AuthBackingCtrl(LoginContext loginContext, Credentials credential) {
//        this.loginContext = loginContext;
//        this.credential = credential;
//    }

    public AuthBackingCtrl() {
    }

    public String loginAction() {

        FacesContext context = FacesContext.getCurrentInstance();
        ////HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        String url = null;
        System.out.println("User Login: "+credential.getLogin());
        System.out.println("User Password: "+credential.getPassword());
        Users users;
        users = this.user.findUser(credential.getLogin(), credential.getPassword());
       //System.out.println("User Trouvé: "+users.getLoginName());
        //if (users!=null){

          try {
        request.login(credential.getLogin(), HashPasswordGenerator.generateHash(credential.getPassword()));
         //request.login(credential.getLogin(),credential.getPassword());              
         ////loginContext.login();
        } catch (ServletException ex) {
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login et/ou mot de passe invalide!", null));
        }
     // //Set<Principal> rolePrincipals = loginContext.getSubject().getPrincipals(Principal.class);
        //Principal principal=request.getUserPrincipal();
        //System.out.println("Principals="+principal);
        // String role = rolePrincipals.iterator().next().getName();
        if (request.isUserInRole("admins")) {
        url= "/View/Personne/list.jsf";
        }
         else
          if (request.isUserInRole("users")) {
             url= "/View/Users/list.jsf";
         }
        //}
//  Groups groupname=this.group.findGroup(role);
//  String usergroupname;
//      usergroupname = this.usergroup.userGroups(user.getLoginName(), groupname);
        // Get the passed in subject from the DoAs
//        AccessControlContext context1 = AccessController.getContext();
//        Subject subject = Subject.getSubject(context1);
//        if (subject == null) {
//            throw new AccessControlException("Denied");
//        }
       //
        // Iterate through the principal set looking for joeuser.  If
        // he is not found,
//        Set principals = subject.getPrincipals();
//        Iterator iterator = principals.iterator();
//        while (iterator.hasNext()) {
//            PrincipalImpl principal = (PrincipalImpl) iterator.next();
//            if (principal.getName().equals(users.getUserName())) {
//                url = "/View/Personne/list.jsf?faces-redirect=true";
//                return url;
//            }
//        }
//        throw new AccessControlException("Denied");
//url = "/View/Personne/list.jsf?faces-redirect=true";
               return url;
    }

  //}
    // We can use things like: req.isUserInRole("ADMIN");
    //}
    public String logout() {
        String result = "/index?faces-redirect=true";

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            request.logout();
        } catch (ServletException e) {
            //log.log(Level.SEVERE, "Failed to logout user!", e);
            result = "/loginError?faces-redirect=true";
        }

        return result;
    }

    public String logoutBis() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext
                = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        session.invalidate();
        return "/login?faces-redirect=true";
    }

public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Credentials getCredential() {
        return credential;
    }

    public void setCredential(Credentials credential) {
        this.credential = credential;
    }

//    public IUsers getUser() {
//        return user;
//    }
//
//    public void setUser(IUsers user) {
//        this.user = user;
//    }

    public IGroups getGroup() {
        return group;
    }

    public void setGroup(IGroups group) {
        this.group = group;
    }

    public IUserGroup getUsergroup() {
        return usergroup;
    }

    public void setUsergroup(IUserGroup usergroup) {
        this.usergroup = usergroup;
    }

//    public LoginContext getLoginContext() {
//        return loginContext;
//    }
//
//    public void setLoginContext(LoginContext loginContext) {
//        this.loginContext = loginContext;
//    }
  
}
