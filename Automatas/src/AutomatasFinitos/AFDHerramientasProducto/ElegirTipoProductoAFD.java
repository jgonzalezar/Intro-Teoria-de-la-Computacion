/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomatasFinitos.AFDHerramientasProducto;

import AutomatasFinitos.AFD;
import java.util.ArrayList;

/**
 *
 * @author brandon
 */
public class ElegirTipoProductoAFD extends AFDProducto{
    
    public ElegirTipoProductoAFD(AFD afd1,AFD afd2, String operacion){
        super(afd1, afd2);
        F = new ArrayList<>();
        String type = operacion;
        switch (type)
        {
            case "union":
                AFDProductoO estadosUnion;
                estadosUnion = new AFDProductoO(afd1, afd2);
                F = estadosUnion.getF();
                break;
            case "interseccion":
                AFDProductoY estadosInter;
                estadosInter = new AFDProductoY(afd1, afd2);
                F = estadosInter.getF();
                break;
            case "diferencia":
                AFDProductoD estadosDif;
                estadosDif = new AFDProductoD(afd1, afd2);
                F = estadosDif.getF();
                break;
            case "diferenciaSimetrica":
                AFDProductoDSim estadosDSim;
                estadosDSim = new AFDProductoDSim(afd1, afd2);
                F = estadosDSim.getF();
                break;
        }
    } 
}
