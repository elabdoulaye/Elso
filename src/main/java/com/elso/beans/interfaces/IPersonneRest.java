/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elso.beans.interfaces;

import com.elso.entity.Personne;
import java.util.List;
import javax.ejb.Local;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Elabdoulaye
 */
@Local
public interface IPersonneRest {
   public Response createPersonne(JAXBElement <Personne>personneJaxb);
   public Personne getPersonneById(@PathParam("id") Long id);
   public List<Personne> PersonneByStatut(@PathParam("statut") String statut);
}
