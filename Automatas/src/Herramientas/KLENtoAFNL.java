/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas;

import AutomatasFinitos.AFD;
import AutomatasFinitos.AFNL;
import AutomatasFinitos.Alfabeto;
import java.util.ArrayList;
import java.util.Arrays;
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
        
        Q= new ArrayList<>();
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
        
        TransitionAFNL tras = new TransitionAFNL(Q.size());
        asd = result.get("Separations");
        reset = new HashSet<>(asd);
        asd.clear();
        asd.addAll(reset);
        for (String asd1 : asd) {
            String[] separos = asd1.split(":");
            String estado1=separos[0];
            String[] sep = separos[1].split(">");
            char caracter = sep[0].charAt(0);
            String[] dos = sep[1].split(";");
            int[] hey = new int[dos.length];
            for (int i = 0; i < dos.length; i++) {
                hey[i]=Q.indexOf(dos[i]);
            }
            tras.add(caracter, Q.indexOf(estado1), hey);
        }
        
        Delta = tras;
        
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
            int asd = cer;
            for (int i = pare+1; i < word.length(); i++) {
                if(word.charAt(i)=='('){
                    cont++;
                }
                if(word.charAt(i)==')'){
                    if(cont==0){
                        asd=i;
                    }else{
                        cont--;
                    }
                }
            }
            char ex;
            if(asd+1==word.length()){
                ex=' ';
            }else{
                ex=word.charAt(asd+1);
            }
            char next2=' ';
            if(asd+2<word.length()){
                next2=word.charAt(asd+2);
            }
            int dos=asd+1;
            if(ex=='*'||ex=='+'&&next2=='U'){
                
            }
            String uno = word.substring(1,asd);
            String dod = word.substring(asd+1);
            HashMap<String,ArrayList<String>> cad1= evalu(uno);
            HashMap<String,ArrayList<String>> cad2=evalu(dod);
            
        }else if(uni==-1||pare==-1){
            if(uni==-1&&pare==-1){
                for (int i = 0; i < word.length(); i++) {
                    char d = word.charAt(i);
                    char e;
                    if(i+1==word.length()){
                        e=' ';
                    }else{
                        e = word.charAt(i+1);
                    }
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
                            String name0=i+""+d+"0";
                            String name01=i+""+d+"01";
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
                String uno = word.substring(0,pare);
                String dod = word.substring(pare);
                HashMap<String,ArrayList<String>> cad1= evalu(uno);
                HashMap<String,ArrayList<String>> cad2=evalu(dod);
                cad.get("Sigma").addAll(cad1.get("Sigma"));
                cad.get("Sigma").addAll(cad2.get("Sigma"));
                Set<String> reset = new HashSet<>(cad.get("Sigma"));
                cad.get("Sigma").clear();
                cad.get("Sigma").addAll(reset);
                cad1.get("States").forEach((string) -> {
                    cad.get("States").add("1"+string);
                });
                cad2.get("States").forEach((string) -> {
                    cad.get("States").add("2"+string);
                });
                reset = new HashSet<>(cad.get("States"));
                cad.get("States").clear();
                cad.get("States").addAll(reset);
                cad.get("States").add("U0");
                
                cad.get("ini").add("U0");
                
                cad1.get("final").forEach((string) -> {
                    cad.get("Separations").add("1"+string+":$>"+cad2.get("ini").get(0));
                });
                cad2.get("final").forEach((string) -> {
                    cad.get("final").add("2"+string);
                });
                reset = new HashSet<>(cad.get("final"));
                cad.get("final").clear();
                cad.get("final").addAll(reset);
                
                cad.get("Separations").add("U0:$>"+"1"+cad1.get("ini").get(0));
                for (String asd1 : cad1.get("Separations")) {
                    String[] separos = asd1.split(":");
                    String estado1 = separos[0];
                    String[] sep = separos[1].split(">");
                    char caracter = sep[0].charAt(0);
                    String[] dos = sep[1].split(";");
                    String ddd="";
                    for (String aDo : dos) {
                        ddd+="1"+aDo+";";
                    }
                    cad.get("Separations").add("1"+estado1+":"+caracter+">"+ddd.substring(0, ddd.length()-1));
                }
                for (String asd1 : cad2.get("Separations")) {
                    String[] separos = asd1.split(":");
                    String estado1 = separos[0];
                    String[] sep = separos[1].split(">");
                    char caracter = sep[0].charAt(0);
                    String[] dos = sep[1].split(";");
                    String ddd="";
                    for (String aDo : dos) {
                        ddd+="2"+aDo+";";
                    }
                    cad.get("Separations").add("2"+estado1+":"+caracter+">"+ddd.substring(0, ddd.length()-1));
                }
            }else if(pare==-1){
                String uno = word.substring(0,uni);
                String dod = word.substring(uni+1);
                HashMap<String,ArrayList<String>> cad1= evalu(uno);
                HashMap<String,ArrayList<String>> cad2=evalu(dod);
                cad.get("Sigma").addAll(cad1.get("Sigma"));
                cad.get("Sigma").addAll(cad2.get("Sigma"));
                Set<String> reset = new HashSet<>(cad.get("Sigma"));
                cad.get("Sigma").clear();
                cad.get("Sigma").addAll(reset);
                cad1.get("States").forEach((string) -> {
                    cad.get("States").add("1"+string);
                });
                cad2.get("States").forEach((string) -> {
                    cad.get("States").add("2"+string);
                });
                reset = new HashSet<>(cad.get("States"));
                cad.get("States").clear();
                cad.get("States").addAll(reset);
                cad.get("States").add("U0");
                
                cad.get("ini").add("U0");
                
                cad1.get("final").forEach((string) -> {
                    cad.get("final").add("1"+string);
                });
                cad2.get("final").forEach((string) -> {
                    cad.get("final").add("2"+string);
                });
                reset = new HashSet<>(cad.get("final"));
                cad.get("final").clear();
                cad.get("final").addAll(reset);
                
                cad.get("Separations").add("U0:$>"+"1"+cad1.get("ini").get(0)+";2"+cad2.get("ini").get(0));
                for (String asd1 : cad1.get("Separations")) {
                    String[] separos = asd1.split(":");
                    String estado1 = separos[0];
                    String[] sep = separos[1].split(">");
                    char caracter = sep[0].charAt(0);
                    String[] dos = sep[1].split(";");
                    String ddd="";
                    for (String aDo : dos) {
                        ddd+="1"+aDo+";";
                    }
                    cad.get("Separations").add("1"+estado1+":"+caracter+">"+ddd.substring(0, ddd.length()-1));
                }
                for (String asd1 : cad2.get("Separations")) {
                    String[] separos = asd1.split(":");
                    String estado1 = separos[0];
                    String[] sep = separos[1].split(">");
                    char caracter = sep[0].charAt(0);
                    String[] dos = sep[1].split(";");
                    String ddd="";
                    for (String aDo : dos) {
                        ddd+="2"+aDo+";";
                    }
                    cad.get("Separations").add("2"+estado1+":"+caracter+">"+ddd.substring(0, ddd.length()-1));
                }
            }
        }else if(uni<pare){
            
        }else{
            
        }
        
        cad.get("Sigma").remove("$");
        return cad;
    }
    
    public static void main(String[] args) {
        AFNL sss  = new KLENtoAFNL("ab*aUcUa");
        //AFD s = sss.AFN_LambdaToAFD();
        
        
        System.out.println(sss.procesarCadena("a"));
        System.out.println(sss.procesarCadena("c"));
        System.out.println(sss.procesarCadena("aa"));
        System.out.println(sss.procesarCadena("abbba"));
        
        System.out.println(sss.toString());
    }
}

