/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TeoriaDeLaCompu;

import java.util.Arrays;

/**
 *
 * @author fanat
 */
public class AFD {
    public final char[] E;
    public final int Q;
    public final int q0;
    public final int[] F;
    public final int[][] T;

    public AFD(char[] E, int Q, int q0, int[] F, int[][] T) {
        this.E = E;
        this.Q = Q;
        this.q0 = q0;
        this.F = F;
        this.T = T;
    }
    
    public boolean Delta(String word){
        if(word==null||word.length()==0){            
            return ExtrasOrden.Exist(F,q0);
        }else if(word.length()==1){
            return Delta(word.charAt(0));
        }
        return Finish(Delta(word.substring(0, word.length()-1),word.charAt(word.length()-1)));
    }
    
    public boolean Delta(char[] word){
        return Delta(Arrays.toString(word));
    }

    private int Delta(String word, char u) {
        if(word.length()==0){
            return Delta(q0,u);
        }
        int det = Delta(word.substring(0, word.length()-1),word.charAt(word.length()-1));
        return Delta(det,u);
    }

    private boolean Delta(char u) {
        int q = Delta(q0,u);
        return Finish(q);
    }

    private int Delta(int i, char u) {
        if(ExtrasOrden.getIndex(E, u)==-1){
            //System.err.println("El simbolo "+u+"no pertenece a el Alfabeto");
            int as = -u;
            return as;
        }
        if(i<0){
            int as = -1;
            return as;
        }
        int tas = T[ExtrasOrden.getIndex(E, u)][i];
        return tas;
    }

    private boolean Finish(int q) {
        if(ExtrasOrden.Exist(F,q)){        
            return true;
        }
        if(q<-1){
            char a = (char)-q;
            System.out.println(a);
            System.err.println("El simbolo \""+a+"\" no pertenece a el Alfabeto");
        }
        return false;
    }
    
    
}
