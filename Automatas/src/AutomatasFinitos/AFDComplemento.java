/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomatasFinitos;
import java.util.ArrayList;
/**
 *
 * @author Nath_Uribe
 */
public class AFDComplemento extends AFD{
    
    public AFDComplemento(AFD afd){
        ArrayList<Integer> estadosRechazo = null;
        for(int i=0;i<Q.size();i++){
            if(!F.contains(i)){
                estadosRechazo.add(i);
                
            }
        }
        
        this.Delta=afd.Delta;
        this.F=estadosRechazo;
        this.Q=afd.Q;
        this.Sigma=afd.Sigma;
        this.q0=afd.q0;
    }
     
    
}