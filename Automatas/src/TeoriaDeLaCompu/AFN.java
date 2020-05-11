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
public class AFN {
    private final char[] E;
    private final int Q;
    private final int q0;
    private final int[] F;
    private final int[][][] T;

    public AFN(char[] E, int Q, int q0, int[] F, int[][][] T) {
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

    private int[] Delta(String word, char u) {
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
        int[] tas = T[ExtrasOrden.getIndex(E, u)][i];
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
                System.out.println(a);
                System.err.println("El simbolo \""+a+"\" no pertenece a el Alfabeto");
            }
        }
        return false;
    }
    
    public AFD transform(){
        int q = Q;
        int[] f ={};
        int qT = (int) Math.pow(2,q)-1;
        int[][] t= new int[T.length][qT];
        int[] ge = new int[qT];
        for(int i=0;i<t.length;i++){
            for(int j=0;j<t[i].length;j++){
                int h = j;
                int q2 = q-1;
                t[i][j] = -1;
                System.out.println(""+h+" "+j);
                while(h>=0){
                    int k =(int)Math.pow(2, q2);
                    if(h>=k-1){
                        System.out.println(""); 
                        for (int w = 0; w < T[i][q2].length; w++) {
                            int y = T[i][q2][w];
                            int e;
                            if(y <0){
                                e = 0;
                            }else{
                                e = (int)Math.pow(2,y);
                            }
                            System.out.println("e "+e );
                            if(!hasBinari(t[i][j]+1, e)) t[i][j]+= e;
                            
                            System.out.println("t ij" + t[i][j]);
                        }
                        h-=k;
                        System.out.println("h "+ h);
                        if(ExtrasOrden.Exist(F, q2))f=ExtrasOrden.Add(f, j);
                    }
                    q2--;
                }
                int s = t[i][j];
                System.out.println(" s " + s);
                if(s != -1){
                    System.out.println("sa"+s);
                    ge[s]= -1;
                }
            }
        }
        
        for(int i=0;i<ge.length;i++){
            if(ge[i]!=-1){
                System.out.println("as"+i);
                ge[i] = i;
            }
        }
        ge = ExtrasOrden.RemoveInt(ge, -1);
        ge = ExtrasOrden.RemoveInt(ge, (int)Math.pow(2, q0)-1);
        
        for(int i=0;i<ge.length;i++){
                System.out.println("as"+ge[i]);
        }
       
        
        AFD aFD = removeNoUse(f,t,qT,ge,E,(int)Math.pow(2, q0)-1);
        
        return aFD;
    }
    
    private AFD removeNoUse(int[] f,int[][] t,int q,int[] qe, char[] E,int q0){
        int w = q-qe.length;
        for(int i=0;i<f.length;i++){
            if(f.length==1)break;
            if(ExtrasOrden.Exist(qe, f[i])){
                
                f[i]=-1;
            }
        }
        f = ExtrasOrden.RemoveInt(f, -1);
        /*for(){
            
        }*/
        System.out.println(w+"  "+t[0].length+"  "+ qe.length +" " +q);
        
        /*for(int i = 0){
            
        }*/
        
        qe = ExtrasOrden.RemoveInt(qe, -1);
        qe = ExtrasOrden.RemoveInt(qe, (int)Math.pow(2, q0)-1);
        
        if(qe.length<1){
            return new AFD(E, q, q0, f, t);
        }
        
        return removeNoUse(f, t, q, qe, E, q0);
    }
    
    private boolean hasBinari(int big, int e){
        double a;
        int b;
        int toBig=big;
        do{
         a = Math.log10(toBig)/Math.log10(2);
         b = (int) a;
         int d = (int) Math.pow(2, b);
         if(d==e) return true;
         toBig -=d;
        }while(a-b>0);
        return false;
    }
    
    public boolean isLimbo(int q){
        if(q<0||q>Q)return true;
        int[] qs = {q};
        if(Finish(qs)) return false;
        for(int i =0;i<T.length;i++){
            qs= T[i][q];
            if(Finish(qs)) return false;
            for (int j = 0; j < qs.length; j++) {
                if(qs[j]!= q){
                    if(!isLimbo(qs[j]))return false;
                }
            }
        }
        return true;
    }
}

