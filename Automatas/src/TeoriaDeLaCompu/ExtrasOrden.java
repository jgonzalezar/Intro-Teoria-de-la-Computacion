/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TeoriaDeLaCompu;

/**
 *
 * @author fanat
 */
public class ExtrasOrden {
    
    static public int[] Add(int[] f, int s){
        if(Exist(f, s))return f;
        int[] F = new int[f.length+1];
        for (int i = 0; i < f.length; i++) {
            F[i]=f[i];
        }
        F[f.length]=s;
        return F;
    }
    static public int[] RepeatDel(int[] F){
        if(F.length<=1){
            return F;
        }
        int[] F2 = new int[F.length];
        F2[0] = F[0];
        for (int i = 1; i < F2.length; i++) {
            F2[i]=-1;
        }
        for (int i = 1; i < F2.length; i++) {
            if(Exist(F2,F[i])){
                F2[i]=-1;
            }else{
                F2[i]=F[i];
            }
        }
        F2 = RemoveInt(F2, -1);
        return F2;
    }
/*
    private int[] orderList(int[] F){        
        return F;
    }*/
    
    static public boolean Exist(int[] A,int b){
        for(int i = 0; i<A.length;i++){
            if(A[i]==b){
                return true;
            }
        }
        return false;
    }
    
    static public boolean Exist(char[] A,char b){
        for(char a : A){
            if(a==b){
                return true;
            }
        }
        return false;
    }
    
    static public int[] Group(int[][] a){
        int[] q = new int[0];
        for(int i = 0; i <a.length;i++){
            int as =0;
            if(a[i]!=null)as=a[i].length;
            int bs =q.length;
            int[] q2 = new int[bs+as];
            for(int j = 0;j<q.length;j++){
                q2[j]=q[j];
            }
            for(int j =q.length; j<q.length+as;j++){
                q2[j]=a[i][j-q.length];
            }
            q=q2;
        }
        return q;
    }
    
    /*static public int[] Group(int[] a){
        int[] q = new int[0];
        for(int i = 0; i <a.length;i++){
            int as =0;
            if(a[i]!=null)as=a[i].length;
            int bs =q.length;
            int[] q2 = new int[bs+as];
            for(int j = 0;j<q.length;j++){
                q2[j]=q[j];
            }
            for(int j =q.length; j<q.length+as;j++){
                q2[j]=a[i][j-q.length];
            }
            q=q2;
        }
        return q;
    }*/
    
    static public int[] RemoveInt(int[] a,int b){
        int as= 0;
        int[] ad = new int[a.length];
        for(int i = 0; i<a.length;i++){
            if(a[i]!=b){
                ad[as]=a[i]; 
                as++;
            }
        }
        int[] aw = new int[as];
        for(int i = 0; i<as;i++){
            aw[i]=ad[i];
        }
        return aw;
    }
    
    static public boolean Exist(String[] A,String b){
        for(String a : A){
            if(a == null ? b == null : a.equals(b)){
                return true;
            }
        }
        return false;
    }
    
    static public int getIndex(int[] E, int a){
        for(int i =0;i<E.length;i++){
            if(E[i]==a){
                return i;
            }
        }
        return -1;
    }
    
     static public int getIndex(char[] E, char a){
        for(int i =0;i<E.length;i++){
            if(E[i]==a){
                return i;
            }
        }
        return -1;
    }
    
    static public int Extra(int Q, int[] F){
       /* for(){
            
        }*/return -1;
    }
    
}
