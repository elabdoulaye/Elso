/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.elso.Utils;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

/**
 *
 * @author Elabdoulaye
 */
public class HashPasswordGenerator {
    
    public static String generateHash(String password){
       
        String output=Hashing.sha256().hashString(password, Charsets.UTF_8).toString();
        //System.out.println(output);
        return output;
    }
//    public static void main (String args[]){
//        generateHash("abdou");
//    }
}
