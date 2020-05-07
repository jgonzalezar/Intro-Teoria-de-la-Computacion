/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creador;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fanat
 */
public class Creador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int i = ReadInt("cantidad maxima de digitos a generar");
        int j =ReadInt("cantidad de elementos del universo");
        char dat[];
        System.out.println("Escriba los elementos del alfabeto y al terminar oprima enter(este no puede ser incluido)");
        String a = s.nextLine();
        int d=0;
        while(a.length()<j){
            a+=s.nextLine();
        }
        dat = replaceRepeat(a.toCharArray());
        
        boolean voids = YesNo("permite elemento vacio?");
        creadorPalabras creadore = new creadorPalabras(i,dat);
        creadore.Crear(voids);
        for(int h = 0; h <j;h++){
            System.out.println(dat[h]);
        }
    }
    
    static private boolean Exist(char[] universe, int b){
        for(int i = 0;i< universe.length;i++){
            if(universe[i]==b)return true;
        }
        return false;
    }
    
    static public char NewInTheUniverse(char[] universe,char a) throws IOException{
        Scanner s = new Scanner(System.in);
        if(Exist(universe, a)){
            System.out.println("El simbolo: "+a+" ya pertenece al universo intente de nuevo");    
            a = s.next().charAt(0);
            return NewInTheUniverse(universe, a);
        }
        return a;
    }
    
    static public char[] replaceRepeat(char[] dat){
        char hey[] = new char[dat.length];
        if(dat.length<=0)return null;
        hey[0]= dat[0];
        for(int i =1;i<dat.length;i++){
            try {
                hey[i]=NewInTheUniverse(hey, dat[i]);
            } catch (IOException ex) {
                Logger.getLogger(Creador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return hey;

    }
    
    static public int ReadInt(String comando){
        System.out.println(comando);
        Scanner s = new Scanner(System.in);
        int i=0;
        boolean t = true;
        while(t){
            try{
                i =Integer.valueOf(s.nextLine().trim());
                if(i>0){
                    t = false;
                }else{
                    System.out.println("el numero dado debe ser mayor que 0");
                }
            }catch(NumberFormatException e){
                System.out.println("Debes dar un Numero unicamente, por favor intente de nuevo");
            }
        }
        return i;
    }

    private static boolean YesNo(String comando) {
        System.out.println(comando + " (Y/N) default: Y");
        while(true){
            try {
                char as = (char) System.in.read();
                switch (as) {
                    case '\n':
                    case 'Y':
                        return true;
                    case 'N':
                        return false;
                    default:
                        System.out.println("por favor responda de nuevo con N/Y");
                        break;
                }
            } catch (IOException ex) {
                System.out.println("try again");
            }
            
        }
    }
}
