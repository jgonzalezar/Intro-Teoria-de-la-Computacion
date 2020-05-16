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
public class AFNL {
    private final char[] E;
    private final int Q;
    private final int q0;
    private final int[] F;
    private final int[][][] T;

    public AFNL(char[] E, int Q, int q0, int[] F, int[][][] T) {
        this.E = E;
        this.Q = Q;
        this.q0 = q0;
        this.F = F;
        this.T = T;
    }
    
    public boolean Delta(String word){ // inicial la que llama el usuario
        if(word==null||word.length()==0){            
            return ExtrasOrden.Exist(F,q0);
        }else if(word.length()==1){            
            return Delta(word.charAt(0));
        }
        return Finish(Delta(word.substring(0, word.length()-1),word.charAt(word.length()-1)));
    }
    
    public boolean Delta(char[] word){ // inicial la que llama el usuario
        return Delta(Arrays.toString(word));
    }

    private int[] Delta(String word, char u) { // recuersiva del ultimo
        
                //q8 a q9
                //q3 l
                //q1 o
                //  h
        if(word.length()==0){
            int[] a = {q0};
            return Delta(a,u);
        }
        int[] det = Delta(word.substring(0, word.length()-1),word.charAt(word.length()-1));
        return Delta(det,u);
    }

    private boolean Delta(char u) {
        int[] q={q0};
        q = Delta(q,u);
        return Finish(q);
    }

    private int[] Delta(int[] q, char u) {
        int[] q1 = { -1 };
        if(q[0]==-1){
            return q1;
        }
        
        for (int i = 0; i<q.length;i++) {
            int[] q2 = Delta(q[i],u);
            int[][] q3 = new int[2][];
            q3[0]= q1;
            q3[1]= q2;
            q1 = ExtrasOrden.Group(q3);
        }
       /* for (int i = 0; i<q1.length;i++) {
            System.out.println("delta in" + q1[i]);
        }*/
        q1 = ExtrasOrden.RemoveInt(q1, -1);
        if(q1.length==0){
            int[] q2 = { -1 };
            return q2;
        }
        return q1;
    }

    private int[] Delta(int i, char u) {
        if(ExtrasOrden.getIndex(E, u)==-1){
            //System.err.println("El simbolo "+u+"no pertenece a el Alfabeto");
            int[] as = {-u};
            return as;
        }
        if(i<0){
            int[] as = {-1};
            return as;
        }
        int[] tas = T[ExtrasOrden.getIndex(E, u)+1][i];
        int[] Lamda = T[0][i];
        int[] Lamda2[] = new int[Lamda.length+1][];
        Lamda2[0]=tas;
        for(int j =0; j< Lamda.length;j++){
            Lamda2[j+1] = Delta(Lamda[j],u);
        }
        tas = ExtrasOrden.Group(Lamda2);
        return tas;
    }

    private boolean Finish(int[] q) {
        q = ExtrasOrden.RepeatDel(q);
        for (int i = 0; i<q.length;i++) {
            if(ExtrasOrden.Exist(F,q[i])){
                
                return true;
            }
            if(q[i]<-1){
                char a = (char)-q[i];
                System.err.println("El simbolo \""+a+"\" no pertenece a el Alfabeto");
            }
        }
        return false;
    }
    
    public AFN tranform(){
        int[][][] t = new int [E.length][][];
        for(int i=0;i<E.length;i++){
            for (int j = 0; j < Q; j++) {
                int[] e[] = new int[2][];
                e[0]= Delta(j,E[i]);
                e[1] = T[i][j];
                
                t[i][j]=ExtrasOrden.Group(e);
            }
        }
        return new AFN(E, Q, q0, F, t);
    }
    
}
