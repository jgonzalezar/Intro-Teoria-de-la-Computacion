/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomatasFinitos.subTipesAFD.AFDHerramientasProducto;

import AutomatasFinitos.AFD;
import java.util.ArrayList;

/**
 *
 * @author brandon
 */
public class AFDProductoD extends AFDProducto{
    
    public AFDProductoD(AFD afd1, AFD afd2) {
        super(afd1, afd2);
        ArrayList<Integer> F1 = afd1.getF();
        ArrayList<Integer> F2 = afd2.getF();
        ArrayList<String> Q1 = afd1.getQ();
        ArrayList<String> Q2 = afd2.getQ();
        F = new ArrayList<>();
        for (int i = 0; i < Q1.size(); i++) 
          for (int j = 0; j < Q2.size(); j++)
              if (F1.contains(i) && !(F2.contains(j)))
                  F.add(i*Q2.size()+j);
    }
}
