/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creador;

import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *
 * @author fanat
 */
public class creadorPalabras {

    private int cantidad;
    private char[] datos;
    public creadorPalabras(int i,char[] datos) {
        cantidad=i;
        this.datos = datos;
    }
    
    public void Crear(boolean voids){
        FileWriter fichero1 = null;
        PrintWriter pw1 = null;
        try
        {
            String name = list(datos);
            fichero1 = new FileWriter("src/CombinacionAlphabetos/E_"+name+cantidad+".txt");
            pw1 = new PrintWriter(fichero1);
            pw1.println("");
            char[] bin = null;
            for (int i = 0; i < cantidad; i++){
                System.out.println(i);
                if(bin == null){
                    char[] din = sumBin(bin);
                    pw1.println(list(din)+" ");
                    bin = din;
                }else{
                    int cant = bin.length;
                    while(cant == bin.length){
                        char[] din = sumBin(bin);
                        pw1.println(list(din)+" ");
                        bin = din;
                    }
                }
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero1)
              fichero1.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
          
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
    private char[] sumBin(char[] Bin){
        
        if(Bin == null){
            char[] din = new char[1];
            din[0]= datos[0];
            return din;
        }
        int tam = Bin.length;
        boolean extra=true;
        char[] din = new char[Bin.length];
        for(int i = 0; i< Bin.length;i++){
            extra = ((din[i]=getChar(extra,Bin[i])) == datos[0]) && extra;
        }
        
        if(extra){
            char[] ed = new char[din.length+1];
            for(int i=0;i<din.length;i++){
                ed[i]=din[i];
            }
            ed[din.length]=datos[0];
            
            din = ed;
        }
        
        return din;
    }
    
    private char getChar(boolean e,char hey){
        if(!e){
            return hey;
        }
        if(datos[datos.length-1]==hey){
            return datos[0];
        }
        for(int i=0; i< datos.length-1;i++){
            if(datos[i]==hey){
                return datos[i+1];
            }
        }
        return (char) 0;
    }
    
    private String list(char[] list){
        String list2="";
        for(int i=0; i<list.length;i++){
            list2+=list[i];
        }
        return list2;
    }
}
