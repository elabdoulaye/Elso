 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elso.controller;

import com.elso.beans.interfaces.IPersonne;
import com.elso.entity.Personne;
import com.elso.filter.PersonneFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.*;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;

/**
 *
 * @author pcci967
 */
@Named("personneCtrl")
@SessionScoped
public class PersonneCtrl implements Serializable {

    @Inject
    private IPersonne Ipersonne;
//    @Inject 
//    private transient Logger logger;
    private Personne personneModel;//=new Personne();
    private PersonneFilter personneFilter;
    private List<Personne> personneList;//=FindAll();
    private List<Personne> filteredPersonne;
    private Long id;

    public PersonneCtrl() {
        //personneModel = new Personne();
    }

    @PostConstruct
    public void init() {
        personneModel = new Personne();
        personneFilter = new PersonneFilter();
        personneList = FindAll();
        //filteredPersonne = new ArrayList<Personne>();
    }
//    public PersonneCtrl() {
//        personneModel = new Personne();
//        personneFilter = new PersonneFilter();
//        personneList = FindAll();
//        filteredPersonne = new ArrayList<Personne>();
//    }

    public void viewPersonne() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 320);

        RequestContext.getCurrentInstance().openDialog("create_1", options, null);
        this.personneModel = new Personne();
    }

    public void editAction(ActionEvent event) {
        this.personneModel = new Personne();
    }

    public void saveAction(ActionEvent event) {
        System.out.println("This is :" + personneModel);
        this.Ipersonne.addPersonne(personneModel);
        String msgInfo = "Enregistrement effectué !!!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msgInfo, ""));
         //personneList = Ipersonne.findPersonneListByCriteria(new PersonneFilter());
        personneList = Ipersonne.findPersonneList();
    }

    public void updateAction(ActionEvent event) {
        this.Ipersonne.updatePersonne(personneModel);
        String msgInfo = "Mise à jour effectuée !!!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msgInfo, ""));
         //personneList = Ipersonne.findPersonneListByCriteria(new PersonneFilter());
        personneList = Ipersonne.findPersonneList();
    }

    public void deleteAction(ActionEvent event) {
        if (personneModel.getId() == null) {

            String personneIdStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("personneId");
            Long personneId = Long.parseLong(personneIdStr);
//            this.personneModel.setId(personneId);
            personneModel = Ipersonne.getPersonneById(personneId);
        }else if (personneModel.getId()!=null) {
            personneModel = new Personne();
            String personneIdStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("personneId");
            int personneId = Integer.parseInt(personneIdStr);
            personneModel = Ipersonne.getPersonneById(new Long(personneId));
        }

        this.Ipersonne.deletePersonne(personneModel.getId());
        //personneModel=new Personne();
        //RequestContext.getCurrentInstance().reset("frmPersonneList:tbPersonneList:globalFilter");
        String msgInfo = "Suppression effectuée avec succés!!!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msgInfo, ""));
        //personneFilter = new PersonneFilter();
        //personneList = Ipersonne.findPersonneListByCriteria(new PersonneFilter());
        personneList = Ipersonne.findPersonneList();
        //personneModel=null;
        //id=new Long(0);
    }

    public void searchAction(ActionEvent event) {
        this.personneList=null;
        this.personneList = Ipersonne.findPersonneListByCriteria(personneFilter);
        RequestContext req = RequestContext.getCurrentInstance();
        req.addCallbackParam("emptyRecords", this.personneList == null || this.personneList.isEmpty());
        //personneFilter=new PersonneFilter();
    }

    public void getAction(ActionEvent event) {
        //personneModel=new Personne();
        if (personneModel.getId() == null) {
            String personneIdStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("personneId");
            int personneId = Integer.parseInt(personneIdStr);
//            String msgInfo = "Suppression effectuée avec succés!!!";
//       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msgInfo, ""));
            personneModel = Ipersonne.getPersonneById(new Long(personneId));
        } else if (personneModel.getId()!=null) {
            personneModel = new Personne();
            String personneIdStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("personneId");
            int personneId = Integer.parseInt(personneIdStr);
            personneModel = Ipersonne.getPersonneById(new Long(personneId));
        }
        //this.personneModel = Ipersonne.getPersonneById(personneModel.getId());
        // id=personneModel.getId();
        // this.personneModel = Ipersonne.getPersonneById(id);
    }

    public final List<Personne> FindAll() {
        List<Personne> personneList1;
        //personneList = Ipersonne.findPersonneListByCriteria(new PersonneFilter());
        personneList1 = Ipersonne.findPersonneList();
        return personneList1;
    }

    public List<Personne> getFilteredPersonne() {
        return filteredPersonne;
    }

    public void setFilteredPersonne(List<Personne> filteredPersonne) {
        this.filteredPersonne = filteredPersonne;
    }

    public Personne getPersonneModel() {
        return personneModel;
    }

    public void setPersonneModel(Personne personneModel) {
        this.personneModel = personneModel;
    }

    public IPersonne getIpersonne() {
        return Ipersonne;
    }

    public void setIpersonne(IPersonne Ipersonne) {
        this.Ipersonne = Ipersonne;
    }

    public PersonneFilter getPersonneFilter() {
        return personneFilter;
    }

    public void setPersonneFilter(PersonneFilter personneFilter) {
        this.personneFilter = personneFilter;
    }

    public List<Personne> getPersonneList() {
        return Ipersonne.findPersonneListByCriteria(personneFilter);
    }

    public void setPersonneList(List<Personne> personneList) {
        this.personneList = personneList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, Object> onFilter(AjaxBehaviorEvent event) {
        DataTable table = (DataTable) event.getSource();
        List<Personne> obj;
        filteredPersonne = table.getFilteredValue();
        Map<String, Object> filters = table.getFilters();
        return filters;
    }
}
