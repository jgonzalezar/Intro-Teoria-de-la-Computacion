/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas;

import AutomatasFinitos.AFNL;
import AutomatasFinitos.Alfabeto;
import static Herramientas.KLENtoAFNL.Lectura.vac;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author fanat
 */
public class KLENtoAFNL extends AFNL{
    
    public KLENtoAFNL(String lenguaje) {
        HashMap<String,ArrayList<String>> result = evalu(lenguaje);
        ArrayList<String> asd = result.get("Sigma");
        
        Set<String> reset = new HashSet<>(asd);
        asd.clear();
        asd.addAll(reset);
        char[] sigmas = new char[asd.size()];
        for (int i = 0; i < asd.size(); i++) {
            sigmas[i]=asd.get(i).charAt(0);
        }
        
        Sigma = new Alfabeto(sigmas);
        asd = result.get("States");
        reset = new HashSet<>(asd);
        asd.clear();
        asd.addAll(reset);
        
        Q.addAll(asd);
        
        asd = result.get("final");
        reset = new HashSet<>(asd);
        asd.clear();
        asd.addAll(reset);
        
        q0=Q.indexOf(result.get("ini").get(0));
        
        ArrayList<Integer> asds = new ArrayList<>();
        for (int i = 0; i < asd.size(); i++) {
            asds.add(Q.indexOf(asd.get(i)));
        }
        F=asds;
        
        Transition tras = new Transition();
        
        
    }
    
    private HashMap<String,ArrayList<String>> evalu(String word){
        HashMap<String,ArrayList<String>> cad = new HashMap<>();
        cad.put("Sigma", new ArrayList<>());
        cad.put("final", new ArrayList<>());
        cad.put("States", new ArrayList<>());
        cad.put("Separations", new ArrayList<>());
        cad.put("ini", new ArrayList<>());
        cad.put("conec", new ArrayList<>());
        int pare = word.indexOf("(");
        int uni = word.indexOf("U");
        int cer = word.indexOf(")");
        int cont = 0;
        if(pare==0){
            for (int i = pare+1; i < cer; i++) {
                if(word.charAt(i)=='('){
                    cont++;
                }
            }
        }else if(uni==-1||pare==-1){
            if(uni==-1&&pare==-1){
                for (int i = 0; i < word.length(); i++) {
                    char d = word.charAt(i);
                    char e = word.charAt(i+1);
                    cad.get("Sigma").add(d+"");
                    switch (e) {
                        case '*':
                            String name=i+""+d+""+""+e+"";
                            cad.get("States").add(name);
                            cad.get("Separations").add(name+":"+d+">"+name);
                            cad.get("final").add(name);
                            cad.get("conec").add(name+":"+name);
                            if(i==0){
                                cad.get("ini").add(name);
                            }
                            i++;
                            break;
                        case '+':
                            String name1=i+""+d+""+""+e+"1";
                            String name2=i+""+d+""+""+e+"2";
                            cad.get("States").add(name1);
                            cad.get("States").add(name2);
                            cad.get("Separations").add(name1+":"+d+">"+name2);
                            cad.get("Separations").add(name2+":"+d+">"+name2);
                            cad.get("final").add(name2);
                            cad.get("conec").add(name1+":"+name2);
                            if(i==0){
                                cad.get("ini").add(name1);
                            }
                            i++;
                            break;
                        default:
                            String name0=i+""+d+""+""+e+"0";
                            String name01=i+""+d+""+""+e+"01";
                            cad.get("States").add(name0);
                            cad.get("States").add(name01);
                            cad.get("Separations").add(name0+":"+d+">"+name01);
                            cad.get("final").add(name01);
                            cad.get("conec").add(name0+":"+name01);
                            if(i==0){
                                cad.get("ini").add(name0);
                            }
                            break;
                    }
                    
                }
                ArrayList<String> asd = cad.get("conec");
                for (int i = 0; i < asd.size()-1; i++) {
                    String asd1 = asd.get(i);
                    String asd2 = asd.get(i+1);
                    String in = asd1.split(":")[1];
                    String go = asd2.split(":")[0];
                    cad.get("Separations").add(in+":$>"+go);
                }
                
                String asdd = cad.get("final").get(cad.get("final").size()-1);
                cad.get("final").clear();
                cad.get("final").add(asdd);
                
            }else if(uni==-1){
                
            }else if(pare==-1){
                
            }
        }else if(uni<pare){
            
        }else{
            
        }
        
        
        return cad;
    }
    
    
}

