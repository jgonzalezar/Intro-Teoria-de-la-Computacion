/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomatasFinitos.subTipesAFD;
import AutomatasFinitos.AFD;
import java.util.ArrayList;
/**
 *
 * @author Nath_Uribe
 */
public class AFDComplemento extends AFD{
    
    /**
     * constructor para la clase
     * @param afd AFD base 
     */
    
    public AFDComplemento(AFD afd){
        this.Q=afd.getQ();
        ArrayList<Integer> estadosRechazo = new ArrayList<>();
        for(int i=0;i<Q.size();i++){
            if(!afd.getF().contains(i)){
                estadosRechazo.add(i);
                
            }
        }
        
        this.Delta=afd.getDelta();
        this.F=estadosRechazo;
        this.Sigma=afd.getSigma();
        this.q0=afd.getQ0();
    }
     
    
}