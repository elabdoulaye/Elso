/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elso.rest;

import com.elso.entity.Personne;
import com.sun.org.apache.bcel.internal.classfile.Utility;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.StringTokenizer;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import com.elso.Utils.IConstantes;
/**
 *
 * @author tmp_ndiaye2646
 */
@Provider
@Consumes("personnes/format")
public class PersonneReader implements MessageBodyReader<Personne>{

    @Override
    public boolean isReadable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
    
        return Personne.class.isAssignableFrom(type);
    }

    @Override
    public Personne readFrom(Class<Personne> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, String> mm, InputStream inputStream) throws IOException, WebApplicationException {
    
        String str;
        str = IConstantes.convertStreamToString(inputStream) ;
        StringTokenizer s=new StringTokenizer(str);
        Personne personne=new Personne();
        personne.setDeleted(Integer.parseInt(s.nextToken()));
        personne.setId(Long.parseLong(s.nextToken()));
        personne.setNom(s.nextToken());
        personne.setPrenom(s.nextToken());
        personne.setStatut(s.nextToken());
        return personne;
    }
    
}

