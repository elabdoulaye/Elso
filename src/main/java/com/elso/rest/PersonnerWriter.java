/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elso.rest;

import com.elso.entity.Personne;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author tmp_ndiaye2646
 */
@Provider
@Produces("personnes/format")
public class PersonnerWriter implements MessageBodyWriter<Personne>{

    @Override
    public boolean isWriteable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
    return Personne.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(Personne personne, Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
       return personne.getId()+ 1 + personne.getNom().length() + 1 + personne.getPrenom().length() + 1 + personne.getStatut().length();
    }

    @Override
    public void writeTo(Personne personne, Class<?> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, Object> mm, OutputStream out) throws IOException, WebApplicationException {
        out.write(personne.getDeleted());
        out.write('/');
        out.write(personne.getId().intValue());
        out.write('/');
        out.write(personne.getNom().getBytes());
        out.write('/');
        out.write(personne.getPrenom().getBytes());
        out.write('/');
        out.write(personne.getStatut().getBytes());
    }
    
}
