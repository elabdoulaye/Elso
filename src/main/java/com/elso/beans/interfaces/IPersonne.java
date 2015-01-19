/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.elso.beans.interfaces;

import com.elso.entity.Personne;
import com.elso.filter.PersonneFilter;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author pcci967
 */
@Local
public interface IPersonne {
  public void addPersonne(Personne personne);
  public void updatePersonne(Personne personne);
  public Personne getPersonneById(Long id);
  public List<Personne> findPersonneListByCriteria(PersonneFilter personneFilter);
    public List<Personne> findPersonneList();
    public void deletePersonne(Long id);
  
}
